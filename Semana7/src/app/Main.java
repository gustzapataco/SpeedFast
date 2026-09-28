package main;

import dao.ConexionBD;

import view.VentanaPrincipal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            configurarApariencia();

            VentanaPrincipal ventana =
                    new VentanaPrincipal();

            ventana.setVisible(true);
        });
    }

    private static void configurarApariencia() {

        try {
            UIManager.setLookAndFeel(
                    UIManager
                            .getSystemLookAndFeelClassName()
            );

        } catch (Exception e) {

            System.out.println(
                    "No fue posible aplicar "
                            + "la apariencia del sistema."
            );
        }
    }
}