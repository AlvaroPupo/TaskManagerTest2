package com.skills.interapt.taskmanagerandroid;

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
import android.widget.PopupMenu;
import android.widget.Toast;

public class Tab2Fragment extends Fragment implements TaskAdapterTab2.AdapterCallbackTab2, EditTasks.InfoCallbackTab2{

    private TaskDatabase taskDatabaseTab2;
    private TaskAdapterTab2 tab2Adapter;
    private LinearLayoutManager linearLayoutManagerTab2;
    private RecyclerView recyclerViewTab2;
    private EditTasks editTasks;
    private FloatingActionButton floatingActionButton;

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
        floatingActionButton = getActivity().findViewById(R.id.add_tasks_fab);
        populateRecyclerView();
    }


    private void populateRecyclerView() {

        linearLayoutManagerTab2 = new LinearLayoutManager(getContext());
        tab2Adapter = new TaskAdapterTab2(taskDatabaseTab2.taskDao().getTasks(),  this);
        recyclerViewTab2.setLayoutManager(linearLayoutManagerTab2);
        recyclerViewTab2.setHasFixedSize(true);
        recyclerViewTab2.setAdapter(tab2Adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), linearLayoutManagerTab2.getOrientation());
        recyclerViewTab2.addItemDecoration(dividerItemDecoration);
        tab2Adapter.notifyDataSetChanged();
    }
    @Override
    public void rowOnClickedTab2(Tasks tasksNotCompleted) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(tasksNotCompleted.getTaskTitle())
                .setMessage("Created on : " + tasksNotCompleted.getDateCreated() + "\nDescription - " + tasksNotCompleted.getTaskDescription() + "\nCompleted: " + tasksNotCompleted.isCompleted())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }
    public void editTasksButtonClicked(Tasks tasks){

        floatingActionButton.setVisibility(View.INVISIBLE);
        editTasks = EditTasks.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelable("TASKS_NOT_COMPLETED", tasks);
        editTasks.setArguments(bundle);
        editTasks.attachParentEditTasksTab2(this);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.constraint_layout_tab2, editTasks).commit();
    }
    @Override
    public void getInfoTab2() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(editTasks).commit();
        tab2Adapter.updateList(taskDatabaseTab2.taskDao().getTasks());
        floatingActionButton.setVisibility(View.VISIBLE);
        tab2Adapter.notifyDataSetChanged();
    }

    @Override
    public void rowLongClickedTab2(final Tasks tasksNotCompleted) {

    }

    @Override
    public void onSwitchClickedTab2(Tasks taskNotCompleted) {
        if (!taskNotCompleted.isCompleted()) {
            markTaskAsCompleted(taskNotCompleted);
        }
    }

    @Override
    public void onMenuOptionClicked(final Tasks tasks) {
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
                        taskDatabaseTab2.taskDao().deleteTaskList(tasks);
                        tab2Adapter.updateList(taskDatabaseTab2.taskDao().getTasks());
                        Toast.makeText(getActivity(), "Task Deleted!", Toast.LENGTH_LONG).show();
                        tab2Adapter.notifyDataSetChanged();
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

    private void markTaskAsCompleted(final Tasks tasksNotCompleted) {

        tasksNotCompleted.setCompleted(true);
        taskDatabaseTab2.taskDao().updateTaskList(tasksNotCompleted);
        tab2Adapter.updateList(taskDatabaseTab2.taskDao().getTasks());
        Toast.makeText(getActivity(), "Task Completed", Toast.LENGTH_LONG).show();
        tab2Adapter.notifyDataSetChanged();
    }
}
