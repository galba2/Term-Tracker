package DetailsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.term_tracker.AssessmentEditActivity;
import com.example.term_tracker.LookingActivity;
import com.example.term_tracker.NoteEditActivity;
import com.example.term_tracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Model.Assessment;
import Model.Course;
import Model.Note;

public class NoteDetailsActivity extends AppCompatActivity {

    private Note note;
    private TextView noteText;
    private static boolean noteEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        this.setTitle("NOTE DETAILS");
        setNoteEdit(false);

        noteText = findViewById(R.id.note_details_text);

        configureFAB();
    }

    @Override
    protected void onStart() {
        super.onStart();

        setNoteEdit(false);

        note = CourseDetailsActivity.getNotesForCourse().get(CourseDetailsActivity.getNotePos());
        noteText.setText(note.getNoteText());
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
                startActivity(new Intent(NoteDetailsActivity.this, LookingActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void configureFAB() {

        FloatingActionButton fab = findViewById(R.id.floating_action_button_edit);
        FloatingActionButton fabS = findViewById(R.id.floating_action_button_share);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setNoteEdit(true);
                startActivity(new Intent(NoteDetailsActivity.this, NoteEditActivity.class));
            }
        });

        fabS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String courseTitle = getCourseTitle(note.getNoteCourse());

                Intent setIntent = new Intent();
                setIntent.setAction(Intent.ACTION_SEND);
                setIntent.putExtra(Intent.EXTRA_TEXT, note.getNoteText());
                setIntent.putExtra(Intent.EXTRA_TITLE, courseTitle + " Note ");
                setIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(setIntent, null);
                startActivity(shareIntent);
            }
        });
    }

    public static boolean isNoteEdit() {
        return noteEdit;
    }

    public static void setNoteEdit(boolean noteEdit) {
        NoteDetailsActivity.noteEdit = noteEdit;
    }

    private String getCourseTitle(int id){
        String title = "";

        for(Course c: LookingActivity.getmDatabaseAccess().getAllCourses()){
            if(c.getId() == id){
                title = c.getCourseTitle();
            }
        }


        return title;
    }
}