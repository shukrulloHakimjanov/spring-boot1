package com.clean.code.springBoot2.model;

public class Student {
    private Long id;
    private String name;
    private String last;
    private String course;

    public Student(Long id, String name, String last, String course) {
        this.id = id;
        this.name = name;
        this.last = last;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}

