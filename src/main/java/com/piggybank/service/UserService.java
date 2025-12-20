package com.piggybank.service;

import com.piggybank.dto.UserDto;
import com.piggybank.model.User;
import com.piggybank.model.User.UserRole;
import com.piggybank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::fromUser);
    }

    public Optional<User> getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserDto> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role).stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
    }

    public Page<UserDto> getUsersByRole(UserRole role, Pageable pageable) {
        return userRepository.findByRole(role, pageable)
                .map(UserDto::fromUser);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Location filtering methods
    public List<UserDto> getUsersByProvince(String provinceName) {
        return userRepository.findByLocationProvince(provinceName).stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersByDistrict(String districtName) {
        return userRepository.findByLocationDistrict(districtName).stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersBySector(String sectorName) {
        return userRepository.findByLocationSector(sectorName).stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersByCell(String cellName) {
        return userRepository.findByLocationCell(cellName).stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersByVillage(String villageName) {
        return userRepository.findByLocationVillage(villageName).stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
    }

    // Location counting methods
    public long countUsersByProvince(String provinceName) {
        return userRepository.countByLocationProvince(provinceName);
    }

    public long countUsersByDistrict(String districtName) {
        return userRepository.countByLocationDistrict(districtName);
    }

    public long countUsersBySector(String sectorName) {
        return userRepository.countByLocationSector(sectorName);
    }

    public long countUsersByCell(String cellName) {
        return userRepository.countByLocationCell(cellName);
    }

    public long countUsersByVillage(String villageName) {
        return userRepository.countByLocationVillage(villageName);
    }

    public List<Object[]> getTopDistrictsByUserCount() {
        return userRepository.findTopDistrictsByUserCount();
    }

    public long countUsersByRole(UserRole role) {
        return userRepository.countByRole(role);
    }
}