package model;

public class PedidoExpress extends Pedido {

    public PedidoExpress(int idPedido,
                         String direccionEntrega,
                         double distanciaKm) {

        super(
                idPedido,
                direccionEntrega,
                "EXPRESS",
                distanciaKm
        );
    }

    @Override
    public void asignarRepartidor() {

        System.out.println(
                "Pedido express " + getIdPedido()
                        + ": buscando al repartidor más cercano "
                        + "con disponibilidad inmediata."
        );

        agregarAlHistorial(
                "Se solicitó al repartidor más cercano disponible."
        );
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {

        System.out.println(
                "Pedido express " + getIdPedido()
                        + ": " + nombreRepartidor
                        + " fue asignado por cercanía "
                        + "y disponibilidad inmediata."
        );

        agregarAlHistorial(
                "Repartidor asignado: " + nombreRepartidor
                        + " por cercanía y disponibilidad."
        );
    }

    @Override
    public int calcularTiempoEntrega() {

        int tiempo = 10;

        if (getDistanciaKm() > 5) {
            tiempo = tiempo + 5;
        }

        return tiempo;
    }
}