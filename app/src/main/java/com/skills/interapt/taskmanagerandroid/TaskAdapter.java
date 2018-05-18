package com.skills.interapt.taskmanagerandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Tasks> tasksList;


    private AdapterCallback adapterCallback;
    private Tasks tasks;

    public TaskAdapter(List<Tasks> tasksList, AdapterCallback adapterCallback) {
        this.tasksList = tasksList;
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTask(tasksList.get(position));

        holder.itemView.setOnClickListener(holder.onClick(tasksList.get(position)));
        holder.itemView.setOnLongClickListener(holder.onLongClick(tasksList.get(position)));
        holder.switchTasks.setOnCheckedChangeListener(holder.onSwithClicked(tasksList.get(position)));
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public void updateList(List<Tasks> list) {
        tasksList = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

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
        @BindView(R.id.switch_tasks)
        protected Switch switchTasks;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTask(Tasks tasks) {

            taskTitle.setText(tasks.getTaskTitle());
            taskDescription.setText(adapterCallback.getContext().getString(R.string.task_description, tasks.getTaskDescription()));
            taskDate.setVisibility(View.VISIBLE);
            taskDate.setText(adapterCallback.getContext().getString(R.string.task_due_date, tasks.getDateDue(), tasks.getTimeDue()));
            timeCreated.setText(adapterCallback.getContext().getString(R.string.created_on, tasks.getDateCreated()));

            if (tasks.isCompleted()) {
                    rowLayout.setBackgroundResource(R.color.colorPrimary);
                    Calendar calendar = Calendar.getInstance();
                    Date date = calendar.getTime();
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy - HH:mm a", Locale.US);
                    taskDate.setText(adapterCallback.getContext().getString(R.string.completed_on, formatter.format(date)));
                    switchTasks.setText("Mark As Not Completed?");
                } else {
                    rowLayout.setBackgroundResource(R.color.green);
                    switchTasks.setText("Mark As Completed?");
                }
            }


        public View.OnClickListener onClick(final Tasks tasks) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallback.rowClicked(tasks);
                }
            };
        }

        public View.OnLongClickListener onLongClick(final Tasks tasks) {
            return new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    adapterCallback.rowLongClicked(tasks);
                    return true;
                }
            };
        }

        public CompoundButton.OnCheckedChangeListener onSwithClicked(final Tasks tasks) {
            return new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    adapterCallback.onSwitchClicked(tasks);
                }
            };
        }
    }


    public interface AdapterCallback {
        Context getContext();
        void rowClicked(Tasks tasks);
        void rowLongClicked(Tasks tasks);
        void onSwitchClicked(Tasks tasks);
    }
}
