package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Repartidor implements Runnable {

    private int id;
    private String nombre;
    private ZonaDeCarga zonaDeCarga;

    private List<Pedido> pedidosEntregados;

    public Repartidor(String nombre) {

        this.nombre = nombre;

        this.pedidosEntregados =
                new ArrayList<>();
    }

    public Repartidor(int id,
                      String nombre) {

        this.id = id;
        this.nombre = nombre;

        this.pedidosEntregados =
                new ArrayList<>();
    }

    public Repartidor(String nombre,
                      ZonaDeCarga zonaDeCarga) {

        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;

        this.pedidosEntregados =
                new ArrayList<>();
    }

    @Override
    public void run() {

        if (zonaDeCarga == null) {

            System.out.println(
                    "[ERROR] " + nombre
                            + " no tiene una zona de carga asignada."
            );

            return;
        }

        System.out.println(
                "\n[INICIO] " + nombre
                        + " comenzó a retirar pedidos."
        );

        while (true) {

            Pedido pedido =
                    zonaDeCarga.retirarPedido();

            if (pedido == null) {
                break;
            }

            try {
                pedido.asignarRepartidor(nombre);

                pedido.despachar();

                System.out.println(
                        "[EN REPARTO] " + nombre
                                + " está entregando el pedido "
                                + pedido.getIdPedido()
                                + " en "
                                + pedido.getDireccionEntrega()
                                + "."
                );

                int tiempoEspera =
                        ThreadLocalRandom.current().nextInt(
                                1000,
                                3001
                        );

                Thread.sleep(tiempoEspera);

                pedido.setEstado(
                        EstadoPedido.ENTREGADO
                );

                pedido.agregarAlHistorial(
                        "Pedido entregado por "
                                + nombre + "."
                );

                pedidosEntregados.add(pedido);

                System.out.println(
                        "[ENTREGADO] Pedido "
                                + pedido.getIdPedido()
                                + " entregado por "
                                + nombre + "."
                );

            } catch (InterruptedException e) {

                System.out.println(
                        "[ERROR] El trabajo de "
                                + nombre
                                + " fue interrumpido: "
                                + e.getMessage()
                );

                Thread.currentThread().interrupt();
                return;

            } catch (Exception e) {

                System.out.println(
                        "[ERROR] " + nombre
                                + " no pudo procesar el pedido "
                                + pedido.getIdPedido()
                                + ": "
                                + e.getMessage()
                );
            }
        }

        System.out.println(
                "[FIN] " + nombre
                        + " terminó su trabajo."
        );
    }

    @Override
    public String toString() {

        return "Repartidor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public ZonaDeCarga getZonaDeCarga() {
        return zonaDeCarga;
    }

    public List<Pedido> getPedidosEntregados() {
        return pedidosEntregados;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setZonaDeCarga(ZonaDeCarga zonaDeCarga) {
        this.zonaDeCarga = zonaDeCarga;
    }

    public void setPedidosEntregados(List<Pedido> pedidosEntregados) {
        this.pedidosEntregados = pedidosEntregados;
    }
}