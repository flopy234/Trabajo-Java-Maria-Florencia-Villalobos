package com.techlab.pedidos;

import java.util.ArrayList;

public class Pedido {
    private static int contadorPedidos = 0;

    private int id;
    private ArrayList<LineaPedido> lineas;

    public Pedido() {
        this.id = ++contadorPedidos;
        this.lineas = new ArrayList<>();
    }

    public int getId() { return id; }
    public ArrayList<LineaPedido> getLineas() { return lineas; }

    public void agregarLinea(LineaPedido linea) {
        lineas.add(linea);
    }

    public double calcularTotal() {
        double total = 0;
        for (LineaPedido l : lineas) {
            total += l.getSubtotal();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido #").append(id).append("\n");
        for (LineaPedido l : lineas) {
            sb.append(l).append("\n");
        }
        sb.append(String.format("  TOTAL: $%.2f", calcularTotal()));
        return sb.toString();
    }
}