package model;

public class Pedido {
    // Atributos comunes de todos los pedidos
    private int id;
    private String direccionEntrega;
    private EstadoPedido estado;

    // Constructor
    public Pedido(int id, String direccionEntrega) {
        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.estado = EstadoPedido.PENDIENTE;
        }

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getDireccionEntrega() {
            return direccionEntrega;
        }
        public void setDireccionEntrega(String direccionEntrega) {
            this.direccionEntrega = direccionEntrega;
        }
        public EstadoPedido getEstado() {
            return estado;
        }
        public void setEstado(EstadoPedido estado) {
            this.estado = estado;
        }
        public void setEstado(String nuevoEstado) {
            this.estado = EstadoPedido.valueOf(nuevoEstado.toUpperCase());
        }

        @Override
        public String toString() {
            return "Pedido #" + id
                    + " | Direccion: " + direccionEntrega
                    + " | Estado: " + estado;
        }
}