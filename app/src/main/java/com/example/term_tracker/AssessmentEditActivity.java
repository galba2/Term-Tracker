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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import DetailsActivity.AssessmentDetailsActivity;
import DetailsActivity.CourseDetailsActivity;
import DetailsActivity.TermDetailsActivity;
import Model.Assessment;

public class AssessmentEditActivity extends AppCompatActivity {

    private static final String TAG = "AssessmentEditActivity";
    private Assessment assessment;
    private EditText title;
    private DatePicker start;
    private DatePicker end;
    private RadioGroup type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);

        this.setTitle("CONFIGURE ASSESSMENT");

        title = findViewById(R.id.edit_assessment_title);
        start = findViewById(R.id.edit_assessment_start_datepicker);
        end = findViewById(R.id.edit_assessment_finish_datepicker);
        type = findViewById(R.id.edit_assessment_radio_group);

        configureFAB();
        createNotificationChannel();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FloatingActionButton fabD = findViewById(R.id.floating_action_button_delete);

        if(AssessmentDetailsActivity.isAssessmentEdit()){

            assessment = CourseDetailsActivity.getAssessmentsForCourse().get(CourseDetailsActivity.getAssessmentPos());
            title.setText(assessment.getTitle());
            start.updateDate(getYear(assessment.getAssessmentStart()), getMonth(assessment.getAssessmentStart())-1, getDay(assessment.getAssessmentStart()));
            end.updateDate(getYear(assessment.getAssessmentEnd()),getMonth(assessment.getAssessmentEnd())-1,getDay(assessment.getAssessmentEnd()));
            switch(assessment.getAssessmentType()){
                case "Performance":
                    type.check(R.id.edit_assessment_radio_performance);
                    break;
                case "Objective":
                    type.check(R.id.edit_assessment_radio_objective);
                    break;
            }

            fabD.setVisibility(View.VISIBLE);
        }else{
            fabD.setVisibility(View.INVISIBLE);
        }
    }

    private void configureFAB() {

        FloatingActionButton fab = findViewById(R.id.floating_action_button_save);
        FloatingActionButton fabD = findViewById(R.id.floating_action_button_delete);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(AssessmentDetailsActivity.isAssessmentEdit()){

                    assessment.setTitle(title.getText().toString());
                    assessment.setAssessmentStart("" + (start.getMonth() + 1) + "/" + start.getDayOfMonth() + "/" + start.getYear());
                    assessment.setAssessmentEnd("" + (end.getMonth() + 1) + "/" + end.getDayOfMonth() + "/" + end.getYear());
                    switch(type.getCheckedRadioButtonId()){
                        case R.id.edit_assessment_radio_performance:
                            assessment.setAssessmentType("Performance");
                            break;
                        case R.id.edit_assessment_radio_objective:
                            assessment.setAssessmentType("Objective");
                            break;
                    }

                    LookingActivity.getmDatabaseAccess().updateAssessment(assessment);

                }else{

                    String selectedRadio = "";
                    switch(type.getCheckedRadioButtonId()){
                        case R.id.edit_assessment_radio_performance:
                            selectedRadio = "Performance";
                            break;
                        case R.id.edit_assessment_radio_objective:
                            selectedRadio = "Objective";
                            break;
                    }

                    LookingActivity.getmDatabaseAccess().addAssessment( new Assessment(title.getText().toString(),
                                                                            "" + (start.getMonth() + 1) + "/" + start.getDayOfMonth() + "/" + start.getYear(),
                                                                                "" + (end.getMonth() + 1) + "/" + end.getDayOfMonth() + "/" + end.getYear(),
                                                                                                selectedRadio,
                                                                                                    TermDetailsActivity.getCoursesForTerm().get(TermDetailsActivity.getCoursePos()).getId()));
                }

                String intentTitle = title.getText().toString();
                String intentTextStart = "Assessment starts today.";
                String intentTextEnd = "Assessment ends today.";

                LookingActivity.refreshAssessments();

                Calendar c = Calendar.getInstance();
                title.getText().clear();
                start.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 1, c.get(Calendar.DAY_OF_MONTH));
                end.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 1, c.get(Calendar.DAY_OF_MONTH));
                type.check(R.id.edit_assessment_radio_performance);

                Calendar myAlarmDate = Calendar.getInstance();
                myAlarmDate.setTimeInMillis(System.currentTimeMillis());


                Intent intentStart = new Intent(AssessmentEditActivity.this, NotificationBroadcast.class);
                intentStart.putExtra("title",intentTitle);
                intentStart.putExtra("text",intentTextStart);

                PendingIntent pendingIntentStart = PendingIntent.getBroadcast(AssessmentEditActivity.this, MainActivity.requestCode++,intentStart, 0);
                Log.d(TAG,"FROM after pendingIntentStart: MainActivity.requestCode~" + MainActivity.requestCode);
                myAlarmDate.set(start.getYear(), start.getMonth(), start.getDayOfMonth(), 12, 0, 0);

                AlarmManager alarmManagerStart = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManagerStart.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), pendingIntentStart);


                Intent intentEnd = new Intent(AssessmentEditActivity.this, NotificationBroadcast.class);
                intentEnd.putExtra("title",intentTitle);
                intentEnd.putExtra("text",intentTextEnd);

                PendingIntent pendingIntentEnd = PendingIntent.getBroadcast(AssessmentEditActivity.this, MainActivity.requestCode++,intentEnd, 0);
                Log.d(TAG,"FROM after pendingIntentEnd: MainActivity.requestCode~" + MainActivity.requestCode);
                myAlarmDate.set(end.getYear(), end.getMonth(), end.getDayOfMonth(), 12, 0, 0);

                AlarmManager alarmManagerEnd = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), pendingIntentEnd);

                finish();
            }
        });

        fabD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(AssessmentDetailsActivity.isAssessmentEdit()){

                    LookingActivity.getmDatabaseAccess().deleteAssessment(assessment.getId());
                    LookingActivity.refreshAssessments();

                    Calendar c = Calendar.getInstance();
                    title.getText().clear();
                    start.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 1, c.get(Calendar.DAY_OF_MONTH));
                    end.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 1, c.get(Calendar.DAY_OF_MONTH));
                    type.check(R.id.edit_assessment_radio_performance);

                    startActivity(new Intent(AssessmentEditActivity.this, CourseDetailsActivity.class));
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