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

    public boolean guardar(Pedido pedido) {

        String sql = """
                INSERT INTO pedido (
                    codigo_pedido,
                    direccion,
                    distancia_km,
                    tipo,
                    estado
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet clavesGeneradas = null;

        try {
            conexion = ConexionBD.conectar();

            sentencia = conexion.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            sentencia.setString(
                    1,
                    pedido.getIdPedido()
            );

            sentencia.setString(
                    2,
                    pedido.getDireccionEntrega()
            );

            sentencia.setDouble(
                    3,
                    pedido.getDistanciaKm()
            );

            sentencia.setString(
                    4,
                    pedido.getTipoPedido().toUpperCase()
            );

            sentencia.setString(
                    5,
                    pedido.getEstado().name()
            );

            int filasInsertadas = sentencia.executeUpdate();

            if (filasInsertadas > 0) {

                clavesGeneradas =
                        sentencia.getGeneratedKeys();

                if (clavesGeneradas.next()) {
                    pedido.setIdBaseDatos(
                            clavesGeneradas.getInt(1)
                    );
                }

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al guardar el pedido: "
                            + e.getMessage()
            );

        } finally {

            ConexionBD.cerrar(clavesGeneradas);
            ConexionBD.cerrar(sentencia);
            ConexionBD.cerrar(conexion);
        }

        return false;
    }

    public List<Pedido> listarTodos() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    codigo_pedido,
                    direccion,
                    distancia_km,
                    tipo,
                    estado
                FROM pedido
                ORDER BY id
                """;

        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = ConexionBD.conectar();
            sentencia = conexion.prepareStatement(sql);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {

                int idBaseDatos =
                        resultado.getInt("id");

                String codigo =
                        resultado.getString(
                                "codigo_pedido"
                        );

                String direccion =
                        resultado.getString(
                                "direccion"
                        );

                double distancia =
                        resultado.getDouble(
                                "distancia_km"
                        );

                String tipo =
                        resultado.getString("tipo");

                String estado =
                        resultado.getString("estado");

                Pedido pedido = crearPedidoPorTipo(
                        tipo,
                        codigo,
                        direccion,
                        distancia
                );

                pedido.setIdBaseDatos(idBaseDatos);

                pedido.setEstado(
                        EstadoPedido.valueOf(estado)
                );

                pedidos.add(pedido);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar los pedidos: "
                            + e.getMessage()
            );

        } finally {

            ConexionBD.cerrar(resultado);
            ConexionBD.cerrar(sentencia);
            ConexionBD.cerrar(conexion);
        }

        return pedidos;
    }

    public Pedido buscarPorCodigo(String codigoPedido) {

        String sql = """
                SELECT
                    id,
                    codigo_pedido,
                    direccion,
                    distancia_km,
                    tipo,
                    estado
                FROM pedido
                WHERE codigo_pedido = ?
                """;

        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = ConexionBD.conectar();
            sentencia = conexion.prepareStatement(sql);

            sentencia.setString(
                    1,
                    codigoPedido
            );

            resultado = sentencia.executeQuery();

            if (resultado.next()) {

                String tipo =
                        resultado.getString("tipo");

                Pedido pedido = crearPedidoPorTipo(
                        tipo,
                        resultado.getString(
                                "codigo_pedido"
                        ),
                        resultado.getString(
                                "direccion"
                        ),
                        resultado.getDouble(
                                "distancia_km"
                        )
                );

                pedido.setIdBaseDatos(
                        resultado.getInt("id")
                );

                pedido.setEstado(
                        EstadoPedido.valueOf(
                                resultado.getString("estado")
                        )
                );

                return pedido;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar el pedido: "
                            + e.getMessage()
            );

        } finally {

            ConexionBD.cerrar(resultado);
            ConexionBD.cerrar(sentencia);
            ConexionBD.cerrar(conexion);
        }

        return null;
    }

    public boolean existeCodigo(String codigoPedido) {

        String sql = """
                SELECT COUNT(*) AS cantidad
                FROM pedido
                WHERE codigo_pedido = ?
                """;

        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = ConexionBD.conectar();
            sentencia = conexion.prepareStatement(sql);

            sentencia.setString(
                    1,
                    codigoPedido
            );

            resultado = sentencia.executeQuery();

            if (resultado.next()) {
                return resultado.getInt(
                        "cantidad"
                ) > 0;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al comprobar el pedido: "
                            + e.getMessage()
            );

        } finally {

            ConexionBD.cerrar(resultado);
            ConexionBD.cerrar(sentencia);
            ConexionBD.cerrar(conexion);
        }

        return false;
    }

    private Pedido crearPedidoPorTipo(
            String tipo,
            String codigo,
            String direccion,
            double distancia
    ) {

        return switch (tipo.toUpperCase()) {

            case "COMIDA" ->
                    new PedidoComida(
                            codigo,
                            direccion,
                            distancia
                    );

            case "ENCOMIENDA" ->
                    new PedidoEncomienda(
                            codigo,
                            direccion,
                            distancia
                    );

            case "EXPRESS" ->
                    new PedidoExpress(
                            codigo,
                            direccion,
                            distancia
                    );

            default ->
                    throw new IllegalArgumentException(
                            "Tipo de pedido desconocido: "
                                    + tipo
                    );
        };
    }
}