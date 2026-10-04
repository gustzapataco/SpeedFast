SELECT DATABASE();

SELECT
    @@hostname AS servidor,
    @@port AS puerto,
    CURRENT_USER() AS usuario,
    DATABASE() AS base_actual;
    
SELECT *
FROM speedfast_db.repartidores
WHERE nombre = 'PRUEBA ENLACE WORKBENCH';

SHOW DATABASES;

USE speedfast_db;

SELECT *
FROM repartidores
ORDER BY id;

SELECT *
FROM pedidos
ORDER BY id;

SELECT *
FROM speedfast_db.pedidos
ORDER BY id;

SELECT *
FROM speedfast_db.entregas
ORDER BY id;

SELECT COUNT(*) AS entregas_despues
FROM entregas;

SELECT
    e.id AS id_entrega,
    p.id AS id_pedido,
    p.direccion,
    p.tipo,
    p.estado,
    r.id AS id_repartidor,
    r.nombre AS repartidor,
    e.fecha,
    e.hora
FROM entregas e
INNER JOIN pedidos p
    ON e.id_pedido = p.id
INNER JOIN repartidores r
    ON e.id_repartidor = r.id
ORDER BY e.id;

SELECT
    id_pedido,
    COUNT(*) AS cantidad_entregas
FROM entregas
GROUP BY id_pedido
ORDER BY id_pedido;