package com.mihaialexandruteodor.FeatherWriter.repository;

import com.mihaialexandruteodor.FeatherWriter.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
}
