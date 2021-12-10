package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;

import java.util.List;

public interface FWCharacterService {
    List<FWCharacter> getAllFWCharacters();
    void saveFWCharacter(FWCharacter fwCharacter);
    FWCharacter getFWCharacterById(int id);
    void deleteFWCharacterById(int id);
}
