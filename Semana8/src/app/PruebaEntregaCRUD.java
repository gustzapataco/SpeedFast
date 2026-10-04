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

public class PruebaEntregaCRUD {

    public static void main(String[] args) {

        RepartidorDAO repartidorDAO =
                new RepartidorDAO();

        PedidoDAO pedidoDAO =
                new PedidoDAO();

        EntregaDAO entregaDAO =
                new EntregaDAO();

        Repartidor repartidorPrueba = null;
        Pedido pedidoPrueba = null;
        Entrega entregaPrueba = null;

        try {
            System.out.println(
                    "=================================="
            );

            System.out.println(
                    "      PRUEBA CRUD DE ENTREGAS"
            );

            System.out.println(
                    "=================================="
            );

            System.out.println(
                    "\nCREANDO DATOS RELACIONADOS"
            );

            repartidorPrueba =
                    new Repartidor(
                            "REPARTIDOR PRUEBA ENTREGA"
                    );

            boolean repartidorCreado =
                    repartidorDAO.create(
                            repartidorPrueba
                    );

            pedidoPrueba =
                    new PedidoComida(
                            0,
                            "DIRECCIÓN PRUEBA ENTREGA",
                            0
                    );

            boolean pedidoCreado =
                    pedidoDAO.create(
                            pedidoPrueba
                    );

            if (!repartidorCreado
                    || !pedidoCreado) {

                System.out.println(
                        "[ERROR] No fue posible crear "
                                + "los datos relacionados."
                );

                return;
            }

            System.out.println(
                    "\n1. CREATE"
            );

            entregaPrueba =
                    new Entrega(
                            pedidoPrueba.getIdPedido(),
                            repartidorPrueba.getId(),
                            LocalDate.now(),
                            LocalTime.now()
                                    .withNano(0)
                    );

            boolean entregaCreada =
                    entregaDAO.create(
                            entregaPrueba
                    );

            if (!entregaCreada) {

                System.out.println(
                        "La prueba se detiene porque "
                                + "la entrega no fue creada."
                );

                return;
            }

            System.out.println(
                    "Entrega creada:"
            );

            System.out.println(
                    entregaPrueba
            );

            System.out.println(
                    "\n2. READ"
            );

            mostrarEntregas(
                    entregaDAO.readAll()
            );

            System.out.println(
                    "\n3. UPDATE"
            );

            entregaPrueba.setFecha(
                    LocalDate.now().plusDays(1)
            );

            entregaPrueba.setHora(
                    LocalTime.of(12, 30)
            );

            boolean actualizada =
                    entregaDAO.update(
                            entregaPrueba
                    );

            if (actualizada) {

                mostrarEntregas(
                        entregaDAO.readAll()
                );
            }

            System.out.println(
                    "\n4. DELETE"
            );

            boolean eliminada =
                    entregaDAO.delete(
                            entregaPrueba.getId()
                    );

            if (eliminada) {

                entregaPrueba.setId(0);

                System.out.println(
                        "\nListado después de eliminar:"
                );

                mostrarEntregas(
                        entregaDAO.readAll()
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "[ERROR] Ocurrió un problema "
                            + "durante la prueba: "
                            + e.getMessage()
            );

        } finally {

            System.out.println(
                    "\nLIMPIEZA DE DATOS DE PRUEBA"
            );

            if (entregaPrueba != null
                    && entregaPrueba.getId() > 0) {

                entregaDAO.delete(
                        entregaPrueba.getId()
                );
            }

            if (pedidoPrueba != null
                    && pedidoPrueba.getIdPedido() > 0) {

                pedidoDAO.delete(
                        pedidoPrueba.getIdPedido()
                );
            }

            if (repartidorPrueba != null
                    && repartidorPrueba.getId() > 0) {

                repartidorDAO.delete(
                        repartidorPrueba.getId()
                );
            }

            System.out.println(
                    "Prueba CRUD de entregas finalizada."
            );
        }
    }

    private static void mostrarEntregas(
            List<Entrega> entregas) {

        if (entregas.isEmpty()) {

            System.out.println(
                    "No existen entregas registradas."
            );

            return;
        }

        System.out.println(
                "---------------------------------------------------------------------"
        );

        System.out.printf(
                "%-6s %-12s %-16s %-14s %-12s%n",
                "ID",
                "PEDIDO",
                "REPARTIDOR",
                "FECHA",
                "HORA"
        );

        System.out.println(
                "---------------------------------------------------------------------"
        );

        for (Entrega entrega : entregas) {

            System.out.printf(
                    "%-6d %-12d %-16d %-14s %-12s%n",
                    entrega.getId(),
                    entrega.getIdPedido(),
                    entrega.getIdRepartidor(),
                    entrega.getFecha(),
                    entrega.getHora()
            );
        }

        System.out.println(
                "---------------------------------------------------------------------"
        );
    }
}