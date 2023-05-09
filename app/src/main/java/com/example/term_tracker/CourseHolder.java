package com.example.term_tracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CourseHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView titleView;
    TextView dateView;
    TextView statusView;
    OnCourseListener onCourseListener;


    public CourseHolder(@NonNull View itemView, OnCourseListener onCourseListener) {
        super(itemView);

        titleView = itemView.findViewById(R.id.recycler_course_title_view);
        dateView = itemView.findViewById(R.id.recycler_course_date_view);
        statusView = itemView.findViewById(R.id.recycler_course_status_view);
        this.onCourseListener = onCourseListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onCourseListener.onCourseClicked(getAdapterPosition());
    }

    public interface OnCourseListener{
        void onCourseClicked(int position);
    }
}
