package ar.edu.unlu.poo.juegodemesa;
import ar.edu.unlu.poo.juegodemesa.controlador.ControladorConsola;
import ar.edu.unlu.poo.juegodemesa.modelo.Mesa;
import ar.edu.unlu.poo.juegodemesa.vista.VistaConsola;

public class Main {
    public static void main(String[] args) {
        Mesa modelo = new Mesa();
        VistaConsola vista = new VistaConsola();

        ControladorConsola controlador = new ControladorConsola(modelo, vista); //creo controlador con modelo y vista

        // arranca
        controlador.iniciar();
    }
}