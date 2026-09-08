//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        PedidoComida comida =
                new PedidoComida(1, "Av. Providencia 1200");
        Pedido encomienda =
                new PedidoEncomienda(2, "Av. Apoquindo 3500");
        PedidoExpress express =
                new PedidoExpress(3, "Av. Irarrazaval 2500");

        System.out.println("[PEDIDO DE COMIDA]");
        comida.asignarRepartidor();
        comida.asignarRepartidor("Francisco Perez");

        System.out.println("\n[PEDIDO DE ENCOMIENDA]");
        encomienda.asignarRepartidor();
        encomienda.asignarRepartidor("Camila Soto");

        System.out.println("\n[PEDIDO EXPRESS]");
        express.asignarRepartidor();
        express.asignarRepartidor("Pedro Diaz");
        /*
        System.out.println("\n===== POLIMORFISMO =====");

        Pedido[] pedidos = {
                comida,
                encomienda,
                express
        };

        for (Pedido pedido : pedidos) {
            pedido.asignarRepartidor();
        }
        */
    }
}