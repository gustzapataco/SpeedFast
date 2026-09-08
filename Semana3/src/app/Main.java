package app;

import model.Pedido;
import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;
import service.ControladorDeEnvios;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ControladorDeEnvios controlador = new ControladorDeEnvios();

        /*
         * Coleccion Polimorfica: contiene diferentes subclases de Pedido.
         */
        ArrayList<Pedido> pedidos = new ArrayList<>();

        Pedido comida =
                new PedidoComida("#001", "Av. Providencia 1200", 5);
        Pedido encomienda =
                new PedidoEncomienda("#002", "Av. Apoquindo 3500", 10);
        Pedido express =
                new PedidoExpress("#003", "Av. Irarrazaval 2500", 8);

        pedidos.add(comida);
        pedidos.add(encomienda);
        pedidos.add(express);

        /*
         * Registro de los pedidos en el controlador.
         */
        for (Pedido pedido : pedidos) {
            controlador.agregarPedido(pedido);
        }

        System.out.println("\n========= ASIGNACIONES Y TIEMPOS =========");

        /*
         * Pedido de Comida: asignacion automatica.
         */
        System.out.println("[PEDIDO DE COMIDA]");
        comida.reservarPedido();
        comida.asignarRepartidor();
        comida.mostrarResumen();

        /*
         * Pedido de Encomienda: asignacion manual mediante sobrecarga.
         */
        System.out.println("\n[PEDIDO DE ENCOMIENDA]");
        encomienda.reservarPedido();
        encomienda.asignarRepartidor();
        encomienda.mostrarResumen();

        /*
         * Pedido Express: asignacion automatica.
         */
        System.out.println("\n[PEDIDO EXPRESS]");
        express.reservarPedido();
        express.asignarRepartidor();
        express.mostrarResumen();

        /*
         * Despacho y entrega de comida.
         */
        System.out.println("\n========= DESPACHO DE COMIDA =========");
        comida.despachar();
        comida.marcarComoEntregado();
        controlador.registrarEntrega(comida);

        /*
         * Despacho y entrega de encomienda.
         */
        System.out.println("\n========= DESPACHO DE ENCOMIENDA =========");
        encomienda.despachar();
        encomienda.marcarComoEntregado();
        controlador.registrarEntrega(encomienda);

        /*
         * Cancelacion del pedido express.
         */
        System.out.println("\n========= CANCELACION EXPRESS =========");
        express.cancelar();

        /*
         * Mostrar todos los pedidos.
         */
        controlador.mostrarPedidos();

        /*
         * Mostrar historial general de entregas.
         */
        controlador.mostrarHistorialEntregas();

        /*
         * Mostrar historial individual.
         */
        comida.verHistorial();
        encomienda.verHistorial();
        express.verHistorial();
    }
}