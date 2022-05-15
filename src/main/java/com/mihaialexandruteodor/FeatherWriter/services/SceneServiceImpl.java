package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Chapter;
import com.mihaialexandruteodor.FeatherWriter.model.Scene;
import com.mihaialexandruteodor.FeatherWriter.repository.SceneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SceneServiceImpl implements SceneService{

    @Autowired
    private SceneRepository sceneRepository;

    @Override
    public List<Scene> getAllScenes(String username) {
        return this.sceneRepository.getScenesForCurrentUser(username);
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

    @Override
    public void addChapterToScene(Scene scene, Chapter chapter) {
        scene.setChapter(chapter);
        this.sceneRepository.save(scene);
    }

    @Override
    public void removeChapterToScene(Scene scene) {
        scene.removeChapter();
        this.sceneRepository.save(scene);
    }

    @Override
    public void swapScenesText(Scene donor, Scene receiver) {
        String textAux = donor.getText();
        String textDescriptionAux = donor.getDescriptiontext();
        donor.setText(receiver.getText());
        donor.setDescriptiontext(receiver.getDescriptiontext());
        receiver.setText(textAux);
        receiver.setDescriptiontext(textDescriptionAux);
        this.sceneRepository.save(donor);
        this.sceneRepository.save(receiver);
    }

    @Override
    public Page<Scene> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.sceneRepository.findAll(pageable);
    }
}
