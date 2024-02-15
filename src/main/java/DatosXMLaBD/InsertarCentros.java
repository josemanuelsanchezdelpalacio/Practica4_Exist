package DatosXMLaBD;

import conexiones.ConexionMySQL;
import entities.EntityEntity;
import jakarta.persistence.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertarCentros {

    public static void insertar() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("yourPersistenceUnitName"); // Change to your persistence unit name
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Path p = Path.of("target/centrosFinal.xml");
            Document document = builder.parse(p.toFile());
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("entidad");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                String codigo = element.getElementsByTagName("codigo").item(0).getTextContent().isEmpty() ? String.valueOf(-(i + 1)) : element.getElementsByTagName("codigo").item(0).getTextContent();
                String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
                String web = element.getElementsByTagName("web").item(0).getTextContent();
                String correo = element.getElementsByTagName("correoElectronico").item(0).getTextContent();

                EntityEntity entity = new EntityEntity();
                entity.setEntityName(nombre);
                entity.setEntityCode(codigo);
                entity.setWeb(web);
                entity.setEmail(correo);

                em.persist(entity);
            }

            transaction.commit();
            System.out.println("Datos de ENTITY subidos");

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            em.close();
            emf.close();
        }
    }
}
