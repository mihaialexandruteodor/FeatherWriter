package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Scene;
import com.mihaialexandruteodor.FeatherWriter.repository.SceneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SceneServiceImpl implements SceneService{

    @Autowired
    private SceneRepository sceneRepository;

    @Override
    public List<Scene> getAllScenes() {
        return this.sceneRepository.findAll();
    }

    @Override
    public void saveScene(Scene scene) {
        this.sceneRepository.save(scene);
    }

    @Override
    public Scene getSceneById(int id) {
        Optional<Scene> optional = sceneRepository.findById(id);
        Scene scene = null;
        if (optional.isPresent()) {
            scene = optional.get();
        } else {
            throw new RuntimeException(" Scene not found for id :: " + id);
        }
        return scene;
    }

    @Override
    public void deleteSceneById(int id) {
        this.sceneRepository.deleteById(id);
    }
}
