package mylibrary;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {

    private String personName;
    private String personSurname;
    private String personEmail;
    private String personPassword;
    private ArrayList<Book> personBooks;

    public Person() {
    }

    public Person(String personName, String personSurname, String personEmail, String personPassword) {
        this.personName = personName;
        this.personSurname = personSurname;
        this.personEmail = personEmail;
        this.personPassword = personPassword;
        personBooks = new ArrayList<>();
    }

    public void borrowBook(Book b, Library l) {
        l.borrowBookFromLibrary(b);
        personBooks.add(b);
    }

    public void returnBook(Book b, Library l) {

        l.addBookToLibrary(b);
        personBooks.remove(b);
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setPersonSurname(String personSurname) {
        this.personSurname = personSurname;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public void setPersonPassword(String personPassword) {
        this.personPassword = personPassword;
    }

    public void setPersonBooks(ArrayList<Book> personBooks) {
        this.personBooks = personBooks;
    }

    public String getPersonName() {
        return personName;
    }

    public String getPersonSurname() {
        return personSurname;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public String getPersonPassword() {
        return personPassword;
    }

    public ArrayList<Book> getPersonBooks() {
        return personBooks;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personName='" + personName + '\'' +
                ", personSurname='" + personSurname + '\'' +
                ", personEmail='" + personEmail + '\'' +
                ", personPassword='" + personPassword + '\'' +
                ", personBooks=" + personBooks +
                '}';
    }
}
