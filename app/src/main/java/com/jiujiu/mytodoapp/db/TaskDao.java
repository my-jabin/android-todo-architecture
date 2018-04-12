package com.jiujiu.mytodoapp.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jiujiu.mytodoapp.db.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("select * from tasks")
    LiveData<List<Task>> getAllTask();

    @Query("select * from tasks where id = :id")
    LiveData<Task> getTask(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Task task);

    @Update
    void update(Task task);

    @Query("update tasks set completed = :completed where id=:id")
    void updateCompleted(String id, boolean completed);

    @Delete
    void delete(Task task);

    @Query("delete from tasks where id=:id")
    int deleteById(String id);

    @Query("SELECT COUNT() FROM tasks WHERE completed = 1")
    LiveData<Integer> numberOfCompleted();

    @Query("SELECT COUNT() FROM tasks WHERE completed = 0")
    LiveData<Integer> numberOfActive();

    @Query("Delete from tasks WHERE completed = 1")
    int clearCompleted();
}
