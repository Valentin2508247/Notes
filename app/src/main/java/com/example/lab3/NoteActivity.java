package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NoteActivity extends Activity {

    private Note note;
    private MyRepository repository;
    private boolean isNew;
    boolean isAdded;
    private int _id;
    private TagAdapter tagAdapter;

    private static final String TAG = "note_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        if (getActionBar() != null)
            getActionBar().hide();


        isAdded = false;
        repository = new MyRepository(getApplicationContext());
        repository.open();

        ArrayList<Tag> taglist = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            isNew = false;
            _id = extras.getInt("_id");
            Log.d(TAG, "trying invoke getNote(" + _id + ") method");
            note = repository.getNote(_id);
            if (note != null) {

                EditText title = findViewById(R.id.edit_title);
                title.setText(note.getTitle());
                EditText body = findViewById(R.id.edit_body);
                body.setText(note.getBody());
                EditText date = findViewById(R.id.edit_date);
                date.setText(note.getDate());

                Log.d(TAG, "Getting tag list");
                taglist = repository.getTags(note);

            }
            else
            {
                isNew = true;
                Toast.makeText(this, "Cant find note by given id: " + _id, Toast.LENGTH_LONG).show();

            }

        }
        else
        {
            isNew = true;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' hh.mm", Locale.getDefault());
            String currentDate = sdf.format(new Date());
            EditText date = findViewById(R.id.edit_date);
            date.setText(currentDate);

        }



        tagAdapter = new TagAdapter(this, taglist);
        ListView listView = findViewById(R.id.tag_list);
        listView.setAdapter(tagAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {


                Tag tag = (Tag)tagAdapter.getItem(position);
                tagAdapter.removeTag(position);


                return true;
            }
        });




    }

    public void addTag(View view)
    {
        EditText editText = findViewById(R.id.tag_name);
        if (editText.getText().toString().equals(""))
        {
            Toast.makeText(this, "Enter tag name", Toast.LENGTH_LONG).show();
            return;
        }
        Tag tag = new Tag(editText.getText().toString());
        if (!tagAdapter.contains(tag)) {
            Log.d(TAG, "Trying to add tag");
            Log.d(TAG, Boolean.toString(repository.contains(new Tag("q"))));
            if (repository.contains(tag)) {
                tag = repository.getTag(tag.getName());
            }

            long id = repository.insert(tag);
            tag.setId((int)id);
            tagAdapter.addTag(tag);
            editText.setText("");
        }
        else
            Toast.makeText(this, "Tag with name " + tag.getName() + " have been already added.", Toast.LENGTH_LONG).show();
    }


    public void save(View view)
    {
        EditText title = findViewById(R.id.edit_title);
        EditText body = findViewById(R.id.edit_body);
        EditText date = findViewById(R.id.edit_date);

        if (isNew)
        {
            note = new Note(title.getText().toString(), body.getText().toString(), date.getText().toString(), null);
            long id = repository.insert(new Note(title.getText().toString(), body.getText().toString(), date.getText().toString(), null));
            note.setId((int)id);
        }
        else
        {
            note.setTitle(title.getText().toString());
            note.setBody(body.getText().toString());
            note.setDate(date.getText().toString());
            repository.updateNote(note);

            //repository.insert(new Note(title.getText().toString(), body.getText().toString(), date.getText().toString(), null));
        }
        isAdded = true;
        repository.removeLinks(note);
        repository.insert(note, tagAdapter.getArray());
        goHome();
    }

    public void back(View view)
    {
        isAdded = false;
        goHome();
    }

    private void goHome(){
        // переход к главной activity
        Intent data = new Intent();
        data.putExtra(MainActivity.is_added, isAdded);
        setResult(RESULT_OK, data);
        finish();
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

        repository.close();
    }

}
