package com.techlab;

import com.techlab.excepciones.ProductoNoEncontradoException;
import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.pedidos.Pedido;
import com.techlab.productos.Bebida;
import com.techlab.productos.Comida;
import com.techlab.productos.Producto;
import com.techlab.service.ProductoService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductoService servicio = new ProductoService();
        int opcion;

        do {
            System.out.println("\n===== MENÚ TIENDA =====");
            System.out.println("1. Agregar producto");
            System.out.println("2. Listar productos");
            System.out.println("3. Buscar producto por ID");
            System.out.println("4. Actualizar precio o stock");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Crear pedido");
            System.out.println("7. Listar pedidos");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            opcion = leerEntero(scanner);

            switch (opcion) {
                case 1 -> agregarProducto(scanner, servicio);
                case 2 -> servicio.listarProductos();
                case 3 -> buscarProducto(scanner, servicio);
                case 4 -> actualizarProducto(scanner, servicio);
                case 5 -> eliminarProducto(scanner, servicio);
                case 6 -> crearPedido(scanner, servicio);
                case 7 -> servicio.listarPedidos();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    // ---------- MÉTODOS AUXILIARES ----------

    private static void agregarProducto(Scanner sc, ProductoService servicio) {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Precio: ");
        double precio = leerDouble(sc);

        System.out.print("Stock: ");
        int stock = leerEntero(sc);

        System.out.print("Tipo (1=Bebida, 2=Comida, otro=Genérico): ");
        int tipo = leerEntero(sc);

        try {
            if (tipo == 1) {
                System.out.print("Volumen en litros: ");
                double volumen = leerDouble(sc);
                servicio.agregarProducto(new Bebida(nombre, precio, stock, volumen));
            } else if (tipo == 2) {
                System.out.print("Fecha de vencimiento: ");
                String fecha = sc.nextLine();
                servicio.agregarProducto(new Comida(nombre, precio, stock, fecha));
            } else {
                servicio.agregarProducto(new Producto(nombre, precio, stock));
            }
            System.out.println("Producto agregado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void buscarProducto(Scanner sc, ProductoService servicio) {
        System.out.print("ID del producto: ");
        int id = leerEntero(sc);
        try {
            System.out.println(servicio.buscarPorId(id));
        } catch (ProductoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void actualizarProducto(Scanner sc, ProductoService servicio) {
        System.out.print("ID del producto: ");
        int id = leerEntero(sc);
        try {
            Producto p = servicio.buscarPorId(id);
            System.out.println("Producto actual: " + p);

            System.out.print("Nuevo precio (enter para no cambiar): ");
            String precioStr = sc.nextLine();
            if (!precioStr.isEmpty()) {
                p.setPrecio(Double.parseDouble(precioStr));
            }

            System.out.print("Nuevo stock (enter para no cambiar): ");
            String stockStr = sc.nextLine();
            if (!stockStr.isEmpty()) {
                p.setStock(Integer.parseInt(stockStr));
            }

            System.out.println("Producto actualizado: " + p);
        } catch (ProductoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Valor inválido: " + e.getMessage());
        }
    }

    private static void eliminarProducto(Scanner sc, ProductoService servicio) {
        System.out.print("ID del producto a eliminar: ");
        int id = leerEntero(sc);
        try {
            servicio.eliminarProducto(id);
        } catch (ProductoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void crearPedido(Scanner sc, ProductoService servicio) {
        Pedido pedido = servicio.crearPedido();
        String continuar;

        do {
            servicio.listarProductos();
            System.out.print("ID del producto a agregar: ");
            int id = leerEntero(sc);

            System.out.print("Cantidad: ");
            int cant = leerEntero(sc);

            try {
                servicio.agregarProductoAPedido(pedido, id, cant);
                System.out.println("Producto agregado al pedido.");
            } catch (ProductoNoEncontradoException | StockInsuficienteException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("¿Agregar otro producto? (s/n): ");
            continuar = sc.nextLine();
        } while (continuar.equalsIgnoreCase("s"));

        System.out.println("\nPedido generado:");
        System.out.println(pedido);
    }

    // ---------- LECTURA SEGURA ----------

    private static int leerEntero(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número entero válido: ");
            }
        }
    }

    private static double leerDouble(Scanner sc) {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número decimal válido: ");
            }
        }
    }
}