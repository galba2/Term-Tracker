package com.example.term_tracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView noteView;
    OnNoteListener onNoteListener;

    public NoteHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
        super(itemView);

        noteView = itemView.findViewById(R.id.note_note_view);
        this.onNoteListener = onNoteListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onNoteListener.onNoteClicked(getAdapterPosition());
    }

    public interface OnNoteListener{
        void onNoteClicked(int position);
    }

}
