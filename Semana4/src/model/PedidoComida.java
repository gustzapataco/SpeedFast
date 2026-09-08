package model;

public class PedidoComida extends Pedido {

    // constructor
    public PedidoComida(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega,  distanciaKm);
    }
    /*
     * Sobrescritura para asignacion automatica.
     */
    @Override
    public void asignarRepartidor() {
        establecerRepartidorAutomatico("Carlos - mochila termica");
    }
    /*
     * Regla original de la actividad formativa_2
     * 15 minutos base mas 2 minutos por kilometro.
     */
    @Override
    public int calcularTiempoEntrega() {
        return (int) (15 + (2 * getDistanciaKm()));
    }
}