package com.mihaialexandruteodor.FeatherWriter.repository;

import com.mihaialexandruteodor.FeatherWriter.model.Chapter;
import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

    @Query(value = "SELECT * FROM chapter WHERE username = ?1", nativeQuery = true)
    List<Chapter> getChaptersForCurrentUser(String username);

    @Query(value = "SELECT * FROM chapter WHERE username = :username and title LIKE %:title%", nativeQuery = true)
    List<Chapter> searchChapterByTitle(@Param("title") String title, @Param("username") String username);
}
