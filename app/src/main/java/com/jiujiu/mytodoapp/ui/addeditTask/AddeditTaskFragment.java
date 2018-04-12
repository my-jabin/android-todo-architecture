package com.jiujiu.mytodoapp.ui.addeditTask;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.jiujiu.mytodoapp.R;
import com.jiujiu.mytodoapp.databinding.AddeditTaskFragBinding;

public class AddeditTaskFragment extends Fragment {

    public static final String ARGUMENT_ADD_EDIT_TASK_ID = "argument_task_id";

    private AddeditTaskFragBinding mBinding;
    private AddEditTaskViewModel mViewModel;

    public AddeditTaskFragment() {
        // Required empty public constructor
    }

    public static AddeditTaskFragment newInstance() {
        return new AddeditTaskFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.addedit_task_frag, container, false);
        setHasOptionsMenu(true);
        setRetainInstance(false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = AddEditTaskActivity.obtainViewModel(getActivity());
        mViewModel.loadTask(getArguments().getString(ARGUMENT_ADD_EDIT_TASK_ID));
        mViewModel.getTask().observe(getActivity(), task -> mBinding.setTask(task));

        mViewModel.getSaveTaskEvent().observe(getActivity(), o -> {
            String description = mBinding.etTaskDescription.getText().toString();
            String title = mBinding.etTaskTitle.getText().toString();
            if(Strings.isNullOrEmpty(title)){
                //Toast.makeText(getActivity(),"Title cannot be empty",Toast.LENGTH_SHORT).show();
                mBinding.etTaskTitle.setError("Title cannot be empty");
                mBinding.etTaskTitle.requestFocus();
                return;
            }
            mViewModel.saveTask(title, description);
            getActivity().finish();
        });
    }


}
