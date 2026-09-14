package service;

import model.EstadoPedido;
import model.Pedido;

public class Repartidor implements Runnable {

    // Atributos
    private String nombre;
    private ZonaDeCarga zonaDeCarga;

    // Constructor
    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    // Getters n' Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ZonaDeCarga getZonaDeCarga() {
        return zonaDeCarga;
    }

    public void setZonaDeCarga(ZonaDeCarga zonaDeCarga) {
        this.zonaDeCarga = zonaDeCarga;
    }

    @Override
    public void run() {
        Pedido pedido;
        while ((pedido = zonaDeCarga.retirarPedido()) != null) {
            System.out.println(
                    "\n[Repartidor - " + nombre + "] Retirando pedido #"
                    + pedido.getId() + "..."
            );
            System.out.println(
                    "[Repartidor - " + nombre + "] Estado: "
                    + pedido.getEstado()
            );
            System.out.println(
                    "[Repartidor - " + nombre + "] Entregando pedido #"
                    + pedido.getId() + "..."
            );

            try {
                int tiempoEntrega = (int) (Math.random() * 2000) + 1000;
                Thread.sleep(tiempoEntrega);
            } catch (InterruptedException e) {
                System.out.println(
                        "[Repartidor - " + nombre + "] La entrega fue interrumpida."
                );

                return;
            }

            pedido.setEstado(EstadoPedido.ENTREGADO);

            System.out.println(
                    "[Repartidor - " + nombre + "] Pedido #"
                    + pedido.getId() + "entregado."
            );

            System.out.println(
                    "[Repartidor - " + nombre + "] Estado: "
                    + pedido.getEstado()
            );
        }
    }
}