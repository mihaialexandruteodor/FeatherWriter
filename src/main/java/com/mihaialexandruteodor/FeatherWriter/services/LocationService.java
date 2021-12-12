package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Location;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LocationService {
    List<Location> getAllLocations();
    void saveLocation(Location location);
    Location getLocationById(int id);
    void deleteLocationById(int id);
    Page<Location> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
