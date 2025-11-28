package juego;

// posicion dentro del mapa

public class Posicion {
	
	public int x;
	public int y;
	
	public Posicion(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// comprobar si dos posiciones son igualess
	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof Posicion)) {
			return false;
		}
		Posicion p = (Posicion) obj;
		return this.x == p.x && this.y == p.y;
	}
	
	// devolver la posicion
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}