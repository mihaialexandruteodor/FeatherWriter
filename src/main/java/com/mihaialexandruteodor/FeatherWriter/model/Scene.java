package com.mihaialexandruteodor.FeatherWriter.model;

import javax.persistence.*;

@Entity
@Table(name = "scene")
public class Scene {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private int sceneID;

    @Column(name = "text")
    private String text;

    @Column(name = "corkboardtext")
    private String corkboardtext;

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

    public String getCorkboardtext() {
        return corkboardtext;
    }

    public void setCorkboardtext(String corkboardtext) {
        this.corkboardtext = corkboardtext;
    }
}