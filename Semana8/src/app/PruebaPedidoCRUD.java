package app;

import dao.PedidoDAO;

import model.EstadoPedido;
import model.Pedido;
import model.PedidoComida;
import model.PedidoExpress;

import java.util.List;

public class PruebaPedidoCRUD {

    public static void main(String[] args) {

        try {
            PedidoDAO pedidoDAO =
                    new PedidoDAO();

            System.out.println(
                    "=================================="
            );

            System.out.println(
                    "      PRUEBA CRUD DE PEDIDOS"
            );

            System.out.println(
                    "=================================="
            );

            System.out.println(
                    "\n1. CREATE"
            );

            Pedido pedidoPrueba =
                    new PedidoComida(
                            0,
                            "DIRECCIÓN PRUEBA JAVA",
                            0
                    );

            boolean creado =
                    pedidoDAO.create(
                            pedidoPrueba
                    );

            if (!creado) {

                System.out.println(
                        "La prueba se detiene porque "
                                + "el pedido no fue creado."
                );

                return;
            }

            System.out.println(
                    "Pedido creado con ID: "
                            + pedidoPrueba.getIdPedido()
            );

            System.out.println(
                    "\n2. READ"
            );

            mostrarPedidos(
                    pedidoDAO.readAll()
            );

            System.out.println(
                    "\n3. UPDATE"
            );

            Pedido pedidoActualizado =
                    new PedidoExpress(
                            pedidoPrueba.getIdPedido(),
                            "DIRECCIÓN ACTUALIZADA JAVA",
                            0
                    );

            pedidoActualizado.setEstado(
                    EstadoPedido.EN_REPARTO
            );

            boolean actualizado =
                    pedidoDAO.update(
                            pedidoActualizado
                    );

            if (actualizado) {

                mostrarPedidos(
                        pedidoDAO.readAll()
                );
            }

            System.out.println(
                    "\n4. DELETE"
            );

            boolean eliminado =
                    pedidoDAO.delete(
                            pedidoPrueba.getIdPedido()
                    );

            if (eliminado) {

                System.out.println(
                        "\nListado después de eliminar:"
                );

                mostrarPedidos(
                        pedidoDAO.readAll()
                );
            }

            System.out.println(
                    "\n=================================="
            );

            System.out.println(
                    "Prueba CRUD finalizada."
            );

            System.out.println(
                    "=================================="
            );

        } catch (Exception e) {

            System.out.println(
                    "[ERROR] Ocurrió un problema "
                            + "durante la prueba: "
                            + e.getMessage()
            );
        }
    }

    private static void mostrarPedidos(
            List<Pedido> pedidos) {

        if (pedidos.isEmpty()) {

            System.out.println(
                    "No existen pedidos registrados."
            );

            return;
        }

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        System.out.printf(
                "%-6s %-32s %-14s %-14s%n",
                "ID",
                "DIRECCIÓN",
                "TIPO",
                "ESTADO"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        for (Pedido pedido : pedidos) {

            System.out.printf(
                    "%-6d %-32s %-14s %-14s%n",
                    pedido.getIdPedido(),
                    pedido.getDireccionEntrega(),
                    pedido.getTipoPedido(),
                    pedido.getEstado()
            );
        }

        System.out.println(
                "--------------------------------------------------------------------------"
        );
    }
}