package juego;

// representa un jugador del juego
// cada jugador tiene un personaje y una herramienta, y puede ser humano o bot

public class Jugador {
	
	private String nombre;
	private Personaje personaje;
	private Herramienta herramienta;
	private boolean esBot;
	
	// constructor
	public Jugador(String nombre, Personaje personaje, Herramienta herramienta, boolean esBot) {
		this.nombre = nombre;
		this.personaje = personaje;
		this.herramienta = herramienta;
		this.esBot = esBot;
	}
	
	// getters
	public String getNombre() {
		return nombre;
	}
	
	public Personaje getPersonaje() {
		return personaje;
	}
	
	public Herramienta getHerramienta() {
		return herramienta;
	}
	
	public boolean esBot() {
		return esBot;
	}
	
	// indica si el jugador esta vivo
	public boolean estaVivo() {
		return personaje.getVida() > 0;
	}
	
	// ataca a otro jugador
	// usa el ataque base del personaje + el bonus de la herramienta
	// llama a la habilidad especial si la tiene
	// llama a la mejora de la herramienta
	
	public void atacar(Jugador enemigo) {
		// calcular el ataque
		int ataque = personaje.getAtaque() + herramienta.getBonusAtaque();
		
		// aplicar la habilidad especial
		ataque += personaje.habilidadEspecial();
		
		// ver si se modifica la herramienta
		if (herramienta.comprobarModificacion()) {
			herramienta.modifica();
		}
		
		// atacar
		enemigo.getPersonaje.recibirAtaque(ataque);
	}
	
	// toString
	@Override
	public String toString() {
		return nombre + " - " + personaje + " con " + herramienta;
	}
}
