package MyLibrary;

import java.io.Serializable;
import java.util.ArrayList;

public class Library implements Serializable {

    private ArrayList<Book> libraryBooks = new ArrayList<>();

    public Library() {
        Book book1 = new Book("book1");
        Book book2 = new Book("book2");
        Book book3 = new Book("book3");
        Book book4 = new Book("book4");
        Book book5 = new Book("book5");
        Book book6 = new Book("book6");

        libraryBooks.add(book1);
        libraryBooks.add(book2);
        libraryBooks.add(book3);
        libraryBooks.add(book4);
        libraryBooks.add(book5);
        libraryBooks.add(book6);
    }

    public Library(Library l) {
        libraryBooks = l.libraryBooks;
    }

    public void addBookToLibrary(Book b) {
        for (Book book : libraryBooks) {
            if (book.getBookTitle().equals(b.getBookTitle())) {
                System.out.println("Library.java: addBookToLibrary(\"" + b.getBookTitle() + "\")");
                book.setBookBorrowed(false);
                return;
            }
        }
        System.out.println("Library.java: addBookToLibrary(\"" + b.getBookTitle() + "\") - NEW");
        libraryBooks.add(b);
    }

    public void borrowBookFromLibrary(Book b) {

        for (Book book : libraryBooks)
            if (book.getBookTitle().equals(b.getBookTitle())) {
                System.out.println("Library.java: borrowBookFromLibrary(\"" + b.getBookTitle() + "\")");
                book.setBookBorrowed(true);
                return;
            }
        System.err.println("Library.java: borrowBookFromLibrary FAILED");
    }

    public ArrayList<Book> availableLibraryBooks() {
        ArrayList<Book> availableBooks = new ArrayList<>();
        for (Book b : libraryBooks) {
            if (!b.isBookBorrowed()) {
                availableBooks.add(b);
            }
        }
        return availableBooks;
    }

    public ArrayList<Book> allBooks() {
        return libraryBooks;
    }
}