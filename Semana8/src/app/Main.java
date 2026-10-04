package app;

import dao.ConexionDB;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        try (
                Connection conexion =
                        ConexionDB.conectar()
        ) {
            System.out.println(
                    "[ÉXITO] Conexión establecida "
                            + "con speedfast_db."
            );

            MenuConsola menu =
                    new MenuConsola();

            menu.iniciar();

        } catch (SQLException e) {

            System.out.println(
                    "[ERROR] No fue posible iniciar "
                            + "SpeedFast."
            );

            System.out.println(
                    "No se pudo conectar con MySQL."
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

        } catch (Exception e) {

            System.out.println(
                    "[ERROR] Ocurrió un problema inesperado: "
                            + e.getMessage()
            );
        }
    }
}