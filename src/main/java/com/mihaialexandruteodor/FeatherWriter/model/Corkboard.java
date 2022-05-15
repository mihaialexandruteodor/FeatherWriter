package com.mihaialexandruteodor.FeatherWriter.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "corkboard")
public class Corkboard {

    @Column(name = "username")
    private String username;

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int corkboardID;

    @OneToOne(mappedBy = "corkboard")
    private Novel novel;

    @OneToMany(mappedBy = "corkboard", cascade = CascadeType.MERGE)
    private List<Note> notes = new ArrayList<>();

    public int getCorkboardID() {
        return corkboardID;
    }

    public void setCorkboardID(int corkboardID) {
        this.corkboardID = corkboardID;
    }

    public List<Note> getNotes() {return notes;}

    public void setNotes(List<Note> notes) {this.notes = notes;}

    public void addNote(Note note)
    {
        notes.add(note);
    }

    public void removeNote(Note note) {
        notes.remove(note);
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public Novel getNovel() {return novel;}

    public void setNovel(Novel novel) {this.novel = novel;}
}
