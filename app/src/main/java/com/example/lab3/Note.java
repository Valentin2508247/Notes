package com.example.lab3;

public class Note {

    private int id;
    private String title;
    private  String body;
    private String date;
    private String[] tags;

    public Note(int id, String title, String body, String date, String[] tags){
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = date;
        this.tags = tags;
    }

    public Note(String title, String body, String date, String[] tags){
        this.id = -1;
        this.title = title;
        this.body = body;
        this.date = date;
        this.tags = tags;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
}

