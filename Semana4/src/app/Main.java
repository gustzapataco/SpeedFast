package app;

import model.Pedido;
import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;
import model.Repartidor;
import service.ControladorDeEnvios;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        ControladorDeEnvios controlador = new ControladorDeEnvios();

        /*
         * Coleccion Polimorfica: contiene diferentes subclases de Pedido.
         */
        List<Pedido> pedidos = new ArrayList<>();

        Pedido pedido1 =
                new PedidoComida("#001", "Av. Providencia 1200", 5);
        Pedido pedido2 =
                new PedidoEncomienda("#002", "Av. Apoquindo 3500", 10);
        Pedido pedido3 =
                new PedidoExpress("#003", "Av. Irarrazaval 2500", 8);
        Pedido pedido4 =
                new PedidoComida("#004", "Av. Grecia 1450", 6);
        Pedido pedido5 =
                new PedidoExpress("#005", "Av. Matta 850", 7);
        Pedido pedido6 =
                new PedidoComida("#006", "Av. Las Condes 9000", 12);

        pedidos.add(pedido1);
        pedidos.add(pedido2);
        pedidos.add(pedido3);
        pedidos.add(pedido4);
        pedidos.add(pedido5);
        pedidos.add(pedido6);

        /*
         * Registro de los pedidos en el controlador.
         */
        for (Pedido pedido : pedidos) {
            controlador.agregarPedido(pedido);
        }

        /*
         * Se crean tres listas.
         * Cada repartidor recibe dos pedidos.
         */
        List<Pedido> pedidosCamila = new ArrayList<>();
        pedidosCamila.add(pedido1);
        pedidosCamila.add(pedido2);

        List<Pedido> pedidosLuis = new ArrayList<>();
        pedidosLuis.add(pedido3);
        pedidosLuis.add(pedido4);

        List<Pedido> pedidosDaniela = new ArrayList<>();
        pedidosDaniela.add(pedido5);
        pedidosDaniela.add(pedido6);

        /*
         * Cada repartidor representa una tarea Runnable.
         */
        Repartidor camila = new Repartidor("Camila", pedidosCamila);
        Repartidor luis = new Repartidor("Luis", pedidosLuis);
        Repartidor daniela = new Repartidor("Daniela", pedidosDaniela);

        // Se crea un grupo con tres hilos.
        ExecutorService executor = Executors.newFixedThreadPool(5);
        System.out.println("\\n======================================");
        System.out.println("   INICIO DE ENTREGAS SPEEDFAST");
        System.out.println("======================================");

        /*
         * PLos tres repartidores comienzan a trabajar de forma concurrente.
         */
        executor.submit(camila);
        executor.submit(luis);
        executor.submit(daniela);

        /*
         * No se aceptan nuevas tareas.
         * Las tareas actuales continuan ejecutandose.
         */
        executor.shutdown();

        try {
            while (!executor.awaitTermination(
                    1,
                    TimeUnit.SECONDS
            )) {
                System.out.println(
                        "Esperando que los repartidores "
                                + "terminen sus entregas..."
                );
            }

            System.out.println(
                    "\n======================================"
            );
            System.out.println(
                    " TODOS LOS REPARTIDORES TERMINARON"
            );
            System.out.println(
                    "======================================"
            );

        } catch (InterruptedException e) {

            System.out.println(
                    "El programa principal fue interrumpido."
            );

            executor.shutdownNow();
            Thread.currentThread().interrupt();

            return;
        }

        /*
         * Despues de finalizar los hilos, se registran los pedidos que quedaron como ENTREGADOS.
         */
        for (Pedido pedido : pedidos) {
            controlador.agregarPedido(pedido);
        }
        // Se muestran los resultados finales.
        controlador.mostrarPedidos();
        controlador.mostrarHistorialEntregas();
        // Se muestra el historial individual de cada pedido.
        for (Pedido pedido : pedidos) {
            pedido.verHistorial();
        }
    }
}