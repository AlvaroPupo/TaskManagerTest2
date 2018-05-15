package com.skills.interapt.taskmanagerandroid;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.skills.interapt.taskmanagerandroid.DateConverter;
import com.skills.interapt.taskmanagerandroid.TaskDao;
import com.skills.interapt.taskmanagerandroid.Tasks;

@Database(version = 1, entities = Tasks.class)
@TypeConverters(DateConverter.class)
abstract class TaskDatabase extends RoomDatabase {


    public abstract TaskDao taskDao();

}



