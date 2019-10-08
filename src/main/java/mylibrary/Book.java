package mylibrary;

import java.io.Serializable;

public class Book implements Serializable {

    private String bookTitle;
    private boolean isBookBorrowed;

    public Book() {
    }

    public Book(String bookTitle) {
        this.bookTitle = bookTitle;
        isBookBorrowed = false;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setBookBorrowed(boolean b) {
        isBookBorrowed = b;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public boolean isBookBorrowed() {
        return isBookBorrowed;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookTitle='" + bookTitle + '\'' +
                ", isBookBorrowed=" + isBookBorrowed +
                '}';
    }
}
