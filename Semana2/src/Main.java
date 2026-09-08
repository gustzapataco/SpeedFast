//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        PedidoComida comida =
                new PedidoComida("#001", "Av. Providencia 1200", 5);
        Pedido encomienda =
                new PedidoEncomienda("#002", "Av. Apoquindo 3500", 10);
        PedidoExpress express =
                new PedidoExpress("#003", "Av. Irarrazaval 2500", 8);

        System.out.println("[PEDIDO DE COMIDA]");
        comida.mostrarResumen();
        System.out.println("Tiempo estimado: " + comida.calcularTiempoEntrega() + " minutos");

        System.out.println("\n[PEDIDO DE ENCOMIENDA]");
        encomienda.mostrarResumen();
        System.out.println("Tiempo estimado: " + encomienda.calcularTiempoEntrega() + " minutos");

        System.out.println("\n[PEDIDO EXPRESS]");
        express.mostrarResumen();
        System.out.println("Tiempo estimado: " + express.calcularTiempoEntrega() + " minutos");
    }
}