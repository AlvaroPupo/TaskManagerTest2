package com.skills.interapt.taskmanagerandroid;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditTasks extends Fragment implements View.OnClickListener {

//    private Tasks tasks;
    private TaskNotCompleted taskNotCompleted;
    private TaskCompleted taskCompleted;
    private InfoCallback infoCallback;
    private TaskDatabase taskDatabase;
    private TaskAdapter taskAdapter;
    private FloatingActionButton floatingActionButton;
    private Tasks taskToEdit;

    Button editTimePicker, editDatePicker;
    EditText editTimeEditText, editDateEditText, editTaskTitle, editTaskDescription;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public static EditTasks newInstance() {

        Bundle args = new Bundle();

        EditTasks fragment = new EditTasks();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        taskDatabase = ((TaskApplication) getActivity().getApplicationContext()).getDatabase();
        floatingActionButton = getActivity().findViewById(R.id.add_tasks_fab);
        floatingActionButton.setVisibility(View.INVISIBLE);

        taskToEdit = getArguments().getParcelable("TASK");

        editTimePicker = getActivity().findViewById(R.id.edit_set_time_button);
        editDatePicker = getActivity().findViewById(R.id.edit_set_date_button);
        editTimeEditText = getActivity().findViewById(R.id.edit_set_time_edittext);
        editDateEditText = getActivity().findViewById(R.id.edit_set_date_edittext);

        editTaskTitle = getActivity().findViewById(R.id.edit_task_title_edittext);
        editTaskTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View vi, boolean hasFocus) {
                if(hasFocus) {
                    editTaskTitle.setText("");
                } else if (editTaskTitle.getText().toString().isEmpty()){
                    editTaskTitle.setText(taskToEdit.getTaskTitle());
                }
            }
        });
        editTaskDescription = getActivity().findViewById(R.id.edit_description_edittext);
        editTaskDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View vi, boolean hasFocus) {
                if(hasFocus) {
                    editTaskDescription.setText("");
                }else if (editTaskDescription.getText().toString().isEmpty()){
                    editTaskDescription.setText(taskToEdit.getTaskDescription());
                }
            }
        });

        editTaskTitle.setText(taskToEdit.getTaskTitle());
        editTaskDescription.setText(taskToEdit.getTaskDescription());
        editTimeEditText.setText(taskToEdit.getTimeDue());
        editDateEditText.setText(taskToEdit.getDateDue());

        editDatePicker.setOnClickListener(this);
        editTimePicker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == editDatePicker) {

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

                            editDateEditText.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == editTimePicker) {

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

                            editTimeEditText.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    @OnClick(R.id.edit_task_button)
    protected void saveEditedTask(){

        String editedTaskTitle = editTaskTitle.getText().toString();
        taskToEdit.setTaskTitle(editedTaskTitle);
        String editedTaskDescription = editTaskDescription.getText().toString();
        taskToEdit.setTaskDescription(editedTaskDescription);
        String editedDateDue = editDateEditText.getText().toString();
        taskToEdit.setDateDue(editedDateDue);
        String editedTimeDue = editTimeEditText.getText().toString();
        taskToEdit.setTimeDue(editedTimeDue);

        updateEditTasksList(taskToEdit);
        Toast.makeText(getActivity(), "Task Edited!!", Toast.LENGTH_SHORT).show();
    }
    private void updateEditTasksList(final Tasks taskToEdit){
        taskDatabase.taskDao().updateTaskList(taskToEdit);
        infoCallback.getInfo();
    }


    public void attachParentEditTasks(InfoCallback infoCallback) {
        this.infoCallback = infoCallback;
    }

    public interface InfoCallback {
        void getInfo();
    }
}
