package com.techlab.productos;

public class Producto {
    private static int contadorProductos = 0;

    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private boolean disponible; // uso de boolean

    public Producto(String nombre, double precio, int stock) {
        if (precio < 0) throw new IllegalArgumentException("El precio no puede ser negativo.");
        if (stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo.");

        this.id = ++contadorProductos;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.disponible = stock > 0; // operador relacional
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public boolean isDisponible() { return disponible; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public void setPrecio(double precio) {
        if (precio < 0) throw new IllegalArgumentException("El precio no puede ser negativo.");
        this.precio = precio;
    }

    public void setStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo.");
        this.stock = stock;
        this.disponible = stock > 0;
    }

    public void reducirStock(int cantidad) {
        if (cantidad > stock) throw new IllegalArgumentException("Stock insuficiente.");
        this.stock -= cantidad;
        this.disponible = this.stock > 0;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | Precio: $%.2f | Stock: %d | Disponible: %s",
                id, nombre, precio, stock, disponible ? "Sí" : "No");
    }
}