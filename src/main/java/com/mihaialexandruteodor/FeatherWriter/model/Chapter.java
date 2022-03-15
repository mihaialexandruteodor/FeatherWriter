package com.mihaialexandruteodor.FeatherWriter.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "chapter")
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chapterID;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "novelID")
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

    public Novel getNovel() {
        return novel;
    }

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

    public void addScene(Scene scene) {
        this.scenes.add(scene);
    }

    public void removeScene(Scene scene)
    {
        this.scenes.remove(scene);
    }

    public void sortScenes()
    {
        Collections.sort(scenes, (s1, s2) -> {
            Integer id1 = s1.getSceneID();
            Integer id2 = s2.getSceneID();

            return id1.compareTo(id2);
        });
    }

}
