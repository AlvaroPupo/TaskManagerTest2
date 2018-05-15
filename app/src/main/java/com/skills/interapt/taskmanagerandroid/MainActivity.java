package com.skills.interapt.taskmanagerandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//public class MainActivity extends AppCompatActivity implements TaskAdapter.AdapterCallback, AddTaskFragment.ActivityCallback {
//
//    @BindView(R.id.tasks_recycler_view)
//    protected RecyclerView recyclerView;
//
//    private TaskDatabase taskDatabase;
//    private TaskAdapter taskAdapter;
//    //    private List<Tasks> videoGameList;
//    private LinearLayoutManager linearLayoutManager;
//    private AddTaskFragment addTaskFragment;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
//
//        taskDatabase = ((TaskApplication) getApplicationContext()).getDatabase();
//
//        setUpRecyclerView();
//
//    }
//
//    private void setUpRecyclerView() {
//
//        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//        taskAdapter = new TaskAdapter(taskDatabase.tab2Dao().getTasks(), this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(taskAdapter);
//        taskAdapter.notifyDataSetChanged();
//    }
//
//    @OnClick(R.id.add_task_button)
//    protected void addGameButtonClicked() {
//
//        addTaskFragment = AddTaskFragment.newInstance();
//        addTaskFragment.attachParent(this);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, addTaskFragment).commit();
//
//    }
//
//
//    @Override
//    public void addClicked() {
//        getSupportFragmentManager().beginTransaction().remove(addTaskFragment).commit();
//        taskAdapter.updateList(taskDatabase.tab2Dao().getTasks());
//    }
//
//    @Override
//    public Context getContext() {
//        return getApplicationContext();
//    }
//
//    @Override
//    public void rowClicked(Tasks tasks) {
//        if (tasks.isCheckedOut()) {
//            checkGameBackIn(tasks);
//        } else {
//            checkGameOut(tasks);
//        }
//    }
//
//    @Override
//    public void rowLongClicked(final Tasks tasks) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Delete this Task?")
//                .setMessage("Are you sure you would like to delete this task?")
//                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        taskDatabase.tab2Dao().deleteTaskList(tasks);
//                        taskAdapter.updateList(taskDatabase.tab2Dao().getTasks());
//                        Toast.makeText(MainActivity.this, "Task Deleted!", Toast.LENGTH_LONG).show();
//                    }
//                })
//                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//    }
//
//    private void checkGameOut(final Tasks tasks) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Checkout Game?")
//                .setMessage("Are you sure you would like to check out this game?")
//                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        tasks.setCheckedOut(true);
//                        //Update Database record for this game
//                        taskDatabase.tab2Dao().updateTaskList(tasks);
//                        //tell our adapter that the database has been updated so it will update our view.
//                        taskAdapter.updateList(taskDatabase.tab2Dao().getTasks());
//                        //Tell users that this game has been checked out
//                        Toast.makeText(MainActivity.this, "Game Checked Out!", Toast.LENGTH_LONG).show();
//
//                    }
//                })
//                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//
//
//    }
//
//    private void checkGameBackIn(final Tasks tasks) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(R.string.check_in_game)
//                .setMessage(R.string.check_in_message)
//                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        tasks.setCheckedOut(false);
//                        //Update database with updated game information
//                        taskDatabase.tab2Dao().updateTaskList(tasks);
//                        //Let our adapter know that information in the database has changed to update our view accordingly
//                        taskAdapter.updateList(taskDatabase.tab2Dao().getTasks());
//
//                        Toast.makeText(MainActivity.this, R.string.game_checked_in, Toast.LENGTH_LONG).show();
//                    }
//                })
//                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//    }
//}
