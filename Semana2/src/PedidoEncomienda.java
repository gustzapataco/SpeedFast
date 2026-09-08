public class PedidoEncomienda extends Pedido{

    // constructor
    public PedidoEncomienda(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        return (int)(20 + (1.5 * distanciaKm));
    }
}