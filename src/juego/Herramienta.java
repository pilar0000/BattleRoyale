package juego;

// esta clase representa una herramienta del juego

public class Herramienta implements Modificable {
	
	protected String nombre;
	protected int bonusAtaque;
	
	// constructor
	public Herramienta(String nombre, int bonus) {
		this.nombre = nombre;
		this.bonusAtaque = bonus;
	}
	
	// devuelve el bonus de ataque de la herramienta
	public int getBonusAtaque() {
		return bonusAtaque;
	}
	
	// devuelve el nombre de la herramienta
	public String getNombre() {
		return nombre;
	}
	
	// como por defecto las herramientas no se pueden modificar, se utiliza sobreescritura para que las clases sobreescriban este comportamiento
	public boolean comprobarModificacion() {
		return false;
	}
	
	// metodo vacio porque las subclases son quienes implementan la mejora
	public void modifica() {
		
	}
	
	// metodo toString para representar en texto la herramienta
	@Override
	public String toString() {
		return nombre + "(+" + bonusAtaque + "ATK)";
	}
}