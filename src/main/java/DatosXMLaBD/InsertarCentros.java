package DatosXMLaBD;

import entities.EntityEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;

public class InsertarCentros {

    public static void insertCentro() {
        try {
            // Crear la SessionFactory utilizando la configuración en persistence.xml
            SessionFactory sessionFactory = new Configuration().configure("persistence.xml").buildSessionFactory();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Path p = Path.of("target/centrosFinal.xml");
            Document document = builder.parse(p.toFile());

            NodeList nodeList = document.getElementsByTagName("entidad");

            // Creación de los objetos de la entidad ENTITY
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                EntityEntity entidad = new EntityEntity();

                entidad.setEntityName(element.getElementsByTagName("nombre").item(0).getTextContent());
                entidad.setEntityCode(element.getElementsByTagName("codigo").item(0).getTextContent());
                entidad.setEmail(element.getElementsByTagName("email").item(0).getTextContent());

                // Usar la SessionFactory de Hibernate
                try (Session session = sessionFactory.openSession()) {
                    Transaction transaction = session.beginTransaction();
                    session.save(entidad);
                    transaction.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error en la operación de la base de datos");
                }
            }

            // Cerrar la SessionFactory
            sessionFactory.close();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            System.out.println("Error en la operación de la base de datos");
        }
    }
}