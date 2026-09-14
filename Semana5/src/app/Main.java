package app;

import model.Pedido;
import service.Repartidor;
import service.ZonaDeCarga;

public class Main {

    public static void main(String[] args) {
        System.out.println("[Zona de carga inicializada]\n");
        ZonaDeCarga zonaDeCarga = new ZonaDeCarga();
        zonaDeCarga.agregarPedido(
                new Pedido(1, "Providencia")
        );
        zonaDeCarga.agregarPedido(
                new Pedido(2, "Las Condes")
        );
        zonaDeCarga.agregarPedido(
                new Pedido(3, "Nunoa")
        );
        zonaDeCarga.agregarPedido(
                new Pedido(4, "Santiago Centro")
        );
        zonaDeCarga.agregarPedido(
                new Pedido(5, "Recoleta")
        );

        Repartidor repartidor1 = new Repartidor(
                "Juan", zonaDeCarga);
        Repartidor repartidor2 = new Repartidor(
                "Maria", zonaDeCarga);
        Repartidor repartidor3 = new Repartidor(
                "Pedro", zonaDeCarga);

        Thread hilo1 = new Thread(repartidor1);
        Thread hilo2 = new Thread(repartidor2);
        Thread hilo3 = new Thread(repartidor3);

        hilo1.start();
        hilo2.start();
        hilo3.start();

        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();

        } catch (InterruptedException e) {
            System.out.println("El proceso principal tuvo una interrupcion.");
            return;
        }

        System.out.println("[Zona de carga vacia]");
        System.out.println("Todos lso pedidos han sido entregados correctamente.");
    }
}