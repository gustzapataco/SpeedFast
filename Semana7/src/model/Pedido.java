package model;

import interfaces.Cancelable;
import interfaces.Despachable;
import interfaces.Rastreable;

public abstract class Pedido
        implements Despachable, Cancelable, Rastreable {

    private int idBaseDatos;
    private final String idPedido;
    private String direccionEntrega;
    private double distanciaKm;
    private EstadoPedido estado;
    private String nombreRepartidor;

    /*
     * Constructor para crear un pedido nuevo.
     * MySQL genera automatico un ID
     */
    public Pedido(
            String idPedido,
            String direccionEntrega,
            double distanciaKm) {

        this.idBaseDatos = 0;
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
        this.estado = EstadoPedido.PENDIENTE;
        this.nombreRepartidor = "Sin asignar";
    }

    /*
     * Constructor para reconstruir un pedido
     * recuperado desde MySQL
     */
    public Pedido(
            int idBaseDatos,
            String idPedido,
            String direccionEntrega,
            double distanciaKm
    ) {
        this.idBaseDatos = idBaseDatos;
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
        this.estado = EstadoPedido.PENDIENTE;
        this.nombreRepartidor = "Sin asignar";
    }

    public abstract int calcularTiempoEntrega();

    public abstract String getTipoPedido();

    public void mostrarResumen() {
        System.out.println("ID base de dato: " + idBaseDatos);
        System.out.println("Código del pedido: " + idPedido);
        System.out.println("Tipo: " + getTipoPedido());
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm + " km");
        System.out.println("Estado: " + estado);
        System.out.println("Repartidor: " + nombreRepartidor);
    }

    @Override
    public synchronized void despachar() {
        if (estado == EstadoPedido.PENDIENTE) {
            estado = EstadoPedido.EN_REPARTO;
        }
    }

    @Override
    public synchronized void cancelar() {
        if (estado == EstadoPedido.PENDIENTE) {
            estado = EstadoPedido.CANCELADO;
        }
    }

    @Override
    public synchronized String rastrear() {
        return "Pedido "
                + idPedido
                + ": "
                + estado;
    }

    public int getIdBaseDatos() {
        return idBaseDatos;
    }

    public void setIdBaseDatos(int idBaseDatos) {
        this.idBaseDatos = idBaseDatos;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(
            String direccionEntrega
    ) {
        this.direccionEntrega = direccionEntrega;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(
            double distanciaKm
    ) {
        this.distanciaKm = distanciaKm;
    }

    public synchronized EstadoPedido getEstado() {
        return estado;
    }

    public synchronized void setEstado(
            EstadoPedido estado
    ) {
        this.estado = estado;
    }

    public synchronized String getNombreRepartidor() {
        return nombreRepartidor;
    }

    public synchronized void setNombreRepartidor(
            String nombreRepartidor
    ) {
        this.nombreRepartidor = nombreRepartidor;
    }

    @Override
    public String toString() {
        return getTipoPedido()
                + " "
                + idPedido
                + " - "
                + estado;
    }
}