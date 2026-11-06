
package dao;

import conexion.ConexionBD;
import modelo.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Insertar cliente
    public boolean insertar(Cliente cliente) {

        String sql = "INSERT INTO cliente (nombre, telefono) VALUES (?, ?)";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, cliente.getNombre());
                ps.setString(2, cliente.getTelefono());

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    // Listar todos los clientes
    public List<Cliente> listar() {

        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT * FROM cliente ORDER BY id_cliente";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return clientes;
            }

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Cliente cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("telefono")
                    );

                    clientes.add(cliente);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    // Buscar cliente por ID
    public Cliente buscarPorId(int id) {

        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return null;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        return new Cliente(
                            rs.getInt("id_cliente"),
                            rs.getString("nombre"),
                            rs.getString("telefono")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }

        return null;
    }

    // Modificar cliente
    public boolean modificar(Cliente cliente) {

        String sql = "UPDATE cliente SET nombre = ?, telefono = ? WHERE id_cliente = ?";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, cliente.getNombre());
                ps.setString(2, cliente.getTelefono());
                ps.setInt(3, cliente.getId_cliente());

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al modificar cliente: " + e.getMessage());
            return false;
        }
    }

    // Eliminar cliente
    public boolean eliminar(int id) {

        String sql = "DELETE FROM cliente WHERE id_cliente = ?";

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}
