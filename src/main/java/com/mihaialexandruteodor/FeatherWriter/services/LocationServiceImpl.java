package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Location;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import com.mihaialexandruteodor.FeatherWriter.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService{

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Location> getAllLocations(String username) {
        return this.locationRepository.getLocationsForCurrentUser(username);
    }

    @Override
    public List<Location> searchLocations(String username, String queryWord) {
        return this.locationRepository.searchLocationByName(queryWord,username);
    }

    @Override
    public void saveLocation(Location location) {
        this.locationRepository.save(location);
    }

    @Override
    public void addProjectToLocation(Novel novel, Location location) {
        location.setNovel(novel);
        this.locationRepository.save(location);
    }

    @Override
    public void removeProjectFromLocation(Location location) {
        location.removeNovel();
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

    @Override
    public Page<Location> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.locationRepository.findAll(pageable);
    }
}
