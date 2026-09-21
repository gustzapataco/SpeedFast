package view;

import controller.GestorPedidos;
import model.Pedido;
import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class VentanaRegistroPedido
        extends javax.swing.JFrame {

    private final GestorPedidos gestorPedidos;

    public VentanaRegistroPedido() {
        this(null);
    }

    public VentanaRegistroPedido(JFrame ventanaPadre) {
        initComponents();

        gestorPedidos = GestorPedidos.getInstancia();

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(ventanaPadre);

        btnCerrar.addActionListener(e ->
                dispose()
        );
    }

    /*
     * Aquí permanece el método initComponents()
     * generado por NetBeans.
     * No debes modificarlo manualmente.
     */

    private void btnGuardarActionPerformed(
            java.awt.event.ActionEvent evt) {

        guardarPedido();
    }

    private void btnLimpiarActionPerformed(
            java.awt.event.ActionEvent evt) {

        limpiarFormulario();
    }

    private void guardarPedido() {
        // Aquí conservas toda tu lógica actual.
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtDireccion.setText("");
        txtDistancia.setText("");
        cmbTipo.setSelectedIndex(0);
        txtId.requestFocusInWindow();
    }

    /*
     * Aquí permanecen las variables creadas
     * automáticamente por NetBeans.
     */
}