package edu.coe.asmarek.mybookshelf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Anna on 2/9/17.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_book, parent, false);
        }

        final ImageView bookPic = (ImageView) convertView.findViewById(R.id.imgBook);
        TextView bookTitle = (TextView) convertView.findViewById(R.id.bookTitle);
        TextView bookAuthor = (TextView) convertView.findViewById(R.id.bookAuthor);

        bookTitle.setText("Title: " + book.getBookTitle());
        bookAuthor.setText("Author: " + book.getBookAuthor());
        bookTitle.setTextSize(18);
        bookAuthor.setTextSize(16);

        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(book.getBookImageURL(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(responseBody);
                    Bitmap photoBitmap = BitmapFactory.decodeStream(bis);
                    bookPic.setImageBitmap(photoBitmap);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        } catch (Exception e) {
        }

        return convertView;
    }
}
