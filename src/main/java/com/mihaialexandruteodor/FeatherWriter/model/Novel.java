package com.mihaialexandruteodor.FeatherWriter.model;

import javax.persistence.*;

@Entity
@Table(name = "novel")
public class Novel {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private int novelID;

    @Column(name = "authorname")
    private String authorName;

    @Column(name = "draftnumber")
    private int draftNumber;

    @Column(name = "synopsis")
    private String synopsis;

    @Column(name = "authorcontactinfo")
    private String authorContactInfo;

    public int getNovelID() {
        return novelID;
    }

    public void setNovelID(int novelID) {
        this.novelID = novelID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getDraftNumber() {
        return draftNumber;
    }

    public void setDraftNumber(int draftNumber) {
        this.draftNumber = draftNumber;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getAuthorContactInfo() {
        return authorContactInfo;
    }

    public void setAuthorContactInfo(String authorContactInfo) {
        this.authorContactInfo = authorContactInfo;
    }
}
