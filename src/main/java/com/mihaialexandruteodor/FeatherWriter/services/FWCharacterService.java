package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FWCharacterService {
    List<FWCharacter> getAllFWCharacters();
    void saveFWCharacter(FWCharacter fwCharacter);
    void addProjectToCharacter(Novel novel, FWCharacter fwCharacter);
    void removeProjectFromCharacter( FWCharacter fwCharacter);
    FWCharacter getFWCharacterById(int id);
    void deleteFWCharacterById(int id);
    Page<FWCharacter> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
