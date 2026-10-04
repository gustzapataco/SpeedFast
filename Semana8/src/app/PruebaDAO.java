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

        try {
            RepartidorDAO repartidorDAO =
                    new RepartidorDAO();

            PedidoDAO pedidoDAO =
                    new PedidoDAO();

            EntregaDAO entregaDAO =
                    new EntregaDAO();

            System.out.println(
                    "=================================="
            );

            System.out.println(
                    "       PRUEBA DE LOS DAO"
            );

            System.out.println(
                    "=================================="
            );

            Repartidor repartidor =
                    new Repartidor("Camila");

            boolean repartidorGuardado =
                    repartidorDAO.create(repartidor);

            Pedido pedido =
                    new PedidoComida(
                            0,
                            "Av. Providencia 1200",
                            5
                    );

            boolean pedidoGuardado =
                    pedidoDAO.create(pedido);

            if (repartidorGuardado
                    && pedidoGuardado) {

                Entrega entrega =
                        new Entrega(
                                pedido.getIdPedido(),
                                repartidor.getId(),
                                LocalDate.now(),
                                LocalTime.now()
                                        .withNano(0)
                        );

                entregaDAO.create(entrega);
            }

            System.out.println(
                    "\n--- REPARTIDORES REGISTRADOS ---"
            );

            List<Repartidor> repartidores =
                    repartidorDAO.readAll();

            if (repartidores.isEmpty()) {

                System.out.println(
                        "No existen repartidores registrados."
                );

            } else {

                for (Repartidor registrado
                        : repartidores) {

                    System.out.println(registrado);
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "[ERROR] Ocurrió un problema "
                            + "durante la prueba: "
                            + e.getMessage()
            );
        }
    }
}