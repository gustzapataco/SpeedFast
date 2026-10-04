package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/speedfast_db";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "Gus7769+";

    /**
     * crea y devuelve una conexion con bd SpeedFast.
     * @return conexion activa con MySQL.
     * @throws SQLException si ocurre un problema durante la conexion.
     */
    public static Connection conectar()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                CONTRASENA
        );
    }
}