package DatosXMLaBD;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class InsertarFamilias {

    public static void insertarFamilies() {
        try {
            // Cargar el archivo XML
            File file = new File("target/familiasFinal.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            // Obtener la lista de familias
            NodeList nList = doc.getElementsByTagName("familia_profesional");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    // Obtener los datos de la familia
                    String nombreFamilia = eElement.getElementsByTagName("nombre").item(0).getTextContent();

                    // Crear la consulta de inserción
                    String query = "INSERT INTO FAMILY (FamilyName) VALUES (?)";

                    // Preparar y ejecutar la consulta
                /*try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    pstmt.setString(1, nombreFamilia);
                    pstmt.executeUpdate();
                }*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


