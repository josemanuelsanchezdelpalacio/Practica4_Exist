import CSVtoXML.LeerCSV_Centros;
import CSVtoXML.LeerCSV_Proyectos;
import ConsultasXQuery.ConsultaCentros;
import ConsultasXQuery.ConsultaFamilias;
import ConsultasXQuery.ConsultaProyectos;
import DatosXMLaBD.InsertarCentros;
import XML_a_ExistDB.SubirArchivosExistDB;
import libs.Leer;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;

public class Main {

    public static void main(String[] args) {
        boolean salir = false;
        int opcion;

        // Variable para almacenar la conexión a la base de datos
        Collection col = null;

        do {
            System.out.println("1. Leer proyectos desde CSV y guardar en XML");
            System.out.println("2. Subir archivos XML a la base de datos eXist");
            System.out.println("3. Consultas eXist-DB y creación de archivos XML");
            System.out.println("4. Insertar datos de XML a BD");
            System.out.println("0. Salir");

            opcion = Leer.pedirEntero("Introduce una opción: ");

            switch (opcion) {
                case 1:
                    // Leer proyectos desde CSV y guardar en XML
                    LeerCSV_Centros.leerCentros();
                    LeerCSV_Proyectos.leerProyectos();
                    break;
                case 2:
                    // Subir archivos XML a la base de datos eXist
                    col = SubirArchivosExistDB.subirArchivos();
                    break;
                case 3:
                    // Consultas eXist-DB y creación de archivos XML
                    ConsultaFamilias.listarFamilias();
                    ConsultaCentros.consultaExist();
                    ConsultaProyectos.listarProyectos();
                    break;
                case 4:
                    InsertarCentros.insertCentro();
                case 0:
                    System.out.println("Salir");
                    salir = true;
                    break;
                default:
                    System.out.println("La opción seleccionada no existe");
            }

        } while (!salir);

        // Cerrar la conexión después de salir del bucle
        if (col != null) {
            try {
                col.close();
                System.out.println("Cerrada la conexión");
            } catch (XMLDBException e) {
                System.out.println("Error al cerrar la conexión");
            }
        }
    }
}