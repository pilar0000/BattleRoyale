package juego;

// la espada tiene un bonus inicial alto y mejora con poca probabilidad

public class Espada extends Herramienta {
	
	// constructor creando una espada con bonus 10
	public Espada() {
		super("Espada", 10);
	}
	
	// la probabilidad de que mejore es del 10%
	public boolean comprobarModificacion() {
		return Math.random() < 0.10;
	}
	
	// la mejora suma 2 puntos de ataque en el bonus
	public void modifica() {
		bonusAtaque += 2;
	}
}