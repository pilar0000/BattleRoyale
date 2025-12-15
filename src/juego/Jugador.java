package juego;

// contiene al personaje de roll y a su arma.

public class Jugador {

    public String nombre;
    private Roll personaje;
    private Herramienta arma;
    private boolean esHumano;

    // controlar velocidad
    private long ultimoMovimiento = 0;
    private final long tiempoEntreMov = 150;
    
    // devuelve true si han pasado al menos 150 ms desde el ultimo movimiento
    public boolean puedeMover() {
        long ahora = System.currentTimeMillis();
        if (ahora - ultimoMovimiento >= tiempoEntreMov) {
            ultimoMovimiento = ahora;
            return true;
        }
        return false;
    }
    
     // constructor
    public Jugador(String nombre, Roll personaje, Herramienta arma, boolean esHumano) {
        this.nombre = nombre;
        this.personaje = personaje;
        this.arma = arma;
        this.esHumano = esHumano;

        this.personaje.setArma(arma);
    }

    // obtener su posicion
    public Posicion getPos() {
        return personaje.getPos();
    }

    public void setPos(Posicion p) {
        personaje.setPos(p);
    }

    // movimientos
    public void moverArriba() {
        personaje.mover(Direccion.ARRIBA);
    }

    public void moverAbajo() {
        personaje.mover(Direccion.ABAJO);
    }

    public void moverIzquierda() {
        personaje.mover(Direccion.IZQUIERDA);
    }

    public void moverDerecha() {
        personaje.mover(Direccion.DERECHA);
    }

    public boolean estaVivo() {
        return personaje.estaVivo();
    }

    public String getNombre() { 
    	return nombre; 
    }
    public int getVida() { 
    	return personaje.getVida(); 
    }
    public int getMana() { 
    	return personaje.getMana(); 
    }
    public int getAtaque() { 
    	return personaje.getAtaque(); 
    }
    public Herramienta getArma() { 
    	return arma; 
    }
    public Roll getPersonaje() { 
    	return personaje; 
    }
    public boolean esHumano() { 
    	return esHumano; 
    }
    public String getClase() {
        return personaje.getClass().getSimpleName(); 
    }

}