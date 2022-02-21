package com.mihaialexandruteodor.FeatherWriter.model;

import javax.persistence.*;

@Entity
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private int noteID;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Lob
    @Column(name = "image", nullable = true)
    private String image;

    @ManyToOne
    @JoinColumn(name="novelID")
    private Novel novel;

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
