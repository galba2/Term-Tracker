package DetailsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.term_tracker.AssessmentEditActivity;
import com.example.term_tracker.CourseEditActivity;
import com.example.term_tracker.LookingActivity;
import com.example.term_tracker.R;
import com.example.term_tracker.TermEditActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Model.Assessment;
import Model.Term;

public class AssessmentDetailsActivity extends AppCompatActivity {

    private Assessment assessment;
    private TextView title;
    private TextView start;
    private TextView end;
    private TextView type;
    private static boolean assessmentEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        this.setTitle("ASSESSMENT DETAILS");

        setAssessmentEdit(false);

        title = findViewById(R.id.assessment_details_title);
        start = findViewById(R.id.assessment_details_start_input);
        end = findViewById(R.id.assessment_details_end_input);
        type = findViewById(R.id.assessment_details_type_input);

        configureFAB();
    }

    @Override
    protected void onStart() {
        super.onStart();

        setAssessmentEdit(false);

        assessment = CourseDetailsActivity.getAssessmentsForCourse().get(CourseDetailsActivity.getAssessmentPos());
        title.setText(assessment.getTitle());
        start.setText(assessment.getAssessmentStart());
        end.setText(assessment.getAssessmentEnd());
        type.setText(assessment.getAssessmentType());
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
                startActivity(new Intent(AssessmentDetailsActivity.this, LookingActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void configureFAB() {

        FloatingActionButton fab = findViewById(R.id.floating_action_button_edit);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AssessmentDetailsActivity.setAssessmentEdit(true);
                startActivity(new Intent(AssessmentDetailsActivity.this, AssessmentEditActivity.class));
            }
        });
    }

    public static boolean isAssessmentEdit() {
        return assessmentEdit;
    }

    public static void setAssessmentEdit(boolean assessmentEdit) {
        AssessmentDetailsActivity.assessmentEdit = assessmentEdit;
    }
}