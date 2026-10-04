package app;

import dao.ConexionDB;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class DiagnosticoConexion {

    public static void main(String[] args) {

        try (
                Connection conexion =
                        ConexionDB.conectar()
        ) {
            DatabaseMetaData datos =
                    conexion.getMetaData();

            System.out.println(
                    "CONEXIÓN UTILIZADA POR INTELLIJ"
            );

            System.out.println(
                    "URL: "
                            + datos.getURL()
            );

            System.out.println(
                    "Usuario: "
                            + datos.getUserName()
            );

            System.out.println(
                    "Base de datos: "
                            + conexion.getCatalog()
            );

            System.out.println(
                    "AutoCommit: "
                            + conexion.getAutoCommit()
            );

        } catch (SQLException e) {

            System.out.println(
                    "[ERROR] No fue posible obtener "
                            + "los datos de conexión."
            );

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
}