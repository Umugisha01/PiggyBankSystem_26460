package com.piggybank.service;

import com.piggybank.dto.*;
import com.piggybank.model.*;
import com.piggybank.repository.*;
import com.piggybank.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TwoFactorTokenRepository twoFactorTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    private final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Location location = null;
        if (request.getLocationId() != null) {
            location = locationRepository.findById(request.getLocationId())
                    .orElseThrow(() -> new RuntimeException("Location not found"));
        }

        User user = new User(request.getFirstName(), request.getLastName(), 
                request.getEmail(), passwordEncoder.encode(request.getPassword()), 
                request.getPhoneNumber(), User.UserRole.USER, location);

        user = userRepository.save(user);

        // Create savings account for the user
        SavingsAccount savingsAccount = new SavingsAccount(user, 0.0);
        savingsAccountRepository.save(savingsAccount);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = tokenProvider.generateToken(authentication);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        
        // Send welcome email (disabled for testing)
        // emailService.sendWelcomeEmail(user.getEmail(), user.getFirstName());
        
        return new AuthenticationResponse(token, refreshToken.getToken(), user.getEmail(), user.getRole().name());
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if 2FA is enabled
        if (user.isTwoFactorEnabled()) {
            // Generate and send 2FA code
            String otp = generateOTP();
            TwoFactorToken twoFactorToken = new TwoFactorToken(
                    otp, user, LocalDateTime.now().plusMinutes(10)
            );
            twoFactorTokenRepository.save(twoFactorToken);
            emailService.sendTwoFactorCode(user.getEmail(), otp);
            
            AuthenticationResponse response = new AuthenticationResponse();
            response.setRequiresTwoFactor(true);
            response.setEmail(user.getEmail());
            return response;
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = tokenProvider.generateToken(authentication);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthenticationResponse(token, refreshToken.getToken(), user.getEmail(), user.getRole().name());
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = tokenProvider.generateTokenFromEmail(user.getEmail());
                    return new AuthenticationResponse(token, requestRefreshToken, user.getEmail(), user.getRole().name());
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

    @Transactional
    public void logout(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        refreshTokenService.deleteByUser(user);
    }

    public AuthenticationResponse verifyTwoFactor(TwoFactorRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TwoFactorToken token = twoFactorTokenRepository
                .findByTokenAndUserAndUsedFalse(request.getOtp(), user)
                .orElseThrow(() -> new RuntimeException("Invalid or expired OTP"));

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired");
        }

        token.setUsed(true);
        twoFactorTokenRepository.save(token);

        String jwtToken = tokenProvider.generateTokenFromEmail(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthenticationResponse(jwtToken, refreshToken.getToken(), user.getEmail(), user.getRole().name());
    }

    public void sendTwoFactorCode(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = generateOTP();
        TwoFactorToken twoFactorToken = new TwoFactorToken(
                otp, user, LocalDateTime.now().plusMinutes(10)
        );
        twoFactorTokenRepository.save(twoFactorToken);
        emailService.sendTwoFactorCode(user.getEmail(), otp);
    }

    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resetToken = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(
                resetToken, user, LocalDateTime.now().plusHours(1)
        );
        passwordResetTokenRepository.save(passwordResetToken);
        emailService.sendPasswordResetEmail(user.getEmail(), resetToken);
    }

    @Transactional
    public void resetPassword(PasswordResetRequest request) {
        PasswordResetToken resetToken = passwordResetTokenRepository
                .findByTokenAndUsedFalse(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid or expired reset token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);
    }

    private String generateOTP() {
        return String.format("%06d", secureRandom.nextInt(1000000));
    }
}