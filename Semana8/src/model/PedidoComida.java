package model;

public class PedidoComida extends Pedido {

    public PedidoComida(int idPedido,
                        String direccionEntrega,
                        double distanciaKm) {

        super(
                idPedido,
                direccionEntrega,
                "COMIDA",
                distanciaKm
        );
    }

    @Override
    public void asignarRepartidor() {

        System.out.println(
                "Pedido de comida " + getIdPedido()
                        + ": buscando repartidor con mochila térmica."
        );

        agregarAlHistorial(
                "Se solicitó un repartidor con mochila térmica."
        );
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {

        System.out.println(
                "Pedido de comida " + getIdPedido()
                        + ": " + nombreRepartidor
                        + " fue asignado y debe contar "
                        + "con mochila térmica."
        );

        agregarAlHistorial(
                "Repartidor asignado: " + nombreRepartidor
                        + ". Se verificó la mochila térmica."
        );
    }

    @Override
    public int calcularTiempoEntrega() {

        double tiempo =
                15 + (2 * getDistanciaKm());

        return (int) Math.round(tiempo);
    }
}