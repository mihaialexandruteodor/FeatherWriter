package com.mihaialexandruteodor.FeatherWriter.repository;

import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import com.mihaialexandruteodor.FeatherWriter.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    @Query(value = "SELECT * FROM note WHERE username = ?1", nativeQuery = true)
    List<Note> getNotesForCurrentUser(String username);

    @Query(value = "SELECT * FROM note WHERE username = :username and title LIKE %:title%", nativeQuery = true)
    List<Note> searchNoteByTitle(@Param("title") String title, @Param("username") String username);
}
