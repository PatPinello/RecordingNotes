package com.recordingnotes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>
{
    private final Context context;
    Activity a;
    private final ArrayList noteIdTxt;
    private final ArrayList noteTitle;
    private final ArrayList noteText;

    CustomAdapter(Activity a, Context context,
                  ArrayList noteId,
                  ArrayList noteTitle,
                  ArrayList noteText)
    {
        this.a=a;
        this.context = context;
        this.noteIdTxt = noteId;
        this.noteTitle = noteTitle;
        this.noteText = noteText;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater i = LayoutInflater.from(context);
        View v = i.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position)
    {

        holder.noteIdTxt.setText(String.valueOf(noteIdTxt.get(position)));
        holder.noteTitle.setText(String.valueOf(noteTitle.get(position)));
        holder.noteText.setText(String.valueOf(noteText.get(position)));
        holder.mainLayout.setOnClickListener((view) -> {

                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(noteIdTxt.get(position)));
                intent.putExtra("title", String.valueOf(noteTitle.get(position)));
                intent.putExtra("text", String.valueOf(noteText.get(position)));
                a.startActivityForResult(intent,1);

        });

    }

    @Override
    public int getItemCount() {
        return noteIdTxt.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView noteIdTxt,noteTitle,noteText;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            noteIdTxt = itemView.findViewById(R.id.noteIdTxt);
            noteTitle = itemView.findViewById(R.id.noteTitle);
            noteText = itemView.findViewById(R.id.noteText);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
