package edu.coe.asmarek.mybookshelf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddBookText extends AppCompatActivity {
    EditText title;
    EditText author;
    EditText publisher;
    EditText publishYear;
    EditText edition;
    EditText ISBN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_text);

        Button b = (Button) findViewById(R.id.btnAdd);

        final OpenHelper db = new OpenHelper(this);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("edu.coe.asmarek.mybookshelf.Shelf");

                setEditTexts();

                String table = getIntent().getStringExtra("TableName");
                db.setTABLE_NAME(table);

                Book b = new Book(db.getLastID()+1, title.getText().toString(), author.getText().toString(), publisher.getText().toString(),
                        Integer.parseInt(publishYear.getText().toString()), edition.getText().toString(), Integer.parseInt(ISBN.getText().toString()));
                db.addBook(b);

                startActivity(i);
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
