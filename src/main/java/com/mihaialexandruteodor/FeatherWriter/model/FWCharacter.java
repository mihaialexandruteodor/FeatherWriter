package com.mihaialexandruteodor.FeatherWriter.model;

import javax.persistence.*;
//import org.apache.commons.codec.binary.Base64;

@Entity
@Table(name = "fwcharacter")
public class FWCharacter {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private int characterID;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "physicaldescription")
    private String physicaldescription;

    @Column(name = "quirks")
    private String quirks;

    @Column(name = "motivation")
    private String motivation;

    @Column(name = "quotes")
    private String quotes;

    @Column(name = "history")
    private String history;

    @Lob
    @Column(name = "image", nullable = true)
    private String image;

    @ManyToOne
    @JoinColumn(name="novelID")
    private Novel novel;

    public int getCharacterID() {
        return characterID;
    }

    public void setCharacterID(int characterID) {
        this.characterID = characterID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhysicaldescription() {
        return physicaldescription;
    }

    public void setPhysicaldescription(String physicaldescription) {
        this.physicaldescription = physicaldescription;
    }

    public String getQuirks() {
        return quirks;
    }

    public void setQuirks(String quirks) {
        this.quirks = quirks;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Novel getNovel() {return novel;}

    public void setNovel(Novel novel) {
        this.novel = novel;
    }
}
