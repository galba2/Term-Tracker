package com.example.term_tracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.View;

import android.widget.Button;

import DetailsActivity.CourseDetailsActivity;
import DetailsActivity.NoteDetailsActivity;
import DetailsActivity.TermDetailsActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static int requestCode;
    public static int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureStartButton();
        Log.d(TAG,"FROM inside onCreate: id~" + id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void configureStartButton() {

        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LookingActivity.class));
            }
        });
    }


}