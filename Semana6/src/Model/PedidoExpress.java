package model;

public class PedidoExpress extends Pedido {

    public PedidoExpress(String idPedido,
                         String direccionEntrega,
                         double distanciaKm) {

        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        return 10 + (int) (getDistanciaKm() * 2);
    }

    @Override
    public String getTipoPedido() {
        return "Express";
    }
}