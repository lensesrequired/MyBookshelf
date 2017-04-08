package edu.coe.asmarek.mybookshelf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBookText extends AppCompatActivity {
    EditText title;
    EditText author;
    EditText publisher;
    EditText publishYear;
    EditText edition;
    EditText ISBN;

    AppCompatActivity activity = this;

    final OpenHelper db = new OpenHelper(this);

    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_text);

        tableName = getIntent().getStringExtra("TableName");

        Toast.makeText(this, tableName, Toast.LENGTH_SHORT).show();

        if(tableName.matches("shelf")) {
            setTitle("Add Book to Shelf");
        } else if(tableName.matches("wishlist")) {
            setTitle("Add Book to Wishlist");
        } else {
            setTitle("None");
        }

        Button b = (Button) findViewById(R.id.btnAdd);

        setEditTexts();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!title.getText().toString().matches("") && !author.getText().toString().matches("")) {
                    Intent i = new Intent("edu.coe.asmarek.mybookshelf.Shelf");

                    String table = tableName;
                    db.setTABLE_NAME(table);

                    String pub = publisher.getText().toString();

                    if (pub.matches("")) {
                        pub = "0";
                    }

                    Book b = new Book(db.getLastID() + 1, title.getText().toString(), author.getText().toString(), publisher.getText().toString(),
                            Integer.parseInt(pub), edition.getText().toString(), ISBN.getText().toString());
                    db.addBook(b);

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
    }

}
