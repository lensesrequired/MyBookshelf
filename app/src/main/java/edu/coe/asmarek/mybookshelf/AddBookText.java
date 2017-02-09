package edu.coe.asmarek.mybookshelf;

import android.content.Intent;
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

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();

                setEditTexts();

                String[] data = {title.getText().toString(), author.getText().toString(), publisher.getText().toString(), publishYear.getText().toString(), edition.getText().toString(), ISBN.getText().toString()};

                i.putExtra("BookInfo", data);
                setResult(RESULT_OK, i);
                finish();
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
