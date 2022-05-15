package com.mihaialexandruteodor.FeatherWriter.model;

import javax.persistence.*;

@Entity
@Table(name = "scene")
public class Scene {

    @Column(name = "username")
    private String username;

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int sceneID;

    @Column(name = "text")
    private String text;

    @Column(name = "descriptiontext")
    private String descriptiontext;

    @ManyToOne
    @JoinColumn(name="chapterID")
    private Chapter chapter;

    public int getSceneID() {
        return sceneID;
    }

    public void setSceneID(int sceneID) {
        this.sceneID = sceneID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescriptiontext() {
        return descriptiontext;
    }

    public void setDescriptiontext(String descriptiontext) {
        this.descriptiontext = descriptiontext;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public void removeChapter()
    {
        this.chapter = null;
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}
}
