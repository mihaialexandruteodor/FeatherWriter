package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Chapter;

import java.util.List;

public interface ChapterService {
    List<Chapter> getAllChapters();
    void saveChapter(Chapter chapter);
    Chapter getChapterById(int id);
    void deleteChapterById(int id);
}
