package com.mihaialexandruteodor.FeatherWriter.model;

import javax.persistence.*;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private int locationID;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "uniquefeatures")
    private String uniquefeatures;

    @Column(name = "image")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name="novelID")
    private Novel novel;

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUniquefeatures() {
        return uniquefeatures;
    }

    public void setUniquefeatures(String uniquefeatures) {
        this.uniquefeatures = uniquefeatures;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
