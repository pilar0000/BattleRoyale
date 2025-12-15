package juego;

// esta excepcion se lanza cuando hay un problema al intentar escribir en un archivo
// por ejemplo, si no existe la carpeta de destino
// si no se puede acceder al archivo

public class FicheroEscrituraExcepcion extends Exception {
    public FicheroEscrituraExcepcion(String msg) {
        super(msg);
    }
}