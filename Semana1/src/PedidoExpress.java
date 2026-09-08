public class PedidoExpress extends Pedido {
    public PedidoExpress(int idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Compra Express");
    }
    @Override
    public void asignarRepartidor() {
        System.out.println(
                "Asignando repartidor...\nBuscando repartidor cercano.....OK"
        );
    }
    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println(
                "Repartidor " +  nombreRepartidor +
                " encontrado con disponibilidad inmediata."
        );
    }
}