package juego;

// el arco tiene un bonus de ataque medio y mejora con mas frecuencia

public class Arco extends Herramienta {
	
	// constructor del arco, crea un arco con bonus 7
	public Arco() {
		super("Arco", 7);
	}
	
	// la probabilidad de que mejore es del 15%
	public boolean comprobarModificacion() {
		return Math.random() < 0.15;
	}
	
	// suma un punto de ataque en el bonus
	public void modifica() {
		bonusAtaque += 1;
	}
}