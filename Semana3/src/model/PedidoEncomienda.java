package model;

public class PedidoEncomienda extends Pedido {

    // Constructor
    public PedidoEncomienda(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }
    /*
     * Sobrescritura para asignacion automatica.
     */
    @Override
    public void asignarRepartidor() {
        establecerRepartidorAutomatico("Javiera - transporte de encomiendas");
    }

    /*
     * Regla original de la actividad formativa_2
     * 20 minutos base mas 1.5 minutos por kilometro.
     */
    @Override
    public int calcularTiempoEntrega() {
        return (int)(20 + (1.5 * getDistanciaKm()));
    }
}