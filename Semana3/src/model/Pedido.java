package model;

import interfaces.Cancelable;
import interfaces.Despachable;
import interfaces.Rastreable;
import java.util.ArrayList;

public abstract class Pedido implements Despachable, Cancelable, Rastreable {
    // Atributos comunes de todos los pedidos
    private String idPedido;
    private String direccionEntrega;
    private double distanciaKm;
    private String nombreRepartidor;
    private EstadoPedido estado;

    // Historial del pedido individual
    private ArrayList<String> historial;

    // Constructor
    public Pedido(String idPedido, String direccionEntrega, double distanciaKm) {
        if (idPedido == null || idPedido.isBlank()) {
            throw new IllegalArgumentException(
                    "El identificador del pedido es obligatorio.");
        }
        if (direccionEntrega == null || direccionEntrega.isBlank()) {
            throw new IllegalArgumentException(
                    "La direccion de entrega es obligatorio."
            );
        }
        if (distanciaKm <= 0) {
            throw new IllegalArgumentException(
                    "La distancia debe ser mayor que cero."
            );
        }
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
        this.nombreRepartidor = null;
        this.estado = EstadoPedido.CREADO;
        this.historial = new ArrayList<>();

        registrarEvento("Pedido creado");
    }

    /*
     * Metodo abstracto.
     * cada subclase debe calcular su propio tiempo.
     */
    public abstract int calcularTiempoEntrega();

    /*
     * Asignacion automatica.
     * cada subclase debe implementar su propia regla.
     */
    public abstract void asignarRepartidor();

    /*
     * Sobrecarga:
     * permite realizar una asignacion manual.
     */
    public void asignarRepartidor(String nombre) {
        if (!pedidoSePuedeModificar()) {
            System.out.println("No se puede asignar un repartidor al pedido " + idPedido);
            return;
        }
        if (nombre == null || nombre.isBlank()) {
            System.out.println("El nombre del repartidor es obligatorio.");
            return;
        }
        this.nombreRepartidor = nombre;
        registrarEvento("Repartidor asignado manualmente: " + nombre);
        System.out.println("Repartidor " + nombre + " asignado manualmente al pedido " + idPedido);
    }

    /*
     * Metodo protegido para que las subclases puedan realizar la asignacion automatica.
     */
    protected  void establecerRepartidorAutomatico(String nombre) {
        if (!pedidoSePuedeModificar()) {
            System.out.println("No se puede asignar un repartidor al pedido " + idPedido);
            return;
        }
        this.nombreRepartidor = nombre;
        registrarEvento("Repartidor asignado automaticamente: " + nombre);
        System.out.println("Repartidor asignado automaticamente al pedido "
                                    + idPedido
                                    + ": "
                                    + nombre);
    }

    /*
     * Operacion reserva el pedido
     */
    public void reservarPedido() {
        if (estado != EstadoPedido.CREADO) {
            System.out.println("El pedido " + idPedido + " no puede reservarse porque esta " + estado);
            return;
        }
        estado = EstadoPedido.RESERVADO;
        registrarEvento("Pedido reservado");
        System.out.println("Pedido " + idPedido + " reservado correctamente");
    }

    /*
     * Implementacion interfaz Despachable.
     */
    @Override
    public void despachar() {
        if (estado == EstadoPedido.RESERVADO) {
            System.out.println("El pedido " + idPedido + " debe estar reservado antes de despacharse.");
            return;
        }
        if (nombreRepartidor == null) {
            System.out.println("El pedido " + idPedido + " necesita un repartidor.");
            return;
        }
        estado = EstadoPedido.DESPACHADO;
        registrarEvento("Pedido despachado por " + nombreRepartidor);
        System.out.println("Pedido " + idPedido + " despachado correctamente.");
    }

    /*
     * Operacion necesaria para finalizar entrega.
     */
    public void marcarComoEntregado() {
        if (estado != EstadoPedido.DESPACHADO) {
            System.out.println("Solamente un pedido despachado puede entregarse.");
            return;
        }
        estado = EstadoPedido.ENTREGADO;
        registrarEvento("Pedido entregado");
        System.out.println("Pedido " + idPedido + " entregado correctamente.");
    }

    /*
     * Implementando interfaz Cancelable
     */
    @Override
    public void cancelar() {
        if (estado == EstadoPedido.ENTREGADO) {
            System.out.println("El pedido " + idPedido + " ya fue entregado y no puede cancelarse.");
            return;
        }
        if (estado == EstadoPedido.CANCELADO) {
            System.out.println("El pedido " + idPedido + " ya fue cancelado.");
            return;
        }
        estado = EstadoPedido.CANCELADO;
        registrarEvento("Pedido cancelado");
        System.out.println("Pedido " + idPedido + " cancelado correctamente.");
    }

    /*
     * Implementacion de la interfaz Rastreable.
     */
    @Override
    public void verHistorial() {
        System.out.println("\n[HISTORIAL DEL PEDIDO " + idPedido + "]");
        for (String evento : historial) {
            System.out.println("- " + evento);
        }
    }

    /*
     * Nuevo metodo sumativa_1
     */
    public void mostrarResumen() {
        System.out.println("ID Pedido: " + idPedido);
        System.out.println("Direccion: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm + " km");

        if (nombreRepartidor != null) {
            System.out.println("Repartidor: sin asignar");
        } else {
            System.out.println("Repartidor: " + nombreRepartidor);
        }

        System.out.println("Estado: " + estado);
        System.out.println("Tiempo estimado: " + calcularTiempoEntrega() + " minutos");
    }

    private void registrarEvento(String evento) {
        historial.add(evento);
    }
    private boolean pedidoSePuedeModificar() {
        return estado != EstadoPedido.CANCELADO && estado != EstadoPedido.ENTREGADO;
    }

    // Getters
    public String getIdPedido() {
        return idPedido;
    }
    public String getDireccionEntrega() {
        return direccionEntrega;
    }
    public double getDistanciaKm() {
        return distanciaKm;
    }
    public String getNombreRepartidor() {
        return nombreRepartidor;
    }
    public EstadoPedido getEstado() {
        return estado;
    }
}