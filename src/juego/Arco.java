package juego;

// Clase Arco
// representa el arma usada por el arquero.
// tiene un ataque moderado pero mejora mas a menudo.

public class Arco extends Herramienta {
	
	// constructor del arco. llama al constructor de herramienta
	// indica el nombre del arma y el bonus inicial de ataque
	public Arco() {
		super("Arco", 4);
	}
	
	// decide si el arco mejora. utiliza un numero random entre 0 y 1
	// la probabilidad de que mejore es del 30%
	public boolean comprobarModificacion() {
		return Math.random() < 0.30;
	}
	
	// aplica la mejora del arco
	// suma 2 puntos de ataque en el bonus
	public void modifica() {
		bonusAtaque += 2;
	}
}