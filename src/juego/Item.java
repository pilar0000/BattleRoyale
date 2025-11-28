package juego;

// objeto que puede aparecer en el suelo

public class Item {
	
	private TipoItem tipo;
	
	public Item(TipoItem tipo) {
		this.tipo = tipo;
	}
	
	public TipoItem getTipo() {
		return tipo;
	}
	
	@Override
	public String toString() {
		return "Item: " + tipo;
	}
}
