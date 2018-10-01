package com.example.msa.timetable.Model;

public class User {
    String uid;
    String name;
    String emailid;
    Boolean access;

    public User() {
    }

    public User(String uid, String name, String emailid, Boolean access) {
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

    public Boolean getAccess() {
        return access;
    }
}
