package com.mihaialexandruteodor.FeatherWriter.repository;

import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Integer> {

    @Query(value = "SELECT * FROM novel WHERE username = ?1", nativeQuery = true)
    List<Novel> getNovelForCurrentUser(String username);

    @Query(value = "SELECT * FROM novel WHERE username = :username and title LIKE %:title%", nativeQuery = true)
    List<Novel> searchNovelByName(@Param("title") String title, @Param("username") String username);
}
