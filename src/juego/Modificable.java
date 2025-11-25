package juego;

// todas las herramientas que se puedan mejorar tendran esta interfaz
public interface Modificable {
	
	// devuelve true si la herramienta se puede mejorar, y devuelve false en el caso contrario
	boolean comprobarModificacion();
	
	// aplica la mejora a la herramienta
	void modifica();
}
