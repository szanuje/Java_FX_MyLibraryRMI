package MyLibrary;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Launcher {
    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException
    {
        new ServerOperation().main(args);
        new Main().main(args);
        System.exit(0);
    }
}
