package model;

import interfaces.Cancelable;
import interfaces.Despachable;
import interfaces.Rastreable;

public abstract class Pedido
        implements Despachable, Cancelable, Rastreable {

    private String idPedido;
    private String direccionEntrega;
    private double distanciaKm;
    private EstadoPedido estado;
    private String nombreRepartidor;

    public Pedido(String idPedido,
                  String direccionEntrega,
                  double distanciaKm) {

        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
        this.estado = EstadoPedido.PENDIENTE;
        this.nombreRepartidor = "Sin asignar";
    }

    public abstract int calcularTiempoEntrega();

    public abstract String getTipoPedido();

    public void mostrarResumen() {
        System.out.println("ID: " + idPedido);
        System.out.println("Tipo: " + getTipoPedido());
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm + " km");
        System.out.println("Estado: " + estado);
        System.out.println("Repartidor: " + nombreRepartidor);
    }

    @Override
    public void despachar() {
        estado = EstadoPedido.EN_REPARTO;
    }

    @Override
    public void cancelar() {
        estado = EstadoPedido.CANCELADO;
    }

    @Override
    public String rastrear() {
        return "Pedido " + idPedido + ": " + estado;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public String getNombreRepartidor() {
        return nombreRepartidor;
    }

    public void setNombreRepartidor(String nombreRepartidor) {
        this.nombreRepartidor = nombreRepartidor;
    }

    public String getId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}