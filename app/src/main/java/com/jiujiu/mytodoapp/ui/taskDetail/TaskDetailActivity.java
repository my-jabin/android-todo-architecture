package com.jiujiu.mytodoapp.ui.taskDetail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jiujiu.mytodoapp.R;
import com.jiujiu.mytodoapp.Utils.ViewModelFactory;
import com.jiujiu.mytodoapp.ui.addeditTask.AddEditTaskActivity;
import com.jiujiu.mytodoapp.ui.addeditTask.AddeditTaskFragment;

public class TaskDetailActivity extends AppCompatActivity {

    private TaskDetailViewModel mViewModel;
    private String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        taskId =  getIntent().getStringExtra(TaskDetailFragment.TASKDETAILID);
        setupToolBar();

        setupUIComponent();

        setupFragment();

        setupViewModel();
    }

    private void setupViewModel() {
        mViewModel = obtainViewModel(this);
        mViewModel.taskTitle.observe(this, title -> {
            getSupportActionBar().setTitle(title);
        });
    }


    public static TaskDetailViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelProvider.Factory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(TaskDetailViewModel.class);
    }

    private void setupFragment() {
        TaskDetailFragment fragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_container);
        if (fragment == null) {
            fragment = TaskDetailFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString(TaskDetailFragment.TASKDETAILID, taskId);
            fragment.setArguments(bundle);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content_container, fragment, null).commit();
    }

    private void setupUIComponent() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskDetailActivity.this, AddEditTaskActivity.class);
                intent.putExtra(AddeditTaskFragment.ARGUMENT_ADD_EDIT_TASK_ID,taskId);
                startActivity(intent);
            }
        });

    }


    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
