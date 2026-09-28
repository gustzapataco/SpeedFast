package dao;

import model.Entrega;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {

    public boolean guardar(Entrega entrega) {

        String sql = """
                INSERT INTO entrega (
                    id_pedido,
                    id_repartidor,
                    fecha,
                    hora
                )
                VALUES (?, ?, ?, ?)
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

            sentencia.setInt(
                    1,
                    entrega.getIdPedido()
            );

            sentencia.setInt(
                    2,
                    entrega.getIdRepartidor()
            );

            sentencia.setDate(
                    3,
                    Date.valueOf(entrega.getFecha())
            );

            sentencia.setTime(
                    4,
                    Time.valueOf(entrega.getHora())
            );

            int filasInsertadas =
                    sentencia.executeUpdate();

            if (filasInsertadas > 0) {

                clavesGeneradas =
                        sentencia.getGeneratedKeys();

                if (clavesGeneradas.next()) {
                    entrega.setId(
                            clavesGeneradas.getInt(1)
                    );
                }

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al guardar la entrega: "
                            + e.getMessage()
            );

        } finally {

            ConexionBD.cerrar(clavesGeneradas);
            ConexionBD.cerrar(sentencia);
            ConexionBD.cerrar(conexion);
        }

        return false;
    }

    public List<Entrega> listarTodas() {

        List<Entrega> entregas =
                new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    id_pedido,
                    id_repartidor,
                    fecha,
                    hora
                FROM entrega
                ORDER BY fecha, hora
                """;

        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = ConexionBD.conectar();
            sentencia = conexion.prepareStatement(sql);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {

                Entrega entrega = new Entrega(
                        resultado.getInt("id"),
                        resultado.getInt("id_pedido"),
                        resultado.getInt(
                                "id_repartidor"
                        ),
                        resultado.getDate("fecha")
                                .toLocalDate(),
                        resultado.getTime("hora")
                                .toLocalTime()
                );

                entregas.add(entrega);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar las entregas: "
                            + e.getMessage()
            );

        } finally {

            ConexionBD.cerrar(resultado);
            ConexionBD.cerrar(sentencia);
            ConexionBD.cerrar(conexion);
        }

        return entregas;
    }
}
