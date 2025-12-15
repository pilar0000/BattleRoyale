package juego;

// representa el rol de guerrero
//hereda de roll pero sobreescribe las habilidades del guerrero

public class Guerrero extends Roll {

    public Guerrero(String nombre) {
        super(nombre, 150, 20, 50, 70);
    }

    @Override
    public void habilidadNormal() {
        System.out.println(nombre + " realiza un dash golpeando al enemigo.");
        setMana(getMana() - 10);
    }

    @Override
    public void habilidadEspecial() {
        System.out.println(nombre + " entra en Berserk aumentando enormemente su daño.");
        setMana(getMana() - 20);
    }
}
