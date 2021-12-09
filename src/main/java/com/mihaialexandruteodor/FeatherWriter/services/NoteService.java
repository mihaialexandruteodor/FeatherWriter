package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Note;

import java.util.List;

public interface NoteService {
    List<Note> getAllNotes();
    void saveNote(Note note);
    Note getNoteById(int id);
    void deleteNoteById(int id);
}
