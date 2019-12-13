package com.example.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter {

    private final Context context;
    private ArrayList<Note> notes;

    public MyArrayAdapter(Context context, ArrayList<Note> notes)
    {
        super(context, R.layout.note_layout, notes);

        this.context = context;
        this.notes = notes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.note_layout, parent, false);

        TextView title = view.findViewById(R.id.note_title);
        title.setText(notes.get(position).getTitle());
        TextView body = view.findViewById(R.id.note_body);
        body.setText(notes.get(position).getBody());
        TextView date = view.findViewById(R.id.note_date);
        date.setText(notes.get(position).getDate());

        return view;
    }

    public void removeNote(int position)
    {
        notes.remove(position);
    }

    public int getNoteId(int pos)
    {
        //return notes[pos].getId();
        return notes.get(pos).getId();
    }

    public void setArray(ArrayList<Note> array)
    {
        notes.clear();
        for (Note note : array)
            notes.add(note);
        notifyDataSetChanged();
    }
}
