package com.jiujiu.mytodoapp.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.jiujiu.mytodoapp.db.model.Task;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;


public class DataRepository {

    private static DataRepository mInstance;
    private TruthDataSource mTruthDataSource;

    private MediatorLiveData<List<Task>> mObservableTasks ;

    private Map<String, LiveData<Task>> mCachedTasks;

    private DataRepository(TruthDataSource dataSource) {
        this.mTruthDataSource = dataSource;
    }

    private LiveData<Integer> numberOfCompleted = null;
    private LiveData<Integer> numberOfActive = null;

    public static DataRepository getInstance(TruthDataSource localDataSource) {
        if (mInstance == null) {
            synchronized (DataRepository.class) {
                if (mInstance == null) {
                    mInstance = new DataRepository(localDataSource);
                }
            }
        }
        return mInstance;
    }

    public LiveData<List<Task>> getTasks() {
        if(mObservableTasks == null){
            mObservableTasks = new MediatorLiveData<>();
            mObservableTasks.addSource(mTruthDataSource.getTasks(), tasks -> mObservableTasks.postValue(tasks));
        }
        return mObservableTasks;
    }

    public void insertTask(Task task){
        mTruthDataSource.insertTask(task);
    }

    public void updateTask(Task task){
        mTruthDataSource.updateTask(task);
    }

    public void deleteTask(String taskId){
        mTruthDataSource.deleteTask(taskId);
    }

    public void completeTask(Task task, boolean completed){
        mTruthDataSource.completeTask(task,completed);
    }

    public LiveData<Task> getTask(String id) {
        checkNotNull(id);

        if(mCachedTasks != null && mCachedTasks.containsKey(id)){
            return mCachedTasks.get(id);
        }
        LiveData<Task> task =  mTruthDataSource.getTask(id);
        if(mCachedTasks == null){
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.put(id,task);
        return task;
    }


    public LiveData<Integer> getNumberOfActive() {
        if(numberOfActive == null){
            numberOfActive = mTruthDataSource.numberOfActive();
        }
        return numberOfActive;
    }

    public LiveData<Integer> getNumberOfCompleted() {
        if(numberOfCompleted == null){
            numberOfCompleted = mTruthDataSource.numberOfCompleted();
        }
        return numberOfCompleted;
    }

    public void clearCompleted() {
        mTruthDataSource.clearCompleted();
    }
}
