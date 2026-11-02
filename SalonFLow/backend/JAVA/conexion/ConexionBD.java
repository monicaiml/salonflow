package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.util.Properties;

public class ConexionBD {

    public static Connection conectar() {

        Properties propiedades = new Properties();

        try (InputStream entrada = ConexionBD.class
                .getResourceAsStream("/conexion/db.properties")) {

            if (entrada == null) {
                throw new Exception("No se encuentra db.properties");
            }

            propiedades.load(entrada);

            String url = propiedades.getProperty("db.url");
            String user = propiedades.getProperty("db.user");
            String password = propiedades.getProperty("db.password");

            Connection conexion = DriverManager.getConnection(url, user, password);

            System.out.println("Conexion correcta");

            return conexion;

        } catch (Exception e) {
            System.out.println("Error de conexion: " + e.getMessage());
            return null;
        }
    }
}