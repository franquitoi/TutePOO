package ar.edu.unlu.poo.juegodemesa.modelo;
import java.util.*;


public class Ronda {
    private String paloTriunfo;
    private Map<Jugador, Carta> cartasEnJuego;
    private Jugador jugadorMano;
    private ArrayList<Jugador> jugadoresEnRonda;
    private Mazo mazoEnJuego;
    private int puntosUltimaBaza;
    private Jugador ganadorUltimaBaza;
    private boolean bazaRecienTerminada;
    private int indiceTurnoActual;
    private Jugador jugadorGanadorParcial; // guarda al jugador que va ganando la baza
    private Carta cartaDeSalida;// guarda la primera carta que se tiró en la baza
    private String cantoUltimaBaza;
    private GestorCantos juezDeCantos;
    private boolean diezUltimasSumadas;


    public Ronda(ArrayList<Jugador> jugadores, Mazo mazo, int indiceMano) {
        this.cartasEnJuego = new HashMap<>();
        this.jugadoresEnRonda = jugadores;
        this.mazoEnJuego = mazo;
        this.jugadorMano = jugadores.get(indiceMano); // la mesa le dice a la ronda quién empieza
        this.indiceTurnoActual = indiceMano;
        this.juezDeCantos = new GestorCantos();
    }


    public void repartir(){
        int numJugadores = jugadoresEnRonda.size();
        int numCartasPorJugador;

        if(numJugadores == 2){
            numCartasPorJugador = 8;
        }
        else if(numJugadores == 3){
            numCartasPorJugador = 13;
        }
        else{
            numCartasPorJugador = 10;
        }

        Carta ultimaCarta = null;

        // necesito el indice de quién es mano.
        int indiceMano = this.jugadoresEnRonda.indexOf(this.jugadorMano); // al principio, jugadorMano será el 1

        // loop 1
        for(int i = 0; i < numCartasPorJugador; i++){
            // loop 2: en cada vuelta, le damos 1 carta a los 4 jugadores
            for(int j = 0; j < numJugadores; j++){
                // (índice mano + a quién le toca en la vuelta) % total
                int indiceRealJugador = (indiceMano + j) % numJugadores;

                Jugador jugadorQueRecibe = this.jugadoresEnRonda.get(indiceRealJugador);

                Carta cartaDada = this.mazoEnJuego.sacarCarta();
                jugadorQueRecibe.recibirCarta(cartaDada);

                ultimaCarta = cartaDada; // siempre pisamos la última
            }
        }
        this.paloTriunfo = ultimaCarta.getPalo(); // le damos el palo de la ultima al triunfo
    }

    //logica principal del juego, acá se fija si la carta le gana a la anterior y si primero es valida
    public boolean jugarCarta(Jugador jugadorActual,Carta cartaActual){
        boolean esValida =esJugadaValida(jugadorActual,cartaActual);

        if(!esValida){
            return false;
        }
        
        //la jugada es valida, hay que agregarla y ver si gana
        cartasEnJuego.put(jugadorActual, cartaActual);

        if (cartaDeSalida == null) { //si no hay carta anterior...
            this.cartaDeSalida = cartaActual;
            this.jugadorGanadorParcial = jugadorActual;
        } else {
            Carta ganadoraActual = cartasEnJuego.get(this.jugadorGanadorParcial); // tomo la carta ganadora para comparar
            if (chequearSiGana(cartaActual, ganadoraActual)) {
                this.jugadorGanadorParcial = jugadorActual;
            }
        }
        avanzarTurno();
        if (cartasEnJuego.size() == jugadoresEnRonda.size()) {
            terminarBaza();
        }
        return true;
    }

    private void avanzarTurno() {
        this.indiceTurnoActual = (this.indiceTurnoActual + 1) % jugadoresEnRonda.size();
    }


    private void terminarBaza() {
        // sumar puntos
        int puntosBaza = 0;
        for (Carta c : cartasEnJuego.values()) {
            puntosBaza += c.getPuntos();
        }

        boolean esUltimaBaza = mazoEnJuego.estaVacio() && jugadoresEnRonda.get(0).getMano().isEmpty();

        // en el caso de ser la ultima baza de todas, sumo 10 al ganador (guardo estado en atributo)
        if (esUltimaBaza) {
            puntosBaza += 10;
            this.diezUltimasSumadas = true;
        } else {
            this.diezUltimasSumadas = false;
        }


        this.puntosUltimaBaza = puntosBaza;
        this.ganadorUltimaBaza = this.jugadorGanadorParcial;
        this.bazaRecienTerminada = true; // se levanta la bandera
        this.jugadorMano = this.jugadorGanadorParcial; // cambio de mano al ganador
        this.indiceTurnoActual = this.jugadoresEnRonda.indexOf(this.jugadorGanadorParcial);
        this.jugadorGanadorParcial.sumarPuntos(puntosBaza); // sumo puntos al ganador


        // logica que revisa con el gestor de cantos, si tienen canto
        GestorCantos.ResultadoCanto c1 = juezDeCantos.analizarMano(
                jugadorGanadorParcial, paloTriunfo, jugadoresEnRonda.size()
        );

        // en el caso que sea con equipos, se fija si el del otro tambien tiene canto
        GestorCantos.ResultadoCanto c2 = null;
        if (jugadoresEnRonda.size() == 4) {
            int indexGanador = jugadoresEnRonda.indexOf(jugadorGanadorParcial);
            Jugador companiero = jugadoresEnRonda.get((indexGanador + 2) % 4);
            c2 = juezDeCantos.analizarMano(
                    companiero, paloTriunfo, jugadoresEnRonda.size()
            );
        }

        // se fija cual de los dos cantos es mejor
        GestorCantos.ResultadoCanto ganador = juezDeCantos.decidirMejorCanto(c1, c2);
        this.cantoUltimaBaza = null; // reseteo el canto si es q hubo antes

        if (ganador != null) {
            ganador.autor.registrarCantoRealizado(ganador.palo);
            ganador.autor.sumarPuntos(ganador.puntos);
            // guardo mensaje de canto...
            this.cantoUltimaBaza = ganador.mensaje;
            if (ganador.mensaje.equals("TUTE")) {
                this.cantoUltimaBaza = "TUTE";
            }
        }

        // el ganador empieza la siguiente ronda
        this.jugadorMano = this.jugadorGanadorParcial;

        // reseteo las cartas en juego y la ultima carta
        this.cartasEnJuego.clear();
        this.cartaDeSalida = null;

        // chequeo si quedan cartas (si no, nadie roba)
        if (mazoEnJuego.estaVacio()) {
            return;
        }

        // el ganador roba primero
        this.jugadorGanadorParcial.recibirCarta(mazoEnJuego.sacarCarta());

        // consigo el índice del ganador y el total de jugadores
        int indiceGanador = this.jugadoresEnRonda.indexOf(this.jugadorGanadorParcial);
        int totalJugadores = this.jugadoresEnRonda.size();

        // hago un loop que corre (Total - 1) veces
        // empiezo en i=1 para saltear al ganador
        for (int i = 1; i < totalJugadores; i++) {

            // misma formula que la de repartir
            int indiceSiguiente = (indiceGanador + i) % totalJugadores;
            Jugador siguiente = this.jugadoresEnRonda.get(indiceSiguiente);

            // roba carta
            if(mazoEnJuego.estaVacio()){
                return;
            }
            Carta cartaRobada = mazoEnJuego.sacarCarta();
            if (cartaRobada != null) {
                siguiente.recibirCarta(cartaRobada);
            }
        }

        this.jugadorMano = this.jugadorGanadorParcial;
    }

    //metodo que verifica si la jugada es válida con las reglas de juego de Tute
    private boolean esJugadaValida(Jugador jugador, Carta cartaJugada) {
        List<Carta> mano = jugador.getMano();

        // regla 1: si es la primera carta de la baza, cualquiera es válida.
        if (cartaDeSalida == null) {
            return true;
        }

        // regla 2: no es la primera. Vemos el palo de salida
        String paloSalida = cartaDeSalida.getPalo();

        // regla 3: juega mismo palo que el anterior?
        if (cartaJugada.getPalo().equals(paloSalida)) {
            return true; // es el mismo palo del anterior
        }

        // regla 4: no jugó al palo. ¿tenía para jugar al palo?
        if (jugadorTienePalo(mano, paloSalida)) {
            // está haciendo trampa, tenía cartas de ese palo y no la jugó
            return false;
        }

        // regla 5: no tiene del palo de salida. ¿jugó un triunfo?
        if (cartaJugada.getPalo().equals(this.paloTriunfo)) {
            return true; // no tenía del palo de salida, así que "fallar" con triunfo es legal
        }

        // regla 6: no jugó ni palo de salida ni triunfo
        // ¿Tenía triunfos en la mano?
        if (jugadorTienePalo(mano, this.paloTriunfo)) {
            // ¡TRAMPA! (En Tute estricto) Debía jugar triunfo si tenía
            return false;
        }

        // regla 7: puede jugar lo que quiera, no tenía palo de salida Y no tenía triunfos
        return true;
    }

    //metodo que verifica si la carta jugada es ganadora
    private boolean chequearSiGana(Carta nueva, Carta vieja) {
        // ¿nueva es triunfo y vieja no? -> Gana nueva
        if (nueva.getPalo().equals(paloTriunfo) && !vieja.getPalo().equals(paloTriunfo)) {
            return true;
        }
        // ¿vieja es triunfo y nueva no? -> gana vieja (nueva pierde)
        if (!nueva.getPalo().equals(paloTriunfo) && vieja.getPalo().equals(paloTriunfo)) {
            return false;
        }
        // ¿ambas son triunfo? -> gana la más alta
        if (nueva.getPalo().equals(paloTriunfo) && vieja.getPalo().equals(paloTriunfo)) {
            return nueva.getValor() > vieja.getValor();
        }
        // 4. ninguna es triunfo. ¿Son del mismo palo?
        if (nueva.getPalo().equals(vieja.getPalo())) {
            return nueva.getValor() > vieja.getValor();
        }
        // 5. ninguna es triunfo y nueva no es del palo de 'vieja' (que es la de salida)
        // la 'nueva' "falló" y no mata.
        return false;
    }

    //metodo que se fija si el jugador tiene en la mano alguna carta del mismo palo
    private boolean jugadorTienePalo(List<Carta> mano, String palo) {
        for (Carta c : mano) {
            if (c.getPalo().equalsIgnoreCase(palo)) {
                return true;
            }
        }
        return false;
    }

    public Jugador getJugadorTurnoActual() {
        if (jugadoresEnRonda.isEmpty()) return null;
        return jugadoresEnRonda.get(indiceTurnoActual);
    }

    public Map<Jugador, Carta> getCartasEnJuego() {
        return cartasEnJuego;
    }

    public String getCantoUltimaBaza() {
        return this.cantoUltimaBaza;
    }

    public boolean isBazaRecienTerminada() {
        return bazaRecienTerminada;
    }

    public void apagarFlagBaza() {
        this.bazaRecienTerminada = false; // Para no avisar dos veces lo mismo
    }

    public Jugador getGanadorUltimaBaza() {
        return ganadorUltimaBaza;
    }

    public String getPaloTriunfo() {
        return this.paloTriunfo;
    }

    public boolean huboDiezUltimas() {
        return this.diezUltimasSumadas;
    }

    public int getPuntosUltimaBaza() {
        return puntosUltimaBaza;
    }
}
