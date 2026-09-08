package model;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Repartidor implements Runnable {

    // Atributos
    private String nombre;
    private List<Pedido> pedidosAsignados;

    // Constructor
    public Repartidor(
            String nombre,
            List<Pedido> pedidosAsignados
    ) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del repartidor es obligatorio."
            );
        }

        if (pedidosAsignados == null
                || pedidosAsignados.isEmpty()) {

            throw new IllegalArgumentException(
                    "El repartidor debe tener pedidos asignados."
            );
        }

        this.nombre = nombre;
        this.pedidosAsignados = pedidosAsignados;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public List<Pedido> getPedidosAsignados() {
        return pedidosAsignados;
    }

    // Este metodo contiene la tarea que sera ejecutada por un hilo del ExecutorService.
    @Override
    public void run() {
        System.out.println("\n[" + nombre + "] Comenzo su jornada.");

        // Cada repartidor procesa sus pedidos secuencialmente.
        for (Pedido pedido : pedidosAsignados) {
            try  {
                System.out.println(
                        "\n[Repartidor: " + nombre
                                + "] Entregando "
                                + pedido.getClass().getSimpleName()
                                + " "
                                + pedido.getIdPedido()
                                + "..."
                );

                /*
                 * Se utiliza la asignacion manual porque el repartidor actual debe quedar
                 * registrado en el pedido.
                 */
                pedido.asignarRepartidor(nombre);

                // CREADO -> RESERVADO
                pedido.reservarPedido();

                // RESERVADO -> DESPACHADO
                pedido.despachar();

                System.out.println(
                        "[Repartidor: " + nombre
                                + "] Pedido "
                                + pedido.getIdPedido()
                                + " en camino hacia "
                                + pedido.getDireccionEntrega()
                );

                /*
                 * Genera una pausa aleatoria entre
                 * 1000 y 3000 milisegundo
                 */
                int pausaAleatoria = ThreadLocalRandom.current().nextInt(1000, 3001);
                Thread.sleep(pausaAleatoria);

                // DESPACHADO -> ENTREGADO
                pedido.marcarComoEntregado();
                System.out.print(
                        "[Repartidor: " + nombre
                                + "] Finalizo la entrega del pedido "
                                + pedido.getIdPedido()
                                + "."
                );

            } catch (InterruptedException e) {
                System.out.println("[Repartidor: " + nombre
                                            + "] Fue interrumpido mientras realizaba sus entregas."
                );

                // Se recupera el estado de interrupcion.
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println(
                "\n[Repartidor: " + nombre + "] Termino todas sus entregas.");
    }
}