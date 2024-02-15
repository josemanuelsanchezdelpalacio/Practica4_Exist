package DatosXMLaBD;

import conexiones.ConexionMySQL;
import entities.FamilyEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
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
import java.sql.SQLException;

public class InsertarFamilias {

    public static void insertar() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("yourPersistenceUnitName"); // Change to your persistence unit name
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Path p = Path.of("target/familiasFinal.xml");
            Document document = builder.parse(p.toFile());

            NodeList nodeList = document.getElementsByTagName("familia");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                String codigo = element.getElementsByTagName("codigo").item(0).getTextContent();
                String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();

                FamilyEntity family = new FamilyEntity();
                family.setFamilyCode(codigo);
                family.setFamilyName(nombre);

                em.persist(family);
            }

            transaction.commit();
            System.out.println("Datos de FAMILY subidos");

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
