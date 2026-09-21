package view;

import controller.GestorPedidos;
import model.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class VentanaListaPedidos extends JFrame {

    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JTable tablaPedidos;
    private JButton btnActualizar;
    private JButton btnCerrar;

    private DefaultTableModel modeloTabla;
    private final GestorPedidos gestorPedidos;

    public VentanaListaPedidos() {
        gestorPedidos = GestorPedidos.getInstancia();

        configurarVentana();
        configurarTabla();
        configurarEventos();
        cargarPedidos();
    }

    private void configurarVentana() {
        setContentPane(panelPrincipal);
        setTitle("SpeedFast - Lista de pedidos");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaPedidos.setModel(modeloTabla);
        tablaPedidos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
    }

    private void configurarEventos() {
        btnActualizar.addActionListener(e -> cargarPedidos());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void cargarPedidos() {
        modeloTabla.setRowCount(0);

        List<Pedido> pedidos =
                gestorPedidos.obtenerPedidos();

        for (Pedido pedido : pedidos) {
            Object[] fila = {
                    pedido.getIdPedido(),
                    pedido.getTipoPedido(),
                    pedido.getDireccionEntrega(),
                    pedido.getDistanciaKm() + " km",
                    pedido.calcularTiempoEntrega() + " min",
                    pedido.getEstado(),
                    pedido.getNombreRepartidor()
            };

            modeloTabla.addRow(fila);
        }
    }
}