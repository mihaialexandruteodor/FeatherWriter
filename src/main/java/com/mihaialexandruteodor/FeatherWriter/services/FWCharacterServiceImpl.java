package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import com.mihaialexandruteodor.FeatherWriter.repository.FWCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FWCharacterServiceImpl implements FWCharacterService{

    @Autowired
    private FWCharacterRepository fwCharacterRepository;

    @Override
    public List<FWCharacter> getAllFWCharacters(String username) {
        return this.fwCharacterRepository.getCharactersForCurrentUser(username);
    }

    @Override
    public void saveFWCharacter(FWCharacter fwCharacter) {
        this.fwCharacterRepository.save(fwCharacter);
    }

    @Override
    public void addProjectToCharacter(Novel novel, FWCharacter fwCharacter) {
        fwCharacter.setNovel(novel);
        this.fwCharacterRepository.save(fwCharacter);
    }

    @Override
    public void removeProjectFromCharacter( FWCharacter fwCharacter) {
        fwCharacter.removeNovel();
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

    @Override
    public Page<FWCharacter> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.fwCharacterRepository.findAll(pageable);
    }
}
