package com.example.msa.timetable.Model;

import java.util.ArrayList;

public class User {
    private String uid;
    private String name;
    private String emailid;
    private int access;
    private ArrayList<Class> classes;

    public User() {
    }

    public User(String uid, String name, String emailid, int access, ArrayList<Class> classes) {
        this.uid = uid;
        this.name = name;
        this.emailid = emailid;
        this.access = access;
        this.classes = classes;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmailid() {
        return emailid;
    }

    public int getAccess() {
        return access;
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }
}
