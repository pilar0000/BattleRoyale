package juego;

// se lanza cuando un personaje intenta usar una habilidad y no tiene recursos
// por ejemplo si no tiene suficiente mana

public class RecursoInsuficienteExcepcion extends Exception {
	
    public RecursoInsuficienteExcepcion(String msg) {
        super(msg);
    }
}