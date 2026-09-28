package controller;

import model.EstadoPedido;
import model.Pedido;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GestorPedidos {

    private static final GestorPedidos INSTANCIA =
            new GestorPedidos();

    private final Queue<Pedido> pedidos;

    private GestorPedidos() {
        pedidos = new LinkedList<>();
    }

    public static GestorPedidos getInstancia() {
        return INSTANCIA;
    }

    public synchronized boolean agregarPedido(
            Pedido pedido) {

        if (pedido == null) {
            return false;
        }

        if (existePedido(pedido.getIdPedido())) {
            return false;
        }

        return pedidos.offer(pedido);
    }

    public synchronized boolean existePedido(
            String idPedido) {

        if (idPedido == null) {
            return false;
        }

        for (Pedido pedido : pedidos) {

            if (pedido.getIdPedido()
                    .equalsIgnoreCase(idPedido)) {

                return true;
            }
        }

        return false;
    }

    public synchronized Pedido buscarPedido(
            String idPedido) {

        if (idPedido == null) {
            return null;
        }

        for (Pedido pedido : pedidos) {

            if (pedido.getIdPedido()
                    .equalsIgnoreCase(idPedido)) {

                return pedido;
            }
        }

        return null;
    }

    public synchronized Pedido
    obtenerPrimerPedidoPendiente() {

        for (Pedido pedido : pedidos) {

            if (pedido.getEstado()
                    == EstadoPedido.PENDIENTE) {

                return pedido;
            }
        }

        return null;
    }

    public synchronized List<Pedido>
    obtenerPedidos() {

        return new ArrayList<>(pedidos);
    }

    public synchronized int cantidadPedidos() {
        return pedidos.size();
    }
}