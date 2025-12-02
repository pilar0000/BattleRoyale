package juego;

// mapa del juego

import java.util.ArrayList;

public class Mapa {

    private int ancho;
    private int alto;
    private Item[][] celdas;
    private ArrayList<Jugador> jugadores;
    private int zonaSegura;
    
    // constructor 
    public Mapa(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        this.celdas = new Item[ancho][alto];
        this.jugadores = new ArrayList<>();
        this.zonaSegura = Math.min(ancho, alto) / 2;  
    }

    // poner al jugador en una posicion
    public void colocarJugador(Jugador j) {
        jugadores.add(j);
    }

    // mover al jugador en una direccion
    public void moverJugador(Jugador j, Direccion d) {
        j.mover(d);
    }

    // ver si en esa posicion hay un item
    public boolean hayItem(Posicion p) {
        return celdas[p.getX()][p.getY()] != null;
    }

    // recoger el item y borrarlo del mapa
    public Item recogerItem(Posicion p) {
        Item i = celdas[p.getX()][p.getY()];
        celdas[p.getX()][p.getY()] = null;
        return i;
    }

    // mostrar el mapa en texto (si quereis lo cambiamos)
    public void mostrar() {
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                boolean jugadorAqui = false;

                for (Jugador j : jugadores) {
                    if (j.getPos().getX() == x && j.getPos().getY() == y) {
                        System.out.print("J ");
                        jugadorAqui = true;
                        break;
                    }
                }

                if (!jugadorAqui) {
                    if (celdas[x][y] != null) {
                    	System.out.print("I ");
                    }else {
                    	System.out.print(". ");
                    }
                }
            }
            System.out.println();
        }
    }

    // reducir la zona segura
    public void cerrarZona() {
        zonaSegura--;
        if (zonaSegura < 0) {
        	zonaSegura = 0;
        }
    }

    // ver si una posicion esta dentro de la zona segura
    public boolean dentroZonaSegura(Posicion p) {
        int x = p.getX();
        int y = p.getY();

        return x >= zonaSegura && y >= zonaSegura && x < ancho - zonaSegura && y < alto - zonaSegura;
    }
}