package MyLibrary;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerOperation extends UnicastRemoteObject implements RMIInterface, Serializable {

    private static final long serialVersionUID = 20120731125400L;
    private ArrayList<Person> users;
    private Library library;

    public ServerOperation() throws RemoteException {
        super();
        users = new ArrayList<>();
        library = new Library();
    }

    @Override
    public void saveUser(Person p) throws RemoteException {

        users.add(p);
        System.out.println("email=" + p.getPersonEmail() + " : password=" + p.getPersonPassword() + " REGISTERED");
    }

    @Override
    public Person accessUser(String email, String password) throws RemoteException {
        for (Person p : users) {
            if (p.getPersonEmail().equals(email) && p.getPersonPassword().equals(password)) {
                System.out.println("email=" + email + " : password=" + password + " LOGGED IN");
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
            if (p.getPersonEmail().equals(user.getPersonEmail()) && p.getPersonPassword().equals(user.getPersonPassword())) {
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

    @Override
    public void serverStopAndSerialize() throws RemoteException {

        try {
            FileOutputStream file = new FileOutputStream("DATABASE.txt");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this);
            out.close();
            file.close();
            System.err.println("server serialized");
            Naming.unbind("//localhost/MyServer");
            UnicastRemoteObject.unexportObject(this, true);
            System.err.println("server closed");

        } catch (FileNotFoundException fnf) {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("DATABASE.txt"), "utf-8"))) {
                FileOutputStream file = new FileOutputStream("DATABASE.txt");
                ObjectOutputStream out = new ObjectOutputStream(file);
                out.writeObject(this);
                out.close();
                file.close();
                System.err.println("server serialized");
                Naming.unbind("//localhost/MyServer");
                UnicastRemoteObject.unexportObject(this, true);
                System.err.println("server closed");
            } catch (IOException e) {
                e.printStackTrace();
            } catch(NotBoundException n) {
                n.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotBoundException n) {
            n.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {
            String filename = "DATABASE.txt";
            FileInputStream file = new FileInputStream(filename);
            ObjectInput in = new ObjectInputStream(file);

            Naming.rebind("//localhost/MyServer", (ServerOperation) in.readObject());
            in.close();
            file.close();
            System.err.println("Server deserialized and ready");
        } catch (FileNotFoundException e) {

            try {
                Naming.rebind("//localhost/MyServer", new ServerOperation());
                System.err.println("Server ready");
            } catch (RemoteException | MalformedURLException r) {
                r.printStackTrace();
            }

        } catch (IOException | ClassNotFoundException io) {
            System.err.println("Server exception: " + io.toString());
            io.printStackTrace();
        }
    }
}