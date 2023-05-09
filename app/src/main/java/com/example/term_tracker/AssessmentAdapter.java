package com.example.term_tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Model.Assessment;
import Model.Course;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentHolder>{

    Context context;
    ArrayList<Assessment> assessments;
    private AssessmentHolder.OnAssessmentListener onAssessmentListener;

    public AssessmentAdapter(Context context, ArrayList<Assessment> assessments, AssessmentHolder.OnAssessmentListener onAssessmentListener) {
        this.context = context;
        this.assessments = assessments;
        this.onAssessmentListener = onAssessmentListener;
    }

    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AssessmentHolder(LayoutInflater.from(context).inflate(R.layout.assessment_view, parent, false), onAssessmentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {
        holder.titleView.setText(assessments.get(position).getTitle());
        holder.dateView.setText(assessments.get(position).getDates());
        holder.typeView.setText(assessments.get(position).getAssessmentType());
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }
}
