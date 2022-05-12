package com.mihaialexandruteodor.FeatherWriter.repository;

import com.mihaialexandruteodor.FeatherWriter.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location,Integer> {
    @Query(value = "SELECT * FROM location WHERE username = ?1", nativeQuery = true)
    List<Location> getLocationsForCurrentUser(String username);

    @Query(value = "SELECT * FROM location WHERE username = :username and name LIKE %:name%", nativeQuery = true)
    List<Location> searchLocationByName(@Param("name") String name, @Param("username") String username);
}
