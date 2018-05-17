package com.skills.interapt.taskmanagerandroid;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Tab2Fragment extends Fragment implements TaskAdapterTab2.AdapterCallbackTab2, AddTaskFragment.ActivityCallback{

    private TaskDatabase taskDatabaseTab2;
    private TaskAdapterTab2 tab2Adapter;
    private LinearLayoutManager linearLayoutManagerTab2;
    private RecyclerView recyclerViewTab2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerViewTab2 = getActivity().findViewById(R.id.recyclerview_tab2);
        taskDatabaseTab2 = ((TaskApplication) getActivity().getApplicationContext()).getDatabase();
        populateRecyclerView();
    }


    private void populateRecyclerView() {

        linearLayoutManagerTab2 = new LinearLayoutManager(getContext());
        tab2Adapter = new TaskAdapterTab2(taskDatabaseTab2.taskDaoTab2().getAllTasksNotCompleted(),  this);
        recyclerViewTab2.setLayoutManager(linearLayoutManagerTab2);
        recyclerViewTab2.setHasFixedSize(false);
        recyclerViewTab2.setAdapter(tab2Adapter);
        tab2Adapter.notifyDataSetChanged();
    }
    @Override
    public void rowOnClickedTab2(TaskNotCompleted tasksNotCompleted) {
        if (!tasksNotCompleted.isCompletedNotC()) {
            markTaskAsCompleted(tasksNotCompleted);
        } else {
            markAsNotCompleted(tasksNotCompleted);
        }
    }

    @Override
    public void rowLongClickedTab2(final TaskNotCompleted tasksNotCompleted) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete this Task?")
                .setMessage("Are you sure you would like to delete this task?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskDatabaseTab2.taskDaoTab2().deleteTaskListNotC(tasksNotCompleted);
                        tab2Adapter.updateList(taskDatabaseTab2.taskDaoTab2().getAllTasksNotCompleted());
                        Toast.makeText(getActivity(), "Task Deleted!", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void markTaskAsCompleted(final TaskNotCompleted tasksNotCompleted) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Mark As Complete?")
                .setMessage("Are you sure you would like to mark this task as complete?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tasksNotCompleted.setCompletedNotC(true);
                        //Update Database record for this game
                        taskDatabaseTab2.taskDaoTab2().updateTaskListNotC(tasksNotCompleted);
                        //tell our adapter that the database has been updated so it will update our view.
                        tab2Adapter.updateList(taskDatabaseTab2.taskDaoTab2().getAllTasksNotCompleted());
                        //Tell users that this game has been checked out
                        Toast.makeText(getActivity(), "Task Completed", Toast.LENGTH_LONG).show();

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void markAsNotCompleted(final TaskNotCompleted tasksNotCompleted) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.mark_as_not_completed)
                .setMessage(R.string.mark_as_not_complete)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tasksNotCompleted.setCompletedNotC(false);
                        //Update database with updated game information
                        taskDatabaseTab2.taskDaoTab2().updateTaskListNotC(tasksNotCompleted);
                        //Let our adapter know that information in the database has changed to update our view accordingly
                        tab2Adapter.updateList(taskDatabaseTab2.taskDaoTab2().getAllTasksNotCompleted());

                        Toast.makeText(getActivity(), R.string.not_completed_mark, Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void addClicked() {
        tab2Adapter.updateList(taskDatabaseTab2.taskDaoTab2().getAllTasksNotCompleted());

    }
}
