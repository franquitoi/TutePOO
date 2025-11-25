package ar.edu.unlu.poo.juegodemesa.modelo;

import java.util.*;

public class Mazo {
    private Deque <Carta> cartas;

    public Mazo() {
        cartas = new ArrayDeque<>();
        String[] palos = {"Espada", "Basto", "Oro", "Copa"};
        // cro las 40 cartas (del 1 al 12, sin 8 y 9)
        for (String palo : palos) {
            for (int i = 1; i <= 12; i++) {
                if (i != 8 && i != 9) {
                    cartas.add(new Carta(palo, i));
                }
            }
        }
    }

    public boolean estaVacio() {
        return cartas.isEmpty();
    }

    public void mezclar(){
        // Paso 1: convierto a lista porque el deque no se puede shufflear
        List<Carta> lista = new ArrayList<>(cartas);
        // Paso 2: mezclo la lista
        Collections.shuffle(lista);
        // Paso 3: vuelvo a llenar el deque con el nuevo orden aleatorio
        cartas = new ArrayDeque<>(lista);
    }

    public Carta sacarCarta() {
        if (!this.cartas.isEmpty()) {
            return this.cartas.removeFirst();
        }
        return null;
    }
}
