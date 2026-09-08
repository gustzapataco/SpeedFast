public class PedidoComida extends Pedido {

    // constructor
    public PedidoComida(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega,  distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {

        return (int)(15 + (2 * distanciaKm));
    }
}