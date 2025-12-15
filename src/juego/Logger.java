package juego;

// clase logger
// guarda un log de todo lo que pasa en la partida

import java.io.PrintWriter;

public class Logger {

    private String[] log = new String[1000];
    private int indice = 0;
    
    public String[] getLog() {
        return log;
    }

    // añadir una linea al log
    public void add(String linea) {
        if (indice < log.length) {
            log[indice++] = linea;
        }
    }

    // mostrar el log por consola
    public void mostrar() {
        for (int i = 0; i < indice; i++) {
            System.out.println(log[i]);
        }
    }

    // guardar el log en un archivo
    public void guardar(String ruta) throws FicheroEscrituraExcepcion {
        try (PrintWriter pw = new PrintWriter(ruta)) {
            for (int i = 0; i < indice; i++) {
                pw.println(log[i]);
            }
        } catch (Exception e) {
            throw new FicheroEscrituraExcepcion("No se ha podido guardar el log en: " + ruta);
        }
    }
}
