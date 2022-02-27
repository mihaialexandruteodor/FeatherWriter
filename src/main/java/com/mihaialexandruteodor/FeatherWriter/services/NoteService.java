package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import com.mihaialexandruteodor.FeatherWriter.model.Note;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoteService {
    List<Note> getAllNotes();
    void saveNote(Note note);
    void addProjectToNote(Novel novel, Note note);
    void removeProjectFromNote( Note note);
    Note getNoteById(int id);
    void deleteNoteById(int id);
    Page<Note> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
