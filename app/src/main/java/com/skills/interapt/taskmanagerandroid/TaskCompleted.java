package com.skills.interapt.taskmanagerandroid;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class TaskCompleted implements Parcelable{

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

    protected TaskCompleted(Parcel in) {
        id = in.readInt();
        taskTitleDone = in.readString();
        taskDescriptionDone = in.readString();
        isCompletedDone = in.readByte() != 0;
        dateDueDone = in.readString();
        timeDueDone = in.readString();
        dateCreatedDone = in.readString();
    }

    public static final Creator<TaskCompleted> CREATOR = new Creator<TaskCompleted>() {
        @Override
        public TaskCompleted createFromParcel(Parcel in) {
            return new TaskCompleted(in);
        }

        @Override
        public TaskCompleted[] newArray(int size) {
            return new TaskCompleted[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(taskTitleDone);
        dest.writeString(taskDescriptionDone);
        dest.writeByte((byte) (isCompletedDone ? 1 : 0));
        dest.writeString(dateDueDone);
        dest.writeString(timeDueDone);
        dest.writeString(dateCreatedDone);
    }
}
