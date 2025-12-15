package juego;

// se lanza cuando el juego intenta leer un archivo y ocurre algun problema´
// por ejemplo: no existe el archivo o la ruta es incorrecta

public class FicheroLecturaExcepcion extends Exception {
    public FicheroLecturaExcepcion(String msg) {
        super(msg);
    }
}