package com.mihaialexandruteodor.FeatherWriter.model;

import javax.persistence.*;

@Entity
@Table(name = "corkboard")
public class Corkboard {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private int corkboardID;

    @Column(name = "orderofsceneids")
    private String orderofsceneids;
}
