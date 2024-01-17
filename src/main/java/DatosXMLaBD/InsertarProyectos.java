package DatosXMLaBD;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class InsertarProyectos {

    public static void insertProjects() {
        try {
            // Cargar el archivo XML
            File file = new File("target/proyectosFinal.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            // Obtener la lista de proyectos
            NodeList nList = doc.getElementsByTagName("Proyecto");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    // Obtener los datos del proyecto
                    String tituloProyecto = eElement.getElementsByTagName("tituloProyecto").item(0).getTextContent();
                    String fechaInicio = eElement.getElementsByTagName("fechaInicio").item(0).getTextContent();
                    String fechaFin = eElement.getElementsByTagName("fechaFin").item(0).getTextContent();

                    // Crear la consulta de inserción
                    String query = "INSERT INTO PROJECT (Title, InitDate, EndDate) VALUES (?, ?, ?)";

                    // Preparar y ejecutar la consulta
                /*try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    pstmt.setString(1, tituloProyecto);
                    pstmt.setString(2, fechaInicio);
                    pstmt.setString(3, fechaFin);
                    pstmt.executeUpdate();
                }*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
