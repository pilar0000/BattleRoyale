package juego;

import java.util.ArrayList;

// representa al mapa del juego, donde ocurre la partida

public class Mapa {

    private int ancho;
    private int alto;
    
    // variables para la zona segura
    private int zonaXMin = 0;
    private int zonaXMax;
    private int zonaYMin = 0;
    private int zonaYMax;
    
    public void inicializarZonaSegura() {
    	zonaXMax = ancho - 1;
    	zonaYMax = alto -1;
    }
    
    public boolean dentroZonaSegura(Posicion p) {
    	int x = p.getX();
    	int y = p.getY();
    	return (x >= zonaXMin && x <= zonaXMax && y >= zonaYMin && y <= zonaYMax);
    }
    
    public void reducirZona() {
        if (zonaXMin < zonaXMax) zonaXMin++;
        if (zonaYMin < zonaYMax) zonaYMin++;
        if (zonaXMax > zonaXMin) zonaXMax--;
        if (zonaYMax > zonaYMin) zonaYMax--;
    }
    
    // elementos del mapa
    private Item[][] celdas;
    private ArrayList<Jugador> jugadores;

    private ArrayList<Item> items;
    
    // constructor
    public Mapa(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;

        celdas = new Item[ancho][alto];
        jugadores = new ArrayList<>();
        items = new ArrayList<>();
    }

    // getters
    public int getZonaXMin() {
    	return zonaXMin;
    }
    public int getZonaXMax() {
    	return zonaXMax;
    }
    public int getZonaYMin() {
    	return zonaYMin;
    }
    public int getZonaYMax() {
    	return zonaYMax;
    }
    
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

    public Item[][] getCeldas() {
        return celdas;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void agregarItem(Item item) {
        items.add(item);

        Posicion p = item.getPos();
        celdas[p.getX()][p.getY()] = item;
    }
    
    // ver si hay un item
    public boolean hayItem(Posicion p) {
        return celdas[p.getX()][p.getY()] != null;
    }
    
    // recoger un item
    public Item recogerItem(Posicion p) {
        Item it = celdas[p.getX()][p.getY()];
        celdas[p.getX()][p.getY()] = null;

        if (it != null)
            items.remove(it);

        return it;
    }
    
    // colocar un item
    public void colocarItem(Posicion p, Item it) {
        celdas[p.getX()][p.getY()] = it;

        if (!items.contains(it))
            items.add(it);
    }

    // colocar jugadores
    public void colocarJugador(Jugador j, Posicion p) {

        j.setPos(p);
        jugadores.add(j);
    }

}