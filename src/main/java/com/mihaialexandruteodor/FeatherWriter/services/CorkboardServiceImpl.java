package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Corkboard;
import com.mihaialexandruteodor.FeatherWriter.repository.CorkboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CorkboardServiceImpl implements  CorkboardService{

    @Autowired
    private CorkboardRepository corkboardRepository;

    @Override
    public List<Corkboard> getAllCorkboards() {
        return this.corkboardRepository.findAll();
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
    public void deleteCorkboardById(int id) {
        this.corkboardRepository .deleteById(id);
    }
}
