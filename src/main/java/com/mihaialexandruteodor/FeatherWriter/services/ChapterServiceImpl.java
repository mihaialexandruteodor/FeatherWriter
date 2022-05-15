package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Chapter;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import com.mihaialexandruteodor.FeatherWriter.model.Scene;
import com.mihaialexandruteodor.FeatherWriter.repository.ChapterRepository;
import com.mihaialexandruteodor.FeatherWriter.utlis.DataSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChapterServiceImpl implements ChapterService{

    @Autowired
    private ChapterRepository chapterRepository;

    @Override
    public List<Chapter> getAllChapters(String username) {

        return this.chapterRepository.getChaptersForCurrentUser(username);
    }

    @Override
    public List<Chapter> searchChapters(String title, String queryWord) {
        return this.chapterRepository.searchChapterByTitle(title,  queryWord);
    }

    @Override
    public void saveChapter(Chapter chapter) {
        this.chapterRepository.save(chapter);
    }

    @Override
    public void addProjectToChapter(Novel novel, Chapter chapter) {
        chapter.setNovel(novel);
        this.chapterRepository.save(chapter);
    }

    @Override
    public void removeProjectFromChapter(Chapter chapter) {
        chapter.removeNovel();
        this.chapterRepository.save(chapter);
    }

    @Override
    public void addSceneToChapter(Scene scene, Chapter chapter) {
        chapter.addScene(scene);
        this.chapterRepository.save(chapter);
    }

    @Override
    public void removeSceneFromChapter(Scene scene, Chapter chapter) {
        chapter.removeScene(scene);
        this.chapterRepository.save(chapter);
    }

    @Override
    public Chapter getChapterById(int id) {
        Optional<Chapter> optional = chapterRepository.findById(id);
        Chapter chapter = null;
        if (optional.isPresent()) {
            chapter = optional.get();
        } else {
            throw new RuntimeException(" Chapter not found for id :: " + id);
        }
        return chapter;
    }

    @Override
    public void swapChapters(Chapter donor, Chapter receiver) {
        String auxTitle = donor.getTitle();
        donor.setTitle(receiver.getTitle());
        receiver.setTitle(auxTitle);
        List<Scene> auxScenes = donor.getScenes();
        donor.setScenes(receiver.getScenes());
        receiver.setScenes(auxScenes);
        chapterRepository.save(donor);
        chapterRepository.save(receiver);
    }

    @Override
    public void deleteChapterById(int id) {
        this.chapterRepository.deleteById(id);
    }

    @Override
    public Page<Chapter> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.chapterRepository.findAll(pageable);
    }
}
