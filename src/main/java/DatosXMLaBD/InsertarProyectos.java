package DatosXMLaBD;

import conexiones.ConexionMySQL;
import entities.ProjectEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class InsertarProyectos {

    public static void insertar() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Path p = Path.of("target/proyectosFinal.xml");
            Document document = builder.parse(p.toFile());

            NodeList nodeList = document.getElementsByTagName("Proyecto");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                String titulo = element.getElementsByTagName("tituloProyecto").item(0).getTextContent();
                String fechaInicio = element.getElementsByTagName("fechaInicio").item(0).getTextContent();
                String fechaFin = element.getElementsByTagName("fechaFin").item(0).getTextContent();

                String estado = "Pendiente";

                if (!fechaInicio.isEmpty() && !fechaFin.isEmpty()) {
                    estado = "Completado";
                } else if (!fechaInicio.isEmpty()) {
                    estado = "En Curso";
                }

                ProjectEntity project = new ProjectEntity();
                project.setTitle(titulo);
                project.setState(estado);

                SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    if (!fechaInicio.isEmpty()) {
                        Date parsedStartDate = sdfInput.parse(fechaInicio);
                        project.setInitDate(java.sql.Date.valueOf(sdfOutput.format(parsedStartDate)));
                    }

                    if (!fechaFin.isEmpty()) {
                        Date parsedEndDate = sdfInput.parse(fechaFin);
                        project.setEndDate(java.sql.Date.valueOf(sdfOutput.format(parsedEndDate)));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                em.persist(project);
            }

            transaction.commit();
            System.out.println("Datos de PROJECT subidos");

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

