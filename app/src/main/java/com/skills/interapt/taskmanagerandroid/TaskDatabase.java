package com.skills.interapt.taskmanagerandroid;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(version = 1, entities = {Tasks.class} )
abstract class TaskDatabase extends RoomDatabase {


    public abstract TaskDao taskDao();

}



