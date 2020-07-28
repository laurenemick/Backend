package com.watermyplants.backend.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "plants")
public class Plant extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long plantid;

    @Column(nullable = false)
    private String nickname;

    private String species;
    @Column(nullable = false)
    private String h2ofrequency;

    private String imageurl;

    public Plant() {
    }

    public Plant(String nickname, String species, String h2ofrequency, String imageurl) {
        this.nickname = nickname;
        this.species = species;
        this.h2ofrequency = h2ofrequency;
        this.imageurl = imageurl;
    }

    @ManyToOne
    @JoinColumn(name = "id",
            nullable = false)
    @JsonIgnoreProperties(value = "plants", allowSetters = true)
    private User user;

    public long getPlantid() {
        return plantid;
    }

    public void setPlantid(long plantid) {
        this.plantid = plantid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getH2ofrequency() {
        return h2ofrequency;
    }

    public void setH2ofrequency(String h2ofrequency) {
        this.h2ofrequency = h2ofrequency;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
