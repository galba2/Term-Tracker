package DetailsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.term_tracker.CourseAdapter;
import com.example.term_tracker.CourseEditActivity;
import com.example.term_tracker.CourseHolder;
import com.example.term_tracker.LookingActivity;
import com.example.term_tracker.R;
import com.example.term_tracker.TermAdapter;
import com.example.term_tracker.TermEditActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Model.Course;
import Model.Term;

public class TermDetailsActivity extends AppCompatActivity implements CourseHolder.OnCourseListener{

    private final static String TAG = "TermDetailsActivity";

    private RecyclerView recyclerView;
    private static ArrayList<Course> coursesForTerm;
    private static boolean termEdit;

    private Term term;
    private TextView title;
    private TextView start;
    private TextView end;

    private static int coursePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        this.setTitle("TERM DETAILS");

        setTermEdit(false);

        coursesForTerm = new ArrayList<Course>();
        term = LookingActivity.getTerms().get(LookingActivity.getTermPos());

        for(Course c: LookingActivity.getCourses()){
            if(c.getCourseTerm() == term.getId()){
                coursesForTerm.add(c);
            }
        }


        title = findViewById(R.id.term_details_title);
        start = findViewById(R.id.term_details_start_input);
        end = findViewById(R.id.term_details_end_input);

        configureFAB();
    }

    @Override
    protected void onStart() {
        super.onStart();

        setTermEdit(false);

        term = LookingActivity.getTerms().get(LookingActivity.getTermPos());
        title.setText(term.getTermTitle());
        start.setText(term.getTermStart());
        end.setText(term.getTermEnd());

        coursesForTerm.clear();
        for(Course c: LookingActivity.getCourses()){
            if(c.getCourseTerm() == term.getId()){
                coursesForTerm.add(c);
            }
        }

        recyclerView = findViewById(R.id.term_details_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CourseAdapter(getApplicationContext(), coursesForTerm, this));
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
                startActivity(new Intent(TermDetailsActivity.this, LookingActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void configureFAB() {

        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        FloatingActionButton fabE = findViewById(R.id.floating_action_button_edit);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(TermDetailsActivity.this, CourseEditActivity.class));
            }
        });

        fabE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setTermEdit(true);
                startActivity(new Intent(TermDetailsActivity.this, TermEditActivity.class));
            }
        });
    }

    @Override
    public void onCourseClicked(int position) {
        setCoursePos(position);
        Intent intent = new Intent(this, CourseDetailsActivity.class);
        Toast.makeText(this,"COURSE " + position + " CLICKED!", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public static boolean isTermEdit() {
        return termEdit;
    }

    public void setTermEdit(boolean termEdit) {
        this.termEdit = termEdit;
    }

    public static int getCoursePos() {
        return coursePos;
    }

    public void setCoursePos(int coursePos) {
        this.coursePos = coursePos;
    }

    public static ArrayList<Course> getCoursesForTerm() {
        return coursesForTerm;
    }
}