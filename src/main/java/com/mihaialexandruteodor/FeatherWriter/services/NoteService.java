package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Corkboard;
import com.mihaialexandruteodor.FeatherWriter.model.Note;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoteService {
    List<Note> getAllNotes(String username);
    List<Note> searchNoteByTitle(String username, String title);
    void saveNote(Note note);
    void addCorkboardToNote(Corkboard corkboard, Note note);
    void removeCorkboardFromNote( Note note);
    Note getNoteById(int id);
    void deleteNoteById(int id);
    Page<Note> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
