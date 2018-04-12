package com.jiujiu.mytodoapp.ui.statistic;


import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiujiu.mytodoapp.R;
import com.jiujiu.mytodoapp.databinding.StatisticFragBinding;


public class StatisticFragment extends Fragment {
    private static final String TAG = "StatisticFragment";
    private StatisticFragBinding mBinding;
    private StatisticViewModel mViewModel;

    public StatisticFragment() {
        // Required empty public constructor
    }

    public static StatisticFragment newInstance(){
        return new StatisticFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.statistic_frag, container, false);
        if(mBinding == null){
            mBinding = DataBindingUtil.bind(view);
        }
        mViewModel = StatisticActivity.obtainViewModel(getActivity());
        mViewModel.isEmpty().observe(this, empty -> {
            mBinding.setIsEmpty(empty);
            mBinding.executePendingBindings();
        });
        mViewModel.getActiveStatistic().observe(this, s -> {
            mBinding.tvNumberActiveTask.setText(s);
            mBinding.executePendingBindings();
        });
        mViewModel.getCompletedStatistic().observe(this, s -> {
            mBinding.tvNumberCompletedTask.setText(s);
            mBinding.executePendingBindings();
        });
        return mBinding.getRoot();
    }

}
