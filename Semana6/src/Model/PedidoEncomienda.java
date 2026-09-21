package model;

public class PedidoEncomienda extends Pedido {

    public PedidoEncomienda(String idPedido,
                            String direccionEntrega,
                            double distanciaKm) {

        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        return 20 + (int) (getDistanciaKm() * 4);
    }

    @Override
    public String getTipoPedido() {
        return "Encomienda";
    }
}