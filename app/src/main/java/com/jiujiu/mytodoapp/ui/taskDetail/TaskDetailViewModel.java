package com.jiujiu.mytodoapp.ui.taskDetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;

import com.google.common.base.Strings;
import com.jiujiu.mytodoapp.TodoApp;
import com.jiujiu.mytodoapp.db.DataRepository;
import com.jiujiu.mytodoapp.db.model.Task;

public class TaskDetailViewModel extends AndroidViewModel {

    private DataRepository mRepository;
    private final MutableLiveData<String> mTaskId = new MutableLiveData<>();
    public final LiveData<Task> task = Transformations.switchMap(mTaskId, id -> mRepository.getTask(id));
    public final LiveData<String> taskTitle = Transformations.map(task, task -> task == null ? "" : task.getTitle());


    public TaskDetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((TodoApp) application).getDataRepository();
    }


    public void setTaskId(String taskId) {
        if (!Strings.isNullOrEmpty(taskId))
            mTaskId.setValue(taskId);
    }

    public void completeTask(Task task, View view) {
        mRepository.completeTask(task, ((CheckBox) view).isChecked());
    }

    public void deleteTask() {
        mRepository.deleteTask(mTaskId.getValue());
    }
}
