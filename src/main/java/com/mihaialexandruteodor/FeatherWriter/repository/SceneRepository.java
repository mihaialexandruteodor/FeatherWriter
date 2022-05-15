package com.mihaialexandruteodor.FeatherWriter.repository;

import com.mihaialexandruteodor.FeatherWriter.model.Scene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SceneRepository extends JpaRepository<Scene, Integer> {

    @Query(value = "SELECT * FROM scene WHERE username = ?1", nativeQuery = true)
    List<Scene> getScenesForCurrentUser(String username);
}
