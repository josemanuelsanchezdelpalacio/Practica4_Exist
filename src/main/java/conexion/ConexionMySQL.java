package conexion;

import java.lang.module.InvalidModuleDescriptorException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    private static final String URL = "jdbc:mariadb://localhost:3606";
    private static final String USUARIO = "root";
    private static final String CLAVE = "admin";


    public static Connection conectar(String nombreBD) {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection((URL+"/"+nombreBD), USUARIO, CLAVE);
            System.out.println("Conexión OK.");
        }catch (SQLException e) {
            System.out.println("Error en la conexión");
            //e.printStackTrace();
        } catch (InvalidModuleDescriptorException e) {
            System.out.println("Error PAM");
        }
        return conexion;
    }
}



