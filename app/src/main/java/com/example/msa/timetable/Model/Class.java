package com.example.msa.timetable.Model;

import java.util.ArrayList;

public class Class {
    private String code;
    private String name;
    private ArrayList<String> teachers;
    private ArrayList<String> theorysubjects;
    private ArrayList<String> practicalSubjects;
    private ArrayList<String> timetable;
    public Class() {
    }

    public Class(String code, String name, ArrayList<String> teachers, ArrayList<String> theorysubjects, ArrayList<String> practicalSubjects) {
        this.code = code;
        this.name = name;
        this.teachers = teachers;
        this.theorysubjects = theorysubjects;
        this.practicalSubjects = practicalSubjects;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getTeachers() {
        return teachers;
    }

    public ArrayList<String> getTheorysubjects() {
        return theorysubjects;
    }

    public ArrayList<String> getPracticalSubjects() {
        return practicalSubjects;
    }
}
