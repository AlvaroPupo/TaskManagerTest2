package com.skills.interapt.taskmanagerandroid;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TaskNotCompleted {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String taskTitleNotC;
    private String taskDescriptionNotC;
    private boolean isCompletedNotC;

    private String dateDueNotC;
    private String timeDueNotC;
    private String dateCreatedNotC;

    public TaskNotCompleted(String taskTitleNotC, String taskDescriptionNotC, boolean isCompletedNotC, String dateDueNotC, String timeDueNotC, String dateCreatedNotC) {
        this.taskTitleNotC = taskTitleNotC;
        this.taskDescriptionNotC = taskDescriptionNotC;
        this.isCompletedNotC = isCompletedNotC;
        this.dateDueNotC = dateDueNotC;
        this.timeDueNotC = timeDueNotC;
        this.dateCreatedNotC = dateCreatedNotC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskTitleNotC() {
        return taskTitleNotC;
    }

    public void setTaskTitleNotC(String taskTitleNotC) {
        this.taskTitleNotC = taskTitleNotC;
    }

    public String getTaskDescriptionNotC() {
        return taskDescriptionNotC;
    }

    public void setTaskDescriptionNotC(String taskDescriptionNotC) {
        this.taskDescriptionNotC = taskDescriptionNotC;
    }

    public boolean isCompletedNotC() {
        return isCompletedNotC;
    }

    public void setCompletedNotC(boolean completedNotC) {
        isCompletedNotC = completedNotC;
    }

    public String getDateDueNotC() {
        return dateDueNotC;
    }

    public void setDateDueNotC(String dateDueNotC) {
        this.dateDueNotC = dateDueNotC;
    }

    public String getTimeDueNotC() {
        return timeDueNotC;
    }

    public void setTimeDueNotC(String timeDueNotC) {
        this.timeDueNotC = timeDueNotC;
    }

    public String getDateCreatedNotC() {
        return dateCreatedNotC;
    }

    public void setDateCreatedNotC(String dateCreatedNotC) {
        this.dateCreatedNotC = dateCreatedNotC;
    }
}
