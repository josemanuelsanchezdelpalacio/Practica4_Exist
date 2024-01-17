package ConsultasXQuery;

import conexion.ConexionExistDB;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XQueryService;

import java.io.FileWriter;
import java.io.IOException;

public class ConsultaProyectos {

    static Collection col = ConexionExistDB.conexionExistDb();
    public static void listarProyectos() {
        final String driver = "org.exist.xmldb.DatabaseImpl";

        // Inicializar el driver
        Class cl = null;
        try {
            cl = Class.forName(driver);

            Database database = (Database) cl.newInstance();
            database.setProperty("create-database", "true");
            DatabaseManager.registerDatabase(database);

            Collection col = null;
            // Obtener la colección

            col = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/");
            XQueryService xqs = (XQueryService) col.getService("XQueryService", "1.0");
            xqs.setProperty("indent", "yes");

            //Consulta para obtener los centros
            ResourceSet resultCentros = xqs.query("let $proyectos := doc(\"coleccionXML/proyectosFP.xml\")//Row\n" +
                    "return <proyectos>{\n" +
                    "    for $x in $proyectos\n" +
                    "    return\n" +
                    "    <Proyecto>\n" +
                    "        <tituloProyecto>{$x/TÍTULODELPROYECTO/data()}</tituloProyecto>\n" +
                    "        <fechaInicio>{$x/AUTORIZACIÓN/data()}</fechaInicio>\n" +
                    "        <fechaFin>{$x/CONTINUIDAD/data()}</fechaFin>\n" +
                    "    </Proyecto>\n" +
                    "}</proyectos>");

            //Obtener los resultados
            ResourceIterator i = resultCentros.getIterator();
            Resource res = null;

            //Escribir los resultados en un archivo XML
            while (i.hasMoreResources()) {

                res = i.nextResource();
                FileWriter fw = new FileWriter("target/familiasFinal.xml");
                fw.write(res.getContent().toString());
                System.out.println("Consulta realizada correctamente");

            }
        } catch (ClassNotFoundException e) {
            System.out.println("No se pudo encontrar la clase");
        } catch (XMLDBException e) {
            System.out.println("Error con la coleccion");
        } catch (IOException e) {
            System.out.println("Error durante la lectura del XML");
        } catch (InstantiationException e) {
            System.out.println("No se puede instanciar la bd");
        } catch (IllegalAccessException e) {
            System.out.println("Error al acceder a la coleccion");
        }finally {

            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    System.out.println("Error al cerrar la conexión");
                }
            }
        }
    }
}
