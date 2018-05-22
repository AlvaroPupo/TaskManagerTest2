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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class Tab3Fragment extends Fragment implements TaskAdapterTab3.AdapterCallbackTab3, EditTasks.InfoCallbackTab3 {

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
        tab3Adapter = new TaskAdapterTab3(taskDatabaseTab3.taskDao().getTasks(), this);
        recyclerViewTab3.setLayoutManager(linearLayoutManagerTab3);
        recyclerViewTab3.setHasFixedSize(false);
        recyclerViewTab3.setAdapter(tab3Adapter);
        tab3Adapter.notifyDataSetChanged();
    }

    @Override
    public void rowClickedTab3(Tasks tasksCompleted) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(tasksCompleted.getTaskTitle())
                .setMessage("Created on : " + tasksCompleted.getDateCreated() + "\nDescription - " + tasksCompleted.getTaskDescription() + "\nCompleted: " + tasksCompleted.isCompleted())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
    public void editTasksButtonClicked(final Tasks tasks){

        editTasks = EditTasks.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelable("TASKS_COMPLETED", tasks);
        editTasks.setArguments(bundle);
        editTasks.attachParentEditTasksTab3(this);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.constraint_layout_tab3, editTasks).commit();
    }

    @Override
    public void getInfoTab3() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(editTasks).commit();
        tab3Adapter.updateList(taskDatabaseTab3.taskDao().getTasks());
        floatingActionButton.setVisibility(View.VISIBLE);
        tab3Adapter.notifyDataSetChanged();
    }

    @Override
    public void rowLongClickedTab3(final Tasks tasksCompleted) {

    }

    @Override
    public void onSwitchClickedTab3(Tasks taskCompleted) {
        if (taskCompleted.isCompleted()) {
            markAsNotCompleted(taskCompleted);
        }
    }

    @Override
    public void onOptionMenuButtonClicked(final Tasks tasks) {

        View v = getActivity().findViewById(R.id.option_menu_button);
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.inflate(R.menu.recycler_view_items_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_2:
                        editTasksButtonClicked(tasks);
                        break;
                    case R.id.item_3:
                        onDeleteButtonClicked(tasks);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
    public void onDeleteButtonClicked(final Tasks tasks){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete this Task?")
                .setMessage("Are you sure you would like to delete this task?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskDatabaseTab3.taskDao().deleteTaskList(tasks);
                        tab3Adapter.updateList(taskDatabaseTab3.taskDao().getTasks());
                        Toast.makeText(getActivity(), "Task Deleted!", Toast.LENGTH_LONG).show();
                        tab3Adapter.notifyDataSetChanged();
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

    private void markAsNotCompleted(final Tasks taskCompleted) {

        taskCompleted.setCompleted(false);
        taskDatabaseTab3.taskDao().updateTaskList(taskCompleted);
        tab3Adapter.updateList(taskDatabaseTab3.taskDao().getTasks());
        Toast.makeText(getActivity(), R.string.not_completed_mark, Toast.LENGTH_LONG).show();
        tab3Adapter.notifyDataSetChanged();

    }
}
