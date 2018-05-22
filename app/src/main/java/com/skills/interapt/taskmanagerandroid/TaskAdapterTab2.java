package com.skills.interapt.taskmanagerandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskAdapterTab2 extends RecyclerView.Adapter<TaskAdapterTab2.ViewHolder>{

    private List<Tasks> tasksNotCompletedList;
    private AdapterCallbackTab2 adapterCallbackTab2;

    public TaskAdapterTab2(List<Tasks> tasksNotCompletedList, AdapterCallbackTab2 adapterCallbackTab2) {
        this.tasksNotCompletedList = tasksNotCompletedList;
        this.adapterCallbackTab2 = adapterCallbackTab2;
    }

    @NonNull
    @Override
    public TaskAdapterTab2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapterTab2.ViewHolder holder, int position) {
        holder.bindNotCompletedTasks(tasksNotCompletedList.get(position));

        holder.itemView.setOnClickListener(holder.onClick(tasksNotCompletedList.get(position)));
        holder.itemView.setOnLongClickListener(holder.onLongClick(tasksNotCompletedList.get(position)));
        holder.switchTasks.setOnCheckedChangeListener(holder.onSwitchClickedTab2(tasksNotCompletedList.get(position)));
        holder.optionMenuButton.setOnClickListener(holder.onMenuOptionButtonClicked(tasksNotCompletedList.get(position)));
    }

    @Override
    public int getItemCount() {
        return tasksNotCompletedList.size();
    }

    public void updateList(List<Tasks> list){
        tasksNotCompletedList = list;
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
        @BindView(R.id.option_menu_button)
        protected Button optionMenuButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindNotCompletedTasks(Tasks tasks) {

            rowLayout.setBackgroundResource(R.color.green);
            taskTitle.setText(tasks.getTaskTitle());
            taskDescription.setText(adapterCallbackTab2.getContext().getString(R.string.task_description, tasks.getTaskDescription()));
            taskDate.setVisibility(View.VISIBLE);
            taskDate.setText(adapterCallbackTab2.getContext().getString(R.string.task_due_date, tasks.getDateDue(), tasks.getTimeDue()));
            timeCreated.setText(adapterCallbackTab2.getContext().getString(R.string.created_on, tasks.getDateCreated()));
            switchTasks.setText("Not Completed!!");

            if (tasks.isCompleted()) {
                rowLayout.setVisibility(View.GONE);
            }
        }

        public View.OnClickListener onClick(final Tasks tasks) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallbackTab2.rowOnClickedTab2(tasks);
                }
            };
        }

        public View.OnLongClickListener onLongClick(final Tasks tasks) {
            return new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    adapterCallbackTab2.rowLongClickedTab2(tasks);
                    return true;
                }
            };
        }

        public CompoundButton.OnCheckedChangeListener onSwitchClickedTab2(final Tasks taskNotCompleted) {
            return new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    adapterCallbackTab2.onSwitchClickedTab2(taskNotCompleted);
                }
            };
        }

        public View.OnClickListener onMenuOptionButtonClicked(final Tasks tasks) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallbackTab2.onMenuOptionClicked(tasks);
                }
            };
        }
    }
    public interface AdapterCallbackTab2 {
        void rowOnClickedTab2(Tasks tasks);
        void rowLongClickedTab2(Tasks tasks);
        Context getContext();
        void onSwitchClickedTab2(Tasks taskNotCompleted);
        void onMenuOptionClicked(Tasks tasks);
    }

}
