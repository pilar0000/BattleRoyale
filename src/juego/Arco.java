package juego;

// el arco tiene un bonus de ataque medio y mejora con mas frecuencia

public class Arco extends Herramienta {
	
	// constructor del arco, crea un arco con bonus 4
	public Arco() {
		super("Arco", 4);
	}
	
	// la probabilidad de que mejore es del 30%
	public boolean comprobarModificacion() {
		return Math.random() < 0.30;
	}
	
	// suma 2 puntos de ataque en el bonus
	public void modifica() {
		bonusAtaque += 2;
	}
}