package juego;

// personaje

public abstract class Roll {

    protected String nombre;
    protected int vida;
    protected int vidaMax;
    protected int ataqueBase;
    protected int defensa;
    protected int mana;
    protected int manaMax;

    protected Posicion pos; 
    protected Herramienta arma; 
    
    // constructor
    public Roll(String nombre, int vidaMax, int ataqueBase, int defensa, int manaMax) {
        this.nombre = nombre;
        this.vidaMax = vidaMax;
        this.vida = vidaMax;
        this.ataqueBase = ataqueBase;
        this.defensa = defensa;
        this.manaMax = manaMax;
        this.mana = 0;
        this.pos = new Posicion(0, 0);
        this.arma = null;
    }

    // mover
    public void mover(Direccion dir) {
        this.pos = pos.mover(dir);
    }

    // recibirAtaque
    public void recibirAtaque(int danio) {
        int dañoFinal = danio - defensa;
        if (dañoFinal < 0) {
        	dañoFinal = 0;
        }

        vida -= dañoFinal;
        if (vida < 0) {
        	vida = 0;
        }
    }
    
    public int getVida() { 
    	return vida; 
    }
    public int getVidaMax() { 
    	return vidaMax; 
    }
    public int getMana() { 
    	return mana; 
    }
    public int getManaMax() {
    	return manaMax; 
    }


    // atacar
    public int atacar() {
        return ataqueBase + (arma != null ? arma.getBonusAtaque() : 0);
    }

    // habilidadNormal
    public abstract void habilidadNormal(Roll objetivo);

    // habilidadEspecial
    public abstract void habilidadEspecial(Roll objetivo);

    // estaVivo
    public boolean estaVivo() {
        return vida > 0;
    }

    // getPos
    public Posicion getPos() {
        return pos;
    }

    public void setPos(Posicion p) {
        this.pos = p;
    }

    // setArma
    public void setArma(Herramienta arma) {
        this.arma = arma;
    }

    @Override
    public String toString() {
        return nombre + " [Vida: " + vida + "/" + vidaMax + ", Mana: " + mana + "/" + manaMax + ", Ataque: " + ataqueBase + ", Defensa: " + defensa + ", Pos: " + pos + "]";
    }
}
