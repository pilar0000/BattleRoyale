package juego;

import java.util.ArrayList;

public class Mapa {

    private int ancho;
    private int alto;

    private Item[][] celdas;
    private ArrayList<Jugador> jugadores;

    public Mapa(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;

        celdas = new Item[ancho][alto];
        jugadores = new ArrayList<>();
    }

    // getters
    public Item getItem(Posicion p) {
        return celdas[p.getX()][p.getY()];
    }
    
    public int getAncho() { 
    	return ancho; 
    }
    public int getAlto() { 
    	return alto; 
    }

    public ArrayList<Jugador> getJugadores() { 
    	return jugadores; 
    }

    // acciones
    public void colocarJugador(Jugador j, Posicion p) {
        j.getPos().mover(Direccion.ARRIBA);
        j.setPos(p);
        jugadores.add(j);
    }

    public boolean hayItem(Posicion p) {
        return celdas[p.getX()][p.getY()] != null;
    }

    public Item recogerItem(Posicion p) {
        Item it = celdas[p.getX()][p.getY()];
        celdas[p.getX()][p.getY()] = null;
        return it;
    }

    public void colocarItem(Posicion p, Item it) {
        celdas[p.getX()][p.getY()] = it;
    }

    public Item[][] getCeldas() {
        return celdas;
    }
    public boolean dentroZonaSegura(Posicion p) {
        // por ahora siempre devuelve true pero el que haga partida lo reduce
        return true;
    }


}