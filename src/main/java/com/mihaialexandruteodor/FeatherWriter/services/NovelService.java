package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NovelService {
    List<Novel> getAllNovels(String username);
    List<Novel> searchNovels(String username, String queryWord);
    void saveNovel(Novel novel);
    Novel getNovelById(int id);
    void deleteNovelById(int id);
    void addCharacterToProject(Novel project, FWCharacter character) ;
    void removeCharacterFromProject(Novel project, FWCharacter character);
    void addChapterToProject(Novel project, Chapter chapter) ;
    void removeChapterFromProject(Novel project, Chapter chapter) ;
    void addLocationToProject(Novel project, Location location) ;
    void removeLocationFromProject(Novel project, Location location) ;
    Page<Novel> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
