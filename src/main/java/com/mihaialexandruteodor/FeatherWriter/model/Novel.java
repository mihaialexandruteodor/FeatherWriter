package com.mihaialexandruteodor.FeatherWriter.model;

import javax.persistence.*;
import java.util.List;

import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Table(name = "novel")
public class Novel {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
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
    private List<Location> locations;

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL)
    private List<Chapter> chapters;

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL)
    private List<FWCharacter> characters;

    @OneToMany(mappedBy = "novel", cascade = CascadeType.ALL)
    private List<Note> notes;

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

    public List<Location> getLocations() {return locations;}

    public void setLocations(List<Location> locations) {this.locations = locations;}

    public List<Chapter> getChapters() {return chapters;}

    public void setChapters(List<Chapter> chapters) {this.chapters = chapters;}

    public List<FWCharacter> getCharacters() {return characters;}

    public void setCharacters(List<FWCharacter> characters) {this.characters = characters;}

    public List<Note> getNotes() {return notes;}

    public void setNotes(List<Note> notes) {this.notes = notes;}

    public Corkboard getCorkboard() {return corkboard;}

    public void setCorkboard(Corkboard corkboard) {this.corkboard = corkboard;}

    public void addCharacter(FWCharacter character)
    {
        characters.add(character);
    }

    public void removeCharacter(FWCharacter character) {
        characters.remove(character);
    }
}
