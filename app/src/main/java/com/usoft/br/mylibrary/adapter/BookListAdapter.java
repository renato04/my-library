package com.usoft.br.mylibrary.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.usoft.br.mylibrary.AddBookActivity;
import com.usoft.br.mylibrary.R;
import com.usoft.br.mylibrary.db.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookListAdapter extends BaseAdapter {
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Book> books = null;
    private ArrayList<Book> arraylist;
    private Button deleteButton;

    public BookListAdapter(Context context, List<Book> books) {
        mContext = context;
        this.books = books;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Book>();
        this.arraylist.addAll(books);
    }

    public class ViewHolder {
        TextView title;
    }

    public void clear(){
        books.clear();
    }

    public void addAll(ArrayList<Book> books){
        this.books = books;
    }


    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Book getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setDeleteClickListener(View.OnClickListener listener){
        deleteButton.setOnClickListener(listener);
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.book_item, null);
            // Locate the TextViews in listview_item.xml
            holder.title = view.findViewById(R.id.book_title);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.title.setText(books.get(position).getTitle());
        view.findViewById(R.id.book_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddBookActivity.class);
                intent.putExtra("book", books.get(position));
                intent.setAction(AddBookActivity.EDIT_BOOK);
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        books.clear();
        if (charText.length() == 0) {
            books.addAll(arraylist);
        }
        else
        {
            for (Book book : arraylist)
            {
                if (book.getTitle().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    books.add(book);
                }
            }
        }
        notifyDataSetChanged();
    }
}
