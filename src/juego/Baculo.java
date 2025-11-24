package juego;

// el baculo tiene poco bonus inicial pero es el que mas se mejora

public class Baculo extends Herramienta {
	
	// constructor que crea un baculo con bonus 5
	public Baculo() {
		super("Báculo", 5);
	}
	
	// el baculo se mejora con una probabilidad de 20%
	public boolean comprobarModificacion() {
		return Math.random() < 0.2;
	}
	
	// la mejora aumenta 3 puntos de ataque
	public void modifica() {
		bonusAtaque += 3;
	}
}