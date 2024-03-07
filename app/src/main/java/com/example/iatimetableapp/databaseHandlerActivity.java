package com.example.iatimetableapp;

import java.util.*;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class databaseHandlerActivity extends SQLiteOpenHelper{

    public static final String tag = "databaseHandlerActivity";

    //database global values
    private static final int db_Version = 1;
    private static final String db_Name="mainDatabase";
    private static final String table_ID="_id";

    //classes table values
    private static final String table_classes = "Classes";
    private static final String column_className = "className";
    private static final String column_classLocation = "location";
    private static final String column_classTeacher = "teacher";
    private static final String column_classDay1 = "day1";
    private static final String column_classDay1_time = "day1_time";
    private static final String column_classDay2 = "day2";
    private static final String column_classDay2_time = "day2_time";
    private static final String column_classDay3 = "day3";
    private static final String column_classDay3_time = "day3_time";
    private static final String column_classDay4 = "day4";
    private static final String column_classDay4_time = "day4_time";
    private static final String column_classDay5 = "day5";
    private static final String column_classDay5_time = "day5_time";
    private static final String column_classDay6 = "day6";
    private static final String column_classDay6_time = "day6_time";
    private static final String column_classDay7 = "day7";
    private static final String column_classDay7_time = "day7_time";

    //tasks table values
    private static final String table_tasks = "Tasks";
    private static final String column_taskName = "taskName";
    private static final String column_taskAssocClass = "taskClass";
    private static final String column_taskDeadline = "taskDeadline";

    private static final String classCreationLine = "create table if not exists "
            + table_classes + "(" + table_ID + " integer primary key autoincrement, "
            + column_className + " TEXT, "
            + column_classLocation + " TEXT, "
            + column_classTeacher + " TEXT, "
            + column_classDay1 + " BOOL, "
            + column_classDay1_time + " TIME, "
            + column_classDay2 + " BOOL, "
            + column_classDay2_time + " TIME, "
            + column_classDay3 + " BOOL, "
            + column_classDay3_time + " TIME, "
            + column_classDay4 + " BOOL, "
            + column_classDay4_time + " TIME, "
            + column_classDay5 + " BOOL, "
            + column_classDay5_time + " TIME, "
            + column_classDay6 + " BOOL, "
            + column_classDay6_time + " TIME, "
            + column_classDay7 + " BOOL, "
            + column_classDay7_time + " TIME)";

    private static final String tasksCreationLine = "create table if not exists "
            + table_tasks + "(" + table_ID + " integer primary key autoincrement, "
            + column_taskName + " TEXT, "
            + column_taskAssocClass + " INTEGER, "
            + column_taskDeadline + " TIME)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(classCreationLine);
        db.execSQL(tasksCreationLine);
    }

    public databaseHandlerActivity(Context context){
        super(context, db_Name, null, db_Version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public void setClass(Subject subject){
        SQLiteDatabase classesDatabase = this.getWritableDatabase();
        ContentValues input = new ContentValues();
        input.put(column_className, subject.getName());
        input.put(column_classLocation, subject.getLocation());
        input.put(column_classTeacher, subject.getTeacher());
        input.put(column_classDay1, subject.isDay1());
        input.put(column_classDay1_time, subject.getDay1Time());
        input.put(column_classDay2, subject.isDay2());
        input.put(column_classDay2_time, subject.getDay2Time());
        input.put(column_classDay3, subject.isDay3());
        input.put(column_classDay3_time, subject.getDay3Time());
        input.put(column_classDay4, subject.isDay4());
        input.put(column_classDay4_time, subject.getDay4Time());
        input.put(column_classDay5, subject.isDay5());
        input.put(column_classDay5_time, subject.getDay5Time());
        input.put(column_classDay6, subject.isDay6());
        input.put(column_classDay6_time, subject.getDay6Time());
        input.put(column_classDay7, subject.isDay7());
        input.put(column_classDay7_time, subject.getDay7Time());

        classesDatabase.insert(table_classes, null, input);
    }

    public void setTask(Task task){
        SQLiteDatabase tasksDatabase = this.getWritableDatabase();
        ContentValues input = new ContentValues();

        input.put(column_taskName, task.getName());
        input.put(column_taskAssocClass, task.getAssoClass());
        input.put(column_taskDeadline, task.getDeadline());

        tasksDatabase.insert(table_tasks, null, input);
    }



    public ArrayList<Subject> getClassesOnDay(String day){
        Log.i(tag, "getClassesOnDay - running");

        SQLiteDatabase classesDatabase = this.getReadableDatabase();

        String sortColumn = "";
        switch(day){
            case "day1": sortColumn = column_classDay1_time; break;
            case "day2": sortColumn = column_classDay2_time; break;
            case "day3": sortColumn = column_classDay3_time; break;
            case "day4": sortColumn = column_classDay4_time; break;
            case "day5": sortColumn = column_classDay5_time; break;
            case "day6": sortColumn = column_classDay6_time; break;
            case "day7": sortColumn = column_classDay7_time; break;
        }
        Cursor cursor = classesDatabase.rawQuery("select * from " + table_classes + " where " + day + "= 1" + " order by " + sortColumn + " asc", null);

        ArrayList<Subject> classesOnDay = new ArrayList<>();

        int count = cursor.getCount();
        Log.i(tag, "getClassesOnDay - selected classes total # = " + count);

        cursor.moveToFirst();
        for (int i = 0; i < count; i++) {

            Subject subject = new Subject();
            subject.setId(cursor.getInt(0));
            subject.setName(cursor.getString(1));
            subject.setLocation(cursor.getString(2));
            subject.setTeacher(cursor.getString(3));

            switch(day){
                case "day1": subject.setDay1Time(cursor.getString(5));
                case "day2": subject.setDay2Time(cursor.getString(7));
                case "day3": subject.setDay3Time(cursor.getString(9));
                case "day4": subject.setDay4Time(cursor.getString(11));
                case "day5": subject.setDay5Time(cursor.getString(13));
                case "day6": subject.setDay6Time(cursor.getString(15));
                case "day7": subject.setDay7Time(cursor.getString(17));
            }

            classesOnDay.add(subject);
            Log.i(tag, "getClassesOnDay - selected class #" + subject.getId() + " - name: " + subject.getName());

            cursor.moveToNext();
        }

        return classesOnDay;
    }

    public ArrayList<Task> getTasks(){
        Log.i(tag, "getTasks - running");

        SQLiteDatabase tasksDatabase = this.getReadableDatabase();

        Cursor cursor = tasksDatabase.rawQuery("select * from " + table_tasks, null);

        ArrayList<Task> tasks = new ArrayList<>();

        int count = cursor.getCount();
        Log.i(tag, "getTasks - selected tasks total # = " + count);

        cursor.moveToFirst();
        for (int i = 0; i < count; i++) {
            Task task = new Task();
            task.setId(cursor.getInt(0));
            task.setName(cursor.getString(1));
            task.setAssoClass(cursor.getString(2));
            task.setDeadline(cursor.getString(3));

            tasks.add(task);
            Log.i(tag, "getTasks - selected task #" + task.getId() + " - name: " + task.getName());

            cursor.moveToNext();
        }

        return tasks;
    }

    public Task getTask(int id){
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from " + table_tasks + " where _id = " + id, null);
        cursor.moveToFirst();

        Task task = new Task();
        task.setId(cursor.getInt(0));
        task.setName(cursor.getString(1));
        task.setAssoClass(cursor.getString(2));
        task.setDeadline(cursor.getString(3));

        return task;
    }

    public Subject getClass(int id){
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from " + table_classes + " where _id = " + id, null);
        cursor.moveToFirst();

        Subject subject = new Subject();
        subject.setId(cursor.getInt(0));
        subject.setName(cursor.getString(1));
        subject.setLocation(cursor.getString(2));
        subject.setTeacher(cursor.getString(3));

        return subject;
    }

    public void deleteAll(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from " + table_classes);
        database.execSQL("delete from " + table_tasks);
    }

    public void deleteTask(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from " + table_tasks + " where _id = " + id);
    }

    public void deleteClass(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from " + table_classes + " where _id = " + id);
    }



}
