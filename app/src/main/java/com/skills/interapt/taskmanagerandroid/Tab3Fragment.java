package com.skills.interapt.taskmanagerandroid;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Tab3Fragment extends Fragment implements TaskAdapterTab3.AdapterCallbackTab3, EditTasks.InfoCallbackTab3{

    private TaskDatabase taskDatabaseTab3;
    private TaskAdapterTab3 tab3Adapter;
    private LinearLayoutManager linearLayoutManagerTab3;
    private RecyclerView recyclerViewTab3;
    private EditTasks editTasks;
    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerViewTab3 = getActivity().findViewById(R.id.recycler_view_tab3);
        taskDatabaseTab3 = ((TaskApplication) getActivity().getApplicationContext()).getDatabase();
        floatingActionButton = getActivity().findViewById(R.id.add_tasks_fab);
        populateRecyclerView();
    }

    private void populateRecyclerView() {

        linearLayoutManagerTab3 = new LinearLayoutManager(getActivity().getApplicationContext());
        tab3Adapter = new TaskAdapterTab3(taskDatabaseTab3.taskDaoTab3().getAllTasksCompleted(), this);
        recyclerViewTab3.setLayoutManager(linearLayoutManagerTab3);
        recyclerViewTab3.setHasFixedSize(false);
        recyclerViewTab3.setAdapter(tab3Adapter);
        tab3Adapter.notifyDataSetChanged();
    }

    @Override
    public void rowClickedTab3(TaskCompleted tasksCompleted) {
        editTasks = EditTasks.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelable("TASKS_COMPLETED", tasksCompleted);
        editTasks.setArguments(bundle);
        editTasks.attachParentEditTasksTab3(this);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.constraint_layout_tab3, editTasks).commit();
    }
    @Override
    public void getInfoTab3() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(editTasks).commit();
        tab3Adapter.updateList(taskDatabaseTab3.taskDaoTab3().getAllTasksCompleted());
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void rowLongClickedTab3(final TaskCompleted tasksCompleted) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete this Task?")
                .setMessage("Are you sure you would like to delete this task?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskDatabaseTab3.taskDaoTab3().deleteTaskListCompleted(tasksCompleted);
                        tab3Adapter.updateList(taskDatabaseTab3.taskDaoTab3().getAllTasksCompleted());
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

    private void markAsCompleted(final TaskCompleted tasksIsCompleted) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Mark As Complete?")
                .setMessage("Are you sure you would like to mark this task as complete?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tasksIsCompleted.setCompletedDone(true);
                        //Update Database record for this game
                        taskDatabaseTab3.taskDaoTab3().updateTaskListCompleted(tasksIsCompleted);
                        //tell our adapter that the database has been updated so it will update our view.
                        tab3Adapter.updateList(taskDatabaseTab3.taskDaoTab3().getAllTasksCompleted());
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

    private void markAsNotCompleted(final TaskCompleted taskIsCompleted) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.mark_as_not_completed)
                .setMessage(R.string.mark_as_not_complete)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskIsCompleted.setCompletedDone(false);
                        //Update database with updated game information
                        taskDatabaseTab3.taskDaoTab3().updateTaskListCompleted(taskIsCompleted);
                        //Let our adapter know that information in the database has changed to update our view accordingly
                        tab3Adapter.updateList(taskDatabaseTab3.taskDaoTab3().getAllTasksCompleted());

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
}
