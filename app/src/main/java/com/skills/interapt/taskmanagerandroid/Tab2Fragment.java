package com.skills.interapt.taskmanagerandroid;

import android.content.Context;
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

public class Tab2Fragment extends Fragment implements Tab2Adapter.AdapterCallbackTab2, AddTaskFragment.ActivityCallbackTab2{

    private TaskDatabase taskDatabaseTab2;
    private Tab2Adapter tab2Adapter;
    //    private List<Tasks> videoGameList;
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
        tab2Adapter = new Tab2Adapter(taskDatabaseTab2.taskDao().getTasks(), this);
        recyclerViewTab2.setLayoutManager(linearLayoutManagerTab2);
        recyclerViewTab2.setHasFixedSize(true);
        recyclerViewTab2.setAdapter(tab2Adapter);
        tab2Adapter.notifyDataSetChanged();
    }

    @Override
    public Context getContextTab2() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void rowClickedTab2(Tasks tasksNotCompleted) {
        if (tasksNotCompleted.isCompleted()) {
            checkGameBackIn(tasksNotCompleted);
        } else {
            checkGameOut(tasksNotCompleted);
        }
    }

    @Override
    public void rowLongClickedTab2(final Tasks tasksNotCompleted) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete this Task?")
                .setMessage("Are you sure you would like to delete this task?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskDatabaseTab2.taskDao().deleteTaskList(tasksNotCompleted);
                        tab2Adapter.updateList(taskDatabaseTab2.taskDao().getTasks());
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

    private void checkGameOut(final Tasks tasksNotCompleted) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Checkout Game?")
                .setMessage("Are you sure you would like to check out this game?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tasksNotCompleted.setCompleted(true);
                        //Update Database record for this game
                        taskDatabaseTab2.taskDao().updateTaskList(tasksNotCompleted);
                        //tell our adapter that the database has been updated so it will update our view.
                        tab2Adapter.updateList(taskDatabaseTab2.taskDao().getTasks());
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

    private void checkGameBackIn(final Tasks tasksNotCompleted) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.check_in_game)
                .setMessage(R.string.check_in_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tasksNotCompleted.setCompleted(false);
                        //Update database with updated game information
                        taskDatabaseTab2.taskDao().updateTaskList(tasksNotCompleted);
                        //Let our adapter know that information in the database has changed to update our view accordingly
                        tab2Adapter.updateList(taskDatabaseTab2.taskDao().getTasks());

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

    @Override
    public void activityCallbackTab2() {
        tab2Adapter.updateList(taskDatabaseTab2.taskDao().getTasks());
    }
}
