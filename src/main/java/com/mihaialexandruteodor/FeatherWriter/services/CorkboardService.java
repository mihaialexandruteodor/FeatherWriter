package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Corkboard;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CorkboardService {
    List<Corkboard> getAllCorkboards();
    void saveCorkboard(Corkboard corkboard);
    Corkboard getCorkboardById(int id);
    void deleteCorkboardById(int id);
    Page<Corkboard> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
