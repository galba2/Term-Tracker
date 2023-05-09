package com.example.term_tracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AssessmentHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

    TextView titleView;
    TextView dateView;
    TextView typeView;
    OnAssessmentListener onAssessmentListener;


    public AssessmentHolder(@NonNull View itemView, OnAssessmentListener onAssessmentListener) {
        super(itemView);

        titleView = itemView.findViewById(R.id.recycler_assessment_title_view);
        dateView = itemView.findViewById(R.id.recycler_assessment_date_view);
        typeView = itemView.findViewById(R.id.recycler_assessment_type_view);
        this.onAssessmentListener = onAssessmentListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onAssessmentListener.onAssessmentClicked(getAdapterPosition());
    }

    public interface OnAssessmentListener{
        void onAssessmentClicked(int position);
    }
}
