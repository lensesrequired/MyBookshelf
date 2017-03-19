package edu.coe.asmarek.mybookshelf;

/**
 * Created by Anna on 2/9/17.
 */

public class Book {
    private int bookID;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private Integer bookPublishYear;
    private String bookEdition;
    private Integer bookISBN;

    public Book() {
        this.bookID = 0;
        this.bookTitle = "";
        this.bookAuthor = "";
        this.bookPublisher = "";
        this.bookPublishYear = 0;
        this.bookEdition = "";
        this.bookISBN = 0;
    }

    public Book(int bookID, String bookTitle, String bookAuthor, String bookPublisher, Integer bookPublishYear, String bookEdition, Integer bookISBN) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookPublishYear = bookPublishYear;
        this.bookEdition = bookEdition;
        this.bookISBN = bookISBN;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int ID) {
        this.bookID = ID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public Integer getBookPublishYear() {
        return bookPublishYear;
    }

    public void setBookPublishYear(Integer bookPublishYear) {
        this.bookPublishYear = bookPublishYear;
    }

    public String getBookEdition() {
        return bookEdition;
    }

    public void setBookEdition(String bookEdition) {
        this.bookEdition = bookEdition;
    }

    public Integer getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(Integer bookISBN) {
        this.bookISBN = bookISBN;
    }
}
