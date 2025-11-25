package ar.edu.unlu.poo.juegodemesa.modelo;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Jugador {
    private String nombre;
    private List<Carta> mano;
    private int puntaje;
    private Set<String> palosCantados;


    public Jugador(String nombre) {
        this.nombre = nombre;
        mano = new ArrayList<Carta>();
        this.puntaje = 0;
        this.palosCantados = new HashSet<>();
    }


    public boolean yaCantoPalo(String palo) {
        return palosCantados.contains(palo);
    }

    public void registrarCantoRealizado(String palo) {
        if (!palo.equals("Todos")) { // "Todos" es para el Tute
            this.palosCantados.add(palo);
        }
    }

    public void resetearCantos() {
        this.palosCantados.clear();
        this.puntaje = 0;
    }

    public void sumarPuntos(int puntosSumados) {
        this.puntaje += puntosSumados;
    }

    public int getPuntaje() {
        return this.puntaje;
    }

    public void recibirCarta(Carta carta) {
        if (carta != null) {
            this.mano.add(carta);
        }
    }

    public List<Carta> getMano(){
        return mano;
    }

    public String getNombre() {
        return this.nombre;
    }

}
