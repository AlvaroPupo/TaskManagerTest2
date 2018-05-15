package com.skills.interapt.taskmanagerandroid;

import android.app.Application;
import android.arch.persistence.room.Room;

public class TaskApplication extends Application{

    private TaskDatabase database;
    public static String DATABASE_NAME = "task_list_database";

    @Override
    public void onCreate() {
        super.onCreate();

        database = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
    }
    public TaskDatabase getDatabase() {
        return database;
    }

}
