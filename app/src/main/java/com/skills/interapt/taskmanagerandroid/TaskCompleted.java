package com.skills.interapt.taskmanagerandroid;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TaskCompleted {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String taskTitleDone;
    private String taskDescriptionDone;
    private boolean isCompletedDone;

    private String dateDueDone;
    private String timeDueDone;
    private String dateCreatedDone;

    public TaskCompleted(String taskTitleDone, String taskDescriptionDone, boolean isCompletedDone, String dateDueDone, String timeDueDone, String dateCreatedDone) {
        this.taskTitleDone = taskTitleDone;
        this.taskDescriptionDone = taskDescriptionDone;
        this.isCompletedDone = isCompletedDone;
        this.dateDueDone = dateDueDone;
        this.timeDueDone = timeDueDone;
        this.dateCreatedDone = dateCreatedDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskTitleDone() {
        return taskTitleDone;
    }

    public void setTaskTitleDone(String taskTitleDone) {
        this.taskTitleDone = taskTitleDone;
    }

    public String getTaskDescriptionDone() {
        return taskDescriptionDone;
    }

    public void setTaskDescriptionDone(String taskDescriptionDone) {
        this.taskDescriptionDone = taskDescriptionDone;
    }

    public boolean isCompletedDone() {
        return isCompletedDone;
    }

    public void setCompletedDone(boolean completedDone) {
        isCompletedDone = completedDone;
    }

    public String getDateDueDone() {
        return dateDueDone;
    }

    public void setDateDueDone(String dateDueDone) {
        this.dateDueDone = dateDueDone;
    }

    public String getTimeDueDone() {
        return timeDueDone;
    }

    public void setTimeDueDone(String timeDueDone) {
        this.timeDueDone = timeDueDone;
    }

    public String getDateCreatedDone() {
        return dateCreatedDone;
    }

    public void setDateCreatedDone(String dateCreatedDone) {
        this.dateCreatedDone = dateCreatedDone;
    }
}
