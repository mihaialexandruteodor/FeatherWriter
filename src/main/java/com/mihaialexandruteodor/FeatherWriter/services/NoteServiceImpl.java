package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Corkboard;
import com.mihaialexandruteodor.FeatherWriter.model.Note;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import com.mihaialexandruteodor.FeatherWriter.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public void addCorkboardToNote(Corkboard corkboard, Note note) {
        note.setCorkboard(corkboard);
        this.noteRepository.save(note);
    }

    @Override
    public void removeCorkboardFromNote(Note note) {
        note.removeCorkboard();
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

    @Override
    public Page<Note> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.noteRepository.findAll(pageable);
    }
}
