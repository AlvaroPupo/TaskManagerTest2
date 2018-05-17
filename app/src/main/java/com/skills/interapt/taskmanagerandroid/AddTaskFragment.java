package com.skills.interapt.taskmanagerandroid;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTaskFragment extends Fragment implements View.OnClickListener{

    private ActivityCallback activityCallback;
    private TaskDatabase taskDatabase;
    private FloatingActionButton floatingActionButton;

    Button datePicker, timePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @BindView(R.id.add_task_title_edit_text)
    protected TextInputEditText taskTitle;
    @BindView(R.id.add_description_edit_text)
    protected TextInputEditText taskDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_task, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    public static AddTaskFragment newInstance() {

        Bundle args = new Bundle();

        AddTaskFragment fragment = new AddTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        taskDatabase = ((TaskApplication) getActivity().getApplicationContext()).getDatabase();
        floatingActionButton = getActivity().findViewById(R.id.add_tasks_fab);
        floatingActionButton.setVisibility(View.INVISIBLE);

        txtDate = getActivity().findViewById(R.id.in_date);
        txtTime = getActivity().findViewById(R.id.in_time);
        datePicker = getActivity().findViewById(R.id.btn_date);
        timePicker = getActivity().findViewById(R.id.btn_time);

        timePicker.setOnClickListener(this);
        datePicker.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        if (v == datePicker){

            // Get Current Date
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == timePicker){

            // Get Current Time
            Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    @OnClick(R.id.save_task_fab)
    protected void saveButtonClicked() {

        String newTime = txtTime.getText().toString();
        String newDate = txtDate.getText().toString();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy - hh:mm a", Locale.US);
        String timeCreated = formatter.format(date);

        if(taskTitle.getText().toString().isEmpty() || txtDate.getText().toString().isEmpty() || txtTime.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter required fields", Toast.LENGTH_LONG).show();
        } else {
            Tasks tasks = new Tasks(taskTitle.getText().toString(), taskDescription.getText().toString(), newDate, newTime, timeCreated);
            TaskNotCompleted taskNotCompleted = new TaskNotCompleted(taskTitle.getText().toString(), taskDescription.getText().toString(),false, newDate, newTime, timeCreated);
            TaskCompleted taskCompleted = new TaskCompleted(taskTitle.getText().toString(), taskDescription.getText().toString(), true, newDate, newTime, timeCreated);

            addTaskToDatabase(tasks, taskNotCompleted, taskCompleted);
            Toast.makeText(getActivity(), "Task Added!!", Toast.LENGTH_LONG).show();
        }
    }

    private void addTaskToDatabase(final Tasks tasks, final TaskNotCompleted taskNotCompleted, final TaskCompleted taskCompleted) {
        taskDatabase.taskDao().addTasks(tasks);
        taskDatabase.taskDaoTab2().addTasksTab2(taskNotCompleted);
        taskDatabase.taskDaoTab3().addTasksTab3(taskCompleted);
        activityCallback.addClicked();
    }

    public void attachParent(ActivityCallback activityCallback) {

        this.activityCallback = activityCallback;
    }


    public interface ActivityCallback {

        void addClicked();
    }


}
