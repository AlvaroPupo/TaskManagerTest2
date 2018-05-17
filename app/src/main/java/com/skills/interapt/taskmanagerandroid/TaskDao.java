package com.skills.interapt.taskmanagerandroid;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDao {


    //Allow us to get all video games
    @Query("SELECT * FROM Tasks")
    List<Tasks> getTasks();

    //Allows us to add a single game to the list
    @Insert
    void addTasks(Tasks tasks);

    //Allows us to update the values of an existing game
    @Update
    void updateTaskList(Tasks tasks);

    //Allows us to delete a game from the library
    @Delete
    void deleteTaskList(Tasks tasks);


}

