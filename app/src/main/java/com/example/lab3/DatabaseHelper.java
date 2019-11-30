package com.example.lab3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "databasename.db";
    private static final int SCHEMA = 11;

    public static final String TABLE_NOTE = "note";
    public static final String COLUMN_ID_NOTE = "_id";
    public static final String COLUMN_TITLE_NOTE = "title";
    public static final String COLUMN_BODY_NOTE = "body";
    public static final String COLUMN_DATE_NOTE = "date";

    public static final String TABLE_TAG = "tag";
    public static final String COLUMN_ID_TAG = "_id";
    public static final String COLUMN_NAME_TAG = "name";


    public static final String TABLE_LINK = "link";
    public static final String COLUMN_ID_LINK = "_id";
    public static final String COLUMN_IDNOTE_LINK = "idnote";
    public static final String COLUMN_IDTAG_LINK  = "idtag";


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, SCHEMA);

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NOTE + " (" + COLUMN_ID_NOTE +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE_NOTE + " TEXT, "
        + COLUMN_BODY_NOTE + " TEXT, " + COLUMN_DATE_NOTE + " TEXT);");
        db.execSQL("INSERT  INTO " + TABLE_NOTE + " (" + COLUMN_TITLE_NOTE + ", " + COLUMN_BODY_NOTE + ", " + COLUMN_DATE_NOTE + ") VALUES ('My title', 'Look at Jane. She is absolutely gorgeous!', '29.10.2019');" );

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TAG + " (" + COLUMN_ID_TAG + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME_TAG + " TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_LINK + " (" + COLUMN_ID_LINK + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_IDNOTE_LINK + " INTEGER, " + COLUMN_IDTAG_LINK + " INTEGER" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LINK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
        onCreate(db);
}
}
