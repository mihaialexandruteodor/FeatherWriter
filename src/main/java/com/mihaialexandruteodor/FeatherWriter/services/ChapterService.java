package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.*;
import com.mihaialexandruteodor.FeatherWriter.model.Chapter;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChapterService {
    List<Chapter> getAllChapters(String username);
    List<Chapter> searchChapters(String title, String queryWord);
    void saveChapter(Chapter chapter);
    void addProjectToChapter(Novel novel, Chapter chapter);
    void removeProjectFromChapter(Chapter chapter);
    void addSceneToChapter(Scene scene, Chapter chapter);
    void removeSceneFromChapter(Scene scene, Chapter chapter);
    Chapter getChapterById(int id);
    void deleteChapterById(int id);
    void swapChapters(Chapter donor, Chapter receiver);
    Page<Chapter> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
