package ar.edu.unlu.poo.juegodemesa.observer;

public interface Observable {
    void agregarObservador(Observador observador);
    void quitarObservador(Observador observador);
    void notificarObservadores();
}
