package com.mihaialexandruteodor.FeatherWriter.model;

import javax.persistence.*;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int locationID;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "uniquefeatures")
    private String uniquefeatures;

    @Lob
    @Column(name = "image", nullable = true)
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Novel getNovel() {return novel;}

    public void setNovel(Novel novel) {this.novel = novel;}

    public void removeNovel() {
        this.novel = null;
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}
}
