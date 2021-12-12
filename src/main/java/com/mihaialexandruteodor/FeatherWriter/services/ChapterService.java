package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Chapter;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChapterService {
    List<Chapter> getAllChapters();
    void saveChapter(Chapter chapter);
    Chapter getChapterById(int id);
    void deleteChapterById(int id);
    Page<Chapter> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
