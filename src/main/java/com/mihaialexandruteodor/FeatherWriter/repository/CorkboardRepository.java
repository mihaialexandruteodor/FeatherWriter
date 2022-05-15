package com.mihaialexandruteodor.FeatherWriter.repository;

import com.mihaialexandruteodor.FeatherWriter.model.Chapter;
import com.mihaialexandruteodor.FeatherWriter.model.Corkboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorkboardRepository extends JpaRepository<Corkboard, Integer> {

    @Query(value = "SELECT * FROM corkboard WHERE username = ?1", nativeQuery = true)
    List<Corkboard> getCorkboardForCurrentUser(String username);
}
