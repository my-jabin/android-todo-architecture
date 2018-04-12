package com.jiujiu.mytodoapp.ui.taskDetail;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jiujiu.mytodoapp.R;
import com.jiujiu.mytodoapp.databinding.TaskDetailBinding;

import javax.security.auth.callback.Callback;


public class TaskDetailFragment extends Fragment {
    private static final String TAG = "TaskDetailFragment";
    public static final String TASKDETAILID = "task_detail_id";
    private TaskDetailViewModel mViewModel;
    private TaskDetailBinding mBinding;

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    public static TaskDetailFragment newInstance(){
        return new TaskDetailFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding =  DataBindingUtil.inflate(inflater,R.layout.task_detail, container,false);
        mViewModel = TaskDetailActivity.obtainViewModel(getActivity());
        mBinding.setViewModel(mViewModel);
        mViewModel.task.observe(getActivity(), task -> mBinding.setTask(task));
        setHasOptionsMenu(true);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.setTaskId( getArguments().getString(TASKDETAILID));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_task_detail,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_task_delete:
                mViewModel.deleteTask();
                getActivity().finish();
                return true;
        }
        return false;
    }
}
