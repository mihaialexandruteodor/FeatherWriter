package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Location;
import com.mihaialexandruteodor.FeatherWriter.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class LocationServiceImpl implements LocationService{

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Location> getAllLocations() {
        return this.locationRepository.findAll();
    }

    @Override
    public void saveLocation(Location location) {
        this.locationRepository.save(location);
    }

    @Override
    public Location getLocationById(int id) {
        Optional<Location> optional = locationRepository.findById(id);
        Location location = null;
        if (optional.isPresent()) {
            location = optional.get();
        } else {
            throw new RuntimeException(" Location not found for id :: " + id);
        }
        return location;
    }

    @Override
    public void deleteLocationById(int id) {
        this.locationRepository.deleteById(id);
    }
}
