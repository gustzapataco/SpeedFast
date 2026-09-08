package model;

public class PedidoExpress extends Pedido {

    // Constructor
    public PedidoExpress(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    /*
     * Sobrescritura para asignacion automatica.
     */
    @Override
    public void asignarRepartidor() {
        establecerRepartidorAutomatico("Valentina - repartidora prioritaria");
    }

    /*
     * Regla original de la actividad formativa_2
     * 10 minutos y 5 minutos adicionales
     * cuando la distancia supera los 5 km.
     */
    @Override
    public int calcularTiempoEntrega() {
        int tiempo = 10;
        if (getDistanciaKm() > 5) {
            tiempo += 5;
        }
        return tiempo;
    }
}