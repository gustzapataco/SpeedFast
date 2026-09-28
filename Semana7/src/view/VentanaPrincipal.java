package view;

import controller.GestorPedidos;
import model.EstadoPedido;
import model.Pedido;
import model.Repartidor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Window;

public class VentanaPrincipal extends JFrame {

    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JButton btnRegistrar;
    private JButton btnListar;
    private JButton btnAsignar;
    private JButton btnSalir;

    private final GestorPedidos gestorPedidos;

    public VentanaPrincipal() {

        gestorPedidos =
                GestorPedidos.getInstancia();

        configurarVentana();
        configurarEventos();
    }

    private void configurarVentana() {

        setContentPane(panelPrincipal);

        setTitle(
                "SpeedFast - Gestión de entregas"
        );

        setSize(600, 350);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setResizable(false);

        setLocationRelativeTo(null);
    }

    private void configurarEventos() {

        btnRegistrar.addActionListener(e ->
                abrirVentanaRegistro()
        );

        btnListar.addActionListener(e ->
                abrirVentanaLista()
        );

        btnAsignar.addActionListener(e ->
                asignarRepartidor()
        );

        btnSalir.addActionListener(e ->
                confirmarSalida()
        );
    }

    private void abrirVentanaRegistro() {

        VentanaRegistroPedido ventana =
                new VentanaRegistroPedido(this);

        ventana.setVisible(true);
    }

    private void abrirVentanaLista() {

        VentanaListaPedidos ventana =
                new VentanaListaPedidos(this);

        ventana.setVisible(true);
    }

    private void asignarRepartidor() {

        Pedido pedidoPendiente =
                gestorPedidos
                        .obtenerPrimerPedidoPendiente();

        if (pedidoPendiente == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "No existen pedidos pendientes.",
                    "SpeedFast",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        String nombre =
                JOptionPane.showInputDialog(
                        this,
                        "Pedido por asignar: "
                                + pedidoPendiente
                                .getIdPedido()
                                + "\nIngrese el nombre "
                                + "del repartidor:",
                        "Asignar repartidor",
                        JOptionPane.QUESTION_MESSAGE
                );

        if (nombre == null) {
            return;
        }

        nombre = nombre.trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el nombre "
                            + "del repartidor.",
                    "Dato incorrecto",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Pedido pedidoSeleccionado =
                pedidoPendiente;

        Repartidor repartidor =
                new Repartidor(
                        nombre,
                        () -> comprobarEntrega(
                                pedidoSeleccionado
                        )
                );

        repartidor.asignarPedido(
                pedidoSeleccionado
        );

        Thread hiloRepartidor =
                new Thread(
                        repartidor,
                        "Repartidor-" + nombre
                );

        hiloRepartidor.start();

        JOptionPane.showMessageDialog(
                this,
                "Pedido "
                        + pedidoSeleccionado
                        .getIdPedido()
                        + " asignado a "
                        + nombre
                        + ".\nLa entrega comenzó "
                        + "en un hilo independiente.",
                "Entrega iniciada",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void comprobarEntrega(
            Pedido pedido) {

        if (pedido.getEstado()
                == EstadoPedido.ENTREGADO) {

            JOptionPane.showMessageDialog(
                    this,
                    "El pedido "
                            + pedido.getIdPedido()
                            + " fue entregado por "
                            + pedido
                            .getNombreRepartidor()
                            + ".",
                    "Entrega finalizada",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void confirmarSalida() {

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Desea cerrar SpeedFast "
                                + "y todas sus ventanas?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION
                );

        if (respuesta
                == JOptionPane.YES_OPTION) {

            for (Window ventana
                    : Window.getWindows()) {

                ventana.dispose();
            }

            System.exit(0);
        }
    }
}