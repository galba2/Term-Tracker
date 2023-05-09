package com.example.term_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Database.DatabaseAccess;
import DetailsActivity.*;
import Model.*;


public class LookingActivity extends AppCompatActivity implements TermHolder.OnTermListener, CourseHolder.OnCourseListener,
                                                                        AssessmentHolder.OnAssessmentListener{

    private final static String TAG = "LookingActivity";

    private RecyclerView recyclerView;
    private static ArrayList<Term> terms;
    private static ArrayList<Course> courses;
    private static ArrayList<Assessment> assessments;
    private static ArrayList<Note> notes;
    private static DatabaseAccess mDatabaseAccess;
    private static int termPos;
    private static int coursePos;
    private static int assessmentPos;
    private static int notePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looking);

        mDatabaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        terms = mDatabaseAccess.getAllTerms();
        courses = mDatabaseAccess.getAllCourses();
        assessments = mDatabaseAccess.getAllAssessments();
        notes = mDatabaseAccess.getAllNotes();

        configureFAB();
    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerView = findViewById(R.id.recyclerview_looking_act);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TermAdapter(getApplicationContext(), terms, this::onTermClicked));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FloatingActionButton fab = findViewById(R.id.floating_action_button);

        switch(item.getItemId()){

            case R.id.terms:

                this.setTitle("TERMS");
                fab.setVisibility(View.VISIBLE);

                recyclerView = findViewById(R.id.recyclerview_looking_act);

                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(new TermAdapter(getApplicationContext(), terms, this::onTermClicked));
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void configureFAB() {

        FloatingActionButton fab = findViewById(R.id.floating_action_button);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch(getTitle().toString()){
                    case "TERMS":
                        startActivity(new Intent(LookingActivity.this, TermEditActivity.class));
                        break;

                    case"COURSES":
                        startActivity(new Intent(LookingActivity.this, CourseEditActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    public void onTermClicked(int position) {
        setTermPos(position);

        Intent intent = new Intent(this, TermDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCourseClicked(int position) {
        setCoursePos(position);
        Intent intent = new Intent(this, CourseDetailsActivity.class);

        startActivity(intent);
    }

    @Override
    public void onAssessmentClicked(int position) {
        setAssessmentPos(position);
        Intent intent = new Intent(this, AssessmentDetailsActivity.class);
        startActivity(intent);
    }

    public static DatabaseAccess getmDatabaseAccess() {
        return mDatabaseAccess;
    }

    public static ArrayList<Term> getTerms() {
        return terms;
    }

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public static ArrayList<Assessment> getAssessments() {
        return assessments;
    }

    public static ArrayList<Note> getNotes() {
        return notes;
    }

    public static void refreshTerms(){
        terms = mDatabaseAccess.getAllTerms();
    }

    public static void refreshCourses(){
        courses = mDatabaseAccess.getAllCourses();
    }
    public static void refreshAssessments(){
        assessments = mDatabaseAccess.getAllAssessments();
    }

    public static void refreshNotes(){
        notes = mDatabaseAccess.getAllNotes();
    }

    public static int getTermPos() {
        return termPos;
    }

    public static int getCoursePos() {
        return coursePos;
    }

    public static int getAssessmentPos() {
        return assessmentPos;
    }

    public static int getNotePos() {
        return notePos;
    }

    public  void setTermPos(int termPos) {
        this.termPos = termPos;
    }

    public  void setCoursePos(int coursePos) {
        this.coursePos = coursePos;
    }

    public  void setAssessmentPos(int assessmentPos) {
        this.assessmentPos = assessmentPos;
    }

    public  void setNotePos(int notePos) {
        this.notePos = notePos;
    }
}