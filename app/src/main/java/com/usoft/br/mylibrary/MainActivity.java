package com.usoft.br.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.SearchView;

import com.usoft.br.mylibrary.adapter.BookListAdapter;
import com.usoft.br.mylibrary.db.Book;
import com.usoft.br.mylibrary.db.BookDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final MainActivity instance;
    private final BookDbHelper db;
    //private ArrayAdapter<Book> bookAdapter;
    private ListView booksListView;
    private BookListAdapter bookListaAdapter;
    private ArrayList<Book> books;

    public MainActivity(){
        this.instance = this;
        this.db = new BookDbHelper(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        books = new ArrayList<>();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        booksListView =  findViewById(R.id.list_book);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(instance, AddBookActivity.class);
                intent.setAction(AddBookActivity.ADD_BOOK);
                startActivityForResult(intent, Integer.parseInt(AddBookActivity.ADD_BOOK));
            }
        });

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem mSearch = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Pesquisar");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateUI();

                if (newText.equals(""))
                    updateUI();

                bookListaAdapter.filter(newText);
                return true;
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                updateUI();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateUI();
    }

    @Override
    public void onPostResume() {
        updateUI();
        super.onPostResume();
    }

    private void updateUI() {

        books.clear();
        books = this.db.getAll();
        if (bookListaAdapter == null) {
            bookListaAdapter = new BookListAdapter(this, books);
            booksListView.setAdapter(bookListaAdapter);
        } else {
            bookListaAdapter.clear();
            bookListaAdapter.addAll(books);
            bookListaAdapter.notifyDataSetChanged();
            booksListView.invalidateViews();
            booksListView.refreshDrawableState();
        }
    }
}
