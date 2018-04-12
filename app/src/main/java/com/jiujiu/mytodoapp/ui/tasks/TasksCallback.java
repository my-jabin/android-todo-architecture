package com.jiujiu.mytodoapp.ui.tasks;

import android.view.View;

import com.jiujiu.mytodoapp.db.model.Task;

public interface TasksCallback {

    void onCompletedChanged(Task task, View view);

    void onTaskClicked(Task task);
}
