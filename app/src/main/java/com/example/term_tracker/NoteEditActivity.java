package com.example.term_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import DetailsActivity.CourseDetailsActivity;
import DetailsActivity.NoteDetailsActivity;
import DetailsActivity.TermDetailsActivity;
import Model.Note;

public class NoteEditActivity extends AppCompatActivity {

    private static final String TAG = "NoteEditActivity";
    private Note note;
    private EditText noteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        this.setTitle("CONFIGURE NOTE");

        noteText = findViewById(R.id.edit_note_note);

        configureFAB();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FloatingActionButton fabD = findViewById(R.id.floating_action_button_delete);

        Log.d(TAG,"FROM: onStart()");
        if(NoteDetailsActivity.isNoteEdit()){
            note = CourseDetailsActivity.getNotesForCourse().get(CourseDetailsActivity.getNotePos());
            Log.d(TAG,"FROM: onStart() inside of if check: noteText~" + note.getNoteText());
            noteText.setText(note.getNoteText());

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

                if(NoteDetailsActivity.isNoteEdit()){
                    note.setNoteText(noteText.getText().toString());
                    LookingActivity.getmDatabaseAccess().updateNote(note);
                }else{
                    LookingActivity.getmDatabaseAccess().addNote( new Note(TermDetailsActivity.getCoursesForTerm().get(TermDetailsActivity.getCoursePos()).getId(),
                                                                    noteText.getText().toString()));
                }

                LookingActivity.refreshNotes();
                noteText.getText().clear();
                startActivity(new Intent(NoteEditActivity.this, CourseDetailsActivity.class));
            }
        });

        fabD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(NoteDetailsActivity.isNoteEdit()){
                    note.setNoteText(noteText.getText().toString());
                    LookingActivity.getmDatabaseAccess().updateNote(note);
                }

                LookingActivity.refreshNotes();
                noteText.getText().clear();
                startActivity(new Intent(NoteEditActivity.this, CourseDetailsActivity.class));
            }
        });
    }
}