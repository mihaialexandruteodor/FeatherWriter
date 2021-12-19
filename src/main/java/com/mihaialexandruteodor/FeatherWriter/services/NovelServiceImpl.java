package com.mihaialexandruteodor.FeatherWriter.services;

import com.mihaialexandruteodor.FeatherWriter.model.Chapter;
import com.mihaialexandruteodor.FeatherWriter.model.Novel;
import com.mihaialexandruteodor.FeatherWriter.repository.NovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NovelServiceImpl implements  NovelService{

    @Autowired
    private NovelRepository novelRepository;

    @Override
    public List<Novel> getAllNovels() {
        return this.novelRepository.findAll();
    }

    @Override
    public void saveNovel(Novel novel) {
        this.novelRepository.save(novel);
    }

    @Override
    public Novel getNovelById(int id) {
        Optional<Novel> optional = novelRepository.findById(id);
        Novel novel = null;
        if (optional.isPresent()) {
            novel = optional.get();
        } else {
            throw new RuntimeException(" Novel not found for id :: " + id);
        }
        return novel;
    }

    @Override
    public void deleteNovelById(int id) {
        this.novelRepository.deleteById(id);
    }

    @Override
    public Page<Novel> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.novelRepository.findAll(pageable);
    }
}