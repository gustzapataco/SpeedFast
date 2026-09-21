package model;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;

public class Repartidor implements Runnable {

    private String nombre;
    private final List<Pedido> pedidosAsignados;
    private final Runnable accionAlFinalizar;

    public Repartidor(String nombre, Runnable accionAlFinalizar) {
        this.nombre = nombre;
        this.pedidosAsignados = new ArrayList<>();
        this.accionAlFinalizar = accionAlFinalizar;
    }

    public synchronized void asignarPedido(Pedido pedido) {
        pedidosAsignados.add(pedido);
        pedido.setNombreRepartidor(nombre);
    }

    @Override
    public void run() {
        for (Pedido pedido : pedidosAsignados) {
            try {
                pedido.despachar();

                actualizarInterfaz();

                System.out.println(
                        nombre + " inició el pedido "
                                + pedido.getIdPedido()
                );

                Thread.sleep(3000);

                pedido.setEstado(EstadoPedido.ENTREGADO);

                System.out.println(
                        nombre + " entregó el pedido "
                                + pedido.getIdPedido()
                );

                actualizarInterfaz();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                System.out.println(
                        "Entrega interrumpida: "
                                + pedido.getIdPedido()
                );

                return;
            }
        }
    }

    private void actualizarInterfaz() {
        if (accionAlFinalizar != null) {
            SwingUtilities.invokeLater(accionAlFinalizar);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public List<Pedido> getPedidosAsignados() {
        return new ArrayList<>(pedidosAsignados);
    }
}