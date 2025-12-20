package com.piggybank.controller;

import com.piggybank.model.Location;
import com.piggybank.model.Location.LocationType;
import com.piggybank.service.LocationService;
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
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        Optional<Location> location = locationService.getLocationById(id);
        return location.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Location> getLocationByName(@PathVariable String name) {
        Optional<Location> location = locationService.getLocationByName(name);
        return location.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public List<Location> getLocationsByType(@PathVariable LocationType type) {
        return locationService.getLocationsByType(type);
    }

    @GetMapping("/type/{type}/sorted")
    public List<Location> getLocationsByTypeSorted(@PathVariable LocationType type,
            @RequestParam String sortBy,
            @RequestParam String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return locationService.getLocationsByType(type, sort);
    }

    @GetMapping("/type/{type}/paged")
    public Page<Location> getLocationsByTypePaged(@PathVariable LocationType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return locationService.getLocationsByType(type, pageable);
    }

    @GetMapping("/provinces")
    public List<Location> getProvinces() {
        return locationService.getProvinces();
    }

    @GetMapping("/parent/{parentId}")
    public List<Location> getChildrenLocations(@PathVariable Long parentId) {
        return locationService.getChildrenLocations(parentId);
    }

    @PostMapping
    public Location createLocation(@RequestBody Location location) {
        return locationService.createLocation(location);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody Location locationDetails) {
        Location updatedLocation = locationService.updateLocation(id, locationDetails);
        return updatedLocation != null ? ResponseEntity.ok(updatedLocation) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        boolean deleted = locationService.deleteLocation(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/exists/name/{name}")
    public boolean locationExistsByName(@PathVariable String name) {
        return locationService.locationExistsByName(name);
    }

    @GetMapping("/exists/code/{code}")
    public boolean locationExistsByCode(@PathVariable String code) {
        return locationService.locationExistsByCode(code);
    }

    @GetMapping("/count/type/{type}")
    public long countLocationsByType(@PathVariable LocationType type) {
        return locationService.countLocationsByType(type);
    }
}