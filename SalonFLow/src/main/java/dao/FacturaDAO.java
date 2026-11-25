
package dao;

import conexion.ConexionBD;
import java.sql.*;
import java.math.BigDecimal;

public class FacturaDAO {

    // Insertar factura
    public boolean insertar(int idCita, BigDecimal total) {

        String sql = "INSERT INTO factura (total, id_cita) VALUES (?, ?)";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return false;
            }

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setBigDecimal(1, total);
            ps.setInt(2, idCita);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar factura: " + e.getMessage());
            return false;
        }
    }

    // Listar facturas
    public ResultSet listar() {

        String sql = "SELECT * FROM factura ORDER BY fecha_emision DESC";

        try {
            Connection con = ConexionBD.conectar();

            if (con == null) {
                return null;
            }

            PreparedStatement ps = con.prepareStatement(sql);

            return ps.executeQuery();

        } catch (SQLException e) {
            System.out.println("Error al listar facturas: " + e.getMessage());
            return null;
        }
    }

    // Buscar factura por ID
    public ResultSet buscarPorId(int id) {

        String sql = "SELECT * FROM factura WHERE id_factura = ?";

        try {
            Connection con = ConexionBD.conectar();

            if (con == null) {
                return null;
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeQuery();

        } catch (SQLException e) {
            System.out.println("Error al buscar factura: " + e.getMessage());
            return null;
        }
    }

    // Buscar factura por cita
    public ResultSet buscarPorCita(int idCita) {

        String sql = "SELECT * FROM factura WHERE id_cita = ?";

        try {
            Connection con = ConexionBD.conectar();

            if (con == null) {
                return null;
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCita);

            return ps.executeQuery();

        } catch (SQLException e) {
            System.out.println("Error al buscar factura: " + e.getMessage());
            return null;
        }
    }

    // Eliminar factura
    public boolean eliminar(int id) {

        String sql = "DELETE FROM factura WHERE id_factura = ?";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return false;
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar factura: " + e.getMessage());
            return false;
        }
    }

    // Calcular el total de los servicios de una cita
    public BigDecimal calcularTotal(int idCita) {

        String sql = "SELECT SUM(precio) AS total " +
                    "FROM cita_servicio WHERE id_cita = ?";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return null;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, idCita);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        BigDecimal total = rs.getBigDecimal("total");

                        if (total != null) {
                            return total;
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al calcular total: " + e.getMessage());
        }

        return null;
    }

      // Comprobar si una cita ya tiene factura
    public boolean existeFactura(int idCita) {

        String sql = "SELECT COUNT(*) FROM factura WHERE id_cita = ?";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return true;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, idCita);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al comprobar factura: " + e.getMessage());
            return true;
        }

        return true;
    }

    // Comprobar si una cita se puede facturar
    public boolean citaFacturable(int idCita) {

        String sql = "SELECT COUNT(*) FROM cita " +
                    "WHERE id_cita = ? AND estado != 'cancelada'";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, idCita);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al comprobar cita: " + e.getMessage());
        }

        return false;
    }
}
