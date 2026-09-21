package model;

public class PedidoComida extends Pedido {

    public PedidoComida(String idPedido,
                        String direccionEntrega,
                        double distanciaKm) {

        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        return 15 + (int) (getDistanciaKm() * 3);
    }

    @Override
    public String getTipoPedido() {
        return "Comida";
    }
}