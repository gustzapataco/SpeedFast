package service;

import interfaces.Cancelable;
import model.EstadoPedido;
import model.Pedido;
import java.util.ArrayList;

public class ControladorDeEnvios {
    private ArrayList<Pedido> pedidos;
    private ArrayList<Pedido> historialEntregas;
    public ControladorDeEnvios() {
        pedidos = new ArrayList<>();
        historialEntregas = new ArrayList<>();
    }
    public void agregarPedido(Pedido pedido) {
        if (pedido == null) {
            System.out.println("No se puede agregar un pedido nulo.");
            return;
        }

        pedidos.add(pedido);
        System.out.println("Pedido " + pedido.getIdPedido() + " agregado al sistema.");
    }
    public void registrarEntrega(Pedido pedido) {
        if (pedido.getEstado() != EstadoPedido.ENTREGADO) {
            System.out.println("El pedido " + pedido.getIdPedido() + " todavia no ha sido entregado.");
            return;
        }
        if (historialEntregas.contains(pedido)) {
            System.out.println("La entrega ya se encuentra registrada.");
            return;
        }
        historialEntregas.add(pedido);
        System.out.println("Entrega del pedido " + pedido.getIdPedido() + " registrada en el historial.");
    }
    public void mostrarPedidos() {
        System.out.println("\n======= PEDIDOS =======");
        for (Pedido pedido : pedidos) {
            System.out.println();
            pedido.mostrarResumen();
        }
    }
    public void mostrarHistorialEntregas() {
        System.out.println("\n======= HISTORIAL ENTREGAS =======");
        if (historialEntregas.isEmpty()) {
            System.out.println("No existen entregas registradas.");
            return;
        }
        for (Pedido pedido : historialEntregas) {
            System.out.println("Pedido: " + pedido.getIdPedido());
            System.out.println("Direccion: " + pedido.getDireccionEntrega());
            System.out.println("Repartidor: " + pedido.getNombreRepartidor());
            System.out.println("------------------------");
        }
    }
}