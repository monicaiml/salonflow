
package controller;

import dao.ClienteDAO;
import modelo.Cliente;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private ClienteDAO clienteDAO = new ClienteDAO();

    // GET: Listar todos los clientes
    @GetMapping
    public List<Cliente> listar() {
        return clienteDAO.listar();
    }

    // GET: Buscar un cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable int id) {

        Cliente cliente = clienteDAO.buscarPorId(id);

        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);
    }

    // POST: Insertar un cliente
    @PostMapping
    public ResponseEntity<String> insertar(@RequestBody Cliente cliente) {

        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()
                || cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre y el teléfono son obligatorios.");
        }

        boolean resultado = clienteDAO.insertar(cliente);

        if (resultado) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado correctamente.");
        }

        return ResponseEntity.internalServerError().body("No se ha podido crear el cliente.");
    }

    // PUT: Modificar un cliente
    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable int id, @RequestBody Cliente cliente) {

        cliente.setId_cliente(id);

        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()
                || cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre y el teléfono son obligatorios.");
        }

        boolean resultado = clienteDAO.modificar(cliente);

        if (resultado) {
            return ResponseEntity.ok("Cliente modificado correctamente.");
        }

        return ResponseEntity.notFound().build();
    }

    // DELETE: Eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {

        boolean resultado = clienteDAO.eliminar(id);

        if (resultado) {
            return ResponseEntity.ok("Cliente eliminado correctamente.");
        }

        return ResponseEntity.notFound().build();
    }
}
