package com.example.iatimetableapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

public class Subject {
    private String tag = "Subject";

    private int id;

    private String name;
    private String location;
    private String teacher;

    private boolean day1;
    private boolean day2;
    private boolean day3;
    private boolean day4;
    private boolean day5;
    private boolean day6;
    private boolean day7;

    private String day1Time;
    private String day2Time;
    private String day3Time;
    private String day4Time;
    private String day5Time;
    private String day6Time;
    private String day7Time;

    private String time;

    public Subject(){}

    public Subject(String name,
                   String location,
                   String teacher,
                   boolean day1,
                   boolean day2,
                   boolean day3,
                   boolean day4,
                   boolean day5,
                   boolean day6,
                   boolean day7,
                   String day1Time,
                   String day2Time,
                   String day3Time,
                   String day4Time,
                   String day5Time,
                   String day6Time,
                   String day7Time) {
        this.name = name;
        this.location = location;
        this.teacher = teacher;
        this.day1 = day1;
        this.day2 = day2;
        this.day3 = day3;
        this.day4 = day4;
        this.day5 = day5;
        this.day6 = day6;
        this.day7 = day7;
        this.day1Time = day1Time;
        this.day2Time = day2Time;
        this.day3Time = day3Time;
        this.day4Time = day4Time;
        this.day5Time = day5Time;
        this.day6Time = day6Time;
        this.day7Time = day7Time;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public boolean isDay1() {
        return day1;
    }

    public void setDay1(boolean day1) {
        this.day1 = day1;
    }

    public boolean isDay2() {
        return day2;
    }

    public void setDay2(boolean day2) {
        this.day2 = day2;
    }

    public boolean isDay3() {
        return day3;
    }

    public void setDay3(boolean day3) {
        this.day3 = day3;
    }

    public boolean isDay4() {
        return day4;
    }

    public void setDay4(boolean day4) {
        this.day4 = day4;
    }

    public boolean isDay5() {
        return day5;
    }

    public void setDay5(boolean day5) {
        this.day5 = day5;
    }

    public boolean isDay6() {
        return day6;
    }

    public void setDay6(boolean day6) {
        this.day6 = day6;
    }

    public boolean isDay7() {
        return day7;
    }

    public void setDay7(boolean day7) {
        this.day7 = day7;
    }

    public String getDay1Time() {
        return day1Time;
    }

    public void setDay1Time(String day1Time) {
        this.day1Time = day1Time;
    }

    public String getDay2Time() {
        return day2Time;
    }

    public void setDay2Time(String day2Time) {
        this.day2Time = day2Time;
    }

    public String getDay3Time() {
        return day3Time;
    }

    public void setDay3Time(String day3Time) {
        this.day3Time = day3Time;
    }

    public String getDay4Time() {
        return day4Time;
    }

    public void setDay4Time(String day4Time) {
        this.day4Time = day4Time;
    }

    public String getDay5Time() {
        return day5Time;
    }

    public void setDay5Time(String day5Time) {
        this.day5Time = day5Time;
    }

    public String getDay6Time() {
        return day6Time;
    }

    public void setDay6Time(String day6Time) {
        this.day6Time = day6Time;
    }

    public String getDay7Time() {
        return day7Time;
    }

    public void setDay7Time(String day7Time) {
        this.day7Time = day7Time;
    }

    public String getTime(int day){
        switch (day){
            case 1: time = getDay1Time(); break;
            case 2: time = getDay2Time(); break;
            case 3: time = getDay3Time(); break;
            case 4: time = getDay4Time(); break;
            case 5: time = getDay5Time(); break;
            case 6: time = getDay6Time(); break;
            case 7: time = getDay7Time(); break;
        }

        return time;
    }
}
