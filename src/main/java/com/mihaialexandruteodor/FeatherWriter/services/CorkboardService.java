package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Corkboard;
import com.mihaialexandruteodor.FeatherWriter.model.Note;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CorkboardService {
    List<Corkboard> getAllCorkboards();
    void saveCorkboard(Corkboard corkboard);
    Corkboard getCorkboardById(int id);
    void deleteCorkboardById(int id);
    void addNoteToCorkboard(Corkboard corkboard, Note note) ;
    void removeNoteFromCorkboard(Corkboard corkboard, Note note) ;
    Page<Corkboard> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
