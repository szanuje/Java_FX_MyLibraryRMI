package mylibrary.launchers;

import mylibrary.Book;
import mylibrary.Library;
import mylibrary.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIInterface extends Remote {
    public void saveUser(Person p) throws RemoteException;

    public Person accessUser(String email, String password) throws RemoteException;

    public ArrayList<Book> getAvailableBooks() throws RemoteException;

    public void updateDatabase(Person p, Library l) throws RemoteException;

    public Library accessLibrary() throws RemoteException;


}
