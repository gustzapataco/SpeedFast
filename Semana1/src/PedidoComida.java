public class PedidoComida extends Pedido {
    public PedidoComida(int idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Comida");
    }

    @Override
    public void asignarRepartidor() {
        System.out.println(
                "Asignando repartidor..." +
                "\nbuscando repartidor con mochila termica.....OK"
        );
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println(
                "Repartidor " + nombreRepartidor +
                " asignado."
                );
    }
}