package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Note;
import com.mihaialexandruteodor.FeatherWriter.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements  NoteService{

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public List<Note> getAllNotes() {
        return this.noteRepository.findAll();
    }

    @Override
    public void saveNote(Note note) {
        this.noteRepository.save(note);
    }

    @Override
    public Note getNoteById(int id) {
        Optional<Note> optional = noteRepository.findById(id);
        Note note = null;
        if (optional.isPresent()) {
            note = optional.get();
        } else {
            throw new RuntimeException(" Note not found for id :: " + id);
        }
        return note;
    }

    @Override
    public void deleteNoteById(int id) {
        this.noteRepository.deleteById(id);
    }
}
