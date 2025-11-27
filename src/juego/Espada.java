package juego;

// la espada tiene un bonus inicial alto y mejora con poca probabilidad

public class Espada extends Herramienta {
	
	// constructor creando una espada con bonus 5
	public Espada() {
		super("Espada", 5);
	}
	
	// la probabilidad de que mejore es del 25%
	public boolean comprobarModificacion() {
		return Math.random() < 0.25;
	}
	
	// la mejora suma 3 puntos de ataque en el bonus
	public void modifica() {
		bonusAtaque += 3;
	}
}