package com.usoft.br.mylibrary.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class BookDbHelper extends SQLiteOpenHelper {

    private String[] allCollumns;
    public BookDbHelper(Context context) {
        super(context, BookContract.DB_NAME, null, BookContract.DB_VERSION);
        allCollumns = new String[]{Book._ID, Book.COL_BOOK_TITLE, Book.COL_BOOK_AUTHOR};
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + Book.TABLE + " ( " +
                Book._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Book.COL_BOOK_AUTHOR + " TEXT NOT NULL, " +
                Book.COL_BOOK_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Book.TABLE);
        onCreate(db);
    }

    public void addOrUpdateBook(Book book){

        Book existingBook = this.getById(book.getId());

        if (existingBook == null){
            addBook(book);
        }
        else{
            updateBook(book);
        }
    }

    public Book getById(String id){
        if (id == null )
            return null;

        Book book= null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Book.TABLE, allCollumns,
                Book._ID + "=?", new String[]{id}, null, null, null);

        while (cursor.moveToNext()) {
            int titleIndex = cursor.getColumnIndex(Book.COL_BOOK_TITLE);
            int authorIndex = cursor.getColumnIndex(Book.COL_BOOK_AUTHOR);
            int idIndex = cursor.getColumnIndex(Book._ID);


             book = new Book(cursor.getString(idIndex),
                            cursor.getString(titleIndex),
                            cursor.getString(authorIndex));

        }
        db.close();

        return book;
    }

    public void addBook(Book book){
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Book.COL_BOOK_AUTHOR, book.getAuthor());
            values.put(Book.COL_BOOK_TITLE, book.getTitle());
            db.insertWithOnConflict(Book.TABLE,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            if(db != null)
                db.close();
        }
    }

    public void updateBook(Book book){
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Book.COL_BOOK_AUTHOR, book.getAuthor());
            values.put(Book.COL_BOOK_TITLE, book.getTitle());
            db.update(Book.TABLE,values , Book._ID + "=?", new String[]{book.getId()});
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            if(db != null)
                db.close();
        }
    }

    public ArrayList<Book> getAll(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Book> bookList = new ArrayList<>();

        try{
            Cursor cursor = db.query(Book.TABLE,
                    allCollumns,
                    null, null, null, null, Book.COL_BOOK_TITLE);
            while (cursor.moveToNext()) {
                int titleIndex = cursor.getColumnIndex(Book.COL_BOOK_TITLE);
                int authorIndex = cursor.getColumnIndex(Book.COL_BOOK_AUTHOR);
                int idIndex = cursor.getColumnIndex(Book._ID);

                bookList.add(
                        new Book(cursor.getString(idIndex),
                                cursor.getString(titleIndex),
                                cursor.getString(authorIndex))
                );
            }

        }catch (Exception ex){

        }finally {
            if(db != null)
                db.close();
        }

        return bookList;
    }

    public void deleteBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Book.TABLE,
                Book._ID + " = ?",
                new String[]{book.getId()});
        db.close();
    }
}
