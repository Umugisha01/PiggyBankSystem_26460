package com.piggybank.config;

import com.piggybank.model.SavingsAccount;
import com.piggybank.model.User;
import com.piggybank.repository.SavingsAccountRepository;
import com.piggybank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create admin user if not exists
        if (!userRepository.existsByEmail("admin@piggybank.com")) {
            User admin = new User("Admin", "User", "admin@piggybank.com", 
                    passwordEncoder.encode("admin123"), "+250788000000", 
                    User.UserRole.ADMIN, null);

            admin = userRepository.save(admin);

            // Create savings account for admin
            SavingsAccount adminAccount = new SavingsAccount(admin, 0.0);
            savingsAccountRepository.save(adminAccount);

            System.out.println("Admin user created: admin@piggybank.com / admin123");
        }
    }
}