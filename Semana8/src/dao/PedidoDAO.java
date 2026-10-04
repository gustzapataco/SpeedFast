package dao;

import model.EstadoPedido;
import model.Pedido;
import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public boolean create(Pedido pedido) {

        String sql = """
                INSERT INTO pedidos (
                    direccion,
                    tipo,
                    estado
                )
                VALUES (?, ?, ?)
                """;

        try (
                Connection conexion =
                        ConexionDB.conectar();

                PreparedStatement sentencia =
                        conexion.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {
            completarParametros(
                    sentencia,
                    pedido
            );

            int filasAfectadas =
                    sentencia.executeUpdate();

            if (filasAfectadas > 0) {

                try (
                        ResultSet clavesGeneradas =
                                sentencia.getGeneratedKeys()
                ) {
                    if (clavesGeneradas.next()) {

                        int idGenerado =
                                clavesGeneradas.getInt(1);

                        pedido.setIdPedido(
                                idGenerado
                        );
                    }
                }

                System.out.println(
                        "[ÉXITO] Pedido creado con ID "
                                + pedido.getIdPedido() + "."
                );

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "[ERROR] No fue posible crear el pedido."
            );

            mostrarErrorSQL(e);
        }

        return false;
    }

    public List<Pedido> readAll() {

        List<Pedido> pedidos =
                new ArrayList<>();

        String sql = """
                SELECT id,
                       direccion,
                       tipo,
                       estado
                FROM pedidos
                ORDER BY id
                """;

        try (
                Connection conexion =
                        ConexionDB.conectar();

                PreparedStatement sentencia =
                        conexion.prepareStatement(sql);

                ResultSet resultado =
                        sentencia.executeQuery()
        ) {
            while (resultado.next()) {

                int id =
                        resultado.getInt("id");

                String direccion =
                        resultado.getString("direccion");

                String tipo =
                        resultado.getString("tipo");

                String estado =
                        resultado.getString("estado");

                Pedido pedido =
                        crearPedidoPorTipo(
                                id,
                                direccion,
                                tipo
                        );

                if (pedido != null) {

                    pedido.setEstado(
                            EstadoPedido.valueOf(estado)
                    );

                    pedidos.add(pedido);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "[ERROR] No fue posible consultar "
                            + "los pedidos."
            );

            mostrarErrorSQL(e);

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "[ERROR] MySQL contiene un tipo "
                            + "o estado no reconocido."
            );

            System.out.println(
                    "Detalle: " + e.getMessage()
            );
        }

        return pedidos;
    }

    public boolean update(Pedido pedido) {

        String sql = """
                UPDATE pedidos
                SET direccion = ?,
                    tipo = ?,
                    estado = ?
                WHERE id = ?
                """;

        try (
                Connection conexion =
                        ConexionDB.conectar();

                PreparedStatement sentencia =
                        conexion.prepareStatement(sql)
        ) {
            completarParametros(
                    sentencia,
                    pedido
            );

            sentencia.setInt(
                    4,
                    pedido.getIdPedido()
            );

            int filasAfectadas =
                    sentencia.executeUpdate();

            if (filasAfectadas > 0) {

                System.out.println(
                        "[ÉXITO] Pedido actualizado."
                );

                return true;
            }

            System.out.println(
                    "[ERROR] No existe un pedido "
                            + "con el ID indicado."
            );

        } catch (SQLException e) {

            System.out.println(
                    "[ERROR] No fue posible actualizar "
                            + "el pedido."
            );

            mostrarErrorSQL(e);
        }

        return false;
    }

    public boolean delete(int id) {

        String sql = """
                DELETE FROM pedidos
                WHERE id = ?
                """;

        try (
                Connection conexion =
                        ConexionDB.conectar();

                PreparedStatement sentencia =
                        conexion.prepareStatement(sql)
        ) {
            sentencia.setInt(1, id);

            int filasAfectadas =
                    sentencia.executeUpdate();

            if (filasAfectadas > 0) {

                System.out.println(
                        "[ÉXITO] Pedido eliminado."
                );

                return true;
            }

            System.out.println(
                    "[ERROR] No existe un pedido "
                            + "con el ID indicado."
            );

        } catch (SQLException e) {

            System.out.println(
                    "[ERROR] No fue posible eliminar el pedido."
            );

            System.out.println(
                    "Puede tener entregas asociadas."
            );

            mostrarErrorSQL(e);
        }

        return false;
    }

    private void completarParametros(
            PreparedStatement sentencia,
            Pedido pedido)
            throws SQLException {

        sentencia.setString(
                1,
                pedido.getDireccionEntrega()
        );

        sentencia.setString(
                2,
                pedido.getTipoPedido()
        );

        sentencia.setString(
                3,
                pedido.getEstado().name()
        );
    }

    private Pedido crearPedidoPorTipo(
            int id,
            String direccion,
            String tipo) {

        switch (tipo) {

            case "COMIDA":

                return new PedidoComida(
                        id,
                        direccion,
                        0
                );

            case "ENCOMIENDA":

                return new PedidoEncomienda(
                        id,
                        direccion,
                        0
                );

            case "EXPRESS":

                return new PedidoExpress(
                        id,
                        direccion,
                        0
                );

            default:

                System.out.println(
                        "[ERROR] Tipo de pedido desconocido: "
                                + tipo
                );

                return null;
        }
    }

    private void mostrarErrorSQL(
            SQLException e) {

        System.out.println(
                "Mensaje: " + e.getMessage()
        );

        System.out.println(
                "Código SQL: " + e.getErrorCode()
        );

        System.out.println(
                "Estado SQL: " + e.getSQLState()
        );
    }
}