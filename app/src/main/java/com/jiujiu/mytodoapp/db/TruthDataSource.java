package com.jiujiu.mytodoapp.db;

import android.arch.lifecycle.LiveData;

import com.jiujiu.mytodoapp.Utils.AppExecutor;
import com.jiujiu.mytodoapp.db.model.Task;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TruthDataSource {

    private static TruthDataSource mInstance;
    private TaskDao mTaskDao;
    private AppExecutor mExecutor;

    private TruthDataSource(TaskDao taskDao, AppExecutor executor) {
        this.mTaskDao = taskDao;
        this.mExecutor = executor;
    }

    public static TruthDataSource getInstance(TaskDao taskDao, AppExecutor executor) {
        if (mInstance == null) {
            synchronized (TruthDataSource.class) {
                if (mInstance == null) {
                    mInstance = new TruthDataSource(taskDao, executor);
                }
            }
        }
        return mInstance;
    }

    public void insertTask(Task task) {
        checkNotNull(task);
        mExecutor.diskExecutor().execute(() -> mTaskDao.insert(task));
    }

    public LiveData<List<Task>> getTasks() {
        return mTaskDao.getAllTask();
    }

    public void completeTask(Task task, boolean completed) {
        checkNotNull(task);
        mExecutor.diskExecutor().execute(() -> mTaskDao.updateCompleted(task.getId(), completed));
    }

    public LiveData<Task> getTask(String id) {
        return mTaskDao.getTask(id);
    }

    public void updateTask(Task task) {
        checkNotNull(task);
        mExecutor.diskExecutor().execute(() -> mTaskDao.update(task));

    }

    public void deleteTask(String taskId) {
        checkNotNull(taskId);
        mExecutor.diskExecutor().execute(() -> mTaskDao.deleteById(taskId));
    }

    public LiveData<Integer> numberOfCompleted() {
        return mTaskDao.numberOfCompleted();
    }

    public LiveData<Integer> numberOfActive() {
        return mTaskDao.numberOfActive();
    }

    public void clearCompleted() {
        mExecutor.diskExecutor().execute(() -> mTaskDao.clearCompleted());
    }
}
