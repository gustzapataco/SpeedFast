package model;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;

public class Repartidor implements Runnable {

    private int id;
    private String nombre;
    private final List<Pedido> pedidosAsignados;
    private final Runnable accionAlFinalizar;

    /*
     * Constructor para crear un repartidor nuevo.
     * MySQL todavía no ha generado su ID.
     */
    public Repartidor(
            String nombre,
            Runnable accionAlFinalizar
    ) {
        this.id = 0;
        this.nombre = nombre;
        this.pedidosAsignados = new ArrayList<>();
        this.accionAlFinalizar = accionAlFinalizar;
    }

    /*
     * Constructor para reconstruir
     * un repartidor recuperado desde MySQL.
     */
    public Repartidor(
            int id,
            String nombre,
            Runnable accionAlFinalizar
    ) {
        this.id = id;
        this.nombre = nombre;
        this.pedidosAsignados = new ArrayList<>();
        this.accionAlFinalizar = accionAlFinalizar;
    }

    public synchronized void asignarPedido(
            Pedido pedido
    ) {
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
                        nombre
                                + " inició el pedido "
                                + pedido.getIdPedido()
                );

                Thread.sleep(3000);

                pedido.setEstado(
                        EstadoPedido.ENTREGADO
                );

                System.out.println(
                        nombre
                                + " entregó el pedido "
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
            SwingUtilities.invokeLater(
                    accionAlFinalizar
            );
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pedido> getPedidosAsignados() {
        return new ArrayList<>(
                pedidosAsignados
        );
    }

    @Override
    public String toString() {
        return nombre;
    }
}