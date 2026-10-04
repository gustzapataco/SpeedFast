package app;

import dao.ConexionDB;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) {

        // CODIGO DE PRUEBA
        Connection conexion = null;

        try {
            System.out.println(
                    "Probando conexión con MySQL..."
            );

            conexion = ConexionDB.conectar();

            if (conexion != null
                    && !conexion.isClosed()) {

                System.out.println(
                        "[ÉXITO] Conexión establecida "
                                + "con speedfast_db."
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "[ERROR] No fue posible conectar "
                            + "con MySQL."
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

        } finally {

            if (conexion != null) {

                try {
                    conexion.close();

                    System.out.println(
                            "Conexión cerrada correctamente."
                    );

                } catch (SQLException e) {
                    System.out.println(
                            "[ERROR] No fue posible cerrar "
                                    + "la conexión: "
                                    + e.getMessage()
                    );
                }
            }
        }
    }
}