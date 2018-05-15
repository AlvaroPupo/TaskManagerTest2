package com.skills.interapt.taskmanagerandroid;

import android.content.Context;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Tab1Fragment extends Fragment implements TaskAdapter.AdapterCallback, AddTaskFragment.ActivityCallback {

    @BindView(R.id.recycler_view_tab1)
    protected RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private TaskDatabase taskDatabase;
    private TaskAdapter taskAdapter;
    //    private List<Tasks> videoGameList;
    private LinearLayoutManager linearLayoutManager;
    private AddTaskFragment addTaskFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        floatingActionButton = getActivity().findViewById(R.id.add_tasks_fab);
        taskDatabase = ((TaskApplication) getContext()).getDatabase();
        setUpRecyclerView();
    }


    private void setUpRecyclerView() {

        linearLayoutManager = new LinearLayoutManager(getContext());
        taskAdapter = new TaskAdapter(taskDatabase.taskDao().getTasks(), this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.add_tasks_fab)
    protected void addTaskFloatingActionButton() {

        addTaskFragment = AddTaskFragment.newInstance();
        addTaskFragment.attachParent(this);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contraint_layout_tab1, addTaskFragment).commit();


    }

    @Override
    public void addClicked() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(addTaskFragment).commit();
        taskAdapter.updateList(taskDatabase.taskDao().getTasks());
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }


    @Override
    public void rowClicked(Tasks tasks) {
        if (tasks.isCompleted()) {
            checkGameBackIn(tasks);
        } else {
            checkGameOut(tasks);
        }
    }


    @Override
    public void rowLongClicked(final Tasks tasks) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete this Task?")
                .setMessage("Are you sure you would like to delete this task?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskDatabase.taskDao().deleteTaskList(tasks);
                        taskAdapter.updateList(taskDatabase.taskDao().getTasks());
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

    private void checkGameOut(final Tasks tasks) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Checkout Game?")
                .setMessage("Are you sure you would like to check out this game?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tasks.setCompleted(true);
                        //Update Database record for this game
                        taskDatabase.taskDao().updateTaskList(tasks);
                        //tell our adapter that the database has been updated so it will update our view.
                        taskAdapter.updateList(taskDatabase.taskDao().getTasks());
                        //Tell users that this game has been checked out
                        Toast.makeText(getActivity(), "Game Checked Out!", Toast.LENGTH_LONG).show();

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

    private void checkGameBackIn(final Tasks tasks) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.check_in_game)
                .setMessage(R.string.check_in_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tasks.setCompleted(false);
                        //Update database with updated game information
                        taskDatabase.taskDao().updateTaskList(tasks);
                        //Let our adapter know that information in the database has changed to update our view accordingly
                        taskAdapter.updateList(taskDatabase.taskDao().getTasks());

                        Toast.makeText(getActivity(), R.string.game_checked_in, Toast.LENGTH_LONG).show();
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
