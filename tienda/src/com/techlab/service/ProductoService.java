package com.techlab.service;

import com.techlab.excepciones.ProductoNoEncontradoException;
import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.pedidos.LineaPedido;
import com.techlab.pedidos.Pedido;
import com.techlab.productos.Producto;

import java.util.ArrayList;

public class ProductoService {
    private ArrayList<Producto> productos = new ArrayList<>();
    private ArrayList<Pedido> pedidos = new ArrayList<>();

    // ---------- PRODUCTOS ----------
    public void agregarProducto(Producto p) {
        productos.add(p);
    }

    public void listarProductos() {
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    public Producto buscarPorId(int id) throws ProductoNoEncontradoException {
        for (Producto p : productos) {
            if (p.getId() == id) return p;
        }
        throw new ProductoNoEncontradoException("No se encontró un producto con ID " + id);
    }

    public Producto buscarPorNombre(String nombre) throws ProductoNoEncontradoException {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) return p;
        }
        throw new ProductoNoEncontradoException("No se encontró el producto: " + nombre);
    }

    public void eliminarProducto(int id) throws ProductoNoEncontradoException {
        Producto p = buscarPorId(id);
        productos.remove(p);
        System.out.println("Producto eliminado: " + p.getNombre());
    }

    // ---------- PEDIDOS ----------
    public Pedido crearPedido() {
        Pedido pedido = new Pedido();
        pedidos.add(pedido);
        return pedido;
    }

    public void agregarProductoAPedido(Pedido pedido, int idProducto, int cantidad)
            throws ProductoNoEncontradoException, StockInsuficienteException {

        Producto p = buscarPorId(idProducto);

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }
        if (cantidad > p.getStock()) {
            throw new StockInsuficienteException(
                "Stock insuficiente para " + p.getNombre() +
                ". Disponible: " + p.getStock() + ", solicitado: " + cantidad);
        }

        pedido.agregarLinea(new LineaPedido(p, cantidad));
        p.reducirStock(cantidad);
    }

    public void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println(p);
            System.out.println("-------------------------------");
        }
    }
}