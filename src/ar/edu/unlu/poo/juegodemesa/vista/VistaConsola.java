package ar.edu.unlu.poo.juegodemesa.vista;
import ar.edu.unlu.poo.juegodemesa.modelo.Carta;
import ar.edu.unlu.poo.juegodemesa.modelo.Jugador;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VistaConsola {
    private Scanner sc = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println("\n--- Tute en POO ---");
        System.out.println("1. Dos jugadores");
        System.out.println("2. Tres jugadores");
        System.out.println("3. Cuatro jugadores");
        System.out.println("0. Salir del juego");
        System.out.print("Seleccione una opción: ");
    }

    // metodo que obtiene opcion para el menu principal
    public int obtenerOpcion() {
        int opcion = -1;
        try {
            String input = sc.nextLine(); // se lee el renglon entero
            if(!input.trim().isEmpty()) {
                opcion = Integer.parseInt(input);
            }
        } catch (NumberFormatException e) {
            opcion = -1;
        }
        return opcion;
    }

    public String agregarJugador() {
        String nombre = "";
        // el bucle sigue mientras el nombre esté vacío o sean solo espacios (trim los quita)
        while (nombre.trim().isEmpty()) {
            System.out.print("Ingrese el nombre del jugador: ");
            nombre = sc.nextLine();

            if (nombre.trim().isEmpty()) {
                System.out.println("El nombre no puede estar vacío, escribí algo");
            }
        }
        return nombre;
    }

    // muestra las cartas con un numerito al lado: "[0] 1 de Espada", "[1] 7 de Oro"...
    public void mostrarMano(List<Carta> mano) {
        System.out.println("Tu mano:");
        for (int i = 0; i < mano.size(); i++) {
            Carta c = mano.get(i);
            System.out.println(" [" + i + "] " + c.getNumero() + " de " + c.getPalo());
        }
    }

    // se le pide al jugador que elija una de las cartas de su mano
    public int pedirIndiceCarta(int cantidadCartas) {
        System.out.print("Elegí el número de carta a jugar (0-" + (cantidadCartas - 1) + "): ");
        // validar que sea entero...
        int opcion = -1;
        while (true) {
            try {
                String input = sc.nextLine();
                opcion = Integer.parseInt(input);
                if (opcion >= 0 && opcion < cantidadCartas) {
                    break;
                }
                System.out.print("Número fuera de rango, intentá de nuevo: ");
            } catch (NumberFormatException e) {
                System.out.print("Por favor ingresá un número válido: ");
            }
        }
        return opcion;
    }

    //una mini pausa
    public void pausaParaContinuar() {
        System.out.println("Presioná Enter para continuar...");
        sc.nextLine(); // limpia el buffer...
    }

    public void mostrarResultadoBaza(String nombreGanador, int puntos) {
        System.out.println("\n------------------------------------------------");
        System.out.println("¡BAZA TERMINADA!");
        System.out.println("Ganador: " + nombreGanador);
        System.out.println("Puntos sumados: " + puntos);
        System.out.println("------------------------------------------------\n");
    }

    public void limpiarPantalla() {
        System.out.println("\n\n\n\n\n");
    }

    // metodo clave que muestra el palo del triunfo,
    public void mostrarEstadoJuego(String paloTriunfo, Map<Jugador, Carta> cartasEnMesa) {
        System.out.println("=== ESTADO DE LA MESA ===");
        System.out.println("TRIUNFO: " + (paloTriunfo != null ? paloTriunfo.toUpperCase() : "N/A"));

        if (cartasEnMesa != null && !cartasEnMesa.isEmpty()) {
            System.out.println("-------------------------");
            for (Map.Entry<Jugador, Carta> entry : cartasEnMesa.entrySet()) {
                System.out.println(" * " + entry.getKey().getNombre() + " tiró: " +
                        entry.getValue().getNumero() + " de " + entry.getValue().getPalo());
            }
        }
        System.out.println("=========================\n");
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}


