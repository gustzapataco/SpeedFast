public class Pedido {
    // atributos
    protected int idPedido;
    protected String direccionEntrega;
    protected String tipoPedido;
    // constructor
    public Pedido(int idPedido, String direccionEntrega, String tipoPedido){
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
    }
    public void asignarRepartidor() {
        System.out.println("Asignando un repartidor al pedido.");
    }
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Repartidor asignado: " + nombreRepartidor);
    }
}