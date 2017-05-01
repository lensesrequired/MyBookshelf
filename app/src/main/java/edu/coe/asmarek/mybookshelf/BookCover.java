package edu.coe.asmarek.mybookshelf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.ByteArrayInputStream;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Anna on 4/29/17.
 */

public class BookCover extends LinearLayout {
    private String url;
    private ImageView img;

    public BookCover(Context context) {
        super(context);

        initViews(context);
    }

    public BookCover(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public BookCover(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    private void initViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.bookcover, this);

        img = (ImageView) findViewById(R.id.photo);
    }

    public void LoadImageFromWebOperations() {
        if(!url.equals(null)) {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(responseBody);
                    Bitmap photoBitmap = BitmapFactory.decodeStream(bis);
                    img.setImageBitmap(photoBitmap);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        }
    }
}
