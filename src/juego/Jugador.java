package juego;

// representa un jugador del juego

public class Jugador {

    private String nombre;
    private Roll personaje;
    private Herramienta herramienta;
    private boolean esHumano;
    
    // constructor
    public Jugador(String nombre, Roll personaje, Herramienta herramienta, boolean esHumano) {
        this.nombre = nombre;
        this.personaje = personaje;
        this.herramienta = herramienta;
        this.esHumano = esHumano;
        this.personaje.setArma(herramienta);
    }
   
    public String GetNombre()
    {
    	return nombre;
    }
    // obtener su posicion
    public Posicion getPos() {
        return personaje.getPos();
    }
    
    // mover al jugador
    public void mover(Direccion dir) {
        personaje.mover(dir);
    }
    
    // ver si esta vivo
    public boolean estaVivo() {
        return personaje.estaVivo();
    }

    public void recibirAtaque(int d) {
        personaje.recibirAtaque(d);
    }

    public void atacar(Jugador otro) {
        int danio = personaje.atacar();
        otro.recibirAtaque(danio);
    }

    @Override
    public String toString() {
        return nombre + " - " + personaje.toString();
    }
}