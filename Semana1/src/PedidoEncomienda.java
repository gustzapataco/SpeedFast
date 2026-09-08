public class PedidoEncomienda extends Pedido{

    public PedidoEncomienda(int idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Encomienda");
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Asignando repartidor...\nValidando peso y embalaje.....OK");
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println(
                "Repartidor " + nombreRepartidor +
                " asignado.");
    }
}