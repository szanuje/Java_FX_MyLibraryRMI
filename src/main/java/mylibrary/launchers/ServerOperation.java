package mylibrary.launchers;


import mylibrary.Book;
import mylibrary.Library;
import mylibrary.Person;
import mylibrary.Serializer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
public class ServerOperation extends UnicastRemoteObject implements RMIInterface, Serializable {

    private ArrayList<Person> users;
    private Library library;

    private static Registry registry;

    public ServerOperation() throws RemoteException {
        super();
        users = new ArrayList<>();
        library = new Library();
    }

    @XmlElement
    public ArrayList<Person> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Person> users) {
        this.users = users;
    }

    @XmlElement
    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public static Registry getRegistry() {
        return registry;
    }

    public static void setRegistry(Registry registry) {
        ServerOperation.registry = registry;
    }

    @Override
    public void saveUser(Person p) throws RemoteException {

        users.add(p);
        System.out.println("---NEW USER REGISTERED---");
        System.out.println(p.toString());
        System.out.println("-------------------------");
    }

    @Override
    public Person accessUser(String email, String password) throws RemoteException {
        for (Person p : users) {
            if (p.getPersonEmail().equals(email) && p.getPersonPassword().equals(password)) {
                System.out.println("-----USER LOGGED IN-----");
                System.out.println("email=" + email + "\npassword=" + password);
                return p;
            }
        }
        System.err.println("accessUser() - Wrong email or password");
        return null;
    }

    @Override
    public ArrayList<Book> getAvailableBooks() throws RemoteException {
        return library.availableLibraryBooks();
    }

    @Override
    public void updateDatabase(Person p, Library l) throws RemoteException {
        for (Person user : users) {
            if (p.getPersonEmail().equals(user.getPersonEmail()) &&
                    p.getPersonPassword().equals(user.getPersonPassword())) {
                System.err.println("updating database...");
                users.remove(user);
                users.add(p);
                library = new Library(l);
                return;

            }
        }
    }

    @Override
    public Library accessLibrary() throws RemoteException {
        return library;
    }

    public static void main(String[] args) {

        ServerOperation server = Serializer.deserializeFromXml();
        if (server == null) {
            try {
                server = new ServerOperation();
            } catch (RemoteException r) {
                r.printStackTrace();
            }
        }
        try {
            registry = LocateRegistry.createRegistry(1090);
            registry.bind("//localhost/MyServer", server);
            System.err.println("Server started...");
        } catch (AlreadyBoundException | RemoteException e) {
            e.printStackTrace();
        }

        try {
            System.out.printf("Press any key and click ENTER to close server... ");
            System.in.read();
            Serializer.serializeToXml(server);
            System.err.println("Server closed");
            registry.unbind("//localhost/MyServer");
            UnicastRemoteObject.unexportObject(server, true);
        } catch (IOException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}