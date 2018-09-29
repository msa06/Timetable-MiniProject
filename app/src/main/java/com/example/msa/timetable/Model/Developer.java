package com.example.msa.timetable.Model;

public class Developer {
    String name;
    int photoid;

    public Developer(String name, int photoid) {
        this.name = name;
        this.photoid = photoid;
    }

    public String getName() {
        return name;
    }

    public int getPhotoid() {
        return photoid;
    }
}
