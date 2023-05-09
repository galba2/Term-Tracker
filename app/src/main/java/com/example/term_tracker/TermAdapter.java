package com.example.term_tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Model.Term;

public class TermAdapter extends RecyclerView.Adapter<TermHolder>{

    Context context;
    ArrayList<Term> terms;
    private TermHolder.OnTermListener onTermListener;

    public TermAdapter(Context context, ArrayList<Term> terms, TermHolder.OnTermListener onTermListener) {
        this.context = context;
        this.terms = terms;
        this.onTermListener = onTermListener;
    }

    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TermHolder(LayoutInflater.from(context).inflate(R.layout.term_view, parent, false), onTermListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {
        holder.titleView.setText(terms.get(position).getTermTitle());
        holder.dateView.setText(terms.get(position).getDates());

    }

    @Override
    public int getItemCount() {
        return terms.size();
    }
}
