package juego;

import java.util.ArrayList;
import java.util.Random;

public class IA {

    private static Random rnd = new Random();

    // ia facil: aleatoria
    public static Jugador objetivoFacil(Partida partida, Jugador bot) {
        ArrayList<Jugador> lista = partida.getJugadores();

        ArrayList<Jugador> vivos = new ArrayList<>();

        for (Jugador j : lista) {
            if (j != bot && j.estaVivo()) {
                vivos.add(j);
            }
        }

        if (vivos.isEmpty()) return null;

        return vivos.get(rnd.nextInt(vivos.size()));
    }

    // ia normal: jugador mas cercano
    public static Jugador objetivoNormal(Partida partida, Jugador bot) {

        Jugador objetivo = null;
        double mejorDist = Double.MAX_VALUE;

        for (Jugador j : partida.getJugadores()) {
            if (j == bot || !j.estaVivo()) continue;

            double dist = distancia(bot.getPos(), j.getPos());

            if (dist < mejorDist) {
                mejorDist = dist;
                objetivo = j;
            }
        }

        return objetivo;
    }

    // ia dificil: prioriza debilitar
    public static Jugador objetivoDificil(Partida partida, Jugador bot) {

        Jugador mejor = null;
        double puntuacionMejor = Double.MAX_VALUE;

        for (Jugador j : partida.getJugadores()) {

            if (j == bot || !j.estaVivo()) continue;

            // las distancias pequeñas son mejores
            double dist = distancia(bot.getPos(), j.getPos());

            // prioriza a los enemigos con poca vida
            double vidaFactor = j.getVida() * 0.5;

            double puntuacion = dist + vidaFactor;

            if (puntuacion < puntuacionMejor) {
                puntuacionMejor = puntuacion;
                mejor = j;
            }
        }

        return mejor;
    }

    // distancia
    private static double distancia(Posicion p1, Posicion p2) {

        int dx = p1.getX() - p2.getX();
        int dy = p1.getY() - p2.getY();

        return Math.sqrt(dx * dx + dy * dy);
    }
    
    // hacer que los bots se muevan en la gui
    public static void moverBotAleatorio(Jugador bot, Mapa mapa) {
        Direccion[] dirs = Direccion.values();
        Direccion d = dirs[(int)(Math.random()*dirs.length)];
        Posicion nueva = bot.getPos().mover(d);

        // evitar salir del mapa
        if (nueva.getX() >= 0 && nueva.getX() < mapa.getAncho() &&
            nueva.getY() >= 0 && nueva.getY() < mapa.getAlto()) 
        {
            bot.setPos(nueva);
        }
    }

}
