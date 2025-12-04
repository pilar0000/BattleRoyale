package juego;

public class Jugador {

    private String nombre;
    private Roll personaje;
    private Herramienta arma;
    private boolean esHumano;

    public Jugador(String nombre, Roll personaje, Herramienta arma, boolean esHumano) {
        this.nombre = nombre;
        this.personaje = personaje;
        this.arma = arma;
        this.esHumano = esHumano;

        this.personaje.setArma(arma);
    }

   // getters
    public String getNombre() { return nombre; }

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

    public Posicion getPos() { 
    	return personaje.getPos(); 
    }
    
    public Roll getPersonaje() { 
    	return personaje; 
    }
    public boolean esHumano() { 
    	return esHumano; 
    }


    // acciones    
    public void setPos(Posicion p) {
        personaje.setPos(p);
    }

    
    public void mover(Direccion d) {
        personaje.mover(d);
    }

    public void recibirDanio(int dmg) {
        personaje.recibirDanio(dmg);
    }

    public void atacar(Jugador otro) {
        otro.recibirDanio(getAtaque());
    }

    public boolean estaVivo() { 
        return personaje.estaVivo();
    }

    public void habilidadNormal() {
        personaje.habilidadNormal();
    }

    public void habilidadEspecial() {
        personaje.habilidadEspecial();
    }

    @Override
    public String toString() {
        return nombre + " (" + personaje.getClass().getSimpleName() + ")";
    }

}