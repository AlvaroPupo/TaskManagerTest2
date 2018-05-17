package com.skills.interapt.taskmanagerandroid;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDaoTab3 {

    @Query("SELECT * FROM TaskCompleted")
    List<TaskCompleted> getAllTasksCompleted();

    @Insert
    void addTasksTab3(TaskCompleted taskCompleted);

    @Update
    void updateTaskListCompleted(TaskCompleted taskCompleted);

    @Delete
    void deleteTaskListCompleted(TaskCompleted taskCompleted);
}
