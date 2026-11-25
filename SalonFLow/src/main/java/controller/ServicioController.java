
package controller;

import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.ServicioDAO;
import modelo.Servicio;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    private ServicioDAO servicioDAO = new ServicioDAO();

    // Listar todos los servicios
    @GetMapping
    public List<Servicio> listar() {

        List<Servicio> servicios = new ArrayList<>();
        ResultSet resultado = servicioDAO.listar();

        if (resultado != null) {
            try {
                Statement sentencia = resultado.getStatement();
                Connection conexion = sentencia.getConnection();

                try (Connection con = conexion;
                     Statement stmt = sentencia;
                     ResultSet rs = resultado) {

                    while (rs.next()) {
                        Servicio servicio = new Servicio(
                            rs.getInt("id_servicio"),
                            rs.getString("nombre_servicio"),
                            rs.getDouble("precio")
                        );

                        servicios.add(servicio);
                    }
                }

            } catch (SQLException e) {
                System.out.println("Error al listar servicios: " + e.getMessage());
            }
        }

        return servicios;
    }

    // Buscar servicio por ID
    @GetMapping("/{id}")
    public Servicio buscarPorId(@PathVariable int id) {

        ResultSet resultado = servicioDAO.buscarPorId(id);
        Servicio servicio = null;

        if (resultado != null) {
            try {
                Statement sentencia = resultado.getStatement();
                Connection conexion = sentencia.getConnection();

                try (Connection con = conexion;
                     Statement stmt = sentencia;
                     ResultSet rs = resultado) {

                    if (rs.next()) {
                        servicio = new Servicio(
                            rs.getInt("id_servicio"),
                            rs.getString("nombre_servicio"),
                            rs.getDouble("precio")
                        );
                    }
                }

            } catch (SQLException e) {
                System.out.println("Error al buscar servicio: " + e.getMessage());
            }
        }

        return servicio;
    }

    // Crear servicio
    @PostMapping
    public boolean insertar(@RequestBody Servicio servicio) {

        if (servicio.getNombre_servicio() == null ||
            servicio.getNombre_servicio().trim().isEmpty() ||
            servicio.getPrecio() < 0) {
            return false;
        }

        return servicioDAO.insertar(servicio);
    }

    // Modificar servicio
    @PutMapping("/{id}")
    public boolean modificar(@PathVariable int id, @RequestBody Servicio servicio) {

        if (servicio.getNombre_servicio() == null ||
            servicio.getNombre_servicio().trim().isEmpty() ||
            servicio.getPrecio() < 0) {
            return false;
        }

        servicio.setId_servicio(id);

        return servicioDAO.modificar(servicio);
    }

    // Eliminar servicio
    @DeleteMapping("/{id}")
    public boolean eliminar(@PathVariable int id) {

        return servicioDAO.eliminar(id);
    }
}
