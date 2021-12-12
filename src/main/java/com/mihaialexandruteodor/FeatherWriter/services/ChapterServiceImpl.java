package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Chapter;
import com.mihaialexandruteodor.FeatherWriter.repository.ChapterRepository;
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
    public List<Chapter> getAllChapters() {
        return this.chapterRepository.findAll();
    }

    @Override
    public void saveChapter(Chapter chapter) {
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
