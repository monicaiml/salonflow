package conexion;

import java.sql.Connection;

public class PruebaConexion {

    public static void main(String[] args) {

        Connection conexion = ConexionBD.conectar();

        if (conexion != null) {
            System.out.println("Prueba finalizada correctamente.");

            try {
                conexion.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar la conexión.");
            }
        } else {
            System.out.println("No se ha podido conectar.");
        }
    }
}