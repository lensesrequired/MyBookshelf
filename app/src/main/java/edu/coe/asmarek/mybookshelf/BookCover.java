package edu.coe.asmarek.mybookshelf;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

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
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            img.setImageDrawable(d);
        } catch (Exception e) {
        }
    }
}
