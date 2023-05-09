package com.example.term_tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Model.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteHolder>{

    Context context;
    ArrayList<Note> notes;
    private NoteHolder.OnNoteListener onNoteListener;

    public NoteAdapter(Context context, ArrayList<Note> notes, NoteHolder.OnNoteListener onNoteListener) {
        this.context = context;
        this.notes = notes;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteHolder(LayoutInflater.from(context).inflate(R.layout.note_view, parent, false), onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        holder.noteView.setText(notes.get(position).getNoteText());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


}
