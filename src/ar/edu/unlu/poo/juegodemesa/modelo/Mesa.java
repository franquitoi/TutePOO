package ar.edu.unlu.poo.juegodemesa.modelo;

import ar.edu.unlu.poo.juegodemesa.observer.Observable;
import ar.edu.unlu.poo.juegodemesa.observer.Observador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Mesa implements Observable {
    private List<Observador> observadores;
    private ArrayList<Jugador> jugadoresEnPartida;
    private Mazo mazoPartida;
    private int indiceProximoMano;
    private Ronda rondaActual;

    public Mesa() {
        this.observadores = new ArrayList<>();
        this.jugadoresEnPartida = new ArrayList<>(); // ¡Inicializala!
        this.mazoPartida = new Mazo(); // ¡Inicializalo!
        this.indiceProximoMano = 0;
    }

    public void agregarJugador(Jugador jugador){
        this.jugadoresEnPartida.add(jugador);
    }

    // metodo que mezcla mazo y crea un nuevo objeto ronda el cual reparte
    public void iniciarPartida(){
        for (Jugador j : jugadoresEnPartida) {
            j.resetearCantos();
        }
        mazoPartida.mezclar();
        rondaActual = new Ronda(jugadoresEnPartida,mazoPartida,indiceProximoMano);
        rondaActual.repartir();
        this.indiceProximoMano = indiceProximoMano + 1; // para que de toda la vuelta al terminar una ronda (que no haya mas cartas)
        notificarObservadores();
    }

    //metodo que conecta con el controlador la mesa y la ronda
    public boolean jugarCarta(Jugador jugador, int indiceCarta) {
        if (rondaActual == null) return false;

        // se consigue la carta real de la mano del jugador mediante el índice
        Carta carta = jugador.getMano().get(indiceCarta);

        // se juega la carta en la rondaActual (le derivo la responsabilidad y lo haga la ronda)
        boolean jugadaExitosa = rondaActual.jugarCarta(jugador, carta);

        if (jugadaExitosa) {
            // saco la carta de la mano del jugador solo si fue válida
            jugador.getMano().remove(indiceCarta);
            if (!rondaActual.isBazaRecienTerminada()) {
                notificarObservadores(); // aviso a la vista que cambie únicamente si la ronda no termino
            }
        }
        return jugadaExitosa;
    }

    public Map<Jugador, Carta> getCartasEnMesa() {
        if (rondaActual == null) {
            return null;
        }
        return rondaActual.getCartasEnJuego(); // delego a Ronda
    }

    public boolean huboBazaTerminada() {
        if (rondaActual == null) return false;
        return rondaActual.isBazaRecienTerminada();
    }

    public Jugador getGanadorUltimaBaza() {
        return rondaActual.getGanadorUltimaBaza();
    }

    public int getPuntosUltimaBaza() {
        return rondaActual.getPuntosUltimaBaza();
    }

    // "limpia" la notificación después de leerla
    public void confirmarBazaLeida() {
        if (rondaActual != null) {
            rondaActual.apagarFlagBaza();
        }
    }

    public Jugador getJugadorActual() {
        if (rondaActual == null) return null;
        return rondaActual.getJugadorTurnoActual();
    }

    public Jugador getJugadorEnPartida(int indiceJugador) {
        return jugadoresEnPartida.get(indiceJugador);
    }

    // se fija si los jugadores no tienen cartas, en el caso que no, terminó
    public boolean isRondaTerminada() {
        for (Jugador j : jugadoresEnPartida) {
            // si AL MENOS UNO todavía tiene cartas, la ronda NO terminó
            if (!j.getMano().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int getCantidadJugadores() {
        return jugadoresEnPartida.size();
    }

    public boolean huboDiezUltimas() {
        if (rondaActual == null) return false;
        return rondaActual.huboDiezUltimas();
    }

    public String getPaloTriunfo() {
        if (rondaActual == null) return null;
        return rondaActual.getPaloTriunfo();
    }

    public String getCantoUltimaBaza() {
        if (rondaActual == null) return null;
        return rondaActual.getCantoUltimaBaza();
    }

    @Override
    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    @Override
    public void quitarObservador(Observador observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificarObservadores() {
        for (Observador observador : observadores) {
            observador.actualizar();
        }
    }

}
