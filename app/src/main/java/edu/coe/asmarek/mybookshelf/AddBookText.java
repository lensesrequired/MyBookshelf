package edu.coe.asmarek.mybookshelf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class AddBookText extends AppCompatActivity {
    EditText title;
    EditText author;
    EditText publisher;
    EditText publishYear;
    EditText edition;
    EditText ISBN;
    Spinner shelf;

    AppCompatActivity activity = this;

    final OpenHelper db = new OpenHelper(this);

    String shelfName;
    Integer editBookID;
    ArrayList<String> shelves;

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
            shelf.setSelection(shelves.indexOf(b.getBookShelf()));
        }

        Button b = (Button) findViewById(R.id.btnAdd);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!title.getText().toString().matches("") && !author.getText().toString().matches("")) {
                    Intent i = new Intent("edu.coe.asmarek.mybookshelf.BookListViewActivity");

                    shelfName = shelf.getSelectedItem().toString().replace(" ", "");

                    String pub = publishYear.getText().toString();

                    if (pub.matches("")) {
                        pub = "0";
                    }

                    Book b = new Book(editBookID==-1 ? db.getLastID("books") + 1:editBookID, title.getText().toString(), author.getText().toString(), publisher.getText().toString(),
                            Integer.parseInt(pub), edition.getText().toString(), ISBN.getText().toString(), shelfName);
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
            }
        });
    }

    private void setEditTexts() {
        title = (EditText) findViewById(R.id.edtTitle);
        author = (EditText) findViewById(R.id.edtAuthor);
        publisher = (EditText) findViewById(R.id.edtPublisher);
        publishYear = (EditText) findViewById(R.id.edtYear);
        edition = (EditText) findViewById(R.id.edtEdition);
        ISBN = (EditText) findViewById(R.id.edtISBN);
        shelf = (Spinner) findViewById(R.id.spnShelves);
    }

}
