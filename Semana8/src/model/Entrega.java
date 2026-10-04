package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Entrega {

    // IDETIFICADORES RELACONADOS:
    private int id;
    private int idPedido;
    private int idRepartidor;
    private LocalDate fecha;
    private LocalTime hora;

    // CONSTRUCTOR PARA ENTREGA NUEVA
    public Entrega(int idPedido,
                   int idRepartidor,
                   LocalDate fecha,
                   LocalTime hora) {

        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }
    // CONSTRUCTOR PARA RECUPERAR ENTREGA DESDE MYSQL
    public Entrega(int id,
                   int idPedido,
                   int idRepartidor,
                   LocalDate fecha,
                   LocalTime hora) {

        this.id = id;
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    @Override
    public String toString() {

        return "Entrega{" +
                "id=" + id +
                ", idPedido=" + idPedido +
                ", idRepartidor=" + idRepartidor +
                ", fecha=" + fecha +
                ", hora=" + hora +
                '}';
    }

    // GETTERS
    public int getId() {
        return id;
    }
    public int getIdPedido() {
        return idPedido;
    }
    public int getIdRepartidor() {
        return idRepartidor;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public LocalTime getHora() {
        return hora;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }
    public void setIdRepartidor(int idRepartidor) {
        this.idRepartidor = idRepartidor;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}