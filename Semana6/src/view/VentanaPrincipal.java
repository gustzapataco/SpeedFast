package view;

import controller.GestorPedidos;
import model.EstadoPedido;
import model.Pedido;
import model.Repartidor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JButton btnRegistrar;
    private JButton btnListar;
    private JButton btnAsignar;
    private JButton btnSalir;

    private final GestorPedidos gestorPedidos;

    public VentanaPrincipal() {
        gestorPedidos = GestorPedidos.getInstancia();

        inicializarComponentes();
        configurarVentana();
        configurarEventos();
    }

    private void inicializarComponentes() {
        panelPrincipal =
                new JPanel(new BorderLayout(15, 15));

        panelPrincipal.setBorder(
                new EmptyBorder(25, 35, 25, 35)
        );

        lblTitulo = new JLabel(
                "Sistema de entregas SpeedFast",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(
                new Font("SansSerif", Font.BOLD, 22)
        );

        btnRegistrar =
                new JButton("Registrar pedido");

        btnListar =
                new JButton("Listar pedidos");

        btnAsignar =
                new JButton(
                        "Asignar repartidor / Iniciar entrega"
                );

        btnSalir =
                new JButton("Salir");

        JPanel panelBotones =
                new JPanel(new GridLayout(4, 1, 10, 10));

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnListar);
        panelBotones.add(btnAsignar);
        panelBotones.add(btnSalir);

        panelPrincipal.add(
                lblTitulo,
                BorderLayout.NORTH
        );

        panelPrincipal.add(
                panelBotones,
                BorderLayout.CENTER
        );
    }

    private void configurarVentana() {
        setContentPane(panelPrincipal);
        setTitle("SpeedFast - Gestión de entregas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        pack();
        setSize(600, 380);
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
        VentanaRegistroPedido ventanaRegistro =
                new VentanaRegistroPedido(this);

        ventanaRegistro.setVisible(true);
    }

    private void abrirVentanaLista() {
        VentanaListaPedidos ventanaLista =
                new VentanaListaPedidos(this);

        ventanaLista.setVisible(true);
    }

    private void asignarRepartidor() {
        Pedido pedidoPendiente =
                gestorPedidos.obtenerPedidos()
                        .stream()
                        .filter(pedido ->
                                pedido.getEstado()
                                        == EstadoPedido.PENDIENTE
                        )
                        .findFirst()
                        .orElse(null);

        if (pedidoPendiente == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No existen pedidos pendientes.",
                    "SpeedFast",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        String nombre = JOptionPane.showInputDialog(
                this,
                "Pedido por asignar: "
                        + pedidoPendiente.getIdPedido()
                        + "\nIngrese el nombre del repartidor:",
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
                    "Debe ingresar el nombre del repartidor.",
                    "Dato incorrecto",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Pedido pedidoSeleccionado = pedidoPendiente;

        Repartidor repartidor = new Repartidor(
                nombre,
                () -> mostrarEntregaFinalizada(
                        pedidoSeleccionado
                )
        );

        repartidor.asignarPedido(pedidoSeleccionado);

        Thread hiloRepartidor = new Thread(
                repartidor,
                "Repartidor-" + nombre
        );

        hiloRepartidor.start();

        JOptionPane.showMessageDialog(
                this,
                "Pedido "
                        + pedidoSeleccionado.getIdPedido()
                        + " asignado a "
                        + nombre
                        + ".\nLa entrega comenzó "
                        + "en un hilo independiente.",
                "Entrega iniciada",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void mostrarEntregaFinalizada(
            Pedido pedidoSeleccionado) {

        if (pedidoSeleccionado.getEstado()
                == EstadoPedido.ENTREGADO) {

            JOptionPane.showMessageDialog(
                    this,
                    "El pedido "
                            + pedidoSeleccionado.getIdPedido()
                            + " fue entregado por "
                            + pedidoSeleccionado
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

        if (respuesta == JOptionPane.YES_OPTION) {
            for (Window ventana : Window.getWindows()) {
                ventana.dispose();
            }

            System.exit(0);
        }
    }
}        