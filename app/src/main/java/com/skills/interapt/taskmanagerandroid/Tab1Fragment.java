package com.skills.interapt.taskmanagerandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Tab1Fragment extends Fragment implements TaskAdapter.AdapterCallback, AddTaskFragment.ActivityCallback, EditTasks.InfoCallback {

    @BindView(R.id.recycler_view_tab1)
    protected RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private EditTasks editTasks;

    private TaskDatabase taskDatabase;
    private TaskAdapter taskAdapter;

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
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
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
    public void getInfo() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(editTasks).commit();
        taskAdapter.updateList(taskDatabase.taskDao().getTasks());
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }


    @Override
    public void rowClicked(Tasks tasks) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(tasks.getTaskTitle())
                .setMessage("Created on : " + tasks.getDateCreated() + "\nDescription - " + tasks.getTaskDescription() + "\nCompleted: " + tasks.isCompleted())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }

    public void editTasks(Tasks tasks){

        floatingActionButton.setVisibility(View.INVISIBLE);
        editTasks = EditTasks.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelable("TASK", tasks);
        editTasks.setArguments(bundle);
        editTasks.attachParentEditTasks(this);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contraint_layout_tab1, editTasks).commit();
    }

    @Override
    public void onSwitchClicked(Tasks tasks) {
        if (tasks.isCompleted()) {
            markAsNotCompleted(tasks);
        } else {
            markAsCompleted(tasks);
        }
    }

    @Override
    public void onOptionMenuButtonClicked(final Tasks tasks) {

        View v = getActivity().findViewById(R.id.option_menu_button);
        final PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.inflate(R.menu.recycler_view_items_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_2:
                        editTasks(tasks);
                        break;
                    case R.id.item_3:
                        deleteButtonClicked(tasks);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

        public void deleteButtonClicked(final Tasks tasks){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete this Task?")
                .setMessage("Are you sure you would like to delete this task?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskDatabase.taskDao().deleteTaskList(tasks);
                        taskAdapter.updateList(taskDatabase.taskDao().getTasks());
                        Toast.makeText(getActivity(), "Task Deleted!", Toast.LENGTH_LONG).show();
                        taskAdapter.notifyDataSetChanged();
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


    private void markAsCompleted(final Tasks tasks) {

        tasks.setCompleted(true);
        taskDatabase.taskDao().updateTaskList(tasks);
        taskAdapter.updateList(taskDatabase.taskDao().getTasks());

        Toast.makeText(getActivity(), "Task Completed!", Toast.LENGTH_LONG).show();

        taskAdapter.notifyDataSetChanged();
    }

    private void markAsNotCompleted(final Tasks tasks) {

        tasks.setCompleted(false);
        taskDatabase.taskDao().updateTaskList(tasks);
        taskAdapter.updateList(taskDatabase.taskDao().getTasks());

        Toast.makeText(getActivity(), R.string.not_completed_mark, Toast.LENGTH_LONG).show();
        taskAdapter.notifyDataSetChanged();
    }

    }






