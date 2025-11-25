package ar.edu.unlu.poo.juegodemesa.modelo;

public class Carta {
    private String palo;
    private int numero;

    public Carta(String palo, int numero){
        this.palo = palo;
        this.numero = numero;
    }

    //para ver quien gana las bazas
    public int getValor() {
        switch (this.numero) {
            case 1: return 100;
            case 3: return 90;
            case 12: return 80;
            case 11: return 70;
            case 10: return 60;
            case 7: return 50;
            case 6: return 40;
            case 5: return 30;
            case 4: return 20;
            case 2: return 10;
            default: return 0;
        }
    }

    //para los puntos del fin de la ronda
    public int getPuntos() {
        switch (this.numero) {
            case 1:  return 11; // as
            case 3:  return 10; // tres
            case 12: return 4;  // rey
            case 11: return 3;  // caballo
            case 10: return 2;  // sota
            default: return 0;  // el resto
        }
    }

    public String getPalo() {
        return palo;
    }

    public int getNumero() {
        return numero;
    }
}
