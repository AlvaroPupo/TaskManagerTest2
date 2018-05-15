package com.skills.interapt.taskmanagerandroid;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;


@Entity
public class Tasks {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String taskTitle;
    private String taskDescription;
    private boolean isCompleted;

    private String dateDue;
    private String timeDue;
    private String dateCreated;

    public Tasks(String taskTitle, String taskDescription, String dateDue, String timeDue, String dateCreated) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.dateDue = dateDue;
        this.timeDue = timeDue;
        this.dateCreated = dateCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public String getTimeDue() {
        return timeDue;
    }

    public void setTimeDue(String timeDue) {
        this.timeDue = timeDue;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }


}