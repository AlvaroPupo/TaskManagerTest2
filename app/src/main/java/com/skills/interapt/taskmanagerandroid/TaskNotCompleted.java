package com.skills.interapt.taskmanagerandroid;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Switch;

@Entity
public class TaskNotCompleted implements Parcelable {

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

    protected TaskNotCompleted(Parcel in) {
        id = in.readInt();
        taskTitleNotC = in.readString();
        taskDescriptionNotC = in.readString();
        isCompletedNotC = in.readByte() != 0;
        dateDueNotC = in.readString();
        timeDueNotC = in.readString();
        dateCreatedNotC = in.readString();
    }

    public static final Creator<TaskNotCompleted> CREATOR = new Creator<TaskNotCompleted>() {
        @Override
        public TaskNotCompleted createFromParcel(Parcel in) {
            return new TaskNotCompleted(in);
        }

        @Override
        public TaskNotCompleted[] newArray(int size) {
            return new TaskNotCompleted[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(taskTitleNotC);
        dest.writeString(taskDescriptionNotC);
        dest.writeByte((byte) (isCompletedNotC ? 1 : 0));
        dest.writeString(dateDueNotC);
        dest.writeString(timeDueNotC);
        dest.writeString(dateCreatedNotC);
    }
}
