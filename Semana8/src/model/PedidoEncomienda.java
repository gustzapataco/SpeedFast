package model;

public class PedidoEncomienda extends Pedido {

    public PedidoEncomienda(int idPedido,
                            String direccionEntrega,
                            double distanciaKm) {

        super(
                idPedido,
                direccionEntrega,
                "ENCOMIENDA",
                distanciaKm
        );
    }

    @Override
    public void asignarRepartidor() {

        System.out.println(
                "Pedido de encomienda " + getIdPedido()
                        + ": validando el peso y el embalaje."
        );

        agregarAlHistorial(
                "Se solicitó validar el peso y el embalaje."
        );
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {

        System.out.println(
                "Pedido de encomienda " + getIdPedido()
                        + ": " + nombreRepartidor
                        + " fue asignado después de validar "
                        + "el peso y el embalaje."
        );

        agregarAlHistorial(
                "Repartidor asignado: " + nombreRepartidor
                        + ". Se validó el peso y el embalaje."
        );
    }

    @Override
    public int calcularTiempoEntrega() {

        double tiempo =
                20 + (1.5 * getDistanciaKm());

        return (int) Math.round(tiempo);
    }
}