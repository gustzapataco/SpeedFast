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

    public boolean guardar(Repartidor repartidor) {

        String sql = """
                INSERT INTO repartidor (nombre)
                VALUES (?)
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
                    repartidor.getNombre()
            );

            int filasInsertadas =
                    sentencia.executeUpdate();

            if (filasInsertadas > 0) {

                clavesGeneradas =
                        sentencia.getGeneratedKeys();

                if (clavesGeneradas.next()) {

                    int idGenerado =
                            clavesGeneradas.getInt(1);

                    repartidor.setId(idGenerado);
                }

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al guardar el repartidor: "
                            + e.getMessage()
            );

        } finally {

            ConexionBD.cerrar(clavesGeneradas);
            ConexionBD.cerrar(sentencia);
            ConexionBD.cerrar(conexion);
        }

        return false;
    }

    public List<Repartidor> listarTodos() {

        List<Repartidor> repartidores =
                new ArrayList<>();

        String sql = """
                SELECT id, nombre
                FROM repartidor
                ORDER BY nombre
                """;

        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = ConexionBD.conectar();

            sentencia =
                    conexion.prepareStatement(sql);

            resultado =
                    sentencia.executeQuery();

            while (resultado.next()) {

                int id =
                        resultado.getInt("id");

                String nombre =
                        resultado.getString("nombre");

                Repartidor repartidor =
                        new Repartidor(
                                id,
                                nombre,
                                null
                        );

                repartidores.add(repartidor);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar los repartidores: "
                            + e.getMessage()
            );

        } finally {

            ConexionBD.cerrar(resultado);
            ConexionBD.cerrar(sentencia);
            ConexionBD.cerrar(conexion);
        }

        return repartidores;
    }

    public Repartidor buscarPorId(int idRepartidor) {

        String sql = """
                SELECT id, nombre
                FROM repartidor
                WHERE id = ?
                """;

        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = ConexionBD.conectar();

            sentencia =
                    conexion.prepareStatement(sql);

            sentencia.setInt(
                    1,
                    idRepartidor
            );

            resultado =
                    sentencia.executeQuery();

            if (resultado.next()) {

                int id =
                        resultado.getInt("id");

                String nombre =
                        resultado.getString("nombre");

                return new Repartidor(
                        id,
                        nombre,
                        null
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar el repartidor: "
                            + e.getMessage()
            );

        } finally {

            ConexionBD.cerrar(resultado);
            ConexionBD.cerrar(sentencia);
            ConexionBD.cerrar(conexion);
        }

        return null;
    }
}