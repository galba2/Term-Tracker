package com.example.term_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.util.Calendar;

import Database.DatabaseAccess;
import DetailsActivity.TermDetailsActivity;
import Model.Course;
import Model.Term;

public class TermEditActivity extends AppCompatActivity {

    private static final String TAG = "TermEditActivity";
    private Term term;
    private EditText title;
    private DatePicker startPicker;
    private DatePicker endPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);

        this.setTitle("CONFIGURE TERM");

        title = findViewById(R.id.edit_term_title);
        startPicker = findViewById(R.id.edit_term_start_datepicker);
        endPicker = findViewById(R.id.edit_term_finish_datepicker);

        configureFAB();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FloatingActionButton fabD = findViewById(R.id.floating_action_button_delete);

        if(TermDetailsActivity.isTermEdit()){
            term = LookingActivity.getTerms().get(LookingActivity.getTermPos());
            title.setText(term.getTermTitle());
            startPicker.updateDate(getYear(term.getTermStart()), getMonth(term.getTermStart())-1, getDay(term.getTermStart()));
            endPicker.updateDate(getYear(term.getTermEnd()),getMonth(term.getTermEnd())-1,getDay(term.getTermEnd()));

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

                if(TermDetailsActivity.isTermEdit()){

                    term.setTermTitle(title.getText().toString());
                    term.setTermStart("" + (startPicker.getMonth() + 1) + "/" + startPicker.getDayOfMonth() + "/" + startPicker.getYear());
                    term.setTermEnd("" + (endPicker.getMonth() + 1) + "/" + endPicker.getDayOfMonth() + "/" + endPicker.getYear());

                    LookingActivity.getmDatabaseAccess().updateTerm(term);
                }else{

                    LookingActivity.getmDatabaseAccess().addTerm(new Term(title.getText().toString(),
                                                                    "" + (startPicker.getMonth() + 1) + "/" + startPicker.getDayOfMonth() + "/" + startPicker.getYear(),
                                                                        "" + (endPicker.getMonth() + 1) + "/" + endPicker.getDayOfMonth() + "/" + endPicker.getYear()));
                }

                LookingActivity.refreshTerms();

                Calendar c = Calendar.getInstance();
                title.getText().clear();
                startPicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 1, c.get(Calendar.DAY_OF_MONTH));
                endPicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 1, c.get(Calendar.DAY_OF_MONTH));

                finish();
            }
        });

        fabD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TermDetailsActivity.isTermEdit() && checkForNoCourses(term.getId())){

                    LookingActivity.getmDatabaseAccess().deleteTerm(term.getId());
                    LookingActivity.refreshTerms();
                }else{
                    Toast.makeText(TermEditActivity.this,"Can not delete a term with courses!", Toast.LENGTH_LONG).show();
                }

                Calendar c = Calendar.getInstance();
                title.getText().clear();
                startPicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 1, c.get(Calendar.DAY_OF_MONTH));
                endPicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 1, c.get(Calendar.DAY_OF_MONTH));

                startActivity(new Intent(TermEditActivity.this, LookingActivity.class));
            }
        });
    }

    private boolean checkForNoCourses(int termID){
        boolean isNoCourses = true;

        for(Course c: LookingActivity.getmDatabaseAccess().getAllCourses()){

            if(c.getCourseTerm() == termID){
                isNoCourses = false;
            }
        }

        return isNoCourses;
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