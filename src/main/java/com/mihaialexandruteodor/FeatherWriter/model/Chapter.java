package com.mihaialexandruteodor.FeatherWriter.model;

import javax.persistence.*;

@Entity
@Table(name = "chapter")
public class Chapter {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private int chapterID;

    @Column(name = "title")
    private String title;

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

}
