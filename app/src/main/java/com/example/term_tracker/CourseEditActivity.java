package com.example.term_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import DetailsActivity.CourseDetailsActivity;
import DetailsActivity.TermDetailsActivity;
import Model.Course;

public class CourseEditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "CourseEditActivity";
    private Course course;
    private EditText title;
    private DatePicker start;
    private DatePicker end;
    private String status;
    private Spinner statusSpin;
    private EditText name;
    private EditText phone;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);

        this.setTitle("CONFIGURE COURSE");

        title = findViewById(R.id.edit_course_title);
        start = findViewById(R.id.edit_course_start_datepicker);
        end = findViewById(R.id.edit_course_finish_datepicker);
        statusSpin = findViewById(R.id.edit_course_status_spinner);
        name = findViewById(R.id.edit_course_name);
        phone = findViewById(R.id.edit_course_phone);
        email = findViewById(R.id.edit_course_email);

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpin.setAdapter(adapterSpinner);
        statusSpin.setOnItemSelectedListener(this);

        configureFAB();
        createNotificationChannel();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FloatingActionButton fabD = findViewById(R.id.floating_action_button_delete);

        if(CourseDetailsActivity.isCourseEdit()){
            course = TermDetailsActivity.getCoursesForTerm().get(TermDetailsActivity.getCoursePos());
            title.setText(course.getCourseTitle());
            start.updateDate(getYear(course.getCourseStart()), getMonth(course.getCourseStart())-1, getDay(course.getCourseStart()));
            end.updateDate(getYear(course.getCourseEnd()), getMonth(course.getCourseEnd())-1, getDay(course.getCourseEnd()));
            switch(course.getCourseStatus()){
                case "In Progress":
                    statusSpin.setSelection(0);
                    break;
                case "Completed":
                    statusSpin.setSelection(1);
                    break;
                case "Dropped":
                    statusSpin.setSelection(2);
                    break;
                case "Plan to Take":
                    statusSpin.setSelection(3);
                    break;
            }
            name.setText(course.getCourseName());
            phone.setText(course.getCoursePhone());
            email.setText(course.getCourseEmail());

            fabD.setVisibility(View.VISIBLE);
        }else{
            fabD.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        status =  adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void configureFAB() {

        FloatingActionButton fab = findViewById(R.id.floating_action_button_save);
        FloatingActionButton fabD = findViewById(R.id.floating_action_button_delete);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(CourseDetailsActivity.isCourseEdit()){

                    course.setCourseTitle(title.getText().toString());
                    course.setCourseStart("" + (start.getMonth() + 1) + "/" + start.getDayOfMonth() + "/" + start.getYear());
                    course.setCourseEnd("" + (end.getMonth() + 1) + "/" + end.getDayOfMonth() + "/" + end.getYear());
                    course.setCourseStatus(statusSpin.getSelectedItem().toString());
                    course.setCourseName(name.getText().toString());
                    course.setCoursePhone(phone.getText().toString());
                    course.setCourseEmail(email.getText().toString());

                    LookingActivity.getmDatabaseAccess().updateCourse(course);
                }else{

                    LookingActivity.getmDatabaseAccess().addCourse(new Course(title.getText().toString(),
                                                                        "" + (start.getMonth() + 1) + "/" + start.getDayOfMonth() + "/" + start.getYear(),
                                                                            "" + (end.getMonth() + 1) + "/" + end.getDayOfMonth() + "/" + end.getYear(),
                                                                                        status,
                                                                                            name.getText().toString(),
                                                                                                phone.getText().toString(),
                                                                                                    email.getText().toString(),
                                                                                                        LookingActivity.getTerms().get(LookingActivity.getTermPos()).getId()));
                }

                String intentTitle = title.getText().toString();
                String intentTextStart = "Course starts today.";
                String intentTextEnd = "Course ends today.";
                int intentStartYear = start.getYear();
                int intentStartMonth = start.getMonth();
                int intentStartDay = start.getDayOfMonth();
                int intentEndYear = end.getYear();
                int intentEndMonth = end.getMonth();
                int intentEndDay = end.getDayOfMonth();
                Log.d(TAG,"FROM after intent initiations: StYr~" + intentStartYear + "StMn~" + intentStartMonth + "StDy~" + intentStartDay +
                                                                "EnYr~" + intentEndYear + "EnMn~" + intentEndMonth + "EnDy~" + intentEndDay);

                LookingActivity.refreshCourses();

                Calendar c = Calendar.getInstance();
                title.getText().clear();
                start.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 1, c.get(Calendar.DAY_OF_MONTH));
                end.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 1, c.get(Calendar.DAY_OF_MONTH));
                status = "";
                name.getText().clear();
                phone.getText().clear();
                email.getText().clear();

                Calendar myAlarmDate = Calendar.getInstance();
                myAlarmDate.setTimeInMillis(System.currentTimeMillis());


                Intent intentStart = new Intent(CourseEditActivity.this, NotificationBroadcast.class);
                intentStart.putExtra("title",intentTitle);
                intentStart.putExtra("text",intentTextStart);

                PendingIntent pendingIntentStart = PendingIntent.getBroadcast(CourseEditActivity.this, MainActivity.requestCode++,intentStart, 0);
                Log.d(TAG,"FROM after pendingIntentStart: MainActivity.requestCode~" + MainActivity.requestCode);
                myAlarmDate.set(intentStartYear, intentStartMonth, intentStartDay, 04, 0, 0);

                AlarmManager alarmManagerStart = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManagerStart.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), pendingIntentStart);
                Log.d(TAG, "FROM: under alarmManagerStart.set(): Date: " + myAlarmDate.get(Calendar.MONTH) + "~" + myAlarmDate.get(Calendar.DAY_OF_MONTH) + "~" + myAlarmDate.get(Calendar.YEAR));

                myAlarmDate.clear();
                Intent intentEnd = new Intent(CourseEditActivity.this, NotificationBroadcast.class);
                intentEnd.putExtra("title",intentTitle);
                intentEnd.putExtra("text",intentTextEnd);

                PendingIntent pendingIntentEnd = PendingIntent.getBroadcast(CourseEditActivity.this, MainActivity.requestCode++,intentEnd, 0);
                Log.d(TAG,"FROM after pendingIntentEnd: MainActivity.requestCode~" + MainActivity.requestCode);
                myAlarmDate.set(intentEndYear, intentEndMonth, intentEndDay, 04, 0, 0);

                AlarmManager alarmManagerEnd = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), pendingIntentEnd);
                Log.d(TAG, "FROM: under alarmManagerEnd.set(): Date: " + myAlarmDate.get(Calendar.MONTH) + "~" + myAlarmDate.get(Calendar.DAY_OF_MONTH) + "~" + myAlarmDate.get(Calendar.YEAR));

                finish();
            }
        });

        fabD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(CourseDetailsActivity.isCourseEdit()){

                    LookingActivity.getmDatabaseAccess().deleteCourse(course.getId());
                    LookingActivity.refreshCourses();

                    Calendar c = Calendar.getInstance();
                    title.getText().clear();
                    start.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 1, c.get(Calendar.DAY_OF_MONTH));
                    end.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 1, c.get(Calendar.DAY_OF_MONTH));
                    status = "";
                    name.getText().clear();
                    phone.getText().clear();
                    email.getText().clear();

                    startActivity(new Intent(CourseEditActivity.this, TermDetailsActivity.class));
                }
            }
        });
    }



    private void createNotificationChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            CharSequence name = "NotifyChannel";
            String description = "Channel for notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify_term_tracker", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public int getDay(String date){
        String[] dateSplit = date.split("/",3);
        return  Integer.valueOf(dateSplit[1]);
    }

    public int getMonth(String date){
        String[] dateSplit = date.split("/",3);
        return  Integer.valueOf(dateSplit[0]);
    }

    public int getYear(String date){
        String[] dateSplit = date.split("/",3);
        return  Integer.valueOf(dateSplit[2]);
    }
}