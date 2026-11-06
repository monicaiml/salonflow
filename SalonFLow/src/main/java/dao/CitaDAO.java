
package dao;

import conexion.ConexionBD;
import modelo.Cita;
import modelo.Servicio;
import java.sql.*;

public class CitaDAO {

    // Insertar cita y devolver su ID
    public int insertar(Cita cita, int idCliente, int idPeluquero) {

        String sql = "INSERT INTO cita (fecha_hora, estado, id_cliente, id_peluquero) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return -1;
            }

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setTimestamp(1, Timestamp.valueOf(cita.getFecha_hora()));
            ps.setString(2, cita.getEstado());
            ps.setInt(3, idCliente);
            ps.setInt(4, idPeluquero);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar cita: " + e.getMessage());
        }

        return -1;
    }

    // Listar citas
    public ResultSet listar() {

        String sql = "SELECT c.*, cl.nombre AS nombre_cliente, " +
                     "p.nombre AS nombre_peluquero " +
                     "FROM cita c " +
                     "INNER JOIN cliente cl ON c.id_cliente = cl.id_cliente " +
                     "INNER JOIN peluquero p ON c.id_peluquero = p.id_peluquero " +
                     "ORDER BY c.fecha_hora";

        try {
            Connection con = ConexionBD.conectar();

            if (con == null) {
                return null;
            }

            PreparedStatement ps = con.prepareStatement(sql);

            return ps.executeQuery();

        } catch (SQLException e) {
            System.out.println("Error al listar citas: " + e.getMessage());
            return null;
        }
    }

    // Buscar cita por ID
    public ResultSet buscarPorId(int id) {

        String sql = "SELECT * FROM cita WHERE id_cita = ?";

        try {
            Connection con = ConexionBD.conectar();

            if (con == null) {
                return null;
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeQuery();

        } catch (SQLException e) {
            System.out.println("Error al buscar cita: " + e.getMessage());
            return null;
        }
    }

    // Modificar estado de la cita
    public boolean modificarEstado(int id, String estado) {

        String sql = "UPDATE cita SET estado = ? WHERE id_cita = ?";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return false;
            }

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, estado);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar cita: " + e.getMessage());
            return false;
        }
    }

    // Eliminar cita y sus servicios
    public boolean eliminar(int id) {

        String sqlServicios = "DELETE FROM cita_servicio WHERE id_cita = ?";
        String sqlCita = "DELETE FROM cita WHERE id_cita = ?";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return false;
            }

            con.setAutoCommit(false);

            try {
                PreparedStatement ps1 = con.prepareStatement(sqlServicios);
                ps1.setInt(1, id);
                ps1.executeUpdate();
                ps1.close();

                PreparedStatement ps2 = con.prepareStatement(sqlCita);
                ps2.setInt(1, id);

                int filas = ps2.executeUpdate();
                ps2.close();

                con.commit();

                return filas > 0;

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar cita: " + e.getMessage());
            return false;
        }
    }

    // Añadir servicio a una cita
    public boolean agregarServicio(int idCita, Servicio servicio) {

        String sql = "INSERT INTO cita_servicio (id_cita, id_servicio, precio) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return false;
            }

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idCita);
            ps.setInt(2, servicio.getId_servicio());
            ps.setBigDecimal(3, java.math.BigDecimal.valueOf(servicio.getPrecio()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al añadir servicio: " + e.getMessage());
            return false;
        }
    }

    // Consultar servicios de una cita
    public ResultSet listarServicios(int idCita) {

        String sql = "SELECT s.*, cs.precio AS precio_cita " +
                     "FROM cita_servicio cs " +
                     "INNER JOIN servicio s ON cs.id_servicio = s.id_servicio " +
                     "WHERE cs.id_cita = ?";

        try {
            Connection con = ConexionBD.conectar();

            if (con == null) {
                return null;
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCita);

            return ps.executeQuery();

        } catch (SQLException e) {
            System.out.println("Error al listar servicios de la cita: " + e.getMessage());
            return null;
        }
    }
}
