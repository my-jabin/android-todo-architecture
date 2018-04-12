package com.jiujiu.mytodoapp.ui.tasks;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.jiujiu.mytodoapp.R;
import com.jiujiu.mytodoapp.Utils.ViewModelFactory;
import com.jiujiu.mytodoapp.databinding.TaskListBinding;
import com.jiujiu.mytodoapp.db.model.Task;

public class TasksFragment extends Fragment {
    private static final String TAG = "TasksFragment";

    private TasksViewModel mViewModel;
    private TaskListBinding mBinding;
    private TasksRecyclerViewAdapter mAdapter;

    private TasksCallback mCallback = new TasksCallback() {
        @Override
        public void onCompletedChanged(Task task, View view) {
            boolean checked = ((CheckBox) view).isChecked();
            mViewModel.completeTask(task, checked);
        }
        @Override
        public void onTaskClicked(Task task) {
            mViewModel.openTaskEvent(task.getId());
        }
    };


    public TasksFragment() {
    }

    public static TasksFragment newInstance() {
        TasksFragment fragment = new TasksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.task_list, container, false);
        mAdapter = new TasksRecyclerViewAdapter(mCallback);
        mBinding.taskRecycler.setAdapter(mAdapter);
        setHasOptionsMenu(true);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewModelProvider.Factory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        mViewModel = ViewModelProviders.of(getActivity(), factory).get(TasksViewModel.class);
        mViewModel.getTasks().observe(this, tasks -> {
            mAdapter.setTasks(tasks);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_task_all:
                mViewModel.showAllTasks();
                break;
            case R.id.menu_task_active:
                mViewModel.showActiveTasks();
                break;
            case R.id.menu_task_completed:
                mViewModel.showCompletedTasks();
                break;
            case R.id.menu_clear_completed:
                mViewModel.clearCompleted();
                break;
        }
        return true;
    }
}
