package com.skills.interapt.taskmanagerandroid;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;


@Entity
public class Tasks implements Parcelable{

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

    protected Tasks(Parcel in) {
        id = in.readInt();
        taskTitle = in.readString();
        taskDescription = in.readString();
        isCompleted = in.readByte() != 0;
        dateDue = in.readString();
        timeDue = in.readString();
        dateCreated = in.readString();
    }

    public static final Creator<Tasks> CREATOR = new Creator<Tasks>() {
        @Override
        public Tasks createFromParcel(Parcel in) {
            return new Tasks(in);
        }

        @Override
        public Tasks[] newArray(int size) {
            return new Tasks[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(taskTitle);
        dest.writeString(taskDescription);
        dest.writeByte((byte) (isCompleted ? 1 : 0));
        dest.writeString(dateDue);
        dest.writeString(timeDue);
        dest.writeString(dateCreated);
    }
}