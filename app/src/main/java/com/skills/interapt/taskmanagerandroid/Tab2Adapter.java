package com.skills.interapt.taskmanagerandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Tab2Adapter extends RecyclerView.Adapter<Tab2Adapter.ViewHolder>{

    private List<Tasks> tasksNotCompletedList;
    private AdapterCallbackTab2 adapterCallbackTab2;

    public Tab2Adapter(List<Tasks> tasksNotCompletedList, AdapterCallbackTab2 adapterCallbackTab2) {
        this.tasksNotCompletedList = tasksNotCompletedList;
        this.adapterCallbackTab2 = adapterCallbackTab2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTasksNotCompleted(tasksNotCompletedList.get(position));
        holder.itemView.setOnClickListener(holder.onClick(tasksNotCompletedList.get(position)));
        holder.itemView.setOnLongClickListener(holder.onLongClick(tasksNotCompletedList.get(position)));
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

        @BindView(R.id.item_title)
        protected TextView taskNotCompletedTitle;
        @BindView(R.id.item_description)
        protected TextView taskNotCompletedDescription;
        @BindView(R.id.item_date_created)
        protected TextView taskNotCompletedDateCreated;
        @BindView(R.id.item_date_due)
        protected TextView taskNotCompletedDateDue;
        @BindView(R.id.item_row_layout)
        protected ConstraintLayout rowLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTasksNotCompleted(Tasks tasksNotCompleted) {

            taskNotCompletedTitle.setText(tasksNotCompleted.getTaskTitle());
            taskNotCompletedDescription.setText(adapterCallbackTab2.getContextTab2().getString(R.string.task_description, tasksNotCompleted.getTaskDescription()));
            taskNotCompletedDateDue.setVisibility(View.VISIBLE);
            taskNotCompletedDateDue.setText(adapterCallbackTab2.getContextTab2().getString(R.string.task_due_date, tasksNotCompleted.getDateDue(), tasksNotCompleted.getTimeDue()));
            taskNotCompletedDateCreated.setText(adapterCallbackTab2.getContextTab2().getString(R.string.created_on, tasksNotCompleted.getDateCreated()));
            //todo
            //todo
            //todo
            //todo
            //todo
            //todo

        }

        public View.OnClickListener onClick(final Tasks tasksNotCompleted) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallbackTab2.rowClickedTab2(tasksNotCompleted);
                }
            };
        }
        public View.OnLongClickListener onLongClick(final Tasks tasksNotCompleted){
            return new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    adapterCallbackTab2.rowLongClickedTab2(tasksNotCompleted);
                    return true;
                }
            };
        }
    }
    public interface AdapterCallbackTab2 {
        Context getContextTab2();
        void rowClickedTab2(Tasks tasksNotCompleted);
        void rowLongClickedTab2(Tasks tasksNotCompleted);
    }
}
