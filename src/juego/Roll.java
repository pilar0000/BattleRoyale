package juego;

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

        this.pos = new Posicion(0,0);
        this.arma = null;
    }

    // getters
    public String getNombre() { 
    	return nombre; 
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

    public Posicion getPos() { 
    	return pos; 
    }

    public Herramienta getArma() { 
    	return arma; 
    }

    public int getAtaque() {
        return ataqueBase + (arma != null ? arma.getBonusAtaque() : 0);
    }

    // setters
    public void setPos(Posicion p) { 
    	this.pos = p; 
    }

    public void setArma(Herramienta h) {
        this.arma = h;
    }

    public void setVida(int nueva) {
        vida = Math.max(0, Math.min(nueva, vidaMax));
    }

    public void setMana(int nueva) {
        mana = Math.max(0, Math.min(nueva, manaMax));
    }

    // acciones
    public void mover(Direccion d) {
        this.pos = pos.mover(d);
    }

    public void recibirDanio(int dmg) {
        int real = dmg - defensa;
        if (real < 0) real = 0;

        vida -= real;
        if (vida < 0) vida = 0;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

   // habilidades
    public abstract void habilidadNormal();
    public abstract void habilidadEspecial();

    @Override
    public String toString() {
        return nombre + " [Vida " + vida + "/" + vidaMax + ", Mana " + mana + "/" + manaMax + "]";
    }
}