package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Scene;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SceneService {
    List<Scene> getAllScenes();
    void saveScene(Scene scene);
    Scene getSceneById(int id);
    void deleteSceneById(int id);
    Page<Scene> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
