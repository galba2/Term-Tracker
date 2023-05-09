package DetailsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.term_tracker.AssessmentAdapter;
import com.example.term_tracker.AssessmentEditActivity;
import com.example.term_tracker.AssessmentHolder;
import com.example.term_tracker.CourseAdapter;
import com.example.term_tracker.CourseEditActivity;
import com.example.term_tracker.LookingActivity;
import com.example.term_tracker.NoteAdapter;
import com.example.term_tracker.NoteEditActivity;
import com.example.term_tracker.NoteHolder;
import com.example.term_tracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Model.Assessment;
import Model.Course;
import Model.Note;

public class CourseDetailsActivity extends AppCompatActivity implements AssessmentHolder.OnAssessmentListener, NoteHolder.OnNoteListener {

    private RecyclerView recyclerViewAssessment;
    private RecyclerView recyclerViewNote;
    private static ArrayList<Assessment> assessmentsForCourse;
    private static ArrayList<Note> notesForCourse;
    private static boolean courseEdit;


    private Course course;
    private TextView title;
    private TextView start;
    private TextView end;
    private TextView status;
    private TextView name;
    private TextView phone;
    private TextView email;

    private static int assessmentPos;
    private static int notePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        setCourseEdit(false);

        this.setTitle("COURSE DETAILS");


        assessmentsForCourse = new ArrayList<Assessment>();
        notesForCourse = new ArrayList<Note>();
        course = TermDetailsActivity.getCoursesForTerm().get(TermDetailsActivity.getCoursePos());

        for(Assessment assessment: LookingActivity.getAssessments()){
            if(assessment.getAssessmentCourse() == course.getId()){
                assessmentsForCourse.add(assessment);
            }
        }

        for(Note note: LookingActivity.getNotes()){
            if(note.getNoteCourse() == course.getId()){
                notesForCourse.add(note);
            }
        }

        title = findViewById(R.id.course_details_title);
        start = findViewById(R.id.course_details_start_input);
        end = findViewById(R.id.course_details_end_input);
        status = findViewById(R.id.course_details_status_input);
        name = findViewById(R.id.course_details_name_input);
        phone = findViewById(R.id.course_details_phone_input);
        email = findViewById(R.id.course_details_email_input);

        configureFAB();
    }

    @Override
    protected void onStart() {
        super.onStart();

        setCourseEdit(false);

        course = TermDetailsActivity.getCoursesForTerm().get(TermDetailsActivity.getCoursePos());

        checkForNote(course.getId());

        title.setText(course.getCourseTitle());
        start.setText(course.getCourseStart());
        end.setText(course.getCourseEnd());
        status.setText(course.getCourseStatus());
        name.setText(course.getCourseName());
        phone.setText(course.getCoursePhone());
        email.setText(course.getCourseEmail());

        assessmentsForCourse.clear();
        for(Assessment assessment: LookingActivity.getAssessments()){
            if(assessment.getAssessmentCourse() == TermDetailsActivity.getCoursesForTerm().get(TermDetailsActivity.getCoursePos()).getId()){
                assessmentsForCourse.add(assessment);
            }
        }

        notesForCourse.clear();
        for(Note note: LookingActivity.getNotes()){
            if(note.getNoteCourse() == course.getId()){
                notesForCourse.add(note);
            }
        }

        recyclerViewAssessment = findViewById(R.id.course_details_recycler_assessment);
        recyclerViewNote = findViewById(R.id.course_details_recycler_note);

        recyclerViewAssessment.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAssessment.setAdapter(new AssessmentAdapter(getApplicationContext(), assessmentsForCourse, this::onAssessmentClicked));

        recyclerViewNote.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNote.setAdapter(new NoteAdapter(getApplicationContext(), notesForCourse, this::onNoteClicked));
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
                startActivity(new Intent(CourseDetailsActivity.this, LookingActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void configureFAB() {

        FloatingActionButton fabA = findViewById(R.id.floating_action_button_assessment);
        FloatingActionButton fabN = findViewById(R.id.floating_action_button_note);
        FloatingActionButton fabE = findViewById(R.id.floating_action_button_edit);

        fabA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AssessmentDetailsActivity.setAssessmentEdit(false);
                startActivity(new Intent(CourseDetailsActivity.this, AssessmentEditActivity.class));
            }
        });

        fabN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteDetailsActivity.setNoteEdit(false);
                startActivity(new Intent(CourseDetailsActivity.this, NoteEditActivity.class));
            }
        });

        fabE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setCourseEdit(true);
                startActivity(new Intent(CourseDetailsActivity.this, CourseEditActivity.class));
            }
        });
    }

    @Override
    public void onAssessmentClicked(int position) {
        setAssessmentPos(position);
        Intent intent = new Intent(this, AssessmentDetailsActivity.class);
        Toast.makeText(this,"ASSESSMENT " + position + " CLICKED!", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }


    @Override
    public void onNoteClicked(int position) {
        setNotePos(position);
        Toast.makeText(this,"NOTE " + position + " CLICKED!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, NoteDetailsActivity.class);
        startActivity(intent);
    }

    public static ArrayList<Assessment> getAssessmentsForCourse() {
        return assessmentsForCourse;
    }

    public static ArrayList<Note> getNotesForCourse() {
        return notesForCourse;
    }

    public static int getAssessmentPos() {
        return assessmentPos;
    }

    public static void setAssessmentPos(int assessmentPos) {
        CourseDetailsActivity.assessmentPos = assessmentPos;
    }

    public static int getNotePos() {
        return notePos;
    }

    public static void setNotePos(int notePos) {
        CourseDetailsActivity.notePos = notePos;
    }

    public static boolean isCourseEdit() {
        return courseEdit;
    }

    public void setCourseEdit(boolean courseEdit) {
        this.courseEdit = courseEdit;
    }

    private void checkForNote(int courseID){
        int count = 0;

        for(Note n: LookingActivity.getmDatabaseAccess().getAllNotes()){
            if(n.getNoteCourse() == courseID){
                count++;
                break;
            }
        }

        if(count == 0){
            LookingActivity.getmDatabaseAccess().addNote( new Note(courseID, "Default text."));
            LookingActivity.refreshNotes();
        }
    }
}