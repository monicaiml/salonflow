
package controller;

import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.*;

import dao.CitaDAO;
import modelo.Cita;
import modelo.Servicio;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private CitaDAO citaDAO = new CitaDAO();

    // Listar citas
    @GetMapping
    public List<Map<String, Object>> listar() {

        List<Map<String, Object>> citas = new ArrayList<>();
        ResultSet resultado = citaDAO.listar();

        if (resultado != null) {
            try {
                Statement sentencia = resultado.getStatement();
                Connection conexion = sentencia.getConnection();

                try (Connection con = conexion;
                     Statement stmt = sentencia;
                     ResultSet rs = resultado) {

                    while (rs.next()) {
                        Map<String, Object> cita = new LinkedHashMap<>();

                        cita.put("id_cita", rs.getInt("id_cita"));
                        cita.put("fecha_hora", rs.getTimestamp("fecha_hora").toLocalDateTime());
                        cita.put("estado", rs.getString("estado"));
                        cita.put("id_cliente", rs.getInt("id_cliente"));
                        cita.put("id_peluquero", rs.getInt("id_peluquero"));
                        cita.put("nombre_cliente", rs.getString("nombre_cliente"));
                        cita.put("nombre_peluquero", rs.getString("nombre_peluquero"));

                        citas.add(cita);
                    }
                }

            } catch (SQLException e) {
                System.out.println("Error al listar citas: " + e.getMessage());
            }
        }

        return citas;
    }

    // Buscar cita por ID
    @GetMapping("/{id}")
    public Cita buscarPorId(@PathVariable int id) {

        ResultSet resultado = citaDAO.buscarPorId(id);
        Cita cita = null;

        if (resultado != null) {
            try {
                Statement sentencia = resultado.getStatement();
                Connection conexion = sentencia.getConnection();

                try (Connection con = conexion;
                     Statement stmt = sentencia;
                     ResultSet rs = resultado) {

                    if (rs.next()) {
                        cita = new Cita(
                            rs.getInt("id_cita"),
                            rs.getTimestamp("fecha_hora").toLocalDateTime(),
                            rs.getString("estado"),
                            rs.getInt("id_cliente"),
                            rs.getInt("id_peluquero")
                        );
                    }
                }

            } catch (SQLException e) {
                System.out.println("Error al buscar cita: " + e.getMessage());
            }
        }

        return cita;
    }

    // Crear cita
    @PostMapping
    public int insertar(@RequestBody Cita cita) {

        if (cita.getFecha_hora() == null ||
            cita.getId_cliente() <= 0 ||
            cita.getId_peluquero() <= 0 ||
            cita.getEstado() == null ||
            cita.getEstado().trim().isEmpty()) {
            return -1;
        }

        //Comprobacion de que la cita sea posterior al momento actual
        if(!cita.getFecha_hora().isAfter(LocalDateTime.now()))
        {
            return -1;
        }

        int hora = cita.getFecha_hora().getHour();
        int minuto = cita.getFecha_hora().getMinute();

        int minutosTotales = hora * 60 + minuto;

        int aperturaManana = 9 * 60;
        int cierreManana = 14 * 60;

        int aperturaTarde = 17 * 60;
        int cierreTarde = 20 * 60;

        boolean horarioManana =
            minutosTotales >= aperturaManana &&
            minutosTotales <= cierreManana;

        boolean horarioTarde =
            minutosTotales >= aperturaTarde &&
            minutosTotales <= cierreTarde;

        if (!horarioManana && !horarioTarde) {
            return -1;
        }

        if (cita.getFecha_hora().getMinute() % 30 != 0) {
            return -1;
        }

        if (citaDAO.existeCita(cita.getId_peluquero(), cita.getFecha_hora())) {
            return -1;
        }

        return citaDAO.insertar(cita,cita.getId_cliente(),cita.getId_peluquero());
    }


    // Modificar estado
    @PutMapping("/{id}/estado")
    public boolean modificarEstado(
        @PathVariable int id,
        @RequestBody Map<String, String> datos) {

        String estado = datos.get("estado");

        if (estado == null || estado.trim().isEmpty()) {
            return false;
        }

        return citaDAO.modificarEstado(id, estado);
    }

    // Eliminar cita
    @DeleteMapping("/{id}")
    public boolean eliminar(@PathVariable int id) {
        return citaDAO.eliminar(id);
    }

    // Consultar servicios de una cita
    @GetMapping("/{id}/servicios")
    public List<Servicio> listarServicios(@PathVariable int id) {

        List<Servicio> servicios = new ArrayList<>();
        ResultSet resultado = citaDAO.listarServicios(id);

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
                            rs.getDouble("precio_cita")
                        );

                        servicios.add(servicio);
                    }
                }

            } catch (SQLException e) {
                System.out.println("Error al listar servicios de la cita: " + e.getMessage());
            }
        }

        return servicios;
    }

    // Añadir servicio a una cita
    @PostMapping("/{id}/servicios")
    public boolean agregarServicio(
        @PathVariable int id,
        @RequestBody Servicio servicio) {

        if (servicio.getId_servicio() <= 0 || servicio.getPrecio() < 0) {
            return false;
        }

        return citaDAO.agregarServicio(id, servicio);
    }
}
