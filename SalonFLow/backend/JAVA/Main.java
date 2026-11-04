import dao.ClienteDAO;
import dao.PeluqueroDAO;
import dao.ServicioDAO;
import dao.CitaDAO;
import dao.FacturaDAO;

import modelo.Cliente;
import modelo.Peluquero;
import modelo.Servicio;
import modelo.Cita;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    static Scanner teclado = new Scanner(System.in);

    static ClienteDAO clienteDAO = new ClienteDAO();
    static PeluqueroDAO peluqueroDAO = new PeluqueroDAO();
    static ServicioDAO servicioDAO = new ServicioDAO();
    static CitaDAO citaDAO = new CitaDAO();
    static FacturaDAO facturaDAO = new FacturaDAO();

    public static void main(String[] args) {

        int opcion;

        do {
            System.out.println("\n========== SALONFLOW ==========");
            System.out.println("1. Gestionar clientes");
            System.out.println("2. Gestionar peluqueros");
            System.out.println("3. Gestionar servicios");
            System.out.println("4. Gestionar citas");
            System.out.println("5. Gestionar facturas");
            System.out.println("0. Salir");
            System.out.println("===============================");

            opcion = leerEntero("Selecciona una opcion: ");

            switch (opcion) {
                case 1:
                    menuClientes();
                    break;

                case 2:
                    menuPeluqueros();
                    break;

                case 3:
                    menuServicios();
                    break;

                case 4:
                    menuCitas();
                    break;

                case 5:
                    menuFacturas();
                    break;

                case 0:
                    System.out.println("Saliendo de SalonFlow...");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);

        teclado.close();
    }

    // ==========================================
    // MENU DE CLIENTES
    // ==========================================

    public static void menuClientes() {

        int opcion;

        do {
            System.out.println("\n--- GESTION DE CLIENTES ---");
            System.out.println("1. Insertar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar cliente");
            System.out.println("4. Modificar cliente");
            System.out.println("5. Eliminar cliente");
            System.out.println("0. Volver");

            opcion = leerEntero("Selecciona una opcion: ");

            switch (opcion) {
                case 1:
                    insertarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    buscarCliente();
                    break;
                case 4:
                    modificarCliente();
                    break;
                case 5:
                    eliminarCliente();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);
    }

    public static void insertarCliente() {

        System.out.println("\n--- NUEVO CLIENTE ---");

        String nombre = leerTexto("Nombre: ");
        String telefono = leerTexto("Telefono: ");

        Cliente cliente = new Cliente(0, nombre, telefono);

        if (clienteDAO.insertar(cliente)) {
            System.out.println("Cliente insertado correctamente.");
        } else {
            System.out.println("No se ha podido insertar el cliente.");
        }
    }

    public static void listarClientes() {

        System.out.println("\n--- LISTADO DE CLIENTES ---");

        try (ResultSet rs = clienteDAO.listar()) {

            if (rs == null) {
                System.out.println("No se ha podido consultar la base de datos.");
                return;
            }

            boolean hayDatos = false;

            while (rs.next()) {

                hayDatos = true;

                System.out.println(
                    "ID: " + rs.getInt("id_cliente") +
                    " | Nombre: " + rs.getString("nombre") +
                    " | Telefono: " + rs.getString("telefono")
                );
            }

            if (!hayDatos) {
                System.out.println("No hay clientes registrados.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void buscarCliente() {

        int id = leerEntero("Introduce el ID del cliente: ");

        try (ResultSet rs = clienteDAO.buscarPorId(id)) {

            if (rs == null) {
                System.out.println("No se ha podido realizar la consulta.");
                return;
            }

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_cliente"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Telefono: " + rs.getString("telefono"));
            } else {
                System.out.println("No existe ese cliente.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void modificarCliente() {

        int id = leerEntero("ID del cliente que quieres modificar: ");

        try (ResultSet rs = clienteDAO.buscarPorId(id)) {

            if (rs == null) {
                System.out.println("No se ha podido realizar la consulta.");
                return;
            }

            if (!rs.next()) {
                System.out.println("No existe ese cliente.");
                return;
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        String nombre = leerTexto("Nuevo nombre: ");
        String telefono = leerTexto("Nuevo telefono: ");

        Cliente cliente = new Cliente(id, nombre, telefono);

        if (clienteDAO.modificar(cliente)) {
            System.out.println("Cliente modificado correctamente.");
        } else {
            System.out.println("No se ha podido modificar el cliente.");
        }
    }

    public static void eliminarCliente() {

        int id = leerEntero("ID del cliente que quieres eliminar: ");

        if (clienteDAO.eliminar(id)) {
            System.out.println("Cliente eliminado correctamente.");
        } else {
            System.out.println("No se ha podido eliminar el cliente.");
        }
    }

    // ==========================================
    // MENU DE PELUQUEROS
    // ==========================================

    public static void menuPeluqueros() {

        int opcion;

        do {
            System.out.println("\n--- GESTION DE PELUQUEROS ---");
            System.out.println("1. Insertar peluquero");
            System.out.println("2. Listar peluqueros");
            System.out.println("3. Buscar peluquero");
            System.out.println("4. Modificar peluquero");
            System.out.println("5. Eliminar peluquero");
            System.out.println("0. Volver");

            opcion = leerEntero("Selecciona una opcion: ");

            switch (opcion) {
                case 1:
                    insertarPeluquero();
                    break;
                case 2:
                    listarPeluqueros();
                    break;
                case 3:
                    buscarPeluquero();
                    break;
                case 4:
                    modificarPeluquero();
                    break;
                case 5:
                    eliminarPeluquero();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);
    }

    public static void insertarPeluquero() {

        String nombre = leerTexto("Nombre del peluquero: ");
        String especialidad = leerTexto("Especialidad: ");

        Peluquero peluquero = new Peluquero(0, nombre, especialidad);

        if (peluqueroDAO.insertar(peluquero)) {
            System.out.println("Peluquero insertado correctamente.");
        } else {
            System.out.println("No se ha podido insertar el peluquero.");
        }
    }

    public static void listarPeluqueros() {

        try (ResultSet rs = peluqueroDAO.listar()) {

            if (rs == null) {
                System.out.println("No se ha podido realizar la consulta.");
                return;
            }

            boolean hayDatos = false;

            while (rs.next()) {

                hayDatos = true;

                System.out.println(
                    "ID: " + rs.getInt("id_peluquero") +
                    " | Nombre: " + rs.getString("nombre") +
                    " | Especialidad: " + rs.getString("especialidad")
                );
            }

            if (!hayDatos) {
                System.out.println("No hay peluqueros registrados.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void buscarPeluquero() {

        int id = leerEntero("ID del peluquero: ");

        try (ResultSet rs = peluqueroDAO.buscarPorId(id)) {

            if (rs == null) {
                System.out.println("No se ha podido realizar la consulta.");
                return;
            }

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_peluquero"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Especialidad: " + rs.getString("especialidad"));
            } else {
                System.out.println("No existe ese peluquero.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void modificarPeluquero() {

        int id = leerEntero("ID del peluquero que quieres modificar: ");

        try (ResultSet rs = peluqueroDAO.buscarPorId(id)) {

            if (rs == null) {
                System.out.println("No se ha podido realizar la consulta.");
                return;
            }

            if (!rs.next()) {
                System.out.println("No existe ese peluquero.");
                return;
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        String nombre = leerTexto("Nuevo nombre: ");
        String especialidad = leerTexto("Nueva especialidad: ");

        Peluquero peluquero = new Peluquero(id, nombre, especialidad);

        if (peluqueroDAO.modificar(peluquero)) {
            System.out.println("Peluquero modificado correctamente.");
        } else {
            System.out.println("No se ha podido modificar el peluquero.");
        }
    }

    public static void eliminarPeluquero() {

        int id = leerEntero("ID del peluquero que quieres eliminar: ");

        if (peluqueroDAO.eliminar(id)) {
            System.out.println("Peluquero eliminado correctamente.");
        } else {
            System.out.println("No se ha podido eliminar el peluquero.");
        }
    }

    // ==========================================
    // MENU DE SERVICIOS
    // ==========================================

    public static void menuServicios() {

        int opcion;

        do {
            System.out.println("\n--- GESTION DE SERVICIOS ---");
            System.out.println("1. Insertar servicio");
            System.out.println("2. Listar servicios");
            System.out.println("3. Buscar servicio");
            System.out.println("4. Modificar servicio");
            System.out.println("5. Eliminar servicio");
            System.out.println("0. Volver");

            opcion = leerEntero("Selecciona una opcion: ");

            switch (opcion) {
                case 1:
                    insertarServicio();
                    break;
                case 2:
                    listarServicios();
                    break;
                case 3:
                    buscarServicio();
                    break;
                case 4:
                    modificarServicio();
                    break;
                case 5:
                    eliminarServicio();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);
    }

    public static void insertarServicio() {

        String nombre = leerTexto("Nombre del servicio: ");
        double precio = leerDecimal("Precio: ");

        Servicio servicio = new Servicio(0, nombre, precio);

        if (servicioDAO.insertar(servicio)) {
            System.out.println("Servicio insertado correctamente.");
        } else {
            System.out.println("No se ha podido insertar el servicio.");
        }
    }

    public static void listarServicios() {

        try (ResultSet rs = servicioDAO.listar()) {

            if (rs == null) {
                System.out.println("No se ha podido realizar la consulta.");
                return;
            }

            boolean hayDatos = false;

            while (rs.next()) {

                hayDatos = true;

                System.out.println(
                    "ID: " + rs.getInt("id_servicio") +
                    " | Nombre: " + rs.getString("nombre_servicio") +
                    " | Precio: " + rs.getBigDecimal("precio") + " €"
                );
            }

            if (!hayDatos) {
                System.out.println("No hay servicios registrados.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void buscarServicio() {

        int id = leerEntero("ID del servicio: ");

        try (ResultSet rs = servicioDAO.buscarPorId(id)) {

            if (rs == null) {
                System.out.println("No se ha podido realizar la consulta.");
                return;
            }

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_servicio"));
                System.out.println("Nombre: " + rs.getString("nombre_servicio"));
                System.out.println("Precio: " + rs.getBigDecimal("precio") + " €");
            } else {
                System.out.println("No existe ese servicio.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void modificarServicio() {

        int id = leerEntero("ID del servicio que quieres modificar: ");

        try (ResultSet rs = servicioDAO.buscarPorId(id)) {

            if (rs == null) {
                System.out.println("No se ha podido realizar la consulta.");
                return;
            }

            if (!rs.next()) {
                System.out.println("No existe ese servicio.");
                return;
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        String nombre = leerTexto("Nuevo nombre: ");
        double precio = leerDecimal("Nuevo precio: ");

        Servicio servicio = new Servicio(id, nombre, precio);

        if (servicioDAO.modificar(servicio)) {
            System.out.println("Servicio modificado correctamente.");
        } else {
            System.out.println("No se ha podido modificar el servicio.");
        }
    }

    public static void eliminarServicio() {

        int id = leerEntero("ID del servicio que quieres eliminar: ");

        if (servicioDAO.eliminar(id)) {
            System.out.println("Servicio eliminado correctamente.");
        } else {
            System.out.println("No se ha podido eliminar el servicio.");
        }
    }

    // ==========================================
    // MENU DE CITAS
    // ==========================================

    public static void menuCitas() {

        int opcion;

        do {
            System.out.println("\n--- GESTION DE CITAS ---");
            System.out.println("1. Crear cita");
            System.out.println("2. Listar citas");
            System.out.println("3. Buscar cita");
            System.out.println("4. Modificar estado");
            System.out.println("5. Eliminar cita");
            System.out.println("6. Añadir servicio a una cita");
            System.out.println("7. Listar servicios de una cita");
            System.out.println("0. Volver");

            opcion = leerEntero("Selecciona una opcion: ");

            switch (opcion) {
                case 1:
                    insertarCita();
                    break;
                case 2:
                    listarCitas();
                    break;
                case 3:
                    buscarCita();
                    break;
                case 4:
                    modificarEstadoCita();
                    break;
                case 5:
                    eliminarCita();
                    break;
                case 6:
                    agregarServicioCita();
                    break;
                case 7:
                    listarServiciosCita();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);
    }

    public static void insertarCita() {

        System.out.println("Fecha y hora con formato AAAA-MM-DD HH:MM");
        LocalDateTime fecha = leerFecha("Fecha y hora: ");

        System.out.println("Estados disponibles: confirmada, completada, cancelada");
        String estado = leerTexto("Estado: ");

        int idCliente = leerEntero("ID del cliente: ");
        int idPeluquero = leerEntero("ID del peluquero: ");

        Cita cita = new Cita(0, fecha, estado, idCliente, idPeluquero);

        int idCita = citaDAO.insertar(cita, idCliente, idPeluquero);

        if (idCita != -1) {
            System.out.println("Cita creada correctamente. ID: " + idCita);
        } else {
            System.out.println("No se ha podido crear la cita.");
        }
    }

    public static void listarCitas() {

        try (ResultSet rs = citaDAO.listar()) {

            if (rs == null) {
                System.out.println("No se ha podido realizar la consulta.");
                return;
            }

            boolean hayDatos = false;

            while (rs.next()) {

                hayDatos = true;

                System.out.println(
                    "ID: " + rs.getInt("id_cita") +
                    " | Fecha: " + rs.getTimestamp("fecha_hora") +
                    " | Estado: " + rs.getString("estado") +
                    " | Cliente: " + rs.getString("nombre_cliente") +
                    " | Peluquero: " + rs.getString("nombre_peluquero")
                );
            }

            if (!hayDatos) {
                System.out.println("No hay citas registradas.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void buscarCita() {

        int id = leerEntero("ID de la cita: ");

        try (ResultSet rs = citaDAO.buscarPorId(id)) {

            if (rs == null) {
                System.out.println("No se ha podido realizar la consulta.");
                return;
            }

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_cita"));
                System.out.println("Fecha: " + rs.getTimestamp("fecha_hora"));
                System.out.println("Estado: " + rs.getString("estado"));
                System.out.println("ID cliente: " + rs.getInt("id_cliente"));
                System.out.println("ID peluquero: " + rs.getInt("id_peluquero"));
            } else {
                System.out.println("No existe esa cita.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void modificarEstadoCita() {

        int id = leerEntero("ID de la cita: ");

        System.out.println("Estados disponibles: confirmada, completada, cancelada");
        String estado = leerTexto("Nuevo estado: ");

        if (citaDAO.modificarEstado(id, estado)) {
            System.out.println("Estado de la cita modificado.");
        } else {
            System.out.println("No se ha podido modificar la cita.");
        }
    }

    public static void eliminarCita() {

        int id = leerEntero("ID de la cita que quieres eliminar: ");

        if (citaDAO.eliminar(id)) {
            System.out.println("Cita eliminada correctamente.");
        } else {
            System.out.println("No se ha podido eliminar la cita.");
        }
    }

    public static void agregarServicioCita() {

        int idCita = leerEntero("ID de la cita: ");
        int idServicio = leerEntero("ID del servicio que quieres añadir: ");

        try (ResultSet rs = servicioDAO.buscarPorId(idServicio)) {

            if (rs == null) {
                System.out.println("No se ha podido realizar la consulta.");
                return;
            }

            if (!rs.next()) {
                System.out.println("No existe ese servicio.");
                return;
            }

            String nombre = rs.getString("nombre_servicio");
            double precio = rs.getDouble("precio");

            Servicio servicio = new Servicio(idServicio, nombre, precio);

            if (citaDAO.agregarServicio(idCita, servicio)) {
                System.out.println("Servicio añadido a la cita correctamente.");
            } else {
                System.out.println("No se ha podido añadir el servicio.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void listarServiciosCita() {

        int idCita = leerEntero("ID de la cita: ");

        try (ResultSet rs = citaDAO.listarServicios(idCita)) {

            if (rs == null) {
                System.out.println("No se ha podido realizar la consulta.");
                return;
            }

            boolean hayDatos = false;

            while (rs.next()) {

                hayDatos = true;

                System.out.println(
                    "Servicio: " + rs.getString("nombre_servicio") +
                    " | Precio aplicado: " + rs.getBigDecimal("precio_cita") + " €"
                );
            }

            if (!hayDatos) {
                System.out.println("Esta cita no tiene servicios añadidos.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ==========================================
    // MENU DE FACTURAS
    // ==========================================

    public static void menuFacturas() {

        int opcion;

        do {
            System.out.println("\n--- GESTION DE FACTURAS ---");
            System.out.println("1. Crear factura");
            System.out.println("2. Listar facturas");
            System.out.println("3. Buscar factura por ID");
            System.out.println("4. Buscar factura por cita");
            System.out.println("5. Eliminar factura");
            System.out.println("0. Volver");

            opcion = leerEntero("Selecciona una opcion: ");

            switch (opcion) {
                case 1:
                    insertarFactura();
                    break;
                case 2:
                    listarFacturas();
                    break;
                case 3:
                    buscarFactura();
                    break;
                case 4:
                    buscarFacturaPorCita();
                    break;
                case 5:
                    eliminarFactura();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);
    }

    public static void insertarFactura() {

        int idCita = leerEntero("ID de la cita: ");
        BigDecimal total = leerImporte("Importe total de la factura: ");

        if (facturaDAO.insertar(idCita, total)) {
            System.out.println("Factura creada correctamente.");
        } else {
            System.out.println("No se ha podido crear la factura.");
        }
    }

    public static void listarFacturas() {

        try (ResultSet rs = facturaDAO.listar()) {

            if (rs == null) {
                System.out.println("No se ha podido realizar la consulta.");
                return;
            }

            boolean hayDatos = false;

            while (rs.next()) {

                hayDatos = true;

                System.out.println(
                    "ID: " + rs.getInt("id_factura") +
                    " | Fecha: " + rs.getTimestamp("fecha_emision") +
                    " | Total: " + rs.getBigDecimal("total") + " €" +
                    " | ID cita: " + rs.getInt("id_cita")
                );
            }

            if (!hayDatos) {
                System.out.println("No hay facturas registradas.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void buscarFactura() {

        int id = leerEntero("ID de la factura: ");

        try (ResultSet rs = facturaDAO.buscarPorId(id)) {

            if (rs == null) {
                System.out.println("No se ha podido realizar la consulta.");
                return;
            }

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_factura"));
                System.out.println("Fecha: " + rs.getTimestamp("fecha_emision"));
                System.out.println("Total: " + rs.getBigDecimal("total") + " €");
                System.out.println("ID cita: " + rs.getInt("id_cita"));
            } else {
                System.out.println("No existe esa factura.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void buscarFacturaPorCita() {

        int idCita = leerEntero("ID de la cita: ");

        try (ResultSet rs = facturaDAO.buscarPorCita(idCita)) {

            if (rs == null) {
                System.out.println("No se ha podido realizar la consulta.");
                return;
            }

            if (rs.next()) {
                System.out.println("ID factura: " + rs.getInt("id_factura"));
                System.out.println("Fecha: " + rs.getTimestamp("fecha_emision"));
                System.out.println("Total: " + rs.getBigDecimal("total") + " €");
                System.out.println("ID cita: " + rs.getInt("id_cita"));
            } else {
                System.out.println("No hay ninguna factura para esa cita.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void eliminarFactura() {

        int id = leerEntero("ID de la factura que quieres eliminar: ");

        if (facturaDAO.eliminar(id)) {
            System.out.println("Factura eliminada correctamente.");
        } else {
            System.out.println("No se ha podido eliminar la factura.");
        }
    }

    // ==========================================
    // METODOS PARA LEER DATOS
    // ==========================================

    public static String leerTexto(String mensaje) {

        System.out.print(mensaje);
        return teclado.nextLine();
    }

    public static int leerEntero(String mensaje) {

        int numero = 0;
        boolean correcto = false;

        while (!correcto) {

            System.out.print(mensaje);
            String texto = teclado.nextLine();

            try {
                numero = Integer.parseInt(texto);
                correcto = true;
            } catch (NumberFormatException e) {
                System.out.println("Introduce un numero entero valido.");
            }
        }

        return numero;
    }

    public static double leerDecimal(String mensaje) {

        double numero = 0;
        boolean correcto = false;

        while (!correcto) {

            System.out.print(mensaje);
            String texto = teclado.nextLine().replace(",", ".");

            try {
                numero = Double.parseDouble(texto);

                if (numero >= 0) {
                    correcto = true;
                } else {
                    System.out.println("El precio no puede ser negativo.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Introduce un numero valido.");
            }
        }

        return numero;
    }

    public static BigDecimal leerImporte(String mensaje) {

        BigDecimal importe = BigDecimal.ZERO;
        boolean correcto = false;

        while (!correcto) {

            System.out.print(mensaje);
            String texto = teclado.nextLine().replace(",", ".");

            try {
                importe = new BigDecimal(texto);

                if (importe.compareTo(BigDecimal.ZERO) >= 0) {
                    correcto = true;
                } else {
                    System.out.println("El importe no puede ser negativo.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Introduce un importe valido.");
            }
        }

        return importe;
    }

    public static LocalDateTime leerFecha(String mensaje) {

        LocalDateTime fecha = null;
        boolean correcto = false;

        while (!correcto) {

            System.out.print(mensaje);
            String texto = teclado.nextLine();

            try {
                fecha = LocalDateTime.parse(texto.replace(" ", "T"));
                correcto = true;

            } catch (Exception e) {
                System.out.println("Formato incorrecto. Ejemplo: 2026-11-04 15:30");
            }
        }

        return fecha;
    }
}