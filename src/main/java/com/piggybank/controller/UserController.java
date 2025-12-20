package com.piggybank.controller;

import com.piggybank.dto.UserDto;
import com.piggybank.model.User.UserRole;
import com.piggybank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<UserDto> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/role/{role}")
    public List<UserDto> getUsersByRole(@PathVariable UserRole role) {
        return userService.getUsersByRole(role);
    }

    @GetMapping("/role/{role}/paged")
    public Page<UserDto> getUsersByRolePaged(@PathVariable UserRole role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("firstName"));
        return userService.getUsersByRole(role, pageable);
    }

    // Location filtering endpoints
    @GetMapping("/location/province/{name}")
    public List<UserDto> getUsersByProvince(@PathVariable String name) {
        return userService.getUsersByProvince(name);
    }

    @GetMapping("/location/province/{name}/count")
    public long countUsersByProvince(@PathVariable String name) {
        return userService.countUsersByProvince(name);
    }

    @GetMapping("/location/district/{name}")
    public List<UserDto> getUsersByDistrict(@PathVariable String name) {
        return userService.getUsersByDistrict(name);
    }

    @GetMapping("/location/district/{name}/count")
    public long countUsersByDistrict(@PathVariable String name) {
        return userService.countUsersByDistrict(name);
    }

    @GetMapping("/location/sector/{name}")
    public List<UserDto> getUsersBySector(@PathVariable String name) {
        return userService.getUsersBySector(name);
    }

    @GetMapping("/location/sector/{name}/count")
    public long countUsersBySector(@PathVariable String name) {
        return userService.countUsersBySector(name);
    }

    @GetMapping("/location/cell/{name}")
    public List<UserDto> getUsersByCell(@PathVariable String name) {
        return userService.getUsersByCell(name);
    }

    @GetMapping("/location/cell/{name}/count")
    public long countUsersByCell(@PathVariable String name) {
        return userService.countUsersByCell(name);
    }

    @GetMapping("/location/village/{name}")
    public List<UserDto> getUsersByVillage(@PathVariable String name) {
        return userService.getUsersByVillage(name);
    }

    @GetMapping("/location/village/{name}/count")
    public long countUsersByVillage(@PathVariable String name) {
        return userService.countUsersByVillage(name);
    }

    // Analytics endpoints
    @GetMapping("/analytics/top-districts")
    public List<Object[]> getTopDistrictsByUserCount() {
        return userService.getTopDistrictsByUserCount();
    }

    @GetMapping("/count/role/{role}")
    public long countUsersByRole(@PathVariable UserRole role) {
        return userService.countUsersByRole(role);
    }
}