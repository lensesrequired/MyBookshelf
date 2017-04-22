package edu.coe.asmarek.mybookshelf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Anna on 3/18/17.
 */

public class OpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "MyBookShelf1";
    private String BOOKS_TABLE_NAME = "books";
    private String SHELVES_TABLE_NAME = "shelves";
    private String BOOKS_TABLE_CREATE =
            "CREATE TABLE " + BOOKS_TABLE_NAME + " (" +
                    "ID" + " INTEGER, " +
                    "Title" + " TEXT, " +
                    "Author" + " TEXT, " +
                    "Publisher" + " TEXT, " +
                    "Year" + " INTEGER, " +
                    "Edition" + " TEXT, " +
                    "ISBN" + " TEXT, " +
                    "Shelf" + " TEXT);";
    private String SHELVES_TABLE_CREATE =
            "CREATE TABLE " + SHELVES_TABLE_NAME + " (" +
                    "ID" + " INTEGER, " +
                    "ShelfName" + " TEXT);";

    OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BOOKS_TABLE_CREATE);
        db.execSQL(SHELVES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Adding new contact
    void addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();

        int id = getLastID(BOOKS_TABLE_NAME) + 1;

        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("Title", book.getBookTitle());
        values.put("Author", book.getBookAuthor());
        values.put("Publisher", book.getBookPublisher());
        values.put("Year", book.getBookPublishYear());
        values.put("Edition", book.getBookEdition());
        values.put("ISBN", book.getBookISBN());
        values.put("Shelf", book.getBookShelf());

        // Inserting Row
        db.insert(BOOKS_TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    int getLastID(String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

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

        Cursor cursor = db.query(BOOKS_TABLE_NAME, new String[] { "ID",
                        "Title", "Author", "Publisher", "Year", "Edition", "ISBN", "Shelf" }, "ID" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Book book = new Book(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                Integer.parseInt(cursor.getString(4)), cursor.getString(5), (cursor.getString(6)), cursor.getString(7));

        return book;
    }

    // Getting All Contacts
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> booklist = new ArrayList<Book>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + BOOKS_TABLE_NAME;

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
                b.setBookISBN((cursor.getString(6)));
                b.setBookShelf(cursor.getString(7));
                // Adding contact to list
                booklist.add(b);
            } while (cursor.moveToNext());
        }

        // return contact list
        return booklist;
    }

    public ArrayList<Book> getAllBooksOnShelf(String shelfName) {
        ArrayList<Book> booklist = new ArrayList<Book>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + BOOKS_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(shelfName.equals(cursor.getString(7))) {
                    Book b = new Book();
                    b.setBookID(cursor.getInt(0));
                    b.setBookTitle(cursor.getString(1));
                    b.setBookAuthor(cursor.getString(2));
                    b.setBookPublisher(cursor.getString(3));
                    b.setBookPublishYear(Integer.parseInt(cursor.getString(4)));
                    b.setBookEdition(cursor.getString(5));
                    b.setBookISBN((cursor.getString(6)));
                    b.setBookShelf(cursor.getString(7));
                    // Adding contact to list
                    booklist.add(b);
                }
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
        values.put("Shelf", book.getBookShelf());

        // updating row
        return db.update(BOOKS_TABLE_NAME, values, "ID" + " = ?",
                new String[] { String.valueOf(book.getBookID()) });
    }

    // Deleting single contact
    public void deleteBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BOOKS_TABLE_NAME, "ID" + " = ?",
                new String[] { String.valueOf(book.getBookID()) });
        db.close();
    }


    // Getting contacts Count
    public int getBooksCount() {
        String countQuery = "SELECT  * FROM " + BOOKS_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public void addShelf(String shelfName) {
        SQLiteDatabase db = this.getWritableDatabase();

        int id = getLastID(SHELVES_TABLE_NAME) + 1;

        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("ShelfName", shelfName);

        // Inserting Row
        db.insert(SHELVES_TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public int updateShelfName(String oldShelf, String newShelf) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ShelfName", newShelf);

        // updating row
        return db.update(BOOKS_TABLE_NAME, values, "ShelfName" + " = ?",
                new String[] { String.valueOf(oldShelf) });
    }

}
