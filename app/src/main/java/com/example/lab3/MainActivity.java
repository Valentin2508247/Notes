package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {
    private final String TAG = "MainActivity";

    private MyArrayAdapter myArrayAdapter;
    private AdapterView adapterView;
    private MyRepository repository;

    static final String is_added = "is_added";
    private static  final int request_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getActionBar() != null)
            getActionBar().hide();

        Log.d(TAG, "onCreate");


        repository = new MyRepository(this);
        repository.open();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' hh.mm", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        //repository.insert(new Note("лаба3","Аня помоги",  currentDate, null));
        ArrayList<Note> array = repository.getNotes();
        myArrayAdapter = new MyArrayAdapter(this, array);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            ListView listView = findViewById(R.id.listview);
            listView.setAdapter(myArrayAdapter);

            adapterView = listView;
        }
        else
        {
            GridView gridView = findViewById(R.id.gridview);
            gridView.setAdapter(myArrayAdapter);
            adapterView = gridView;
        }


        adapterView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                //TODO:check
                repository.removeNote(myArrayAdapter.getNoteId(position));
                myArrayAdapter.removeNote(position);
                myArrayAdapter.notifyDataSetChanged();

                return true;
            }
        });

        adapterView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Note note = (Note)myArrayAdapter.getItem(position);

                if (note != null)
                {
                    Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                    intent.putExtra("_id", note.getId());
                    startActivityForResult(intent, request_code);
                }
            }
        });

        SearchView search = findViewById(R.id.search_view);
        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                return false;
            }
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                if (newText.equals(""))
                    myArrayAdapter.setArray(repository.getNotes());
                else
                    myArrayAdapter.setArray(repository.getNotesByTag(new Tag(newText)));

                return false;
            }
        });
    }

    public void addTag(View view)
    {

    }

    @Override
    public void onResume(){
        super.onResume();

        Log.d(TAG, "onResume");

    }

    @Override
    public  void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onPause()
    {
        super.onPause();

        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop()
    {
        super.onStop();

        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

        repository.close();
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivityForResult(intent, request_code);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == request_code){
            if(resultCode == RESULT_OK){
                //TODO: code if note added, update listview
                if (data.getBooleanExtra(is_added, false))
                {
                    Log.d(TAG, "onActivityResult with added note");
                }
                else
                {
                    Log.d(TAG, "onActivityResult with no added notes");
                }
                MyRepository repository = new MyRepository(this);
                repository.open();
                ArrayList<Note> array = repository.getNotes();
                myArrayAdapter = new MyArrayAdapter(this, array);
                adapterView.setAdapter(myArrayAdapter);
                repository.close();
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
