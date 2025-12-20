package com.piggybank.service;

import com.piggybank.model.Location;
import com.piggybank.model.Location.LocationType;
import com.piggybank.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    public Optional<Location> getLocationByName(String name) {
        return locationRepository.findByName(name);
    }

    public Optional<Location> getLocationByCode(String code) {
        return locationRepository.findByCode(code);
    }

    public List<Location> getLocationsByType(LocationType type) {
        return locationRepository.findByType(type);
    }

    public List<Location> getLocationsByType(LocationType type, Sort sort) {
        return locationRepository.findByType(type, sort);
    }

    public Page<Location> getLocationsByType(LocationType type, Pageable pageable) {
        return locationRepository.findByType(type, pageable);
    }

    public List<Location> getProvinces() {
        return locationRepository.findProvinces();
    }

    public List<Location> getChildrenLocations(Long parentId) {
        return locationRepository.findByParentId(parentId);
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location updateLocation(Long id, Location locationDetails) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();
            location.setName(locationDetails.getName());
            location.setCode(locationDetails.getCode());
            location.setType(locationDetails.getType());
            location.setParent(locationDetails.getParent());
            return locationRepository.save(location);
        }
        return null;
    }

    public boolean deleteLocation(Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean locationExistsByName(String name) {
        return locationRepository.existsByName(name);
    }

    public boolean locationExistsByCode(String code) {
        return locationRepository.existsByCode(code);
    }

    public long countLocationsByType(LocationType type) {
        return locationRepository.countByType(type);
    }
}