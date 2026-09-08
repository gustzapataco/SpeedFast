public class PedidoExpress extends Pedido {

    // constructor
    public PedidoExpress(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }
    @Override
    public int calcularTiempoEntrega() {
        int tiempo = 10;
        if(distanciaKm > 5){
            tiempo += 5;
        }
        return tiempo;
    }
}