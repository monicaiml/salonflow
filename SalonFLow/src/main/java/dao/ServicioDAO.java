package dao;

import conexion.ConexionBD;
import modelo.Servicio;
import java.sql.*;

public class ServicioDAO {

    // Insertar servicio
    public boolean insertar(Servicio servicio) {

        String sql = "INSERT INTO servicio (nombre_servicio, precio) VALUES (?, ?)";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return false;
            }

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, servicio.getNombre_servicio());
            ps.setBigDecimal(2, java.math.BigDecimal.valueOf(servicio.getPrecio()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar servicio: " + e.getMessage());
            return false;
        }
    }

    // Listar servicios
    public ResultSet listar() {

        String sql = "SELECT * FROM servicio ORDER BY id_servicio";

        try {
            Connection con = ConexionBD.conectar();

            if (con == null) {
                return null;
            }

            PreparedStatement ps = con.prepareStatement(sql);

            return ps.executeQuery();

        } catch (SQLException e) {
            System.out.println("Error al listar servicios: " + e.getMessage());
            return null;
        }
    }

    // Buscar servicio por ID
    public ResultSet buscarPorId(int id) {

        String sql = "SELECT * FROM servicio WHERE id_servicio = ?";

        try {
            Connection con = ConexionBD.conectar();

            if (con == null) {
                return null;
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeQuery();

        } catch (SQLException e) {
            System.out.println("Error al buscar servicio: " + e.getMessage());
            return null;
        }
    }

    // Modificar servicio
    public boolean modificar(Servicio servicio) {

        String sql = "UPDATE servicio SET nombre_servicio = ?, precio = ? WHERE id_servicio = ?";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return false;
            }

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, servicio.getNombre_servicio());
            ps.setBigDecimal(2, java.math.BigDecimal.valueOf(servicio.getPrecio()));
            ps.setInt(3, servicio.getId_servicio());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar servicio: " + e.getMessage());
            return false;
        }
    }

    // Eliminar servicio
    public boolean eliminar(int id) {

        String sql = "DELETE FROM servicio WHERE id_servicio = ?";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return false;
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar servicio: " + e.getMessage());
            return false;
        }
    }
}
