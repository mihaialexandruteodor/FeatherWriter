package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NovelService {
    List<Novel> getAllNovels();
    void saveNovel(Novel novel);
    Novel getNovelById(int id);
    void deleteNovelById(int id);
    void addTeamToProject(Novel project, FWCharacter character) ;
    void removeTeamToProject(Novel project, FWCharacter character) ;
    Page<Novel> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
