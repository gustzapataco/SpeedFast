public abstract class Pedido {

    // atributos
    protected String idPedido;
    protected String direccionEntrega;
    protected double distanciaKm;

    // constructor
    public Pedido(String idPedido, String direccionEntrega, double distanciaKm){
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
    }

    public void mostrarResumen() {

        System.out.println("ID Pedido: " + idPedido);
        System.out.println("Direccion: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm + " Km");
    }

    public abstract int calcularTiempoEntrega();
}