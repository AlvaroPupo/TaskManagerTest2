package com.skills.interapt.taskmanagerandroid;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDaoTab2 {

    @Query("SELECT * FROM TaskNotCompleted")
    List<TaskNotCompleted> getAllTasksNotCompleted();

    @Insert
    void addTasksTab2(TaskNotCompleted taskNotCompleted);

    @Update
    void updateTaskListNotC(TaskNotCompleted taskNotCompleted);

    @Delete
    void deleteTaskListNotC(TaskNotCompleted taskNotCompleted);

}
