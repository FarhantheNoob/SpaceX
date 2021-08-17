package com.example.spacex.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Crew implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int crewId;

    String name, agency, imageUrl, status, wikiInfo;

    public Crew(String name, String agency, String imageUrl, String wikiInfo, String status) {
        this.name = name;
        this.agency = agency;
        this.imageUrl = imageUrl;
        this.status = status;
        this.wikiInfo = wikiInfo;
    }

    public int getCrewId() {
        return crewId;
    }

    public void setCrewId(int crewId) {
        this.crewId = crewId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWikiInfo() {
        return wikiInfo;
    }

    public void setWikiInfo(String wikiInfo) {
        this.wikiInfo = wikiInfo;
    }
}
