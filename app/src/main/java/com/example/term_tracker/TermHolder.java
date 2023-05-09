package com.example.term_tracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TermHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView titleView;
    TextView dateView;
    OnTermListener onTermListener;


    public TermHolder(@NonNull View itemView, OnTermListener onTermListener) {
        super(itemView);

        titleView = itemView.findViewById(R.id.recyclerview_term_title_view);
        dateView = itemView.findViewById(R.id.recycler_term_date_view);
        this.onTermListener = onTermListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onTermListener.onTermClicked(getAdapterPosition());
    }

    public interface OnTermListener{
        void onTermClicked(int position);
    }
}
