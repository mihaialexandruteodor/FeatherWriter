package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Location;
import com.mihaialexandruteodor.FeatherWriter.model.Location;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LocationService {
    List<Location> getAllLocations(String username);
    void saveLocation(Location location);
    void addProjectToLocation(Novel novel, Location location);
    void removeProjectFromLocation(Location location);
    Location getLocationById(int id);
    void deleteLocationById(int id);
    Page<Location> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
