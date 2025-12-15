package juego;

// posicion dentro del mapa
// donde esta cada jugador y cada objeto

public class Posicion {
    private int x;
    private int y;
    
    // constructor
    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // getters
    public int getX() { 
    	return x; 
    }
    public int getY() { 
    	return y; 
    }

    // movimiento segun la direccion
    public Posicion mover(Direccion d) {
        switch (d) {
            case ARRIBA:      
            	return new Posicion(x, y - 1);
            case ABAJO:       
            	return new Posicion(x, y + 1);
            case IZQUIERDA:   
            	return new Posicion(x - 1, y);
            case DERECHA:     
            	return new Posicion(x + 1, y);
            case ARRIBA_IZQ:  
            	return new Posicion(x - 1, y - 1);
            case ARRIBA_DER:  
            	return new Posicion(x + 1, y - 1);
            case ABAJO_IZQ:   
            	return new Posicion(x - 1, y + 1);
            case ABAJO_DER:   
            	return new Posicion(x + 1, y + 1);
        }
        return this;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
