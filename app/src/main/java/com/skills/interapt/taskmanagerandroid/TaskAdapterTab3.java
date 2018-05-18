package com.skills.interapt.taskmanagerandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskAdapterTab3 extends RecyclerView.Adapter<TaskAdapterTab3.ViewHolder>{

    private List<TaskCompleted> tasksCompletedList;
    private AdapterCallbackTab3 adapterCallbackTab3;
    private TaskAdapter.AdapterCallback adapterCallback;

    public TaskAdapterTab3(List<TaskCompleted> tasksCompletedList, AdapterCallbackTab3 adapterCallbackTab3) {
        this.tasksCompletedList = tasksCompletedList;
        this.adapterCallbackTab3 = adapterCallbackTab3;
    }

    public TaskAdapterTab3(List<TaskCompleted> allTasksCompleted, TaskAdapter.AdapterCallback adapterCallback) {
        this.tasksCompletedList = allTasksCompleted;
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapterTab3.ViewHolder holder, int position) {

        holder.bindCompletedTasks(tasksCompletedList.get(position));
        holder.itemView.setOnClickListener(holder.onClick(tasksCompletedList.get(position)));
        holder.itemView.setOnLongClickListener(holder.onLongClick(tasksCompletedList.get(position)));
    }

    @Override
    public int getItemCount() {
        return tasksCompletedList.size();
    }

    public void updateList(List<TaskCompleted> list) {
        tasksCompletedList = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_row_layout)
        protected ConstraintLayout rowLayout;
        @BindView(R.id.item_title)
        protected TextView taskTitle;
        @BindView(R.id.item_date_due)
        protected TextView taskDate;
        @BindView(R.id.item_description)
        protected TextView taskDescription;
        @BindView(R.id.item_date_created)
        protected TextView timeCreated;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindCompletedTasks(TaskCompleted tasks) {


            if(tasks.isCompletedDone()) {
                    rowLayout.setVisibility(View.VISIBLE);
                    taskTitle.setText(tasks.getTaskTitleDone());
                    taskDescription.setText(adapterCallbackTab3.getContext().getString(R.string.task_description, tasks.getTaskDescriptionDone()));
                    taskDate.setVisibility(View.VISIBLE);
                    taskDate.setText(adapterCallbackTab3.getContext().getString(R.string.task_due_date, tasks.getDateDueDone(), tasks.getTimeDueDone()));
                    timeCreated.setText(adapterCallbackTab3.getContext().getString(R.string.created_on, tasks.getDateCreatedDone()));

                    rowLayout.setBackgroundResource(R.color.colorPrimary);
                    Calendar calendar = Calendar.getInstance();
                    Date date = calendar.getTime();
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy - HH:mm a", Locale.US);
                    taskDate.setText(adapterCallbackTab3.getContext().getString(R.string.completed_on, formatter.format(date)));
                } else {
                rowLayout.setVisibility(View.INVISIBLE);
            }
            }

        public View.OnClickListener onClick(final TaskCompleted tasks) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallbackTab3.rowClickedTab3(tasks);
                }
            };
        }

        public View.OnLongClickListener onLongClick(final TaskCompleted tasks) {
            return new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    adapterCallbackTab3.rowLongClickedTab3(tasks);
                    return true;
                }
            };
        }
    }
        public interface AdapterCallbackTab3{
            void rowClickedTab3(TaskCompleted tasks);
            void rowLongClickedTab3(TaskCompleted tasks);
            Context getContext();
        }
    }

