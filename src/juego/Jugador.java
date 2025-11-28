package juego;

// representa a un jugador ya sea humano o bot

public class Jugador {

    private String nombre;
    private Roll personaje;  
    private Herramienta arma;
    private boolean esHumano;

    // estados
    private int venenoTurnos = 0;
    private int paralizadoTurnos = 0;
    private int manaBoostTurnos = 0;

    public Jugador(String nombre, Roll personaje, Herramienta arma, boolean esHumano) {
        this.nombre = nombre;
        this.personaje = personaje;
        this.arma = arma;
        this.esHumano = esHumano;
    }

    public boolean estaVivo() {
        return personaje.estaVivo();
    }

    public void aplicarEstados() {
        // veneno
        if (venenoTurnos > 0) {
            personaje.recibirDanio(2);
            venenoTurnos--;
        }

        // paralizado se reduce
        if (paralizadoTurnos > 0) {
            paralizadoTurnos--;
        }

        // boost de mana
        if (manaBoostTurnos > 0) {
            personaje.setMana(personaje.getManaMax());
            manaBoostTurnos--;
        }

        // regen normal de mana
        personaje.regenerarMana();
    }

    public void atacar(Jugador objetivo) {
        int daño = personaje.getAtaque() + arma.getBonusAtaque();
        objetivo.personaje.recibirDanio(daño);
    }

    public boolean usarHabilidadNormal() {
        return personaje.habilidadNormal(this);
    }

    public boolean usarHabilidadEspecial(Jugador objetivo) {
        return personaje.habilidadEspecial(this, objetivo);
    }

    public Roll getPersonaje() {
        return personaje;
    }

    public Herramienta getArma() {
        return arma;
    }

    public boolean esHumano() {
        return esHumano;
    }

    @Override
    public String toString() {
        return nombre + " - " + personaje.toString() +
                " con " + arma.getNombre();
    }
}
