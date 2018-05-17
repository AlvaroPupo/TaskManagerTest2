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

public class TaskAdapterTab2 extends RecyclerView.Adapter<TaskAdapterTab2.ViewHolder>{

    private List<TaskNotCompleted> tasksNotCompletedList;
    private AdapterCallbackTab2 adapterCallbackTab2;
    private MarkedAsCompleted markedAsCompleted;
    private TaskDatabase taskDatabase;

    public TaskAdapterTab2(List<TaskNotCompleted> tasksNotCompletedList, AdapterCallbackTab2 adapterCallbackTab2) {
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
    }

    @Override
    public int getItemCount() {
        return tasksNotCompletedList.size();
    }

    public void updateList(List<TaskNotCompleted> list){
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindNotCompletedTasks(TaskNotCompleted tasks) {

                taskTitle.setText(tasks.getTaskTitleNotC());
                taskDescription.setText(adapterCallbackTab2.getContext().getString(R.string.task_description, tasks.getTaskDescriptionNotC()));
                taskDate.setVisibility(View.VISIBLE);
                taskDate.setText(adapterCallbackTab2.getContext().getString(R.string.task_due_date, tasks.getDateDueNotC(), tasks.getTimeDueNotC()));
                timeCreated.setText(adapterCallbackTab2.getContext().getString(R.string.created_on, tasks.getDateCreatedNotC()));


            if (tasks.isCompletedNotC()) {

//                rowLayout.setBackgroundResource(R.color.colorPrimary);
//                Calendar calendar = Calendar.getInstance();
//                Date date = calendar.getTime();
//                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy - HH:mm a", Locale.US);
//                taskDate.setText(adapterCallbackTab2.getContext().getString(R.string.completed_on, formatter.format(date)));
//                TaskCompleted taskCompleted = new TaskCompleted(tasks.getTaskTitleNotC(),tasks.getTaskDescriptionNotC(),true, tasks.getDateDueNotC(),tasks.getTimeDueNotC(),tasks.getDateCreatedNotC());
//                addToCompletedTasksDatabase(taskCompleted);

            } else {
                rowLayout.setBackgroundResource(R.color.green);
            }
        }

        public View.OnClickListener onClick(final TaskNotCompleted tasks) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallbackTab2.rowOnClickedTab2(tasks);
                }
            };
        }

        public View.OnLongClickListener onLongClick(final TaskNotCompleted tasks) {
            return new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    adapterCallbackTab2.rowLongClickedTab2(tasks);
                    return true;
                }
            };
        }
//        private void addToCompletedTasksDatabase(final TaskCompleted taskCompleted){
//            markedAsCompleted.bindCompletedTasksTab3(taskCompleted);
//        }
    }
    public interface AdapterCallbackTab2 {
        void rowOnClickedTab2(TaskNotCompleted tasks);
        void rowLongClickedTab2(TaskNotCompleted tasks);
        Context getContext();
    }
    public interface MarkedAsCompleted{
            void bindCompletedTasksTab3(TaskCompleted taskCompleted);
    }
}
