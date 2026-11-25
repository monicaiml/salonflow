
package controller;

import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

import dao.FacturaDAO;
import modelo.Factura;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private FacturaDAO facturaDAO = new FacturaDAO();

    // Listar facturas
    @GetMapping
    public List<Map<String, Object>> listar() {

        List<Map<String, Object>> facturas = new ArrayList<>();
        ResultSet resultado = facturaDAO.listar();

        if (resultado != null) {
            try {
                Statement sentencia = resultado.getStatement();
                Connection conexion = sentencia.getConnection();

                try (Connection con = conexion;
                     Statement stmt = sentencia;
                     ResultSet rs = resultado) {

                    while (rs.next()) {
                        Map<String, Object> factura = new LinkedHashMap<>();

                        factura.put("id_factura", rs.getInt("id_factura"));
                        factura.put("fecha_emision", rs.getTimestamp("fecha_emision").toLocalDateTime());
                        factura.put("total", rs.getBigDecimal("total"));
                        factura.put("id_cita", rs.getInt("id_cita"));

                        facturas.add(factura);
                    }
                }

            } catch (SQLException e) {
                System.out.println("Error al listar facturas: " + e.getMessage());
            }
        }

        return facturas;
    }

    // Buscar factura por ID
    @GetMapping("/{id}")
    public Factura buscarPorId(@PathVariable int id) {

        ResultSet resultado = facturaDAO.buscarPorId(id);
        Factura factura = null;

        if (resultado != null) {
            try {
                Statement sentencia = resultado.getStatement();
                Connection conexion = sentencia.getConnection();

                try (Connection con = conexion;
                     Statement stmt = sentencia;
                     ResultSet rs = resultado) {

                    if (rs.next()) {
                        factura = new Factura(
                            rs.getInt("id_factura"),
                            rs.getTimestamp("fecha_emision").toLocalDateTime(),
                            rs.getBigDecimal("total").doubleValue(),
                            rs.getInt("id_cita")
                        );
                    }
                }

            } catch (SQLException e) {
                System.out.println("Error al buscar factura: " + e.getMessage());
            }
        }

        return factura;
    }

    // Generar factura
    @PostMapping
    public boolean generar(@RequestBody Map<String, Integer> datos) {

        Integer idCita = datos.get("id_cita");

        if (idCita == null || idCita <= 0) {
            return false;
        }

        if (!facturaDAO.citaFacturable(idCita)) {
            return false;
        }

        if (facturaDAO.existeFactura(idCita)) {
            return false;
        }

        BigDecimal total = facturaDAO.calcularTotal(idCita);

        if (total == null) {
            return false;
        }

        return facturaDAO.insertar(idCita, total);
    }

    // Eliminar factura
    @DeleteMapping("/{id}")
    public boolean eliminar(@PathVariable int id) {
        return facturaDAO.eliminar(id);
    }
}
