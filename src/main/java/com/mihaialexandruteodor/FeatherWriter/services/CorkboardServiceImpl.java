package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Corkboard;
import com.mihaialexandruteodor.FeatherWriter.model.Note;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import com.mihaialexandruteodor.FeatherWriter.repository.CorkboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CorkboardServiceImpl implements  CorkboardService{

    @Autowired
    private CorkboardRepository corkboardRepository;

    @Override
    public List<Corkboard> getAllCorkboards(String username) {
        return this.corkboardRepository.getCorkboardForCurrentUser(username);
    }

    @Override
    public void saveCorkboard(Corkboard corkboard) {
        this.corkboardRepository.save(corkboard);
    }

    @Override
    public Corkboard getCorkboardById(int id) {
        Optional<Corkboard> optional = corkboardRepository.findById(id);
        Corkboard corkboard = null;
        if (optional.isPresent()) {
            corkboard = optional.get();
        } else {
            throw new RuntimeException(" Corkboard not found for id :: " + id);
        }
        return corkboard;
    }

    @Override
    public void addNoteToCorkboard(Corkboard corkboard, Note note) {
        corkboard.addNote(note);
        this.corkboardRepository.save(corkboard);
    }

    @Override
    public void removeNoteFromCorkboard(Corkboard corkboard, Note note) {
        corkboard.removeNote(note);
        this.corkboardRepository.save(corkboard);
    }

    @Override
    public void deleteCorkboardById(int id) {
        this.corkboardRepository .deleteById(id);
    }

    @Override
    public Page<Corkboard> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.corkboardRepository.findAll(pageable);
    }
}
