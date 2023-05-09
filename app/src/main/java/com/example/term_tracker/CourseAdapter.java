package com.example.term_tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Model.Course;
import Model.Term;

public class CourseAdapter extends RecyclerView.Adapter<CourseHolder>{

    Context context;
    ArrayList<Course> courses;
    private CourseHolder.OnCourseListener onCourseListener;

    public CourseAdapter(Context context, ArrayList<Course> courses, CourseHolder.OnCourseListener onCourseListener) {
        this.context = context;
        this.courses = courses;
        this.onCourseListener = onCourseListener;
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CourseHolder(LayoutInflater.from(context).inflate(R.layout.course_view, parent, false), onCourseListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        holder.titleView.setText(courses.get(position).getCourseTitle());
        holder.dateView.setText(courses.get(position).getDates());
        holder.statusView.setText(courses.get(position).getCourseStatus());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}
