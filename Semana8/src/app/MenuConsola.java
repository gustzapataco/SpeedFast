package app;

import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;

import model.Entrega;
import model.EstadoPedido;
import model.Pedido;
import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;
import model.Repartidor;
import model.ZonaDeCarga;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MenuConsola {

    private final Scanner teclado;

    private final RepartidorDAO repartidorDAO;
    private final PedidoDAO pedidoDAO;
    private final EntregaDAO entregaDAO;

    public MenuConsola() {

        teclado = new Scanner(System.in);

        repartidorDAO = new RepartidorDAO();
        pedidoDAO = new PedidoDAO();
        entregaDAO = new EntregaDAO();
    }

    public void iniciar() {

        int opcion;

        do {
            mostrarMenuPrincipal();

            opcion = leerEntero(
                    "Seleccione una opción: "
            );

            try {
                switch (opcion) {

                    case 1:
                        menuRepartidores();
                        break;

                    case 2:
                        menuPedidos();
                        break;

                    case 3:
                        menuEntregas();
                        break;

                    case 4:
                        simularEntregasConcurrentes();
                        break;

                    case 5:
                        System.out.println(
                                "\nCerrando SpeedFast..."
                        );
                        break;

                    default:
                        System.out.println(
                                "\n[ERROR] Opción no válida."
                        );
                }

            } catch (Exception e) {

                System.out.println(
                        "\n[ERROR] Ocurrió un problema: "
                                + e.getMessage()
                );
            }

        } while (opcion != 5);
    }

    private void mostrarMenuPrincipal() {

        System.out.println(
                "\n========================================"
        );

        System.out.println(
                "               SPEEDFAST"
        );

        System.out.println(
                "========================================"
        );

        System.out.println(
                "1. Gestionar repartidores"
        );

        System.out.println(
                "2. Gestionar pedidos"
        );

        System.out.println(
                "3. Gestionar entregas"
        );

        System.out.println(
                "4. Simular entregas concurrentes"
        );

        System.out.println(
                "5. Salir"
        );

        System.out.println(
                "========================================"
        );
    }

    // ==================================================
    // MENÚ DE REPARTIDORES
    // ==================================================

    private void menuRepartidores() {

        int opcion;

        do {
            System.out.println(
                    "\n--- GESTIÓN DE REPARTIDORES ---"
            );

            System.out.println(
                    "1. Registrar repartidor"
            );

            System.out.println(
                    "2. Listar repartidores"
            );

            System.out.println(
                    "3. Editar repartidor"
            );

            System.out.println(
                    "4. Eliminar repartidor"
            );

            System.out.println(
                    "5. Volver al menú principal"
            );

            opcion = leerEntero(
                    "Seleccione una opción: "
            );

            switch (opcion) {

                case 1:
                    registrarRepartidor();
                    break;

                case 2:
                    listarRepartidores();
                    break;

                case 3:
                    editarRepartidor();
                    break;

                case 4:
                    eliminarRepartidor();
                    break;

                case 5:
                    break;

                default:
                    System.out.println(
                            "[ERROR] Opción no válida."
                    );
            }

        } while (opcion != 5);
    }

    private void registrarRepartidor() {

        System.out.println(
                "\n--- REGISTRAR REPARTIDOR ---"
        );

        String nombre = leerTextoObligatorio(
                "Ingrese el nombre: "
        );

        Repartidor repartidor =
                new Repartidor(nombre);

        repartidorDAO.create(repartidor);
    }

    private void listarRepartidores() {

        List<Repartidor> repartidores =
                repartidorDAO.readAll();

        mostrarRepartidores(repartidores);
    }

    private void editarRepartidor() {

        System.out.println(
                "\n--- EDITAR REPARTIDOR ---"
        );

        List<Repartidor> repartidores =
                repartidorDAO.readAll();

        if (repartidores.isEmpty()) {

            System.out.println(
                    "No existen repartidores registrados."
            );

            return;
        }

        mostrarRepartidores(repartidores);

        int id = leerEnteroPositivo(
                "Ingrese el ID a editar: "
        );

        Repartidor repartidor =
                buscarRepartidor(
                        repartidores,
                        id
                );

        if (repartidor == null) {

            System.out.println(
                    "[ERROR] El repartidor no existe."
            );

            return;
        }

        String nuevoNombre =
                leerTextoObligatorio(
                        "Ingrese el nuevo nombre: "
                );

        repartidor.setNombre(nuevoNombre);

        repartidorDAO.update(repartidor);
    }

    private void eliminarRepartidor() {

        System.out.println(
                "\n--- ELIMINAR REPARTIDOR ---"
        );

        List<Repartidor> repartidores =
                repartidorDAO.readAll();

        if (repartidores.isEmpty()) {

            System.out.println(
                    "No existen repartidores registrados."
            );

            return;
        }

        mostrarRepartidores(repartidores);

        int id = leerEnteroPositivo(
                "Ingrese el ID a eliminar: "
        );

        Repartidor repartidor =
                buscarRepartidor(
                        repartidores,
                        id
                );

        if (repartidor == null) {

            System.out.println(
                    "[ERROR] El repartidor no existe."
            );

            return;
        }

        repartidorDAO.delete(id);
    }

    private void mostrarRepartidores(
            List<Repartidor> repartidores) {

        System.out.println(
                "\n--- LISTADO DE REPARTIDORES ---"
        );

        if (repartidores.isEmpty()) {

            System.out.println(
                    "No existen repartidores registrados."
            );

            return;
        }

        System.out.println(
                "------------------------------------------"
        );

        System.out.printf(
                "%-8s %-30s%n",
                "ID",
                "NOMBRE"
        );

        System.out.println(
                "------------------------------------------"
        );

        for (Repartidor repartidor : repartidores) {

            System.out.printf(
                    "%-8d %-30s%n",
                    repartidor.getId(),
                    repartidor.getNombre()
            );
        }

        System.out.println(
                "------------------------------------------"
        );
    }

    // ==================================================
    // MENÚ DE PEDIDOS
    // ==================================================

    private void menuPedidos() {

        int opcion;

        do {
            System.out.println(
                    "\n--- GESTIÓN DE PEDIDOS ---"
            );

            System.out.println(
                    "1. Registrar pedido"
            );

            System.out.println(
                    "2. Listar pedidos"
            );

            System.out.println(
                    "3. Editar pedido"
            );

            System.out.println(
                    "4. Eliminar pedido"
            );

            System.out.println(
                    "5. Volver al menú principal"
            );

            opcion = leerEntero(
                    "Seleccione una opción: "
            );

            switch (opcion) {

                case 1:
                    registrarPedido();
                    break;

                case 2:
                    listarPedidos();
                    break;

                case 3:
                    editarPedido();
                    break;

                case 4:
                    eliminarPedido();
                    break;

                case 5:
                    break;

                default:
                    System.out.println(
                            "[ERROR] Opción no válida."
                    );
            }

        } while (opcion != 5);
    }

    private void registrarPedido() {

        System.out.println(
                "\n--- REGISTRAR PEDIDO ---"
        );

        String direccion =
                leerTextoObligatorio(
                        "Ingrese la dirección: "
                );

        String tipo =
                leerTipoPedido();

        EstadoPedido estado =
                leerEstadoPedido();

        Pedido pedido =
                crearPedido(
                        0,
                        direccion,
                        tipo
                );

        if (pedido == null) {

            System.out.println(
                    "[ERROR] No fue posible crear el pedido."
            );

            return;
        }

        pedido.setEstado(estado);

        pedidoDAO.create(pedido);
    }

    private void listarPedidos() {

        List<Pedido> pedidos =
                pedidoDAO.readAll();

        mostrarPedidos(pedidos);
    }

    private void editarPedido() {

        System.out.println(
                "\n--- EDITAR PEDIDO ---"
        );

        List<Pedido> pedidos =
                pedidoDAO.readAll();

        if (pedidos.isEmpty()) {

            System.out.println(
                    "No existen pedidos registrados."
            );

            return;
        }

        mostrarPedidos(pedidos);

        int id = leerEnteroPositivo(
                "Ingrese el ID a editar: "
        );

        Pedido pedidoExistente =
                buscarPedido(
                        pedidos,
                        id
                );

        if (pedidoExistente == null) {

            System.out.println(
                    "[ERROR] El pedido no existe."
            );

            return;
        }

        String direccion =
                leerTextoObligatorio(
                        "Ingrese la nueva dirección: "
                );

        String tipo =
                leerTipoPedido();

        EstadoPedido estado =
                leerEstadoPedido();

        Pedido pedidoActualizado =
                crearPedido(
                        id,
                        direccion,
                        tipo
                );

        if (pedidoActualizado == null) {

            System.out.println(
                    "[ERROR] No fue posible preparar "
                            + "la actualización."
            );

            return;
        }

        pedidoActualizado.setEstado(estado);

        pedidoDAO.update(
                pedidoActualizado
        );
    }

    private void eliminarPedido() {

        System.out.println(
                "\n--- ELIMINAR PEDIDO ---"
        );

        List<Pedido> pedidos =
                pedidoDAO.readAll();

        if (pedidos.isEmpty()) {

            System.out.println(
                    "No existen pedidos registrados."
            );

            return;
        }

        mostrarPedidos(pedidos);

        int id = leerEnteroPositivo(
                "Ingrese el ID a eliminar: "
        );

        Pedido pedido =
                buscarPedido(
                        pedidos,
                        id
                );

        if (pedido == null) {

            System.out.println(
                    "[ERROR] El pedido no existe."
            );

            return;
        }

        pedidoDAO.delete(id);
    }

    private void mostrarPedidos(
            List<Pedido> pedidos) {

        System.out.println(
                "\n--- LISTADO DE PEDIDOS ---"
        );

        if (pedidos.isEmpty()) {

            System.out.println(
                    "No existen pedidos registrados."
            );

            return;
        }

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        System.out.printf(
                "%-7s %-32s %-14s %-14s%n",
                "ID",
                "DIRECCIÓN",
                "TIPO",
                "ESTADO"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        for (Pedido pedido : pedidos) {

            System.out.printf(
                    "%-7d %-32s %-14s %-14s%n",
                    pedido.getIdPedido(),
                    pedido.getDireccionEntrega(),
                    pedido.getTipoPedido(),
                    pedido.getEstado()
            );
        }

        System.out.println(
                "--------------------------------------------------------------------------"
        );
    }

    // ==================================================
    // MENÚ DE ENTREGAS
    // ==================================================

    private void menuEntregas() {

        int opcion;

        do {
            System.out.println(
                    "\n--- GESTIÓN DE ENTREGAS ---"
            );

            System.out.println(
                    "1. Registrar entrega"
            );

            System.out.println(
                    "2. Listar entregas"
            );

            System.out.println(
                    "3. Editar entrega"
            );

            System.out.println(
                    "4. Eliminar entrega"
            );

            System.out.println(
                    "5. Volver al menú principal"
            );

            opcion = leerEntero(
                    "Seleccione una opción: "
            );

            switch (opcion) {

                case 1:
                    registrarEntrega();
                    break;

                case 2:
                    listarEntregas();
                    break;

                case 3:
                    editarEntrega();
                    break;

                case 4:
                    eliminarEntrega();
                    break;

                case 5:
                    break;

                default:
                    System.out.println(
                            "[ERROR] Opción no válida."
                    );
            }

        } while (opcion != 5);
    }

    private void registrarEntrega() {

        System.out.println(
                "\n--- REGISTRAR ENTREGA ---"
        );

        List<Pedido> pedidos =
                pedidoDAO.readAll();

        List<Repartidor> repartidores =
                repartidorDAO.readAll();

        if (pedidos.isEmpty()) {

            System.out.println(
                    "[ERROR] Primero debe registrar "
                            + "al menos un pedido."
            );

            return;
        }

        if (repartidores.isEmpty()) {

            System.out.println(
                    "[ERROR] Primero debe registrar "
                            + "al menos un repartidor."
            );

            return;
        }

        mostrarPedidos(pedidos);

        int idPedido =
                leerEnteroPositivo(
                        "Seleccione el ID del pedido: "
                );

        Pedido pedido =
                buscarPedido(
                        pedidos,
                        idPedido
                );

        if (pedido == null) {

            System.out.println(
                    "[ERROR] El pedido seleccionado "
                            + "no existe."
            );

            return;
        }

        mostrarRepartidores(repartidores);

        int idRepartidor =
                leerEnteroPositivo(
                        "Seleccione el ID del repartidor: "
                );

        Repartidor repartidor =
                buscarRepartidor(
                        repartidores,
                        idRepartidor
                );

        if (repartidor == null) {

            System.out.println(
                    "[ERROR] El repartidor seleccionado "
                            + "no existe."
            );

            return;
        }

        LocalDate fecha =
                leerFecha();

        LocalTime hora =
                leerHora();

        Entrega entrega =
                new Entrega(
                        idPedido,
                        idRepartidor,
                        fecha,
                        hora
                );

        entregaDAO.create(entrega);
    }

    private void listarEntregas() {

        List<Entrega> entregas =
                entregaDAO.readAll();

        mostrarEntregas(entregas);
    }

    private void editarEntrega() {

        System.out.println(
                "\n--- EDITAR ENTREGA ---"
        );

        List<Entrega> entregas =
                entregaDAO.readAll();

        if (entregas.isEmpty()) {

            System.out.println(
                    "No existen entregas registradas."
            );

            return;
        }

        mostrarEntregas(entregas);

        int id = leerEnteroPositivo(
                "Ingrese el ID de la entrega: "
        );

        Entrega entrega =
                buscarEntrega(
                        entregas,
                        id
                );

        if (entrega == null) {

            System.out.println(
                    "[ERROR] La entrega no existe."
            );

            return;
        }

        List<Pedido> pedidos =
                pedidoDAO.readAll();

        List<Repartidor> repartidores =
                repartidorDAO.readAll();

        mostrarPedidos(pedidos);

        int idPedido =
                leerEnteroPositivo(
                        "Ingrese el nuevo ID del pedido: "
                );

        if (buscarPedido(
                pedidos,
                idPedido) == null) {

            System.out.println(
                    "[ERROR] El pedido no existe."
            );

            return;
        }

        mostrarRepartidores(repartidores);

        int idRepartidor =
                leerEnteroPositivo(
                        "Ingrese el nuevo ID del repartidor: "
                );

        if (buscarRepartidor(
                repartidores,
                idRepartidor) == null) {

            System.out.println(
                    "[ERROR] El repartidor no existe."
            );

            return;
        }

        LocalDate fecha =
                leerFecha();

        LocalTime hora =
                leerHora();

        entrega.setIdPedido(idPedido);

        entrega.setIdRepartidor(
                idRepartidor
        );

        entrega.setFecha(fecha);
        entrega.setHora(hora);

        entregaDAO.update(entrega);
    }

    private void eliminarEntrega() {

        System.out.println(
                "\n--- ELIMINAR ENTREGA ---"
        );

        List<Entrega> entregas =
                entregaDAO.readAll();

        if (entregas.isEmpty()) {

            System.out.println(
                    "No existen entregas registradas."
            );

            return;
        }

        mostrarEntregas(entregas);

        int id = leerEnteroPositivo(
                "Ingrese el ID a eliminar: "
        );

        Entrega entrega =
                buscarEntrega(
                        entregas,
                        id
                );

        if (entrega == null) {

            System.out.println(
                    "[ERROR] La entrega no existe."
            );

            return;
        }

        entregaDAO.delete(id);
    }

    private void mostrarEntregas(
            List<Entrega> entregas) {

        System.out.println(
                "\n--- LISTADO DE ENTREGAS ---"
        );

        if (entregas.isEmpty()) {

            System.out.println(
                    "No existen entregas registradas."
            );

            return;
        }

        System.out.println(
                "---------------------------------------------------------------------"
        );

        System.out.printf(
                "%-6s %-12s %-16s %-14s %-12s%n",
                "ID",
                "PEDIDO",
                "REPARTIDOR",
                "FECHA",
                "HORA"
        );

        System.out.println(
                "---------------------------------------------------------------------"
        );

        for (Entrega entrega : entregas) {

            System.out.printf(
                    "%-6d %-12d %-16d %-14s %-12s%n",
                    entrega.getId(),
                    entrega.getIdPedido(),
                    entrega.getIdRepartidor(),
                    entrega.getFecha(),
                    entrega.getHora()
            );
        }

        System.out.println(
                "---------------------------------------------------------------------"
        );
    }

    // ==================================================
    // SIMULACIÓN CONCURRENTE
    // ==================================================

    private void simularEntregasConcurrentes() {

        System.out.println(
                "\n--- SIMULACIÓN CONCURRENTE ---"
        );

        List<Pedido> todosLosPedidos =
                pedidoDAO.readAll();

        List<Repartidor> repartidores =
                repartidorDAO.readAll();

        List<Pedido> pedidosPendientes =
                new ArrayList<>();

        for (Pedido pedido : todosLosPedidos) {

            if (pedido.getEstado()
                    == EstadoPedido.PENDIENTE) {

                pedidosPendientes.add(pedido);
            }
        }

        if (pedidosPendientes.isEmpty()) {

            System.out.println(
                    "No existen pedidos PENDIENTES."
            );

            return;
        }

        if (repartidores.size() < 3) {

            System.out.println(
                    "[ERROR] Debe registrar al menos "
                            + "tres repartidores."
            );

            System.out.println(
                    "Repartidores actuales: "
                            + repartidores.size()
            );

            return;
        }

        System.out.println(
                "Pedidos pendientes: "
                        + pedidosPendientes.size()
        );

        System.out.println(
                "Se utilizarán los primeros "
                        + "tres repartidores registrados."
        );

        ZonaDeCarga zonaDeCarga =
                new ZonaDeCarga();

        for (Pedido pedido : pedidosPendientes) {

            zonaDeCarga.agregarPedido(pedido);
        }

        List<Repartidor> repartidoresActivos =
                new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            Repartidor repartidor =
                    repartidores.get(i);

            repartidor.setZonaDeCarga(
                    zonaDeCarga
            );

            repartidoresActivos.add(
                    repartidor
            );
        }

        ExecutorService ejecutor =
                Executors.newFixedThreadPool(3);

        try {
            System.out.println(
                    "\nIniciando a los repartidores..."
            );

            for (Repartidor repartidor
                    : repartidoresActivos) {

                ejecutor.execute(repartidor);
            }

            ejecutor.shutdown();

            boolean finalizaron =
                    ejecutor.awaitTermination(
                            2,
                            TimeUnit.MINUTES
                    );

            if (!finalizaron) {

                System.out.println(
                        "[ERROR] Las entregas no terminaron "
                                + "dentro del tiempo esperado."
                );

                ejecutor.shutdownNow();
                return;
            }

            guardarResultadosEntregas(
                    repartidoresActivos
            );

            System.out.println(
                    "\n========================================"
            );

            System.out.println(
                    "Todos los pedidos fueron procesados."
            );

            System.out.println(
                    "Estados y entregas guardados en MySQL."
            );

            System.out.println(
                    "========================================"
            );

        } catch (InterruptedException e) {

            System.out.println(
                    "[ERROR] La simulación fue interrumpida: "
                            + e.getMessage()
            );

            ejecutor.shutdownNow();

            Thread.currentThread().interrupt();

        } catch (Exception e) {

            System.out.println(
                    "[ERROR] No fue posible completar "
                            + "la simulación: "
                            + e.getMessage()
            );

            ejecutor.shutdownNow();
        }
    }

    private void guardarResultadosEntregas(
            List<Repartidor> repartidores) {

        System.out.println(
                "\nGuardando resultados en MySQL..."
        );

        for (Repartidor repartidor : repartidores) {

            for (Pedido pedido
                    : repartidor.getPedidosEntregados()) {

                boolean actualizado =
                        pedidoDAO.update(pedido);

                if (actualizado) {

                    Entrega entrega =
                            new Entrega(
                                    pedido.getIdPedido(),
                                    repartidor.getId(),
                                    LocalDate.now(),
                                    LocalTime.now()
                                            .withNano(0)
                            );

                    entregaDAO.create(entrega);
                }
            }
        }
    }

    // ==================================================
    // BÚSQUEDAS
    // ==================================================

    private Repartidor buscarRepartidor(
            List<Repartidor> repartidores,
            int id) {

        for (Repartidor repartidor : repartidores) {

            if (repartidor.getId() == id) {
                return repartidor;
            }
        }

        return null;
    }

    private Pedido buscarPedido(
            List<Pedido> pedidos,
            int id) {

        for (Pedido pedido : pedidos) {

            if (pedido.getIdPedido() == id) {
                return pedido;
            }
        }

        return null;
    }

    private Entrega buscarEntrega(
            List<Entrega> entregas,
            int id) {

        for (Entrega entrega : entregas) {

            if (entrega.getId() == id) {
                return entrega;
            }
        }

        return null;
    }

    // ==================================================
    // CREACIÓN POLIMÓRFICA DE PEDIDOS
    // ==================================================

    private Pedido crearPedido(
            int id,
            String direccion,
            String tipo) {

        switch (tipo) {

            case "COMIDA":

                return new PedidoComida(
                        id,
                        direccion,
                        0
                );

            case "ENCOMIENDA":

                return new PedidoEncomienda(
                        id,
                        direccion,
                        0
                );

            case "EXPRESS":

                return new PedidoExpress(
                        id,
                        direccion,
                        0
                );

            default:
                return null;
        }
    }

    // ==================================================
    // VALIDACIÓN DE ENTRADAS
    // ==================================================

    private int leerEntero(
            String mensaje) {

        while (true) {

            try {
                System.out.print(mensaje);

                String entrada =
                        teclado.nextLine().trim();

                return Integer.parseInt(entrada);

            } catch (NumberFormatException e) {

                System.out.println(
                        "[ERROR] Debe ingresar "
                                + "un número entero."
                );
            }
        }
    }

    private int leerEnteroPositivo(
            String mensaje) {

        while (true) {

            int numero =
                    leerEntero(mensaje);

            if (numero > 0) {
                return numero;
            }

            System.out.println(
                    "[ERROR] El número debe ser "
                            + "mayor que cero."
            );
        }
    }

    private String leerTextoObligatorio(
            String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String texto =
                    teclado.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println(
                    "[ERROR] Este campo es obligatorio."
            );
        }
    }

    private String leerTipoPedido() {

        while (true) {

            System.out.println(
                    "\nTIPO DE PEDIDO"
            );

            System.out.println("1. COMIDA");
            System.out.println("2. ENCOMIENDA");
            System.out.println("3. EXPRESS");

            int opcion = leerEntero(
                    "Seleccione el tipo: "
            );

            switch (opcion) {

                case 1:
                    return "COMIDA";

                case 2:
                    return "ENCOMIENDA";

                case 3:
                    return "EXPRESS";

                default:
                    System.out.println(
                            "[ERROR] Tipo no válido."
                    );
            }
        }
    }

    private EstadoPedido leerEstadoPedido() {

        while (true) {

            System.out.println(
                    "\nESTADO DEL PEDIDO"
            );

            System.out.println("1. PENDIENTE");
            System.out.println("2. EN_REPARTO");
            System.out.println("3. ENTREGADO");

            int opcion = leerEntero(
                    "Seleccione el estado: "
            );

            switch (opcion) {

                case 1:
                    return EstadoPedido.PENDIENTE;

                case 2:
                    return EstadoPedido.EN_REPARTO;

                case 3:
                    return EstadoPedido.ENTREGADO;

                default:
                    System.out.println(
                            "[ERROR] Estado no válido."
                    );
            }
        }
    }

    private LocalDate leerFecha() {

        while (true) {

            try {
                System.out.print(
                        "Ingrese la fecha (AAAA-MM-DD): "
                );

                String entrada =
                        teclado.nextLine().trim();

                return LocalDate.parse(entrada);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "[ERROR] Fecha inválida."
                );

                System.out.println(
                        "Ejemplo correcto: 2026-10-04"
                );
            }
        }
    }

    private LocalTime leerHora() {

        while (true) {

            try {
                System.out.print(
                        "Ingrese la hora (HH:MM): "
                );

                String entrada =
                        teclado.nextLine().trim();

                return LocalTime.parse(entrada);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "[ERROR] Hora inválida."
                );

                System.out.println(
                        "Ejemplo correcto: 15:30"
                );
            }
        }
    }
}