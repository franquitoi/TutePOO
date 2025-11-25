package ar.edu.unlu.poo.juegodemesa.modelo;
import java.util.List;

public class GestorCantos {

    // Clase auxiliar simple para mover los datos (DTO)
    public static class ResultadoCanto {
        public int puntos;
        public String mensaje;
        public String palo;
        public Jugador autor; // Guardamos quién lo canta

        public ResultadoCanto(int p, String m, String pa, Jugador a) {
            this.puntos = p; this.mensaje = m; this.palo = pa; this.autor = a;
        }
    }

    // metodo que revisa la mano del jugador, detecta cantos y devuelve un objeto de resultado
    public ResultadoCanto analizarMano(Jugador jugador, String paloTriunfo, int cantJugadores) {
        List<Carta> mano = jugador.getMano();

        // hay tute???
        if (cantJugadores > 2) {
            int reyes = 0, caballos = 0;
            for (Carta c : mano) {
                if (c.getNumero() == 12) reyes++;
                if (c.getNumero() == 11) caballos++;
            }
            if (reyes == 4 || caballos == 4) {
                return new ResultadoCanto(1000, "TUTE", "Todos", jugador);
            }
        }

        // hay cantos de 20 o 40???
        String[] palos = {"Espada", "Basto", "Oro", "Copa"};
        ResultadoCanto mejorOpcion = null;

        for (String palo : palos) {
            // le pregunto al jugador si ya cantó ese palo (él guarda su memoria)
            if (jugador.yaCantoPalo(palo)) continue;

            boolean tieneCaballo = false;
            boolean tieneRey = false;

            for (Carta c : mano) {
                if (c.getPalo().equalsIgnoreCase(palo)) {
                    if (c.getNumero() == 11) tieneCaballo = true;
                    if (c.getNumero() == 12) tieneRey = true;
                }
            }

            if (tieneCaballo && tieneRey) {
                // si son las 40, retorna ya porque es insuperable
                if (palo.equalsIgnoreCase(paloTriunfo)) {
                    return new ResultadoCanto(40, "¡LAS CUARENTA!", palo, jugador);
                }
                // si son 20, guarda y sigue buscando por si aparece un 40
                if (mejorOpcion == null) {
                    mejorOpcion = new ResultadoCanto(20, "¡Veinte en " + palo + "!", palo, jugador);
                }
            }
        }
        return mejorOpcion;
    }


    public ResultadoCanto decidirMejorCanto(ResultadoCanto c1, ResultadoCanto c2) {
        if (c1 == null && c2 == null) return null;
        if (c1 != null && c2 == null) return c1;
        if (c1 == null && c2 != null) return c2;

        // Si ambos tienen algo, gana el de más puntos (40 > 20)
        // (Si empatan en 20, priorizamos al c1 que suele ser el mano/ganador)
        if (c1.puntos >= c2.puntos) {
            return c1;
        } else {
            return c2;
        }
    }
}
