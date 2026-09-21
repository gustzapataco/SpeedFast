package controller;

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

    public synchronized boolean agregarPedido(Pedido pedido) {
        if (pedido == null || existePedido(pedido.getIdPedido())) {
            return false;
        }

        pedidos.offer(pedido);
        return true;
    }

    public synchronized boolean existePedido(String idPedido) {
        for (Pedido pedido : pedidos) {
            if (pedido.getIdPedido().equalsIgnoreCase(idPedido)) {
                return true;
            }
        }

        return false;
    }

    public synchronized List<Pedido> obtenerPedidos() {
        return new ArrayList<>(pedidos);
    }

    public synchronized Pedido buscarPedido(String idPedido) {
        for (Pedido pedido : pedidos) {
            if (pedido.getIdPedido().equalsIgnoreCase(idPedido)) {
                return pedido;
            }
        }

        return null;
    }

    public synchronized int cantidadPedidos() {
        return pedidos.size();
    }
}