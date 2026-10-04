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

import java.time.LocalDate;
import java.time.LocalTime;

public class EntregaDAO {

    public boolean create(Entrega entrega) {

        String sql = """
                INSERT INTO entregas (
                    id_pedido,
                    id_repartidor,
                    fecha,
                    hora
                )
                VALUES (?, ?, ?, ?)
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
                    entrega
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
                        entrega.setId(idGenerado);
                    }
                }

                System.out.println(
                        "[ÉXITO] Entrega creada con ID "
                                + entrega.getId() + "."
                );

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "[ERROR] No fue posible crear la entrega."
            );

            mostrarErrorSQL(e);
        }

        return false;
    }

    public List<Entrega> readAll() {

        List<Entrega> entregas =
                new ArrayList<>();

        String sql = """
                SELECT id,
                       id_pedido,
                       id_repartidor,
                       fecha,
                       hora
                FROM entregas
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

                int idPedido =
                        resultado.getInt("id_pedido");

                int idRepartidor =
                        resultado.getInt(
                                "id_repartidor"
                        );

                LocalDate fecha =
                        resultado.getDate("fecha")
                                .toLocalDate();

                LocalTime hora =
                        resultado.getTime("hora")
                                .toLocalTime();

                Entrega entrega =
                        new Entrega(
                                id,
                                idPedido,
                                idRepartidor,
                                fecha,
                                hora
                        );

                entregas.add(entrega);
            }

        } catch (SQLException e) {

            System.out.println(
                    "[ERROR] No fue posible consultar "
                            + "las entregas."
            );

            mostrarErrorSQL(e);
        }

        return entregas;
    }

    public boolean update(Entrega entrega) {

        String sql = """
                UPDATE entregas
                SET id_pedido = ?,
                    id_repartidor = ?,
                    fecha = ?,
                    hora = ?
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
                    entrega
            );

            sentencia.setInt(
                    5,
                    entrega.getId()
            );

            int filasAfectadas =
                    sentencia.executeUpdate();

            if (filasAfectadas > 0) {

                System.out.println(
                        "[ÉXITO] Entrega actualizada."
                );

                return true;
            }

            System.out.println(
                    "[ERROR] No existe una entrega "
                            + "con el ID indicado."
            );

        } catch (SQLException e) {

            System.out.println(
                    "[ERROR] No fue posible actualizar "
                            + "la entrega."
            );

            mostrarErrorSQL(e);
        }

        return false;
    }

    public boolean delete(int id) {

        String sql = """
                DELETE FROM entregas
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
                        "[ÉXITO] Entrega eliminada."
                );

                return true;
            }

            System.out.println(
                    "[ERROR] No existe una entrega "
                            + "con el ID indicado."
            );

        } catch (SQLException e) {

            System.out.println(
                    "[ERROR] No fue posible eliminar "
                            + "la entrega."
            );

            mostrarErrorSQL(e);
        }

        return false;
    }

    private void completarParametros(
            PreparedStatement sentencia,
            Entrega entrega)
            throws SQLException {

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
    }

    private void mostrarErrorSQL(SQLException e) {

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