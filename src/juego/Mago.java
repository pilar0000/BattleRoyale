package juego;

// representa al rol de mago

public class Mago extends Roll {

    public Mago(String nombre) {
        super(nombre, 60, 70, 5, 200);
    }

    @Override
    public void habilidadNormal() {
        System.out.println(nombre + " invoca espíritus y se cura 10 puntos.");
        setVida(getVida() + 10);
    }

    @Override
    public void habilidadEspecial() {
        System.out.println(nombre + " lanza una poderosa bola de fuego.");
    }
}
