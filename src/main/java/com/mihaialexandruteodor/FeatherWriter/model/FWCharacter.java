package com.mihaialexandruteodor.FeatherWriter.model;

import javax.persistence.*;

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

    @Column(name = "image")
    private byte[] image;

}
