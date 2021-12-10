package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Chapter;
import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import com.mihaialexandruteodor.FeatherWriter.repository.FWCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FWCharacterServiceImpl implements FWCharacterService{

    @Autowired
    private FWCharacterRepository fwCharacterRepository;

    @Override
    public List<FWCharacter> getAllFWCharacters() {
        return this.fwCharacterRepository.findAll();
    }

    @Override
    public void saveFWCharacter(FWCharacter fwCharacter) {
        this.fwCharacterRepository.save(fwCharacter);
    }

    @Override
    public FWCharacter getFWCharacterById(int id) {
        Optional<FWCharacter> optional = fwCharacterRepository.findById(id);
        FWCharacter fwCharacter = null;
        if (optional.isPresent()) {
            fwCharacter = optional.get();
        } else {
            throw new RuntimeException(" FWCharacter not found for id :: " + id);
        }
        return fwCharacter;
    }

    @Override
    public void deleteFWCharacterById(int id) {
        this.fwCharacterRepository.deleteById(id);
    }
}
