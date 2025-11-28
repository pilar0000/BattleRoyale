package juego;

// objeto que puede aparecer en el suelo

public class Item {

    private TipoItem tipo;
    
    // constructor
    public Item(TipoItem tipo) {
        this.tipo = tipo;
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
         
                while (r.getVida() < nuevaVida) {
                    r.recibirAtaque(-1);
                }
                break;

            case POCION_MANA:
                while (r.getMana() < r.getManaMax()) {
                    r.mana += 1;
                }
                break;
        }
    }
}
