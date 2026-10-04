package model;

import interfaces.Cancelable;
import interfaces.Despachable;
import interfaces.Rastreable;

import java.util.ArrayList;
import java.util.List;

public abstract class Pedido
        implements Despachable, Cancelable, Rastreable {
    // ATRIBUTOS PRINCIPALES PEDIDO
    private int idPedido;
    private String direccionEntrega;
    private String tipoPedido;
    private double distanciaKm;
    private EstadoPedido estado;
    private List<String> historial; //HISTORIAL OPERACIONES REALIZADAS
    // CONSTRUCTOR COMPLETO
    public Pedido(int idPedido,
                  String direccionEntrega,
                  String tipoPedido,
                  double distanciaKm) {

        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
        this.distanciaKm = distanciaKm;
        this.estado = EstadoPedido.PENDIENTE;
        this.historial = new ArrayList<>();

        historial.add(
                "Pedido " + idPedido
                        + " creado con estado PENDIENTE."
        );
    }
    // ASIGNACION FORMA AUTOMATICA
    public void asignarRepartidor() {

        System.out.println(
                "Se está buscando un repartidor para el pedido "
                        + idPedido + "."
        );

        historial.add(
                "Se solicitó una asignación automática."
        );
    }
    // ASIGNACION MANUAL
    public void asignarRepartidor(String nombreRepartidor) {

        System.out.println(
                "El repartidor " + nombreRepartidor
                        + " fue asignado al pedido "
                        + idPedido + "."
        );

        historial.add(
                "Repartidor asignado: "
                        + nombreRepartidor + "."
        );
    }
    // MUESTRA DATOS DEL PEDIDO
    public void mostrarResumen() {

        System.out.println("ID: " + idPedido);
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Tipo: " + tipoPedido);
        System.out.println("Distancia: " + distanciaKm + " km");
        System.out.println("Estado: " + estado);
    }
    // CADA SUBCLASE IMPLEMENTA SU PROPIO CALCULO
    public abstract int calcularTiempoEntrega();
    // IMPLEMENTA INTERFAZ DESPACHABLE
    @Override
    public void despachar() {

        estado = EstadoPedido.EN_REPARTO;

        System.out.println(
                "El pedido " + idPedido
                        + " cambió a EN_REPARTO."
        );

        historial.add(
                "Estado actualizado a EN_REPARTO."
        );
    }
    // IMPLEMENTA INTERFAZ CANCELABLE
    @Override
    public void cancelar() {

        System.out.println(
                "El pedido " + idPedido
                        + " fue cancelado."
        );

        historial.add(
                "El pedido fue cancelado."
        );
    }
    // IMPLEMENTA INTERFAZ RASTREABLE
    @Override
    public void verHistorial() {

        System.out.println(
                "Historial del pedido " + idPedido + ":"
        );

        if (historial.isEmpty()) {

            System.out.println(
                    "No existen operaciones registradas."
            );

        } else {

            for (String registro : historial) {
                System.out.println("- " + registro);
            }
        }
    }
    // AGREGGUEN MENSAJES AL HISTORIAL
    protected void agregarAlHistorial(String registro) {
        historial.add(registro);
    }
    // REPRESENTACION COMPLETA DEL OBJETO
    @Override
    public String toString() {

        return "Pedido{" +
                "idPedido=" + idPedido +
                ", direccionEntrega='" + direccionEntrega + '\'' +
                ", tipoPedido='" + tipoPedido + '\'' +
                ", distanciaKm=" + distanciaKm +
                ", estado=" + estado +
                '}';
    }
    // GETTERS
    public int getIdPedido() {
        return idPedido;
    }
    public String getDireccionEntrega() {
        return direccionEntrega;
    }
    public String getTipoPedido() {
        return tipoPedido;
    }
    public double getDistanciaKm() {
        return distanciaKm;
    }
    public EstadoPedido getEstado() {
        return estado;
    }

    public List<String> getHistorial() {
        return historial;
    }

    // SETTERS
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }
    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }
    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }
    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }
    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public void setHistorial(List<String> historial) { this.historial = historial;
    }
}