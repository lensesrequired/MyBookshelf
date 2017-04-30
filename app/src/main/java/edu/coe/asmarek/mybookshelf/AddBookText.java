package edu.coe.asmarek.mybookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class AddBookText extends AppCompatActivity implements View.OnClickListener {

    private Button scan;
    private EditText title;
    private EditText author;
    private EditText publisher;
    private EditText publishYear;
    private EditText edition;
    private EditText ISBN;
    private BookCover photo;
    private Spinner shelf;

    private AppCompatActivity activity = this;

    final private OpenHelper db = new OpenHelper(this);
    private ZXingScannerView mScannerView;

    private String shelfName;
    private Integer editBookID;
    private ArrayList<String> shelves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_text);

        setEditTexts();

        shelfName = getIntent().getStringExtra("shelfName");
        editBookID = getIntent().getIntExtra("bookID", -1);
        shelves = new ArrayList<String>(Arrays.asList("MyShelf", "Wishlist", "Loans"));

        if(editBookID != -1)
        {
            Book b = db.getBook(editBookID);
            title.setText(b.getBookTitle());
            author.setText(b.getBookAuthor());
            publisher.setText(b.getBookPublisher());
            publishYear.setText(b.getBookPublishYear()!=0 ? b.getBookPublishYear().toString():"");
            edition.setText(b.getBookEdition());
            ISBN.setText(b.getBookISBN());
            photo.setURL(b.getBookImageURL());
            photo.LoadImageFromWebOperations();
            shelf.setSelection(shelves.indexOf(b.getBookShelf()));
        }

        Button b = (Button) findViewById(R.id.btnAdd);

        b.setOnClickListener(this);
    }

    private void setEditTexts() {
        scan = (Button) findViewById(R.id.btnScan);
        title = (EditText) findViewById(R.id.edtTitle);
        author = (EditText) findViewById(R.id.edtAuthor);
        publisher = (EditText) findViewById(R.id.edtPublisher);
        publishYear = (EditText) findViewById(R.id.edtYear);
        edition = (EditText) findViewById(R.id.edtEdition);
        ISBN = (EditText) findViewById(R.id.edtISBN);
        photo = (BookCover) findViewById(R.id.imgBookCover);
        shelf = (Spinner) findViewById(R.id.spnShelves);

        scan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnAdd:
                if (!title.getText().toString().matches("") && !author.getText().toString().matches("")) {
                    Intent i = new Intent("edu.coe.asmarek.mybookshelf.BookListViewActivity");

                    shelfName = shelf.getSelectedItem().toString().replace(" ", "");

                    String pub = publishYear.getText().toString();

                    if (pub.matches("")) {
                        pub = "0";
                    }

                    Book b = new Book(editBookID==-1 ? db.getLastID("books") + 1:editBookID, title.getText().toString(), author.getText().toString(), publisher.getText().toString(),
                            Integer.parseInt(pub), edition.getText().toString(), ISBN.getText().toString(), photo.getURL(), shelfName);

                    if(editBookID==-1) {
                        db.addBook(b);
                    } else {
                        db.updateBook(b);
                    }

                    i.putExtra("shelfName", shelfName);
                    startActivity(i);
                } else {
                    Toast.makeText(activity, "You must enter a Title and Author", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnScan:
                IntentIntegrator integrator = new IntentIntegrator(this);
                integrator.initiateScan();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");

                if(!contents.equals(null) && !format.equals(null) && format.equalsIgnoreCase("EAN_13")){
                    ISBN.setText(contents);

                    String bookSearchString = "https://www.googleapis.com/books/v1/volumes?"+
                            "q=isbn:"+contents+"&key=AIzaSyAx5A-ic604ijUcsQ1z5wpQD5FAWkf81Bs";

                    AsyncHttpClient client = new AsyncHttpClient();
                    client.get(bookSearchString, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            List<Book> bookList = new ArrayList<Book>();

                            String json = new String(responseBody);

                            try {
                                JSONObject object = new JSONObject(json);
                                JSONArray array = object.getJSONArray("items");

                                for (int i = 0; i < array.length(); i++) {
                                    Book book = new Book();
                                    JSONObject item = array.getJSONObject(i);

                                    JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                                    String t = volumeInfo.getString("title");
                                    title.setText(t);

                                    JSONArray authors = volumeInfo.getJSONArray("authors");
                                    String a = authors.getString(0);
                                    author.setText(a);

                                    //String pub = volumeInfo.getString("publisher");
                                    //publisher.setText(pub);

                                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                                    String imageLink = imageLinks.getString("smallThumbnail");
                                    photo.setURL(imageLink);
                                    Log.d("Url", "Url" + imageLink);
                                    photo.LoadImageFromWebOperations();

                                    bookList.add(book);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(activity, "Something went wrong. Sorry.", Toast.LENGTH_SHORT).show();
                        }

                    });


                } else {
                    Toast.makeText(this, "Not a valid scan!", Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Scan was Cancelled!", Toast.LENGTH_LONG).show();
            }
        }

    }
}
