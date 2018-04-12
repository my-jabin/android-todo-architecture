package com.jiujiu.mytodoapp.ui.tasks;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.jiujiu.mytodoapp.TodoApp;
import com.jiujiu.mytodoapp.Utils.SingleLiveEvent;
import com.jiujiu.mytodoapp.db.DataRepository;
import com.jiujiu.mytodoapp.db.model.Task;

import java.util.List;
import java.util.stream.Collectors;

import static com.jiujiu.mytodoapp.ui.tasks.TasksFilterType.*;

public class TasksViewModel extends AndroidViewModel {

    private LiveData<List<Task>> mObservableTasks = null;
    private DataRepository mDataRepository;
    private SingleLiveEvent<Void> mNewTaskEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<String> mOpenTaskEvent = new SingleLiveEvent<>();
    private MutableLiveData<TasksFilterType> mFilterType = new MutableLiveData<>();
    private MediatorLiveData<List<Task>> mFilterTasks = new MediatorLiveData<>();

    public TasksViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = ((TodoApp) application).getDataRepository();
        mObservableTasks = mDataRepository.getTasks();
        showAllTasks();
        mFilterTasks.addSource(mObservableTasks, tasks -> mFilterTasks.postValue(getFilteredTasks(tasks, null)));
        mFilterTasks.addSource(mFilterType, type -> mFilterTasks.postValue(getFilteredTasks(null, type)));
    }

    public LiveData<List<Task>> getTasks() {
        return mFilterTasks;
    }

    private List<Task> getFilteredTasks(List<Task> tasks, TasksFilterType type) {
        tasks = tasks == null ? mObservableTasks.getValue() : tasks;
        type = type == null ? mFilterType.getValue() : type;
        switch (type) {
            case ACTIVE:
                return tasks.stream().filter(task -> !task.isCompleted()).collect(Collectors.toList());
            case COMPLETED:
                return tasks.stream().filter(task -> task.isCompleted()).collect(Collectors.toList());
            default:
                return tasks;
        }
    }

    public void addNewTask() {
        mNewTaskEvent.call();
    }

    public void openTaskEvent(String taskId) {
        mOpenTaskEvent.setValue(taskId);
    }

    public SingleLiveEvent<Void> getNewTaskEvent() {
        return mNewTaskEvent;
    }

    public SingleLiveEvent<String> getOpenTaskEvent() {
        return mOpenTaskEvent;
    }

    public void completeTask(Task task, boolean completed) {
        Task t = new Task(task.getId(), task.getTitle(), task.getDescription(), completed);
        mDataRepository.updateTask(t);
    }

    public void showAllTasks() {
        mFilterType.setValue(ALL);
    }

    public void showActiveTasks() {
        mFilterType.setValue(ACTIVE);
    }

    public void showCompletedTasks() {
        mFilterType.setValue(COMPLETED);
    }

    public void clearCompleted() {
        mDataRepository.clearCompleted();
    }
}
