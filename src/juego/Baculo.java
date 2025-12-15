package juego;

// el baculo tiene poco bonus inicial pero es el que mas se mejora

public class Baculo extends Herramienta {
	
	// constructor que crea un baculo con bonus 3
	public Baculo() {
		super("Báculo", 3);
	}
	
	// el baculo se mejora con una probabilidad de 40%
	public boolean comprobarModificacion() {
		return Math.random() < 0.40;
	}
	
	// la mejora aumenta 4 puntos de ataque
	public void modifica() {
		bonusAtaque += 4;
	}
}