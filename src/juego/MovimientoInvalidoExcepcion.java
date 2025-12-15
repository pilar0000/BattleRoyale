package juego;

// esta excepcion se lanza cuando un personaje hace un movimiento invalido(no la usamos)
// la podriamos usar para que los personajes no se salgan de la zona segura o del mapa

public class MovimientoInvalidoExcepcion extends Exception {
    public MovimientoInvalidoExcepcion(String msg) {
        super(msg);
    }
}