package edu.coe.asmarek.mybookshelf;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Anna on 3/18/17.
 */

public class BookListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemLongClickListener {

    private ListView list;
    private ArrayList<Book> books;
    private ArrayList<String> attr;
    private BookAdapter bookAdapter;
    private ArrayAdapter sortAdapter;
    private String shelfName;
    private Spinner sort;

    final private OpenHelper db = new OpenHelper(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shelfName = getIntent().getStringExtra("shelfName");

        setTitle(shelfName);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent("edu.coe.asmarek.mybookshelf.AddBookText");
                i.putExtra("shelfName", shelfName);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressWarnings("StatementWithEmptyBody")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.myShelf) {
                    Intent i = new Intent("edu.coe.asmarek.mybookshelf.BookListViewActivity");
                    i.putExtra("shelfName", "MyShelf");
                    startActivity(i);
                } else if (id == R.id.myWishlist) {
                    Intent i = new Intent("edu.coe.asmarek.mybookshelf.BookListViewActivity");
                    i.putExtra("shelfName", "Wishlist");
                    startActivity(i);
                } else if (id == R.id.myLoans) {
                    Intent i = new Intent("edu.coe.asmarek.mybookshelf.BookListViewActivity");
                    i.putExtra("shelfName", "Loans");
                    startActivity(i);
                } else if (id == R.id.nav_share) {

                } else if (id == R.id.nav_send) {

                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        sort = (Spinner) findViewById(R.id.spnSort);
        attr = new ArrayList<String>();
        attr.add("Title");
        attr.add("Author");
        sort.setOnItemSelectedListener(this);

        list = (ListView) findViewById(R.id.ownedBookList);
        books = new ArrayList<Book>();
        books = db.getAllBooksOnShelf(shelfName);
        bookAdapter = new BookAdapter(this, books);
        list.setAdapter(bookAdapter);

        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Book b = (Book) parent.getItemAtPosition(position);
        Intent i = new Intent("edu.coe.asmarek.mybookshelf.BookDetails");
        i.putExtra("ID", b.getBookID());
        i.putExtra("shelfName", shelfName);
        startActivity(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (attr.get(position)) {
            case "Title":
                Collections.sort(books, new Comparator<Book>() {
                    public int compare(Book b1, Book b2) {
                        return b1.getBookTitle().compareTo(b2.getBookTitle());
                    }
                });
                bookAdapter.notifyDataSetChanged();
                break;
            case "Author":
                Collections.sort(books, new Comparator<Book>() {
                    public int compare(Book b1, Book b2) {
                        return b1.getBookAuthor().compareTo(b2.getBookAuthor());
                    }
                });
                bookAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Share")
                .setMessage("Share this book?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                    }
                })
                .show();
        return true;
    }
}
