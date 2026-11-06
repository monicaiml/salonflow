
package dao;

import conexion.ConexionBD;
import modelo.Peluquero;
import java.sql.*;

public class PeluqueroDAO {

    // Insertar peluquero
    public boolean insertar(Peluquero peluquero) {

        String sql = "INSERT INTO peluquero (nombre, especialidad) VALUES (?, ?)";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return false;
            }

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, peluquero.getNombre());
            ps.setString(2, peluquero.getEspecialidad());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar peluquero: " + e.getMessage());
            return false;
        }
    }

    // Listar peluqueros
    public ResultSet listar() {

        String sql = "SELECT * FROM peluquero ORDER BY id_peluquero";

        try {
            Connection con = ConexionBD.conectar();

            if (con == null) {
                return null;
            }

            PreparedStatement ps = con.prepareStatement(sql);

            return ps.executeQuery();

        } catch (SQLException e) {
            System.out.println("Error al listar peluqueros: " + e.getMessage());
            return null;
        }
    }

    // Buscar peluquero por ID
    public ResultSet buscarPorId(int id) {

        String sql = "SELECT * FROM peluquero WHERE id_peluquero = ?";

        try {
            Connection con = ConexionBD.conectar();

            if (con == null) {
                return null;
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeQuery();

        } catch (SQLException e) {
            System.out.println("Error al buscar peluquero: " + e.getMessage());
            return null;
        }
    }

    // Modificar peluquero
    public boolean modificar(Peluquero peluquero) {

        String sql = "UPDATE peluquero SET nombre = ?, especialidad = ? WHERE id_peluquero = ?";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return false;
            }

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, peluquero.getNombre());
            ps.setString(2, peluquero.getEspecialidad());
            ps.setInt(3, peluquero.getId_peluquero());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar peluquero: " + e.getMessage());
            return false;
        }
    }

    // Eliminar peluquero
    public boolean eliminar(int id) {

        String sql = "DELETE FROM peluquero WHERE id_peluquero = ?";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return false;
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar peluquero: " + e.getMessage());
            return false;
        }
    }
}
