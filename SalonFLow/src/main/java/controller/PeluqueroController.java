
package controller;

import dao.PeluqueroDAO;
import modelo.Peluquero;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/peluqueros")
public class PeluqueroController {

    private PeluqueroDAO peluqueroDAO = new PeluqueroDAO();

    // GET: Listar todos los peluqueros
    @GetMapping
    public ResponseEntity<List<Peluquero>> listar() {

        ResultSet resultado = peluqueroDAO.listar();

        if (resultado == null) {
            return ResponseEntity.internalServerError().build();
        }

        List<Peluquero> peluqueros = new ArrayList<>();

        try (ResultSet rs = resultado;
             Statement sentencia = resultado.getStatement();
             Connection con = sentencia.getConnection()) {

            while (rs.next()) {

                Peluquero peluquero = new Peluquero(
                    rs.getInt("id_peluquero"),
                    rs.getString("nombre"),
                    rs.getString("especialidad")
                );

                peluqueros.add(peluquero);
            }

            return ResponseEntity.ok(peluqueros);

        } catch (SQLException e) {
            System.out.println("Error al listar peluqueros: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET: Buscar peluquero por ID
    @GetMapping("/{id}")
    public ResponseEntity<Peluquero> buscarPorId(@PathVariable int id) {

        ResultSet resultado = peluqueroDAO.buscarPorId(id);

        if (resultado == null) {
            return ResponseEntity.internalServerError().build();
        }

        try (ResultSet rs = resultado;
             Statement sentencia = resultado.getStatement();
             Connection con = sentencia.getConnection()) {

            if (rs.next()) {

                Peluquero peluquero = new Peluquero(
                    rs.getInt("id_peluquero"),
                    rs.getString("nombre"),
                    rs.getString("especialidad")
                );

                return ResponseEntity.ok(peluquero);
            }

            return ResponseEntity.notFound().build();

        } catch (SQLException e) {
            System.out.println("Error al buscar peluquero: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // POST: Insertar peluquero
    @PostMapping
    public ResponseEntity<String> insertar(@RequestBody Peluquero peluquero) {

        if (peluquero.getNombre() == null
                || peluquero.getNombre().trim().isEmpty()
                || peluquero.getEspecialidad() == null
                || peluquero.getEspecialidad().trim().isEmpty()) {

            return ResponseEntity.badRequest()
                    .body("El nombre y la especialidad son obligatorios.");
        }

        boolean resultado = peluqueroDAO.insertar(peluquero);

        if (resultado) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Peluquero creado correctamente.");
        }

        return ResponseEntity.internalServerError()
                .body("No se ha podido crear el peluquero.");
    }

    // PUT: Modificar peluquero
    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(
            @PathVariable int id,
            @RequestBody Peluquero peluquero) {

        peluquero.setId_peluquero(id);

        if (peluquero.getNombre() == null
                || peluquero.getNombre().trim().isEmpty()
                || peluquero.getEspecialidad() == null
                || peluquero.getEspecialidad().trim().isEmpty()) {

            return ResponseEntity.badRequest()
                    .body("El nombre y la especialidad son obligatorios.");
        }

        boolean resultado = peluqueroDAO.modificar(peluquero);

        if (resultado) {
            return ResponseEntity.ok("Peluquero modificado correctamente.");
        }

        return ResponseEntity.notFound().build();
    }

    // DELETE: Eliminar peluquero
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {

        boolean resultado = peluqueroDAO.eliminar(id);

        if (resultado) {
            return ResponseEntity.ok("Peluquero eliminado correctamente.");
        }

        return ResponseEntity.notFound().build();
    }
}
