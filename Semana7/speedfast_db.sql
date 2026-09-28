-- ====================================================
-- BASE DE DATOS SPEEDFAST
-- SEMANA 7: CONEXION DE JAVA CON MYSQL MEDIANTE JDBC
-- ====================================================

CREATE DATABASE IF NOT EXISTS speedfast_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_spanish_ci;

USE speedfast_db;

-- ====================================================
-- TABLA REPARTIDOR
-- ====================================================
CREATE TABLE IF NOT EXISTS repartidor (
    id INT AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,

    CONSTRAINT pk_repartidor
        PRIMARY KEY (id)
);

-- ====================================================
-- TABLA PEDIDO
-- ====================================================
CREATE TABLE IF NOT EXISTS pedido (
    id INT AUTO_INCREMENT,
    codigo_pedido VARCHAR(20) NOT NULL,
    direccion VARCHAR(150) NOT NULL,
    distancia_km DECIMAL(8,2) NOT NULL,
    tipo ENUM(
        'COMIDA',
        'ENCOMIENDA',
        'EXPRESS'
    ) NOT NULL,
    estado ENUM(
        'PENDIENTE',
        'EN_REPARTO',
        'ENTREGADO',
        'CANCELADO'
    ) NOT NULL DEFAULT 'PENDIENTE',

    CONSTRAINT pk_pedido
        PRIMARY KEY (id),

    CONSTRAINT uk_pedido_codigo
        UNIQUE (codigo_pedido),

    CONSTRAINT chk_pedido_distancia
        CHECK (distancia_km > 0)
);

-- ====================================================
-- TABLA ENTREGA
-- ====================================================
CREATE TABLE IF NOT EXISTS entrega (
    id INT AUTO_INCREMENT,
    id_pedido INT NOT NULL,
    id_repartidor INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,

    CONSTRAINT pk_entrega
        PRIMARY KEY (id),

    CONSTRAINT fk_entrega_pedido
        FOREIGN KEY (id_pedido)
        REFERENCES pedido(id),

    CONSTRAINT fk_entrega_repartidor
        FOREIGN KEY (id_repartidor)
        REFERENCES repartidor(id)
);
