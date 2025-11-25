package ar.edu.unlu.poo.juegodemesa.controlador;
import ar.edu.unlu.poo.juegodemesa.modelo.Carta;
import ar.edu.unlu.poo.juegodemesa.modelo.Jugador;
import ar.edu.unlu.poo.juegodemesa.modelo.Mesa;
import ar.edu.unlu.poo.juegodemesa.observer.Observador;
import ar.edu.unlu.poo.juegodemesa.vista.VistaConsola;

import java.util.Map;

public class ControladorConsola implements Observador {
    private Mesa modelo;
    private VistaConsola vista;

    public ControladorConsola(Mesa modelo, VistaConsola vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.modelo.agregarObservador(this);
    }

    public void iniciar() {
        while (true) {
            vista.mostrarMenu();
            int opcion = vista.obtenerOpcion();

            switch (opcion) {
                case 1:
                    iniciarDosJugadores();
                    break;
                case 2:
                    iniciarTresJugadores();
                    break;
                case 3:
                    iniciarCuatroJugadores();
                    break;
                case 0:
                    vista.mostrarMensaje("Gracias por jugar! (o simplemente haber ejecutado el juego)");
                    return;
                default:
                    vista.mostrarMensaje("Opción no valida.");
                    break;
            }
        }
    }

    private void iniciarDosJugadores() {
        String nombre = vista.agregarJugador();
        String nombre2 = vista.agregarJugador();
        Jugador jugador1 = new Jugador(nombre);
        Jugador jugador2 = new Jugador(nombre2);
        modelo.agregarJugador(jugador1);
        modelo.agregarJugador(jugador2);
        modelo.iniciarPartida();
        vista.mostrarMensaje("Iniciando juego con dos jugadores...");
        vista.pausaParaContinuar();

        cicloDeJuego(); //arranco el juego y si termina veo puntaje para 2 jugadores
    }


    private void iniciarTresJugadores() {
        String nombre = vista.agregarJugador();
        String nombre2 = vista.agregarJugador();
        String nombre3 = vista.agregarJugador();
        Jugador jugador1 = new Jugador(nombre);
        Jugador jugador2 = new Jugador(nombre2);
        Jugador jugador3 = new Jugador(nombre3);
        modelo.agregarJugador(jugador1);
        modelo.agregarJugador(jugador2);
        modelo.agregarJugador(jugador3);
        modelo.iniciarPartida();
        vista.mostrarMensaje("Iniciando juego con tres jugadores...");
        vista.pausaParaContinuar();

        cicloDeJuego(); //arranco el juego y si termina veo puntaje para 3 jugadores
    }

    private void iniciarCuatroJugadores() {
        String nombre = vista.agregarJugador();
        String nombre2 = vista.agregarJugador();
        String nombre3 = vista.agregarJugador();
        String nombre4 = vista.agregarJugador();
        Jugador jugador1 = new Jugador(nombre);
        Jugador jugador2 = new Jugador(nombre2);
        Jugador jugador3 = new Jugador(nombre3);
        Jugador jugador4 = new Jugador(nombre4);
        modelo.agregarJugador(jugador1);
        modelo.agregarJugador(jugador2);
        modelo.agregarJugador(jugador3);
        modelo.agregarJugador(jugador4);
        modelo.iniciarPartida();
        vista.mostrarMensaje("Iniciando juego con cuatro jugadores...");
        vista.pausaParaContinuar();

        cicloDeJuego(); //arranco el juego y si termina veo puntaje para 4 jugadores
    }

    private void cicloDeJuego() {
        // mientras la ronda no haya terminado...
        while (!modelo.isRondaTerminada()) {
            jugarTurno();  // ejecutamos un turno

            // si el juego terminó, imprimo los puntajes de cada jugador
            if (modelo.isRondaTerminada()) {
                vista.mostrarMensaje("¡FIN DE LA RONDA!");

                // **** puntaje 2 jugadores *****
                if(modelo.getCantidadJugadores() == 2){
                    Jugador jugador1 = modelo.getJugadorEnPartida(0);
                    Jugador jugador2 = modelo.getJugadorEnPartida(1);
                    vista.mostrarMensaje("El puntaje del jugador " + jugador1.getNombre() + " es de " +  jugador1.getPuntaje());
                    vista.mostrarMensaje("El puntaje del jugador " + jugador2.getNombre() + " es de" +  jugador2.getPuntaje());

                    // me fijo cual gana
                    if(jugador1.getPuntaje() > jugador2.getPuntaje()){
                        vista.mostrarMensaje("El ganador es " + jugador1.getNombre() + ", ¡Felicidades!");
                    }
                    else {
                        vista.mostrarMensaje("El ganador es " + jugador1.getNombre() + ", ¡Felicidades!");
                    }
                }
                // **** puntaje 3 jugadores *****
                else if(modelo.getCantidadJugadores() == 3){
                    Jugador jugador1 = modelo.getJugadorEnPartida(0);
                    Jugador jugador2 = modelo.getJugadorEnPartida(1);
                    Jugador jugador3 = modelo.getJugadorEnPartida(2);
                    vista.mostrarMensaje("El puntaje del jugador " + jugador1.getNombre() + " es de " +  jugador1.getPuntaje());
                    vista.mostrarMensaje("El puntaje del jugador " + jugador2.getNombre() + " es de" +  jugador2.getPuntaje());
                    vista.mostrarMensaje("El puntaje del jugador " + jugador3.getNombre() + " es de" +  jugador3.getPuntaje());


                    // me fijo cual gana
                    if(jugador1.getPuntaje() > jugador2.getPuntaje() && jugador1.getPuntaje() > jugador3.getPuntaje()){
                        vista.mostrarMensaje("El ganador es " + jugador1.getNombre() + ", ¡Felicidades!");
                    }
                    else if(jugador2.getPuntaje() > jugador1.getPuntaje() && jugador2.getPuntaje() > jugador3.getPuntaje()) {
                        vista.mostrarMensaje("El ganador es " + jugador2.getNombre() + ", ¡Felicidades!");
                    }
                    else{
                        vista.mostrarMensaje("El ganador es " + jugador3.getNombre() + ", ¡Felicidades!");
                    }
                }

                // **** puntaje 4 jugadores (2 parejas o equipos)*****
                else{
                    Jugador j1 = modelo.getJugadorEnPartida(0);
                    Jugador j2 = modelo.getJugadorEnPartida(1);
                    Jugador j3 = modelo.getJugadorEnPartida(2);
                    Jugador j4 = modelo.getJugadorEnPartida(3);

                    // sumo puntos por equipo y veo cual gana
                    int puntosEquipoA = j1.getPuntaje() + j3.getPuntaje();
                    int puntosEquipoB = j2.getPuntaje() + j4.getPuntaje();

                    vista.mostrarMensaje("El puntaje del equipo 1 (" + j1.getNombre() + " y " + j3.getNombre() + ") es de: " + puntosEquipoA);
                    vista.mostrarMensaje("El puntaje del equipo 2 (" + j2.getNombre() + " y " + j4.getNombre() + ") es de: " + puntosEquipoB);

                    if (puntosEquipoA > puntosEquipoB) {
                        vista.mostrarMensaje("El ganador es el equipo 1, ¡Felicidades!");
                    } else{
                        vista.mostrarMensaje("El ganador es el equipo 2, ¡Felicidades!");
                    }
                }

                return;
            }
        }
    }

    // metodo que gestiona el turno de un jugador
    private void jugarTurno() {

        Jugador jugadorActual = modelo.getJugadorActual(); // de quién es el turno actual?
        boolean jugadaExitosa = false;

        // BUCLE DE INTENTOS: no sale de acá hasta que juegue una carta válida
        while (!jugadaExitosa) {
            actualizar();

            vista.mostrarMensaje("\n>>> Es el turno de: " + jugadorActual.getNombre());

            // se muestra la mano desde vista y se pida que elija una de todas las cartas
            vista.mostrarMano(jugadorActual.getMano());
            int indiceElegido = vista.pedirIndiceCarta(jugadorActual.getMano().size());


            // modelo llama a jugar carta que llama al jugar carta de ronda y esa gestiona la decision
            jugadaExitosa = modelo.jugarCarta(jugadorActual, indiceElegido);

            if (!jugadaExitosa) {
                // si el modelo devolvió false, es porque rompió una regla (no asistió, no mató, etc.), va a tener q jugar de nuevo
                vista.mostrarMensaje("JUGADA ILEGAL: Esa carta no cumple las reglas del Tute");
                vista.mostrarMensaje("Recordá: Asistir al palo -> Matar si podés -> Fallar con triunfo");
                vista.pausaParaContinuar();
            }
        }

        // se chequea si se terminó la baza en esta jugada...
        if (modelo.huboBazaTerminada()) {
            String nombreGanador = modelo.getGanadorUltimaBaza().getNombre();
            int puntos = modelo.getPuntosUltimaBaza();

            vista.mostrarResultadoBaza(nombreGanador, puntos);

            // se avisa que se sumaron las 10 ultimas al ganador (si es la ultima baza de todas)
            if (modelo.huboDiezUltimas()) {
                vista.mostrarMensaje("¡BONUS! Se sumaron las 10 de últimas");
            }

            // se avisa que se cantó y se muestra el canto
            String canto = modelo.getCantoUltimaBaza();
            if (canto != null) {
                if (canto.equals("TUTE")) {
                    vista.mostrarMensaje("¡¡¡TUTE!!! " + nombreGanador + " GANÓ EL PARTIDO");
                    System.exit(0);
                }
                vista.mostrarMensaje(nombreGanador + " cantó " + canto);
            }

            modelo.confirmarBazaLeida();
            vista.pausaParaContinuar();
        }
    }

    @Override
    public void actualizar() {
        // Pedimos datos a la mesa
        String triunfo = modelo.getPaloTriunfo();
        Map<Jugador, Carta> cartas = modelo.getCartasEnMesa();

        // Si cartas es null o vacío, igual mostramos el triunfo
        if (cartas != null) {
            vista.limpiarPantalla();
            vista.mostrarEstadoJuego(triunfo, cartas);
        }
    }
}

