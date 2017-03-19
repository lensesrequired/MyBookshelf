package edu.coe.asmarek.mybookshelf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Anna on 3/18/17.
 */

public class OpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "MyBookShelf";
    private String TABLE_NAME;
    private String SHELF_TABLE_NAME = "shelf";
    private String WISHLIST_TABLE_NAME = "wishlist";
    private String SHELF_TABLE_CREATE =
            "CREATE TABLE " + SHELF_TABLE_NAME + " (" +
                    "ID" + " INTEGER, " +
                    "Title" + " TEXT, " +
                    "Author" + " TEXT, " +
                    "Publisher" + " TEXT, " +
                    "Year" + " INTEGER, " +
                    "Edition" + " TEXT, " +
                    "ISBN" + " INTEGER);";
    private String WISHLIST_TABLE_CREATE =
            "CREATE TABLE " + WISHLIST_TABLE_NAME + " (" +
                    "ID" + " INTEGER, " +
                    "Title" + " TEXT, " +
                    "Author" + " TEXT, " +
                    "Publisher" + " TEXT, " +
                    "Year" + " INTEGER, " +
                    "Edition" + " TEXT, " +
                    "ISBN" + " INTEGER);";

    OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void setTABLE_NAME(String t) {
        TABLE_NAME = t;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SHELF_TABLE_CREATE);
        db.execSQL(WISHLIST_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Adding new contact
    void addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();

        int id = getLastID() + 1;

        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Title", book.getBookTitle());
        values.put("Author", book.getBookAuthor());
        values.put("Publisher", book.getBookPublisher());
        values.put("Year", book.getBookPublishYear());
        values.put("Edition", book.getBookEdition());
        values.put("ISBN", book.getBookISBN());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    int getLastID() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        cursor.moveToLast();
        int id;

        try {
            id = cursor.getInt(0);
        } catch (Exception e) {
            id = -1;
        }

        return id;
    }

    // Getting single contact
    Book getBook(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { "ID",
                        "Title", "Author", "Publisher", "Year", "Edition", "ISBN" }, "ID" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Book book = new Book(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                Integer.parseInt(cursor.getString(4)), cursor.getString(5), Integer.parseInt(cursor.getString(6)));

        return book;
    }

    // Getting All Contacts
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> booklist = new ArrayList<Book>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Book b = new Book();
                b.setBookID(cursor.getInt(0));
                b.setBookTitle(cursor.getString(1));
                b.setBookAuthor(cursor.getString(2));
                b.setBookPublisher(cursor.getString(3));
                b.setBookPublishYear(Integer.parseInt(cursor.getString(4)));
                b.setBookEdition(cursor.getString(5));
                b.setBookISBN(Integer.parseInt(cursor.getString(6)));
                // Adding contact to list
                booklist.add(b);
            } while (cursor.moveToNext());
        }

        // return contact list
        return booklist;
    }

    // Updating single contact
    public int updateBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Title", book.getBookTitle());
        values.put("Author", book.getBookAuthor());
        values.put("Publisher", book.getBookPublisher());
        values.put("Year", book.getBookPublishYear());
        values.put("Edition", book.getBookEdition());
        values.put("ISBN", book.getBookISBN());

        // updating row
        return db.update(TABLE_NAME, values, "ID" + " = ?",
                new String[] { String.valueOf(book.getBookID()) });
    }

    // Deleting single contact
    public void deleteBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID" + " = ?",
                new String[] { String.valueOf(book.getBookID()) });
        db.close();
    }


    // Getting contacts Count
    public int getBooksCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
