package com.usoft.br.mylibrary.db;

import android.provider.BaseColumns;

import java.io.Serializable;

public class Book implements BaseColumns, Serializable {
    public static final String TABLE = "books";

    public static final String COL_BOOK_TITLE = "title";
    public static final String COL_BOOK_AUTHOR = "author";

    private String title;
    private String author;
    private String id;

    public Book(String id, String title, String author){
        this.title = title;
        this.author = author;
        this.id = id;
    }

    public Book(String title, String author){
        this.title = title;
        this.author = author;
    }

    public Book(){

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
