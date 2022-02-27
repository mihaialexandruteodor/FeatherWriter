package com.mihaialexandruteodor.FeatherWriter.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Table(name = "novel")
public class Novel {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int novelID;

    @Column(name = "title")
    private String title;

    @Column(name = "authorname")
    private String authorname;

    @Column(name = "draftnumber")
    private int draftnumber;

    @Column(name = "synopsis")
    private String synopsis;

    @Column(name = "authorcontactinfo")
    private String authorcontactinfo;

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL)
    private Set<Location> locations = new HashSet<>();

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL)
    private Set<Chapter> chapters = new HashSet<>();

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL)
    private Set<FWCharacter> characters = new HashSet<>();

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL)
    private Set<Note> notes = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "corkboardID")
    @RestResource(path = "novelCorkboard", rel="corkboard")
    private Corkboard corkboard;

    public int getNovelID() {
        return novelID;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public void setNovelID(int novelID) {
        this.novelID = novelID;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public int getDraftnumber() {
        return draftnumber;
    }

    public void setDraftnumber(int draftnumber) {
        this.draftnumber = draftnumber;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getAuthorcontactinfo() {
        return authorcontactinfo;
    }

    public void setAuthorcontactinfo(String authorcontactinfo) {
        this.authorcontactinfo = authorcontactinfo;
    }

    public Set<Location> getLocations() {return locations;}

    public void setLocations(Set<Location> locations) {this.locations = locations;}

    public Set<Chapter> getChapters() {return chapters;}

    public void setChapters(Set<Chapter> chapters) {this.chapters = chapters;}

    public Set<FWCharacter> getCharacters() {return characters;}

    public void setCharacters(Set<FWCharacter> characters) {this.characters = characters;}

    public Set<Note> getNotes() {return notes;}

    public void setNotes(Set<Note> notes) {this.notes = notes;}

    public Corkboard getCorkboard() {return corkboard;}

    public void setCorkboard(Corkboard corkboard) {this.corkboard = corkboard;}

    public void addCharacter(FWCharacter character)
    {
        characters.add(character);
    }

    public void removeCharacter(FWCharacter character) {
        characters.remove(character);
    }

    public void addChapter(Chapter chapter)
    {
        chapters.add(chapter);
    }

    public void removeChapter(Chapter chapter) {
        chapters.remove(chapter);
    }

    public void addLocation(Location location)
    {
        locations.add(location);
    }

    public void removeLocation(Location location) {
        locations.remove(location);
    }

    public void addNote(Note note)
    {
        notes.add(note);
    }

    public void removeNote(Note note) {
        notes.remove(note);
    }
}
