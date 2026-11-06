
const enlaces = document.querySelectorAll(".sidebar a");
const panel = document.querySelector(".panel");

// Navegación
enlaces.forEach(function(enlace) {
    enlace.addEventListener("click", function(event) {
        event.preventDefault();

        enlaces.forEach(function(elemento) {
            elemento.classList.remove("activo");
        });

        enlace.classList.add("activo");

        const seccion = enlace.textContent.trim();
        mostrarSeccion(seccion);
    });
});

// Mostrar secciones
function mostrarSeccion(seccion) {

    if (seccion === "Inicio") {
        panel.innerHTML = `
            <h2>Bienvenido a SalonFlow</h2>
            <p>Desde aquí puedes gestionar tu peluquería.</p>
            <p>Selecciona una opción del menú para comenzar.</p>
        `;
    }

    else if (seccion === "Clientes") {
        cargarClientes();
    }

    else if (seccion === "Peluqueros") {
        panel.innerHTML = `
            <h2>Gestión de peluqueros</h2>
            <p>Gestión de los empleados de la peluquería.</p>
        `;
    }

    else if (seccion === "Servicios") {
        panel.innerHTML = `
            <h2>Gestión de servicios</h2>
            <p>Administración de los servicios y sus precios.</p>
        `;
    }

    else if (seccion === "Citas") {
        panel.innerHTML = `
            <h2>Gestión de citas</h2>
            <p>Consulta y gestión de las citas.</p>
        `;
    }

    else if (seccion === "Facturas") {
        panel.innerHTML = `
            <h2>Gestión de facturas</h2>
            <p>Consulta de las facturas generadas.</p>
        `;
    }
}

// Obtener clientes de la API
async function cargarClientes() {

    panel.innerHTML = `
        <h2>Gestión de clientes</h2>
        <p>Cargando clientes...</p>
    `;

    try {
        const respuesta = await fetch("/api/clientes");

        if (!respuesta.ok) {
            throw new Error("No se han podido obtener los clientes.");
        }

        const clientes = await respuesta.json();

        let contenido = `
            <div class="titulo-seccion">
                <h2>Gestión de clientes</h2>
                <button id="nuevo-cliente" class="boton-principal">
                    + Nuevo cliente
                </button>
            </div>

            <div id="formulario-cliente"></div>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Teléfono</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
        `;

        if (clientes.length === 0) {
            contenido += `
                <tr>
                    <td colspan="4">No hay clientes registrados.</td>
                </tr>
            `;
        }

        clientes.forEach(function(cliente) {
            contenido += `
                <tr>
                    <td>${escaparHTML(cliente.id_cliente)}</td>
                    <td>${escaparHTML(cliente.nombre)}</td>
                    <td>${escaparHTML(cliente.telefono)}</td>
                    <td>
                        <button class="boton-editar"
                            data-id="${escaparHTML(cliente.id_cliente)}">
                            Editar
                        </button>

                        <button class="boton-eliminar"
                            data-id="${escaparHTML(cliente.id_cliente)}">
                            Eliminar
                        </button>
                    </td>
                </tr>
            `;
        });

        contenido += `
                </tbody>
            </table>
        `;

        panel.innerHTML = contenido;

        // Botón nuevo cliente
        document.querySelector("#nuevo-cliente")
            .addEventListener("click", function() {
                mostrarFormulario();
            });

        // Botones editar
        document.querySelectorAll(".boton-editar")
            .forEach(function(boton) {
                boton.addEventListener("click", function() {
                    editarCliente(Number(boton.dataset.id));
                });
            });

        // Botones eliminar
        document.querySelectorAll(".boton-eliminar")
            .forEach(function(boton) {
                boton.addEventListener("click", function() {
                    eliminarCliente(Number(boton.dataset.id));
                });
            });

        actualizarTotalClientes(clientes.length);

    } catch (error) {
        panel.innerHTML = `
            <h2>Gestión de clientes</h2>
            <p class="mensaje-error">${escaparHTML(error.message)}</p>
        `;
    }
}

// Mostrar formulario de creación o edición
function mostrarFormulario(cliente = null) {

    const formulario = document.querySelector("#formulario-cliente");

    const esEdicion = cliente !== null;

    formulario.innerHTML = `
        <div class="formulario">
            <h3>${esEdicion ? "Modificar cliente" : "Nuevo cliente"}</h3>

            <form id="form-cliente">
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre"
                    value="${esEdicion ? escaparHTML(cliente.nombre) : ""}"
                    required maxlength="100">

                <label for="telefono">Teléfono:</label>
                <input type="text" id="telefono"
                    value="${esEdicion ? escaparHTML(cliente.telefono) : ""}"
                    required maxlength="20">

                <div class="acciones-formulario">
                    <button type="submit" class="boton-principal">
                        Guardar
                    </button>

                    <button type="button" id="cancelar"
                        class="boton-cancelar">
                        Cancelar
                    </button>
                </div>
            </form>
        </div>
    `;

    document.querySelector("#cancelar")
        .addEventListener("click", function() {
            formulario.innerHTML = "";
        });

    document.querySelector("#form-cliente")
        .addEventListener("submit", async function(event) {
            event.preventDefault();

            const nombre = document.querySelector("#nombre").value.trim();
            const telefono = document.querySelector("#telefono").value.trim();

            if (nombre === "" || telefono === "") {
                alert("Todos los campos son obligatorios.");
                return;
            }

            const datos = {
                nombre: nombre,
                telefono: telefono
            };

            try {
                let respuesta;

                if (esEdicion) {
                    respuesta = await fetch("/api/clientes/" + cliente.id_cliente, {
                        method: "PUT",
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(datos)
                    });
                } else {
                    respuesta = await fetch("/api/clientes", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(datos)
                    });
                }

                if (!respuesta.ok) {
                    throw new Error(await respuesta.text());
                }

                alert(esEdicion
                    ? "Cliente modificado correctamente."
                    : "Cliente creado correctamente.");

                cargarClientes();

            } catch (error) {
                alert("Error: " + error.message);
            }
        });
}

// Buscar cliente y mostrar formulario de edición
async function editarCliente(id) {

    try {
        const respuesta = await fetch("/api/clientes/" + id);

        if (!respuesta.ok) {
            throw new Error("No se ha encontrado el cliente.");
        }

        const cliente = await respuesta.json();

        mostrarFormulario(cliente);

    } catch (error) {
        alert(error.message);
    }
}

// Eliminar cliente
async function eliminarCliente(id) {

    const confirmar = confirm("¿Seguro que quieres eliminar este cliente?");

    if (!confirmar) {
        return;
    }

    try {
        const respuesta = await fetch("/api/clientes/" + id, {
            method: "DELETE"
        });

        if (!respuesta.ok) {
            throw new Error(await respuesta.text());
        }

        alert("Cliente eliminado correctamente.");

        cargarClientes();

    } catch (error) {
        alert("No se ha podido eliminar el cliente: " + error.message);
    }
}

// Actualizar contador del panel
function actualizarTotalClientes(total) {
    const contador = document.querySelector("#total-clientes");

    if (contador) {
        contador.textContent = total;
    }
}

// Evitar interpretar los datos como HTML
function escaparHTML(valor) {

    return String(valor).replace(/[&<>"']/g, function(caracter) {
        const equivalencias = {
            "&": "&amp;",
            "<": "&lt;",
            ">": "&gt;",
            '"': "&quot;",
            "'": "&#039;"
        };

        return equivalencias[caracter];
    });
}
