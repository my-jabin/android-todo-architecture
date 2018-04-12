package com.jiujiu.mytodoapp.ui.tasks;

import android.databinding.DataBindingUtil;
import android.provider.ContactsContract;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiujiu.mytodoapp.R;
import com.jiujiu.mytodoapp.databinding.TaskItemBinding;
import com.jiujiu.mytodoapp.db.model.Task;

import java.util.List;
import java.util.Objects;


public class TasksRecyclerViewAdapter extends RecyclerView.Adapter<TasksRecyclerViewAdapter.ViewHolder> {

    private List<Task> mTasks;
    private TasksCallback mCallback;
    public TasksRecyclerViewAdapter(TasksCallback callback) {
        mCallback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TaskItemBinding mBinding =  DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.task_item,parent,false);
        return new ViewHolder(mBinding);
    }

    public void setTasks(List<Task> tasks){
        if(tasks == null)
            return;
        if(mTasks == null || mTasks.size() == 0){
            mTasks = tasks;
            notifyItemRangeInserted(0,tasks.size());
        }else{
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mTasks.size();
                }

                @Override
                public int getNewListSize() {
                    return tasks.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mTasks.get(oldItemPosition).getId().equals(tasks.get(newItemPosition).getId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Task oldTask = mTasks.get(oldItemPosition);
                    Task newTask = tasks.get(newItemPosition);
                    return Objects.equals(oldTask.getId(),newTask.getId()) &&
                            Objects.equals(oldTask.getTitle(),newTask.getTitle()) &&
                            Objects.equals(oldTask.getDescription(),newTask.getDescription()) &&
                            oldTask.isCompleted() == newTask.isCompleted();
                }
            });
            mTasks = tasks;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.binding.setTask( mTasks.get(position));
        holder.binding.setCallback(mCallback);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mTasks ==  null ? 0 : mTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TaskItemBinding binding;
        public ViewHolder(TaskItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
