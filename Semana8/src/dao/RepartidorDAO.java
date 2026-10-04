package dao;

import model.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    /**
     * registra un nuevo repartidor en bd.
     * @param repartidor objeto que contiene nombre del repartidor.
     * @return true si el registro fue correctamente creado.
     */
    public boolean create(Repartidor repartidor) {
        String sql = """
                INSERT INTO repartidores (nombre)
                VALUES (?)
                """;

        try (
                Connection conexion = ConexionDB.conectar();

                PreparedStatement sentencia = conexion.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {
            // REMPLAZA ? POR EL NOMBRE DEL REPARTIDOR
            sentencia.setString(
                    1,
                    repartidor.getNombre()
            );

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas > 0) {

                // RECUPERA EL ID AUTO_INCREMENT GENERADO POR MYSQL
                try (
                        ResultSet clavesGeneradas =
                                sentencia.getGeneratedKeys()
                ) {
                    if (clavesGeneradas.next()) {

                        int idGenerado =
                                clavesGeneradas.getInt(1);

                        repartidor.setId(idGenerado);
                    }
                }

                System.out.println(
                        "[ÉXITO] Repartidor creado con ID "
                                + repartidor.getId() + "."
                );

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "[ERROR] No fue posible crear "
                            + "el repartidor."
            );

            mostrarErrorSQL(e);
        }

        return false;
    }

    /**
     * consulta todos los repartidores almacenados MySQL.
     * @return lista con repartidores encontrados.
     */
    public List<Repartidor> readAll() {

        List<Repartidor> repartidores =
                new ArrayList<>();

        String sql = """
                SELECT id, nombre
                FROM repartidores
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
            // RECORRE CADA FILA OBTENIDA DESDE LA TABLA
            while (resultado.next()) {

                int id =
                        resultado.getInt("id");

                String nombre =
                        resultado.getString("nombre");

                Repartidor repartidor =
                        new Repartidor(
                                id,
                                nombre
                        );
                // CONVIERTE FILAS MYSQL EN UN OBJETO JAVA.
                repartidores.add(repartidor);
            }

        } catch (SQLException e) {

            System.out.println(
                    "[ERROR] No fue posible consultar "
                            + "los repartidores."
            );

            mostrarErrorSQL(e);
        }

        return repartidores;
    }

    /**
     * actualiza nombre de un repartidor utiliando su ID
     * @param repartidor objeto con el ID y nuevo nombre.
     * @return true si el registro fue actualizado.
     */
    public boolean update(
            Repartidor repartidor) {

        String sql = """
                UPDATE repartidores
                SET nombre = ?
                WHERE id = ?
                """;

        try (
                Connection conexion =
                        ConexionDB.conectar();

                PreparedStatement sentencia =
                        conexion.prepareStatement(sql)
        ) {
            sentencia.setString(
                    1,
                    repartidor.getNombre()
            );

            sentencia.setInt(
                    2,
                    repartidor.getId()
            );

            int filasAfectadas =
                    sentencia.executeUpdate();

            if (filasAfectadas > 0) {

                System.out.println(
                        "[ÉXITO] Repartidor actualizado."
                );

                return true;
            }

            System.out.println(
                    "[ERROR] No existe un repartidor "
                            + "con el ID indicado."
            );

        } catch (SQLException e) {

            System.out.println(
                    "[ERROR] No fue posible actualizar "
                            + "el repartidor."
            );

            mostrarErrorSQL(e);
        }

        return false;
    }

    /**
     * elimina un repartidor utilizando su ID.
     * @param id identificador del repartidor.
     * @return true si el registro fue eliminado.
     */
    public boolean delete(int id) {

        String sql = """
                DELETE FROM repartidores
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
                        "[ÉXITO] Repartidor eliminado."
                );

                return true;
            }

            System.out.println(
                    "[ERROR] No existe un repartidor "
                            + "con el ID indicado."
            );

        } catch (SQLException e) {

            System.out.println(
                    "[ERROR] No fue posible eliminar "
                            + "el repartidor."
            );

            System.out.println(
                    "Puede tener entregas asociadas."
            );

            mostrarErrorSQL(e);
        }

        return false;
    }

    private void mostrarErrorSQL(
            SQLException e) {

        System.out.println("\nMensaje: " + e.getMessage());
        System.out.println("\nCódigo SQL: " + e.getErrorCode());
        System.out.println("\nEstado SQL: " + e.getSQLState());
    }
}