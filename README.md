# SpeedFast 
Proyecto integrador desarrollado para la asignatura Desarrollo Orientado a Objetos II.

SpeedFast es una aplicación de consola creada en Java para gestionar repartidores, pedidos y entregas. El proyecto reúne los contenidos trabajados desde la semana S1 hasta la semana S8, incluyendo programación orientada a objetos, concurrencia, conexión JDBC y operaciones CRUD con MySQL.

## Tecnologías utilizadas

- Java SE.
- IntelliJ IDEA.
- MySQL Workbench.
- JDBC.
- MySQL Connector/J.
- GitHub.

## Modelo orientado a objetos
# Clase abstracta Pedido
Contiene los atributos y comportamientos comunes de todos los pedidos:
- Identificador del pedido.
- Dirección de entrega.
- Tipo de pedido.
- Distancia en kilómetros.
- Estado del pedido.
- Historial de operaciones.

También declara el método abstracto `calcularTiempoEntrega()`, que debe ser implementado por cada subclase.

# Tipos de `pedido`
- `**PedidoComida:**` requiere un repartidor con mochila térmica.
- `**PedidoEncomienda:**` requiere validación de peso y embalaje.
- `**PedidoExpress:**` requiere un repartidor cercano y con disponibilidad inmediata.

# Interfaces
- `**Despachable:**` permite cambiar un pedido a estado `EN_REPARTO`.
- `**Cancelable:**` permite cancelar un pedido.
- `**Rastreable:**` permite consultar su historial.

# Estados
La enumeración **EstadoPedido** contiene los siguientes valores:

PENDIENTE,
EN_REPARTO,
ENTREGADO

# Concurrencia
La clase Repartidor implementa Runnable, por lo que cada repartidor puede ejecutar su trabajo en un hilo.
La clase ZonaDeCarga utiliza una BlockingQueue<Pedido> como recurso compartido. Esto permite que los pedidos sean retirados de uno en uno y evita que dos repartidores procesen el mismo pedido.
ExecutorService administra la ejecución concurrente de los repartidores.

# Estructura del proyecto
```
SpeedFast/
├── database/
│   └── speedfast_db.sql
├── lib/
│   └── mysql-connector-j-x.x.x.jar
└── src/
    ├── app/
    │   ├── DiagnosticoConexion.java
    │   ├── Main.java
    │   ├── MenuConsola.java
    │   ├── PruebaConexion.java
    │   ├── PruebaDAO.java
    │   ├── PruebaEntregaCRUD.java
    │   ├── PruebaPedidoCRUD.java
    │   └── PruebaRepartidorCRUD.java
    ├── dao/
    │   ├── ConexionDB.java
    │   ├── RepartidorDAO.java
    │   ├── PedidoDAO.java
    │   └── EntregaDAO.java
    ├── interfaces/
    │   ├── Despachable.java
    │   ├── Cancelable.java
    │   └── Rastreable.java
    └── model/
        ├── EstadoPedido.java
        ├── Pedido.java
        ├── PedidoComida.java
        ├── PedidoEncomienda.java
        ├── PedidoExpress.java
        ├── Repartidor.java
        ├── ZonaDeCarga.java
        └── Entrega.java
```
# Base de datos
El proyecto utiliza la base de datos `speedfast_db`.

```
CREATE DATABASE IF NOT EXISTS speedfast_db;
USE speedfast_db;

CREATE TABLE repartidores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(150) NOT NULL,
    tipo ENUM('COMIDA', 'ENCOMIENDA', 'EXPRESS') NOT NULL,
    estado ENUM('PENDIENTE', 'EN_REPARTO', 'ENTREGADO') NOT NULL
);

CREATE TABLE entregas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_repartidor INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    CONSTRAINT fk_entrega_pedido
        FOREIGN KEY (id_pedido) REFERENCES pedidos(id),
    CONSTRAINT fk_entrega_repartidor
        FOREIGN KEY (id_repartidor) REFERENCES repartidores(id)
);

```

# Configuración MySQL Workbench
1. Abrir la conexión local de MySQL.
2. Crear una pestaña SQL nueva.
3. Copiar el contenido de `database/speedfast_db.sql`.
4. Ejecutar el script con el icono del rayo.
5. Actualizar la sección SCHEMAS.
6. Comprobar que aparezcan las tablas `repartidores`, `pedidos` y `entregas`.

Para verificar las tablas:
```
USE speedfast_db;
SHOW TABLES;

SELECT * FROM repartidores;
SELECT * FROM pedidos;
SELECT * FROM entregas;

```

# Configuración de JDBC en IntelliJ IDEA
1. Descargar MySQL Connector/J.
2. Abrir File > Project Structure.
3. Seleccionar Modules > Dependencies.
4. Presionar `+` y seleccionar JARs or Directories.
5. Agregar el archivo `mysql-connector-j-x.x.x.jar`.
6. Verificar que su alcance sea Compile.
7. Ejecutar Build > Rebuild Project.

# Configuración de la conexión
En `**ConexionDB.java**` se deben configurar los datos correspondientes al servidor local:

```
private static final String URL =
        "jdbc:mysql://localhost:3306/speedfast_db";

private static final String USUARIO = "root";
private static final String CONTRASENA = "TU_CONTRASENA";

```
Se debe reemplazar `TU_CONTRASENA` por la contraseña real del usuario de MySQL.

# Menú de consola en IntelliJ IDEA
```
====================================
=== SPEEDFAST ===
====================================
1. Gestionar repartidores
2. Gestionar pedidos
3. Gestionar entregas
4. Simular entregas concurrentes
5. Salir
====================================
```

# Consulta final en SQL Workbench
La siguiente consulta permite revisar conjuntamente pedidos, repartidores y entregas:

```
SELECT
    e.id AS entrega,
    p.id AS pedido,
    p.direccion,
    p.tipo,
    p.estado,
    r.id AS repartidor,
    r.nombre,
    e.fecha,
    e.hora
FROM entregas e
INNER JOIN pedidos p
    ON p.id = e.id_pedido
INNER JOIN repartidores r
    ON r.id = e.id_repartidor
ORDER BY e.id;

```

# Autor:
**Gustavo Zapata Covarrubias**
Analista Programador | 2026
-- DUOC UC Aula Virtual --
