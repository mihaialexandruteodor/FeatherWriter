package com.mihaialexandruteodor.FeatherWriter.repository;

import com.mihaialexandruteodor.FeatherWriter.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
}
