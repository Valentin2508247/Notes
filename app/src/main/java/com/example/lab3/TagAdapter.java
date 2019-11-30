package com.example.lab3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TagAdapter extends ArrayAdapter {

    private final Context context;
    private static final String TAG = "tag_adapter";
    private ArrayList<Tag> tags;
   // private final Tag[] tags;


    public TagAdapter(Context context, ArrayList<Tag> tags)
    {
        super(context, R.layout.note_layout, tags);

        this.context = context;
        this.tags = tags;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.tag_layout, parent, false);
        TextView name = view.findViewById(R.id.tag_name_in_list);
        name.setText(tags.get(position).getName());




        return view;
    }

    public void addTag(Tag tag)
    {
        tags.add(tag);
        notifyDataSetChanged();
    }

    public void removeTag(int position)
    {
        tags.remove(position);
        notifyDataSetChanged();
    }

    public boolean contains(Tag tag)
    {
        for (Tag t : tags)
            if (t.getName().equals(tag.getName()))
                return true;
        return false;
    }

    public Tag[] getArray()
    {
        Tag[] array = tags.toArray(new Tag[tags.size()]);
        for (Tag tag: array)
            Log.d(TAG, "Tag in array id = " + Integer.toString(tag.getId()) + " name = " + tag.getName());
        return array;
    }


}
