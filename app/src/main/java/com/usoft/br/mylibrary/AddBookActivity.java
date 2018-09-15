package com.usoft.br.mylibrary;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.usoft.br.mylibrary.db.Book;
import com.usoft.br.mylibrary.db.BookDbHelper;

public class AddBookActivity extends AppCompatActivity {

    private final AddBookActivity instance;
    private final BookDbHelper db;
    public static final String ADD_BOOK = "1";
    public static final String EDIT_BOOK = "2";
    private EditText titleText;
    private EditText authorText;
    private Book editBook;

    public AddBookActivity(){
        this.instance = this;
        this.db = new BookDbHelper(this);
        this.editBook = new Book();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        titleText = findViewById(R.id.input_title);
        authorText = findViewById(R.id.input_autor);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(titleText.getText());
                String author = String.valueOf(authorText.getText());
                Book book = new Book(editBook.getId(), name, author);
                db.addOrUpdateBook(book);

                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        EvaluateAction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_book_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            deleteBook();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteBook() {
        setResult(Activity.RESULT_OK);
        db.deleteBook(editBook);
        finish();

    }

    private void EvaluateAction() {
        String action = getIntent().getAction();
        if (action.equals(AddBookActivity.ADD_BOOK)){

            //Do nothing yet
        }else if (action.equals(AddBookActivity.EDIT_BOOK)){
            editBook = (Book) getIntent().getSerializableExtra("book");
            titleText.setText(editBook.getTitle());
            authorText.setText(editBook.getAuthor());
        }
    }
}
