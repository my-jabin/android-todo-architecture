package com.jiujiu.mytodoapp.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity(tableName = "tasks")
public final class Task {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private final String mId;

    @ColumnInfo(name = "title")
    private final String mTitle;

    @ColumnInfo(name = "description")
    private final String mDescription;

    @ColumnInfo(name = "completed")
    private final boolean mCompleted;

    public Task(String mId, String mTitle, String mDescription, boolean mCompleted) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mCompleted = mCompleted;
    }

    @Ignore
    public Task(String mTitle, String mDescription, boolean mCompleted) {
        this(UUID.randomUUID().toString(),mTitle,mDescription,mCompleted);
    }

    @Ignore
    public Task(String title, String description){
        this(title,description,false);
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    @Override
    public String toString() {
        return "Task{" +
                "mId='" + mId + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mCompleted=" + mCompleted +
                '}';
    }
}
