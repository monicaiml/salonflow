package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
        "jdbc:postgresql://localhost:5432/salonflow";

    private static final String USER = "postgres";

    private static final String PASSWORD = "postgres";

    public static Connection conectar() {

        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(
                URL, USER, PASSWORD
            );

            System.out.println("Conexion correcta");

        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }

        return conexion;
    }
}