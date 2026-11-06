
CREATE TABLE cliente (
 
 id_cliente SERIAL PRIMARY KEY,
 nombre VARCHAR (100) NOT NULL,
 telefono VARCHAR (20) NOT NULL
);

CREATE TABLE peluquero (

 id_peluquero SERIAL PRIMARY KEY,
 nombre VARCHAR (100) NOT NULL,
 especialidad VARCHAR (50)
 
);

 CREATE TABLE servicio
(
    id_servicio SERIAL PRIMARY KEY,
    nombre_servicio VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL CHECK (precio >= 0)
) ;


CREATE TABLE cita (
    id_cita SERIAL PRIMARY KEY,
    fecha_hora TIMESTAMP NOT NULL,
    estado VARCHAR(20) NOT NULL
        CHECK (estado IN ('confirmada', 'completada', 'cancelada')),
    id_cliente INTEGER NOT NULL,
    id_peluquero INTEGER NOT NULL,

    CONSTRAINT FK_CITA_CLIENTE
        FOREIGN KEY (id_cliente)
        REFERENCES cliente(id_cliente),

    CONSTRAINT FK_CITA_PELUQUERO
        FOREIGN KEY (id_peluquero)
        REFERENCES peluquero(id_peluquero)
);


CREATE TABLE cita_servicio
(
    id_cita INTEGER NOT NULL,
    id_servicio INTEGER NOT NULL,
    precio DECIMAL(10,2) NOT NULL CHECK (precio >= 0),

    PRIMARY KEY (id_cita, id_servicio),

    CONSTRAINT fk_cita_servicio_cita
        FOREIGN KEY (id_cita)
        REFERENCES cita(id_cita),

    CONSTRAINT fk_cita_servicio_servicio
        FOREIGN KEY (id_servicio)
        REFERENCES servicio(id_servicio)
);

CREATE TABLE factura
(
    id_factura SERIAL PRIMARY KEY,
    fecha_emision TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL CHECK (total >= 0),
    id_cita INTEGER NOT NULL UNIQUE,

    CONSTRAINT fk_factura_cita
        FOREIGN KEY (id_cita)
        REFERENCES cita(id_cita)
);



