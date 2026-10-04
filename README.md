# SpeedFast Backend
Proyecto integrador desarrollado para la asignatura Desarrollo Orientado a Objetos II.

SpeedFast es una aplicación de consola creada en Java para gestionar repartidores, pedidos y entregas. El proyecto reúne los contenidos trabajados desde la semana S1 hasta la semana S8, incluyendo programación orientada a objetos, concurrencia, conexión JDBC y operaciones CRUD con MySQL.

--
## Tecnologías utilizadas

- Java SE.
- IntelliJ IDEA.
- MySQL Workbench.
- JDBC.
- MySQL Connector/J.
- GitHub.

--
## Modelo orientado a objetos
# Clase abstracta Pedido
Contiene los atributos y comportamientos comunes de todos los pedidos:
- Identificador del pedido.
- Dirección de entrega.
- Tipo de pedido.
- Distancia en kilómetros.
- Estado del pedido.
- Historial de operaciones.

También declara el método abstracto calcularTiempoEntrega(), que debe ser implementado por cada subclase.

# Tipos de pedido
- PedidoComida: requiere un repartidor con mochila térmica.
- PedidoEncomienda: requiere validación de peso y embalaje.
- PedidoExpress: requiere un repartidor cercano y con disponibilidad inmediata.

# Interfaces
- Despachable: permite cambiar un pedido a estado EN_REPARTO.
- Cancelable: permite cancelar un pedido.
- Rastreable: permite consultar su historial.

# Estados
La enumeración EstadoPedido contiene los siguientes valores:

</>
PENDIENTE
EN_REPARTO
ENTREGADO

# Concurrencia
La clase Repartidor implementa Runnable, por lo que cada repartidor puede ejecutar su trabajo en un hilo.
La clase ZonaDeCarga utiliza una BlockingQueue<Pedido> como recurso compartido. Esto permite que los pedidos sean retirados de uno en uno y evita que dos repartidores procesen el mismo pedido.
ExecutorService administra la ejecución concurrente de los repartidores.
