package com.mihaialexandruteodor.FeatherWriter.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chapter")
public class Chapter {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int chapterID;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name="novelID")
    private Novel novel;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.MERGE)
    private List<Scene> scenes = new ArrayList<>();

    public int getChapterID() {
        return chapterID;
    }

    public void setChapterID(int chapterID) {
        this.chapterID = chapterID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Novel getNovel() {return novel;}

    public void setNovel(Novel novel) {
        this.novel = novel;
    }

    public void removeNovel() {
        this.novel = null;
    }

    public List<Scene> getScenes() {
        return scenes;
    }

    public void setScenes(List<Scene> scenes) {
        this.scenes = scenes;
    }
}
