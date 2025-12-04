package juego;

// objeto que puede aparecer en el suelo

public class Item {

    private TipoItem tipo;
    private Posicion pos;
    
    // constructor
    public Item(TipoItem tipo, Posicion pos) {
        this.tipo = tipo;
        this.pos = pos;
    }
    
    public Posicion getPos() {
    	return pos;
    }
    
    public void setPos(Posicion p) {
    	this.pos = p;
    }
    
    //  ver si es pocion de vida o de mana
    public TipoItem getTipo() { 
    	return tipo; 
    }
    
    // aplicar los efectos al jugador
    public void aplicar(Roll r) {
        switch (tipo) {

            case POCION_VIDA:
                int nuevaVida = Math.min(r.getVidaMax(), r.getVida() + 20);
                r.setVida(nuevaVida);
                break;

            case POCION_MANA:
                r.setMana(r.getManaMax());
                break;
        }
    }
    @Override
    public String toString() {
        return "Item(" + tipo + ")";
    }
}
