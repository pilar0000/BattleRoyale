package juego;

import java.io.*;
import java.util.ArrayList;

// esta clase hace de administrador de los archivos del juego
// se utiliza para guardar el log de la partida y para leer los archivos de configuracion

public class GestorFicheros {

    // guardar log de la partida con las lineas almacenadas en Logger
    public static void guardarLog(String ruta, Logger logger) throws FicheroEscrituraExcepcion {
    	
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {

            for (String linea : logger.getLog()) {
                if (linea != null)
                    pw.println(linea);
            }

        } catch (Exception e) {
            throw new FicheroEscrituraExcepcion("Error al escribir en el archivo: " + ruta);
        }
    }

    // leer archivo de configuracion
    public static String[] leerConfig(String ruta) throws FicheroLecturaExcepcion {

        ArrayList<String> lineas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {

            String linea;

            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }

        } catch (Exception e) {
            throw new FicheroLecturaExcepcion("No se ha podido leer el archivo: " + ruta);
        }

        return lineas.toArray(new String[0]);
    }
}
