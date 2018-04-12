package com.jiujiu.mytodoapp.ui.addeditTask;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.util.StringUtil;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Strings;
import com.jiujiu.mytodoapp.TodoApp;
import com.jiujiu.mytodoapp.Utils.SingleLiveEvent;
import com.jiujiu.mytodoapp.db.DataRepository;
import com.jiujiu.mytodoapp.db.model.Task;

public class AddEditTaskViewModel extends AndroidViewModel {
    private static final String TAG = "AddEditTaskViewModel";


    private SingleLiveEvent saveTaskEvent = new SingleLiveEvent();
    private MediatorLiveData<Task> mObservableTask = new MediatorLiveData<>();
    private DataRepository mRepository;
    private String mTaskId;
    private boolean isNewTask = false;
    private LiveData<Task> mTask;

    public AddEditTaskViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((TodoApp) application).getDataRepository();
    }

    public void loadTask(String id) {
        if (!Strings.isNullOrEmpty(id)) {
            if (id.equals(mTaskId)) {
                return;
            }
            if (mTask != null) {
                mObservableTask.removeSource(mTask);
            }
            mTask = mRepository.getTask(id);
            mTaskId = id;
            mObservableTask.addSource(mTask, mObservableTask::postValue);
        }else{
            isNewTask = true;
        }
    }

    public LiveData<Task> getTask(){
        return mObservableTask;
    }

    public void callSaveTaskEvent() {
        saveTaskEvent.call();
    }

    public SingleLiveEvent getSaveTaskEvent() {
        return saveTaskEvent;
    }

    public void saveTask(String title, String description) {
        if(isNewTask){
            Task task = new Task(title,description,false);
            mRepository.insertTask(task);
        }else{
            Task t =  mTask.getValue();
            Log.d(TAG, "saveTask: title =  " + t.getTitle() + ", description= " + t.getDescription());
            Task task = new Task(t.getId(),title,description,t.isCompleted());
            mRepository.updateTask(task);
        }
    }
}
