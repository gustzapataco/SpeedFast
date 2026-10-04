package app;

import dao.RepartidorDAO;
import model.Repartidor;

import java.util.List;

public class PruebaRepartidorCRUD {

    public static void main(String[] args) {

        try {
            RepartidorDAO repartidorDAO =
                    new RepartidorDAO();

            System.out.println(
                    "=================================="
            );

            System.out.println(
                    "   PRUEBA CRUD DE REPARTIDORES"
            );

            System.out.println(
                    "=================================="
            );

            System.out.println(
                    "\n1. CREATE"
            );

            Repartidor repartidorPrueba =
                    new Repartidor(
                            "REPARTIDOR PRUEBA JAVA"
                    );

            boolean creado =
                    repartidorDAO.create(
                            repartidorPrueba
                    );

            if (!creado) {

                System.out.println(
                        "La prueba se detiene porque "
                                + "el registro no fue creado."
                );

                return;
            }

            System.out.println(
                    "Objeto después del INSERT:"
            );

            System.out.println(
                    repartidorPrueba
            );

            System.out.println(
                    "\n2. READ"
            );

            mostrarRepartidores(
                    repartidorDAO.readAll()
            );

            System.out.println(
                    "\n3. UPDATE"
            );

            repartidorPrueba.setNombre(
                    "REPARTIDOR ACTUALIZADO JAVA"
            );

            boolean actualizado =
                    repartidorDAO.update(
                            repartidorPrueba
                    );

            if (actualizado) {

                mostrarRepartidores(
                        repartidorDAO.readAll()
                );
            }

            System.out.println(
                    "\n4. DELETE"
            );

            boolean eliminado =
                    repartidorDAO.delete(
                            repartidorPrueba.getId()
                    );

            if (eliminado) {

                System.out.println(
                        "\nListado después de eliminar:"
                );

                mostrarRepartidores(
                        repartidorDAO.readAll()
                );
            }

            System.out.println(
                    "\n=================================="
            );

            System.out.println(
                    "Prueba CRUD finalizada."
            );

            System.out.println(
                    "=================================="
            );

        } catch (Exception e) {

            System.out.println(
                    "[ERROR] Ocurrió un problema "
                            + "durante la prueba: "
                            + e.getMessage()
            );
        }
    }

    private static void mostrarRepartidores(
            List<Repartidor> repartidores) {

        if (repartidores.isEmpty()) {

            System.out.println(
                    "No existen repartidores registrados."
            );

            return;
        }

        System.out.println(
                "----------------------------------------"
        );

        System.out.printf(
                "%-8s %-30s%n",
                "ID",
                "NOMBRE"
        );

        System.out.println(
                "----------------------------------------"
        );

        for (Repartidor repartidor
                : repartidores) {

            System.out.printf(
                    "%-8d %-30s%n",
                    repartidor.getId(),
                    repartidor.getNombre()
            );
        }

        System.out.println(
                "----------------------------------------"
        );
    }
}