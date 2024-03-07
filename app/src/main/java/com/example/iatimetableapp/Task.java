package com.example.iatimetableapp;

public class Task {
    int id;

    String name;
    String assoClass;
    String deadline;

    public Task(){}

    public Task(String name, String assoClass, String deadline) {
        this.name = name;
        this.assoClass = assoClass;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssoClass() {
        return assoClass;
    }

    public void setAssoClass(String assoClass) {
        this.assoClass = assoClass;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
