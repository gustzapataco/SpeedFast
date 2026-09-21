package controller;

import java.util.ArrayList;
import model.EstadoPedido;
import model.Pedido;

public class ZonaDeCarga {
    private final ArrayList<Pedido> pedidos;
    public ZonaDeCarga() {
        pedidos = new ArrayList<>();
    }

    public synchronized void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);

        System.out.println(
                "Pedido #" + pedido.getId()
                        + " agregado. Destino: "
                        + pedido.getDireccionEntrega()
        );
    }

    public synchronized Pedido retirarPedido() {
        if (pedidos.isEmpty()) {
            return null;
        }

        Pedido pedido = pedidos.remove(0);
        pedido.setEstado(EstadoPedido.EN_REPARTO);
        return pedido;
        }
    }