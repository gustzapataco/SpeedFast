package view;

import controller.GestorPedidos;
import model.Pedido;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class VentanaListaPedido
        extends JFrame {

    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JTable tablaPedidos;
    private JButton btnActualizar;
    private JButton btnCerrar;

    private DefaultTableModel modeloTabla;

    private final GestorPedidos gestorPedidos;

    public VentanaListaPedido() {
        this(null);
    }

    public VentanaListaPedido(
            JFrame ventanaPadre) {

        gestorPedidos =
                GestorPedidos.getInstancia();

        configurarVentana(ventanaPadre);
        configurarTabla();
        configurarEventos();
        cargarPedidos();
    }

    private void configurarVentana(
            JFrame ventanaPadre) {

        setContentPane(panelPrincipal);

        setTitle(
                "SpeedFast - Lista de pedidos"
        );

        setSize(950, 450);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(ventanaPadre);
    }

    private void configurarTabla() {

        String[] columnas = {
                "ID",
                "Tipo",
                "Dirección",
                "Distancia",
                "Tiempo",
                "Estado",
                "Repartidor"
        };

        modeloTabla =
                new DefaultTableModel(
                        columnas,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int fila,
                            int columna) {

                        return false;
                    }
                };

        tablaPedidos.setModel(modeloTabla);

        tablaPedidos.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        tablaPedidos.setFillsViewportHeight(
                true
        );
    }

    private void configurarEventos() {

        btnActualizar.addActionListener(e ->
                cargarPedidos()
        );

        btnCerrar.addActionListener(e ->
                dispose()
        );
    }

    public final void cargarPedidos() {

        modeloTabla.setRowCount(0);

        for (Pedido pedido
                : gestorPedidos.obtenerPedidos()) {

            Object[] fila = {
                    pedido.getIdPedido(),
                    pedido.getTipoPedido(),
                    pedido
                            .getDireccionEntrega(),
                    pedido.getDistanciaKm()
                            + " km",
                    pedido
                            .calcularTiempoEntrega()
                            + " min",
                    pedido.getEstado(),
                    pedido
                            .getNombreRepartidor()
            };

            modeloTabla.addRow(fila);
        }
    }
}