package view;

import controller.GestorPedidos;
import model.Pedido;
import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaRegistroPedido
        extends JFrame {

    private JPanel panelPrincipal;

    private JLabel lblTitulo;
    private JLabel lblId;
    private JLabel lblDireccion;
    private JLabel lblDistancia;
    private JLabel lblTipo;

    private JTextField txtId;
    private JTextField txtDireccion;
    private JTextField txtDistancia;

    private JComboBox<String> cmbTipo;

    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnCerrar;

    private final GestorPedidos gestorPedidos;

    public VentanaRegistroPedido() {
        this(null);
    }

    public VentanaRegistroPedido(
            JFrame ventanaPadre) {

        gestorPedidos =
                GestorPedidos.getInstancia();

        configurarVentana(ventanaPadre);
        configurarComboBox();
        configurarEventos();
    }

    private void configurarVentana(
            JFrame ventanaPadre) {

        setContentPane(panelPrincipal);

        setTitle(
                "SpeedFast - Registrar pedido"
        );

        setSize(500, 400);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setResizable(false);

        setLocationRelativeTo(ventanaPadre);
    }

    private void configurarComboBox() {

        if (cmbTipo.getItemCount() == 0) {

            cmbTipo.addItem("Comida");
            cmbTipo.addItem("Encomienda");
            cmbTipo.addItem("Express");
        }
    }

    private void configurarEventos() {

        btnGuardar.addActionListener(e ->
                guardarPedido()
        );

        btnLimpiar.addActionListener(e ->
                limpiarFormulario()
        );

        btnCerrar.addActionListener(e ->
                dispose()
        );
    }

    private void guardarPedido() {

        String id =
                txtId.getText().trim();

        String direccion =
                txtDireccion.getText().trim();

        String textoDistancia =
                txtDistancia.getText().trim();

        if (id.isEmpty()
                || direccion.isEmpty()
                || textoDistancia.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe completar todos "
                            + "los campos.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        double distancia;

        try {
            distancia =
                    Double.parseDouble(
                            textoDistancia
                                    .replace(",", ".")
                    );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "La distancia debe ser "
                            + "un número válido.",
                    "Dato incorrecto",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (distancia <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "La distancia debe ser "
                            + "mayor que cero.",
                    "Dato incorrecto",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (gestorPedidos.existePedido(id)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ya existe un pedido "
                            + "con el ID "
                            + id
                            + ".",
                    "ID duplicado",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String tipo =
                String.valueOf(
                        cmbTipo.getSelectedItem()
                );

        Pedido nuevoPedido;

        switch (tipo) {

            case "Comida":
                nuevoPedido =
                        new PedidoComida(
                                id,
                                direccion,
                                distancia
                        );
                break;

            case "Encomienda":
                nuevoPedido =
                        new PedidoEncomienda(
                                id,
                                direccion,
                                distancia
                        );
                break;

            case "Express":
                nuevoPedido =
                        new PedidoExpress(
                                id,
                                direccion,
                                distancia
                        );
                break;

            default:
                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione un tipo "
                                + "de pedido válido.",
                        "Tipo incorrecto",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
        }

        boolean agregado =
                gestorPedidos.agregarPedido(
                        nuevoPedido
                );

        if (agregado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido registrado correctamente."
                            + "\nID: "
                            + nuevoPedido.getIdPedido()
                            + "\nTipo: "
                            + nuevoPedido.getTipoPedido()
                            + "\nTiempo estimado: "
                            + nuevoPedido
                            .calcularTiempoEntrega()
                            + " minutos.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible registrar "
                            + "el pedido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limpiarFormulario() {

        txtId.setText("");
        txtDireccion.setText("");
        txtDistancia.setText("");

        cmbTipo.setSelectedIndex(0);

        txtId.requestFocusInWindow();
    }
}