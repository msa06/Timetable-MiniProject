package com.example.msa.timetable.Model;

import com.google.firebase.database.Exclude;

public class Period {
    String pn;
    String st;
    String et;
    String tn;
    String pl;
    String id;

    public Period() {
    }

    public Period(String pn, String st, String et, String tn, String pl, String id) {
        this.pn = pn;
        this.st = st;
        this.et = et;
        this.tn = tn;
        this.pl = pl;
        this.id = id;
    }

    public String getPn() {
        return pn;
    }

    public String getSt() {
        return st;
    }

    public String getEt() {
        return et;
    }

    public String getTn() {
        return tn;
    }

    public String getPl() {
        return pl;
    }

    public String getId() {
        return id;
    }

    @Exclude
    public void setvalue(Period period) {
        this.pn = period.getPn();
        this.st = period.getSt();
        this.et = period.getEt();
        this.tn = period.getTn();
        this.pl = period.getPl();
        this.id = period.getId();
    }
}
