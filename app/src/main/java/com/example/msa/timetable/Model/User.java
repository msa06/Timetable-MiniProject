package com.example.msa.timetable.Model;

public class User {
    private String uid;
    private String name;
    private String emailid;
    private int access;

    public User() {
    }

    public User(String uid, String name, String emailid, int access) {
        this.uid = uid;
        this.name = name;
        this.emailid = emailid;
        this.access = access;

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
}
