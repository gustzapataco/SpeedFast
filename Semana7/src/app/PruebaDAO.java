package app;

import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import model.Entrega;
import model.Pedido;
import model.PedidoComida;
import model.Repartidor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PruebaDAO {

    public static void main(String[] args) {

        PedidoDAO pedidoDAO = new PedidoDAO();

        RepartidorDAO repartidorDAO =
                new RepartidorDAO();

        EntregaDAO entregaDAO =
                new EntregaDAO();

        Pedido pedido = new PedidoComida(
                "#010",
                "Av. Providencia 1500",
                7.5
        );

        boolean pedidoGuardado =
                pedidoDAO.guardar(pedido);

        System.out.println(
                "Pedido guardado: " + pedidoGuardado
        );

        Repartidor repartidor =
                new Repartidor(
                        "Ana Torres",
                        null
                );

        boolean repartidorGuardado =
                repartidorDAO.guardar(repartidor);

        System.out.println(
                "Repartidor guardado: "
                        + repartidorGuardado
        );

        if (pedidoGuardado && repartidorGuardado) {

            Entrega entrega = new Entrega(
                    pedido.getIdBaseDatos(),
                    repartidor.getId(),
                    LocalDate.now(),
                    LocalTime.now()
            );

            boolean entregaGuardada =
                    entregaDAO.guardar(entrega);

            System.out.println(
                    "Entrega guardada: "
                            + entregaGuardada
            );
        }

        System.out.println("\nPEDIDOS:");

        List<Pedido> pedidos =
                pedidoDAO.listarTodos();

        for (Pedido pedidoRegistrado : pedidos) {
            pedidoRegistrado.mostrarResumen();
            System.out.println();
        }

        System.out.println("REPARTIDORES:");

        List<Repartidor> repartidores =
                repartidorDAO.listarTodos();

        for (Repartidor repartidorRegistrado
                : repartidores) {

            System.out.println(
                    repartidorRegistrado.getId()
                            + " - "
                            + repartidorRegistrado.getNombre()
            );
        }

        System.out.println("\nENTREGAS:");

        List<Entrega> entregas =
                entregaDAO.listarTodas();

        for (Entrega entregaRegistrada : entregas) {
            System.out.println(entregaRegistrada);
        }
    }
}