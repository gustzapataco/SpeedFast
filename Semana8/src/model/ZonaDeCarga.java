package model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ZonaDeCarga {

    private BlockingQueue<Pedido> pedidosPendientes;

    public ZonaDeCarga() {
        this.pedidosPendientes =
                new LinkedBlockingQueue<>();
    }

    public synchronized void agregarPedido(Pedido pedido) {

        boolean agregado =
                pedidosPendientes.offer(pedido);

        if (agregado) {

            System.out.println(
                    "[ZONA DE CARGA] Pedido "
                            + pedido.getIdPedido()
                            + " agregado como PENDIENTE."
            );

        } else {

            System.out.println(
                    "[ERROR] No fue posible agregar el pedido "
                            + pedido.getIdPedido() + "."
            );
        }
    }

    public synchronized Pedido retirarPedido() {

        Pedido pedido =
                pedidosPendientes.poll();

        if (pedido != null) {

            System.out.println(
                    "[ZONA DE CARGA] Pedido "
                            + pedido.getIdPedido()
                            + " retirado de forma segura."
            );
        }

        return pedido;
    }

    public synchronized boolean estaVacia() {
        return pedidosPendientes.isEmpty();
    }

    public synchronized int cantidadPedidos() {
        return pedidosPendientes.size();
    }
}