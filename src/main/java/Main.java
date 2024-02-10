import CSVtoXML.LeerCSV_Centros;
import CSVtoXML.LeerCSV_Proyectos;
import ConsultasXQuery.ConsultaCentros;
import ConsultasXQuery.ConsultaFamilias;
import ConsultasXQuery.ConsultaProyectos;
import DatosXMLaBD.InsertarCentros;
import DatosXMLaBD.InsertarFamilias;
import DatosXMLaBD.InsertarProyectos;
import ManejoDB.CreacionTablas;
import XML_a_ExistDB.SubirArchivosExistDB;
import libs.Leer;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;

public class Main {

    public static void main(String[] args) {
        boolean salir = false;
        int opcion;

        do {
            System.out.println("1. Leer proyectos desde CSV y guardar en XML");
            System.out.println("2. Subir archivos XML a la base de datos eXist");
            System.out.println("3. Consultas eXistDB y creación de archivos XML");
            System.out.println("4. Exportar datos XQuery a XML");
            System.out.println("5. Insertar datos de XML a BD");
            System.out.println("0. Salir");

            opcion = Leer.pedirEntero("Introduce una opción: ");

            switch (opcion) {
                case 1:
                    //creación de las tablas
                    CreacionTablas.crear();
                    System.out.println("Tablas creadas");
                    break;
                case 2:
                    //leer proyectos desde CSV y guardar en XML
                    LeerCSV_Centros.leerCentros();
                    LeerCSV_Proyectos.leerProyectos();
                    break;
                case 3:
                    //subir archivos XML a la base de datos eXist
                    SubirArchivosExistDB.subirArchivos();
                    break;
                case 4:
                    //consultas eXistDB y creación de archivos XML
                    ConsultaCentros.listarCentros();
                    ConsultaProyectos.listarProyectos();
                    ConsultaFamilias.listarFamilias();
                    break;
                case 5:
                    //insertar los datos a las tablas
                    InsertarCentros.insertar();
                    InsertarFamilias.insertar();
                    InsertarProyectos.insertar();
                    break;
                case 0:
                    System.out.println("Salir");
                    salir = true;
                    break;
                default:
                    System.out.println("La opción seleccionada no existe");
            }

        } while (!salir);
    }
}