
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
        cargarInicio();
    }

    else if (seccion === "Clientes") {
        cargarClientes();
    }

    else if (seccion === "Peluqueros") {
        cargarPeluqueros();
    }

    else if (seccion === "Servicios") {
        cargarServicios();
    }

    else if (seccion === "Citas") {
        cargarCitas();
    }

    else if (seccion === "Facturas") {
        cargarFacturas();
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


async function cargarPeluqueros() {

    panel.innerHTML = `
        <h2>Gestión de peluqueros</h2>
        <p>Cargando peluqueros...</p>
    `;

    try {
        const respuesta = await fetch("/api/peluqueros");

        if (!respuesta.ok) {
            throw new Error("No se han podido obtener los peluqueros.");
        }

        const peluqueros = await respuesta.json();

        let contenido = `
            <div class="titulo-seccion">
                <h2>Gestión de peluqueros</h2>
                <button id="nuevo-peluquero" class="boton-principal">
                    + Nuevo peluquero
                </button>
            </div>

            <div id="formulario-peluquero"></div>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Especialidad</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
        `;

        if (peluqueros.length === 0) {
            contenido += `
                <tr>
                    <td colspan="4">No hay peluqueros registrados.</td>
                </tr>
            `;
        }

        peluqueros.forEach(function(peluquero) {
            contenido += `
                <tr>
                    <td>${escaparHTML(peluquero.id_peluquero)}</td>
                    <td>${escaparHTML(peluquero.nombre)}</td>
                    <td>${escaparHTML(peluquero.especialidad)}</td>
                    <td>
                        <button class="boton-editar"
                            data-id="${escaparHTML(peluquero.id_peluquero)}">
                            Editar
                        </button>

                        <button class="boton-eliminar"
                            data-id="${escaparHTML(peluquero.id_peluquero)}">
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

        document.querySelector("#nuevo-peluquero")
            .addEventListener("click", function() {
                mostrarFormularioPeluquero();
            });

        document.querySelectorAll("#formulario-peluquero ~ table .boton-editar");

        document.querySelectorAll(".boton-editar")
            .forEach(function(boton) {
                boton.addEventListener("click", function() {
                    editarPeluquero(Number(boton.dataset.id));
                });
            });

        document.querySelectorAll(".boton-eliminar")
            .forEach(function(boton) {
                boton.addEventListener("click", function() {
                    eliminarPeluquero(Number(boton.dataset.id));
                });
            });

    } catch (error) {
        panel.innerHTML = `
            <h2>Gestión de peluqueros</h2>
            <p class="mensaje-error">${escaparHTML(error.message)}</p>
        `;
    }
}


function cargarServicios() {

    fetch("/api/servicios")
        .then(function(respuesta) {
            return respuesta.json();
        })
        .then(function(servicios) {

            let html = `
                <div class="titulo-seccion">
                    <h2>Gestión de servicios</h2>
                    <button id="nuevo-servicio" class="boton-principal">
                        Nuevo servicio
                    </button>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Precio</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
            `;

            servicios.forEach(function(servicio) {
                html += `
                    <tr>
                        <td>${servicio.id_servicio}</td>
                        <td>${escaparHTML(servicio.nombre_servicio)}</td>
                        <td>${Number(servicio.precio).toFixed(2)} €</td>
                        <td>
                            <button class="boton-editar"
                                data-id="${servicio.id_servicio}">
                                Editar
                            </button>
                            <button class="boton-eliminar"
                                data-id="${servicio.id_servicio}">
                                Eliminar
                            </button>
                        </td>
                    </tr>
                `;
            });

            html += `
                    </tbody>
                </table>

                <div id="formulario-servicio"></div>
            `;

            panel.innerHTML = html;

            document.querySelector("#nuevo-servicio")
                .addEventListener("click", function() {
                    mostrarFormularioServicio();
                });

            document.querySelectorAll(".boton-editar").forEach(function(boton) {
                boton.addEventListener("click", function() {
                    editarServicio(boton.dataset.id);
                });
            });

            document.querySelectorAll(".boton-eliminar").forEach(function(boton) {
                boton.addEventListener("click", function() {
                    eliminarServicio(boton.dataset.id);
                });
            });

        })
        .catch(function(error) {
            panel.innerHTML = "<p class='mensaje-error'>Error al cargar los servicios.</p>";
            console.error(error);
        });
}


function cargarCitas() {

    Promise.all([
        fetch("/api/citas").then(function(r) {
            return r.json();
        }),
        fetch("/api/clientes").then(function(r) {
            return r.json();
        }),
        fetch("/api/peluqueros").then(function(r) {
            return r.json();
        })
    ])
    .then(function(datos) {

        const citas = datos[0];

        let html = `
            <div class="titulo-seccion">
                <h2>Gestión de citas</h2>
                <button id="nueva-cita" class="boton-principal">
                    Nueva cita
                </button>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Fecha y hora</th>
                        <th>Cliente</th>
                        <th>Peluquero</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
        `;

        citas.forEach(function(cita) {

            html += `
                <tr>
                    <td>${cita.id_cita}</td>
                    <td>${cita.fecha_hora.replace("T", " ")}</td>
                    <td>${escaparHTML(cita.nombre_cliente)}</td>
                    <td>${escaparHTML(cita.nombre_peluquero)}</td>
                    <td>${escaparHTML(cita.estado)}</td>
                    <td>
                        <button class="boton-editar"
                            data-id="${cita.id_cita}">
                            Estado
                        </button>

                        <button class="boton-eliminar"
                            data-id="${cita.id_cita}">
                            Eliminar
                        </button>
                    </td>
                </tr>
            `;
        });

        html += `
                </tbody>
            </table>

            <div id="formulario-cita"></div>
        `;

        panel.innerHTML = html;

        document.querySelector("#nueva-cita")
            .addEventListener("click", function() {
                mostrarFormularioCita(datos[1], datos[2]);
            });

        document.querySelectorAll(".boton-editar").forEach(function(boton) {
            boton.addEventListener("click", function() {
                modificarEstadoCita(boton.dataset.id);
            });
        });

        document.querySelectorAll(".boton-eliminar").forEach(function(boton) {
            boton.addEventListener("click", function() {
                eliminarCita(boton.dataset.id);
            });
        });

    })
    .catch(function(error) {
        console.error(error);
        panel.innerHTML = `
            <p class="mensaje-error">
                Error al cargar las citas.
            </p>
        `;
    });
}

// Obtener facturas de la API
function cargarFacturas() {

    Promise.all([
        fetch("/api/facturas").then(function(r) {
            return r.json();
        }),
        fetch("/api/citas").then(function(r) {
            return r.json();
        })
    ])
    .then(function(datos) {

        const facturas = datos[0];
        const citas = datos[1];

        // Obtener citas que todavía no tienen factura
        const citasFacturables = citas.filter(function(cita) {
            return cita.estado !== "cancelada" &&
                !facturas.some(function(factura) {
                    return factura.id_cita === cita.id_cita;
                });
        });

        let html = `
            <div class="titulo-seccion">
                <h2>Gestión de facturas</h2>
                <button id="nueva-factura" class="boton-principal">
                    Generar factura
                </button>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Fecha de emisión</th>
                        <th>Total</th>
                        <th>ID de cita</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
        `;

        if (facturas.length === 0) {
            html += `
                <tr>
                    <td colspan="5">No hay facturas registradas.</td>
                </tr>
            `;
        }

        facturas.forEach(function(factura) {

            html += `
                <tr>
                    <td>${factura.id_factura}</td>
                    <td>${factura.fecha_emision.replace("T", " ")}</td>
                    <td>${Number(factura.total).toFixed(2)} €</td>
                    <td>${factura.id_cita}</td>
                    <td>
                        <button class="boton-eliminar"
                            data-id="${factura.id_factura}">
                            Eliminar
                        </button>
                    </td>
                </tr>
            `;
        });

        html += `
                </tbody>
            </table>

            <div id="formulario-factura"></div>
        `;

        panel.innerHTML = html;

        // Botón para generar factura
        document.querySelector("#nueva-factura")
            .addEventListener("click", function() {
                mostrarFormularioFactura(citasFacturables);
            });

        // Botones para eliminar facturas
        document.querySelectorAll(".boton-eliminar").forEach(function(boton) {
            boton.addEventListener("click", function() {
                eliminarFactura(boton.dataset.id);
            });
        });

    })
    .catch(function(error) {
        console.error(error);

        panel.innerHTML = `
            <p class="mensaje-error">
                Error al cargar las facturas.
            </p>
        `;
    });
}

// Mostrar formulario para generar una factura
function mostrarFormularioFactura(citas) {

    const formulario = document.querySelector("#formulario-factura");

    if (citas.length === 0) {
        formulario.innerHTML = `
            <p class="mensaje-error">
                No hay citas disponibles para facturar.
            </p>
        `;
        return;
    }

    let opciones = "";

    citas.forEach(function(cita) {
        opciones += `
            <option value="${cita.id_cita}">
                Cita ${cita.id_cita} - ${escaparHTML(cita.nombre_cliente)}
            </option>
        `;
    });

    formulario.innerHTML = `
        <div class="formulario">
            <h3>Nueva factura</h3>

            <form id="form-factura">

                <label for="id-cita-factura">Seleccionar cita:</label>

                <select id="id-cita-factura" required>
                    ${opciones}
                </select>

                <button type="submit" class="boton-principal">
                    Generar factura
                </button>

            </form>
        </div>
    `;

    document.querySelector("#form-factura")
        .addEventListener("submit", function(event) {

            event.preventDefault();

            const idCita = document.querySelector("#id-cita-factura").value;

            fetch("/api/facturas", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    id_cita: Number(idCita)
                })
            })
            .then(function(r) {
                return r.json();
            })
            .then(function(resultado) {

                if (resultado === true) {
                    alert("Factura generada correctamente.");
                    cargarFacturas();
                } else {
                    alert("No se ha podido generar la factura. Comprueba que la cita tiene servicios asociados.");
                }

            })
            .catch(function(error) {
                console.error(error);
                alert("Error al generar la factura.");
            });
        });
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


function mostrarFormularioPeluquero(peluquero = null) {

    const formulario = document.querySelector("#formulario-peluquero");

    const esEdicion = peluquero !== null;

    formulario.innerHTML = `
        <div class="formulario">
            <h3>${esEdicion ? "Modificar peluquero" : "Nuevo peluquero"}</h3>

            <form id="form-peluquero">

                <label for="nombre-peluquero">Nombre:</label>
                <input type="text" id="nombre-peluquero"
                    value="${esEdicion ? escaparHTML(peluquero.nombre) : ""}"
                    required maxlength="100">

                <label for="especialidad-peluquero">Especialidad:</label>
                <input type="text" id="especialidad-peluquero"
                    value="${esEdicion ? escaparHTML(peluquero.especialidad) : ""}"
                    required maxlength="100">

                <div class="acciones-formulario">
                    <button type="submit" class="boton-principal">
                        Guardar
                    </button>

                    <button type="button" id="cancelar-peluquero"
                        class="boton-cancelar">
                        Cancelar
                    </button>
                </div>

            </form>
        </div>
    `;

    document.querySelector("#cancelar-peluquero")
        .addEventListener("click", function() {
            formulario.innerHTML = "";
        });

    document.querySelector("#form-peluquero")
        .addEventListener("submit", async function(event) {

            event.preventDefault();

            const nombre = document.querySelector("#nombre-peluquero").value.trim();
            const especialidad = document.querySelector("#especialidad-peluquero").value.trim();

            if (nombre === "" || especialidad === "") {
                alert("Todos los campos son obligatorios.");
                return;
            }

            const datos = {
                nombre: nombre,
                especialidad: especialidad
            };

            try {
                let respuesta;

                if (esEdicion) {
                    respuesta = await fetch("/api/peluqueros/" + peluquero.id_peluquero, {
                        method: "PUT",
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(datos)
                    });
                } else {
                    respuesta = await fetch("/api/peluqueros", {
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
                    ? "Peluquero modificado correctamente."
                    : "Peluquero creado correctamente.");

                cargarPeluqueros();

            } catch (error) {
                alert("Error: " + error.message);
            }
        });
}


function mostrarFormularioServicio(servicio = null) {

    const formulario = document.querySelector("#formulario-servicio");

    formulario.innerHTML = `
        <h3>${servicio ? "Editar servicio" : "Nuevo servicio"}</h3>

        <form id="form-servicio" class="formulario">

            <label for="nombre_servicio">Nombre del servicio</label>
            <input
                type="text"
                id="nombre_servicio"
                required
                value="${servicio ? escaparHTML(servicio.nombre_servicio) : ""}"
            >

            <label for="precio">Precio (€)</label>
            <input
                type="number"
                id="precio"
                min="0"
                step="0.01"
                required
                value="${servicio ? servicio.precio : ""}"
            >

            <div class="acciones-formulario">
                <button type="submit" class="boton-principal">
                    ${servicio ? "Guardar cambios" : "Crear servicio"}
                </button>

                <button type="button" id="cancelar-servicio"
                    class="boton-cancelar">
                    Cancelar
                </button>
            </div>

        </form>
    `;

    document.querySelector("#cancelar-servicio")
        .addEventListener("click", function() {
            formulario.innerHTML = "";
        });

    document.querySelector("#form-servicio")
        .addEventListener("submit", function(event) {

            event.preventDefault();

            const datos = {
                nombre_servicio: document.querySelector("#nombre_servicio").value.trim(),
                precio: parseFloat(document.querySelector("#precio").value)
            };

            const url = servicio
                ? "/api/servicios/" + servicio.id_servicio
                : "/api/servicios";

            const metodo = servicio ? "PUT" : "POST";

            fetch(url, {
                method: metodo,
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(datos)
            })
                .then(function(respuesta) {
                    if (!respuesta.ok) {
                        throw new Error("No se pudo guardar el servicio");
                    }

                    return respuesta.json();
                })
                .then(function(resultado) {
                    if (resultado === true) {
                        cargarServicios();
                    } else {
                        alert("No se pudo guardar el servicio.");
                    }
                })
                .catch(function(error) {
                    console.error(error);
                    alert("Error al guardar el servicio.");
                });
        });
}


function mostrarFormularioCita(clientes, peluqueros) {

    const formulario = document.querySelector("#formulario-cita");

    fetch("/api/servicios")
        .then(function(respuesta) {
            return respuesta.json();
        })
        .then(function(servicios) {

            let opcionesClientes = "";
            clientes.forEach(function(cliente) {
                opcionesClientes += `
                    <option value="${cliente.id_cliente}">
                        ${escaparHTML(cliente.nombre)}
                    </option>
                `;
            });

            let opcionesPeluqueros = "";
            peluqueros.forEach(function(peluquero) {
                opcionesPeluqueros += `
                    <option value="${peluquero.id_peluquero}">
                        ${escaparHTML(peluquero.nombre)}
                    </option>
                `;
            });

            let opcionesServicios = "";
            servicios.forEach(function(servicio) {
                opcionesServicios += `
                    <option value="${servicio.id_servicio}"
                        data-precio="${servicio.precio}">
                        ${escaparHTML(servicio.nombre_servicio)}
                        - ${Number(servicio.precio).toFixed(2)} €
                    </option>
                `;
            });

            formulario.innerHTML = `
                <h3>Nueva cita</h3>

                <form id="form-cita" class="formulario">

                    <label for="cliente-cita">Cliente</label>
                    <select id="cliente-cita" required>
                        ${opcionesClientes}
                    </select>

                    <label for="peluquero-cita">Peluquero</label>
                    <select id="peluquero-cita" required>
                        ${opcionesPeluqueros}
                    </select>

                    <label for="fecha-cita">Fecha</label>
                    <input type="date" id="fecha-cita" required>

                    <label for="hora-cita">Hora</label>
                    <select id="hora-cita" required>
                        <option value="">Selecciona una hora</option>
                    </select>

                    <label for="estado-cita">Estado</label>
                    <select id="estado-cita" required>
                        <option value="confirmada">Confirmada</option>
                        <option value="completada">Completada</option>
                        <option value="cancelada">Cancelada</option>
                    </select>

                    <label for="servicios-cita">Servicios</label>
                    <select id="servicios-cita" multiple required>
                        ${opcionesServicios}
                    </select>

                    <p id="total-cita">Total: 0.00 €</p>

                    <div class="acciones-formulario">
                        <button type="submit" class="boton-principal">
                            Crear cita
                        </button>

                        <button type="button" id="cancelar-cita"
                            class="boton-cancelar">
                            Cancelar
                        </button>
                    </div>

                </form>
            `;

            const selectorHora = document.querySelector("#hora-cita");

            function generarHorarios(inicio, fin) {
            
                for (let minutos = inicio; minutos <= fin; minutos += 30) {
                
                    const horas = Math.floor(minutos / 60);
                    const resto = minutos % 60;
                
                    const hora = String(horas).padStart(2, "0");
                    const minuto = String(resto).padStart(2, "0");
                
                    const opcion = document.createElement("option");
                
                    opcion.value = hora + ":" + minuto;
                    opcion.textContent = hora + ":" + minuto;
                
                    selectorHora.appendChild(opcion);
                }
            }

            // Mañanas: 09:00 - 14:00
            generarHorarios(9 * 60, 14 * 60);

            // Tardes: 17:00 - 20:00
            generarHorarios(17 * 60, 20 * 60);

            const selectorServicios = document.querySelector("#servicios-cita");
            const total = document.querySelector("#total-cita");

            selectorServicios.addEventListener("change", function() {

                let suma = 0;

                Array.from(selectorServicios.selectedOptions).forEach(function(opcion) {
                    suma += Number(opcion.dataset.precio);
                });

                total.textContent = "Total: " + suma.toFixed(2) + " €";
            });

            document.querySelector("#cancelar-cita")
                .addEventListener("click", function() {
                    formulario.innerHTML = "";
                });

            document.querySelector("#form-cita")
                .addEventListener("submit", function(event) {

                    event.preventDefault();

                    const fecha = document.querySelector("#fecha-cita").value;
                    const hora = document.querySelector("#hora-cita").value;

                    const datosCita = {
                        fecha_hora: fecha + "T" + hora,
                        estado: document.querySelector("#estado-cita").value,
                        id_cliente: Number(document.querySelector("#cliente-cita").value),
                        id_peluquero: Number(document.querySelector("#peluquero-cita").value)
                    };

                    const serviciosSeleccionados = Array.from(
                        selectorServicios.selectedOptions
                    );

                    fetch("/api/citas", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(datosCita)
                    })
                    .then(function(respuesta) {
                        if (!respuesta.ok) {
                            throw new Error("No se pudo crear la cita");
                        }

                        return respuesta.json();
                    })
                    .then(function(idCita) {

                        if (idCita <= 0) {
                            throw new Error("El peluquero ya tiene una cita reservada a esa hora o los datos no son válidos.");
                        }

                        const peticiones = serviciosSeleccionados.map(function(opcion) {

                            const servicio = servicios.find(function(elemento) {
                                return elemento.id_servicio === Number(opcion.value);
                            });

                            return fetch("/api/citas/" + idCita + "/servicios", {
                                method: "POST",
                                headers: {
                                    "Content-Type": "application/json"
                                },
                                body: JSON.stringify(servicio)
                            }).then(function(respuesta) {
                                if (!respuesta.ok) {
                                    throw new Error("Error al añadir un servicio");
                                }

                                return respuesta.json();
                            });
                        });

                        return Promise.all(peticiones);
                    })
                    .then(function(resultados) {

                        if (resultados.some(function(resultado) {
                            return resultado !== true;
                        })) {
                            throw new Error("No se pudieron añadir todos los servicios");
                        }

                        alert("Cita creada correctamente.");
                        cargarCitas();
                    })
                    .catch(function(error) {
                        alert(error.message);
                    });
                });
        })
        .catch(function(error) {
            console.error(error);
            alert("No se pudieron cargar los servicios.");
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

async function editarPeluquero(id) {

    try {
        const respuesta = await fetch("/api/peluqueros/" + id);

        if (!respuesta.ok) {
            throw new Error("No se ha encontrado el peluquero.");
        }

        const peluquero = await respuesta.json();

        mostrarFormularioPeluquero(peluquero);

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

async function eliminarPeluquero(id) {

    const confirmar = confirm("¿Seguro que quieres eliminar este peluquero?");

    if (!confirmar) {
        return;
    }

    try {
        const respuesta = await fetch("/api/peluqueros/" + id, {
            method: "DELETE"
        });

        if (!respuesta.ok) {
            throw new Error(await respuesta.text());
        }

        alert("Peluquero eliminado correctamente.");

        cargarPeluqueros();

    } catch (error) {
        alert("No se ha podido eliminar el peluquero: " + error.message);
    }
}


function editarServicio(id) {

    fetch("/api/servicios/" + id)
        .then(function(respuesta) {
            if (!respuesta.ok) {
                throw new Error("No se pudo obtener el servicio");
            }

            return respuesta.json();
        })
        .then(function(servicio) {
            if (servicio) {
                mostrarFormularioServicio(servicio);
            } else {
                alert("No se ha encontrado el servicio.");
            }
        })
        .catch(function(error) {
            console.error(error);
            alert("Error al obtener el servicio.");
        });
}

// Modificar estado de una cita
function modificarEstadoCita(id) {

    fetch("/api/citas/" + id)
        .then(function(respuesta) {
            return respuesta.json();
        })
        .then(function(cita) {

            const nuevoEstado = prompt(
                "Introduce el nuevo estado:\nconfirmada, completada o cancelada",
                cita.estado
            );

            if (nuevoEstado === null) {
                return;
            }

            const estado = nuevoEstado.trim().toLowerCase();

            if (
                estado !== "confirmada" &&
                estado !== "completada" &&
                estado !== "cancelada"
            ) {
                alert("El estado introducido no es válido.");
                return;
            }

            return fetch("/api/citas/" + id + "/estado", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    estado: estado
                })
            })
                .then(function(respuesta) {
                    return respuesta.json();
                })
                .then(function(resultado) {
                    if (resultado) {
                        alert("Estado actualizado correctamente.");
                        cargarCitas();
                    } else {
                        alert("No se ha podido actualizar el estado.");
                    }
                });
        })
        .catch(function(error) {
            console.error("Error al modificar la cita:", error);
            alert("Error al modificar el estado de la cita.");
        });
}


function eliminarServicio(id) {

    if (confirm("¿Estás seguro de que quieres eliminar este servicio?")) {

        fetch("/api/servicios/" + id, {
            method: "DELETE"
        })
            .then(function(respuesta) {
                if (!respuesta.ok) {
                    throw new Error("No se pudo eliminar el servicio");
                }

                return respuesta.json();
            })
            .then(function(resultado) {
                if (resultado === true) {
                    cargarServicios();
                } else {
                    alert("No se pudo eliminar el servicio.");
                }
            })
            .catch(function(error) {
                console.error(error);
                alert("Error al eliminar el servicio.");
            });
    }
}

// Eliminar una cita
function eliminarCita(id) {

    const confirmar = confirm(
        "¿Estás seguro de que quieres eliminar esta cita?"
    );

    if (!confirmar) {
        return;
    }

    fetch("/api/citas/" + id, {
        method: "DELETE"
    })
        .then(function(respuesta) {
            return respuesta.json();
        })
        .then(function(resultado) {

            if (resultado) {
                alert("Cita eliminada correctamente.");
                cargarCitas();
            } else {
                alert("No se ha podido eliminar la cita.");
            }
        })
        .catch(function(error) {
            console.error("Error al eliminar la cita:", error);
            alert("Ha ocurrido un error al eliminar la cita.");
        });
}

// Eliminar una factura
function eliminarFactura(id) {

    if (confirm("¿Estás seguro de que quieres eliminar esta factura?")) {

        fetch("/api/facturas/" + id, {
            method: "DELETE"
        })
        .then(function(r) {
            return r.json();
        })
        .then(function(resultado) {

            if (resultado === true) {
                alert("Factura eliminada correctamente.");
                cargarFacturas();
            } else {
                alert("No se ha podido eliminar la factura.");
            }

        })
        .catch(function(error) {
            console.error(error);
            alert("Error al eliminar la factura.");
        });
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

// Cargar información de la página de inicio
function cargarInicio() {

    // Actualizar las estadísticas
    actualizarEstadisticas();

    // Mostrar bienvenida y horario
    panel.innerHTML = `
        <h2>Bienvenido a SalonFlow</h2>

        <p>
            Desde esta aplicación podrás gestionar los clientes,
            peluqueros, servicios, citas y facturas de tu peluquería.
        </p>

        <p>Selecciona una opción del menú lateral para comenzar.</p>

        <div class="horario">
            <h3>Horario de apertura</h3>

            <div class="horario-detalles">
                <div>
                    <span>Mañanas</span>
                    <strong>09:00 - 14:00</strong>
                </div>

                <div>
                    <span>Tardes</span>
                    <strong>17:00 - 20:00</strong>
                </div>
            </div>
        </div>
    `;
}

// Cargar inicio al abrir la aplicación
alert(esEdicion
    ? "Cliente modificado correctamente."
    : "Cliente creado correctamente.");

cargarClientes();
actualizarEstadisticas();

// Actualizar los contadores del panel principal
function actualizarEstadisticas() {

    Promise.all([
        fetch("/api/clientes").then(r => r.json()),
        fetch("/api/peluqueros").then(r => r.json()),
        fetch("/api/servicios").then(r => r.json()),
        fetch("/api/citas").then(r => r.json())
    ])
    .then(function(datos) {

        document.querySelector("#total-clientes").textContent = datos[0].length;
        document.querySelector("#total-peluqueros").textContent = datos[1].length;
        document.querySelector("#total-servicios").textContent = datos[2].length;
        document.querySelector("#total-citas").textContent = datos[3].length;

    })
    .catch(function(error) {
        console.error("Error al actualizar estadísticas:", error);
    });
}