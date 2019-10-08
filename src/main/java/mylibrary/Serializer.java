package mylibrary;

import mylibrary.launchers.ServerOperation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Serializer {

    private final static String DATABASE_FILE = "server.xml";

    public static void serializeToXml(ServerOperation server) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ServerOperation.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(server, new File(DATABASE_FILE));
            marshaller.marshal(server, System.out);
            System.err.println("Server serialized to " + DATABASE_FILE + " file");
        } catch (JAXBException prop) {
            prop.printStackTrace();
        }
    }

    public static ServerOperation deserializeFromXml() {

        ServerOperation server;

        try {
            File file = new File("server.xml");
            if (file.exists() && file.canRead()) {
                JAXBContext jaxbContext = JAXBContext.newInstance(ServerOperation.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                server = (ServerOperation) unmarshaller.unmarshal(file);
                System.err.println("Server deserialized from " + DATABASE_FILE + " file");
                System.out.println(server);
                return server;
            }

        } catch (JAXBException jax) {
            jax.printStackTrace();
        }
        return null;
    }
}
