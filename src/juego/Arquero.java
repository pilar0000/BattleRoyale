package juego;

// clase arquero
// representa una clase jugable
// hereda de la clase abstracta Roll, que contiene la logica de los personajes

public class Arquero extends Roll {
	
	// constructor
    public Arquero(String nombre) {
        super(nombre, 100, 50, 20, 100);
    }
    
    // habilidades
    @Override
    public void habilidadNormal() {
        System.out.println(nombre + " dispara una flecha rápida.");
    }

    @Override
    public void habilidadEspecial() {
        System.out.println(nombre + " prepara una flecha eléctrica que paraliza al enemigo.");
    }
}
