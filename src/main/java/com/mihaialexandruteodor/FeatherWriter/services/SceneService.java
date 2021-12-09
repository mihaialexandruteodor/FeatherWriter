package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Scene;

import java.util.List;

public interface SceneService {
    List<Scene> getAllScenes();
    void saveScene(Scene scene);
    Scene getSceneById(int id);
    void deleteSceneById(int id);
}
