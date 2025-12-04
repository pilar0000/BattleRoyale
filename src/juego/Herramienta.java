package juego;

// esta clase representa una herramienta del juego

public abstract class Herramienta implements Modificable {
	
	protected String nombre;
	protected int bonusAtaque;
	
	// constructor
	public Herramienta(String nombre, int bonusAtaque) {
		this.nombre = nombre;
		this.bonusAtaque = bonusAtaque;
	}
	
	// devuelve el bonus de ataque de la herramienta
	public int getBonusAtaque() {
		return bonusAtaque;
	}
	
	// devuelve el nombre de la herramienta
	public String getNombre() {
		return nombre;
	}
	
	// metodo toString para representar en texto la herramienta
	@Override
	public String toString() {
		return nombre + "(+" + bonusAtaque + "ATK)";
	}
}