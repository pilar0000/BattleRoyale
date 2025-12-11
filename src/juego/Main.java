package juego;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
    	
        // menu
        Menu.mostrarMenu();
        int opcion = Menu.pedirOpcion();

        if (opcion != 1) {
            System.out.println("Saliendo del juego...");
            return;
        }
        // datos del jugador 
        String nombre = Menu.pedirNombre();
        String claseElegida = Menu.elegirClase();
        int numHumanos = Menu.pedirNumeroHumanos();
        int numBots = Menu.pedirNumeroBots();
        Dificultad dificultad = Menu.pedirDificultad();

        // crear el mapa y la lista de jugadores
        Mapa mapa = new Mapa(45, 45);
        ArrayList<Jugador> jugadores = new ArrayList<>();

        // crear jugador
        Roll personajeHumano;

        switch (claseElegida) {
            case "Guerrero":
                personajeHumano = new Guerrero(nombre);
                break;

            case "Arquero":
                personajeHumano = new Arquero(nombre);
                break;

            default:
                personajeHumano = new Mago(nombre);
                break;
        }

        // arma segun la clase
        Herramienta armaHumano =
                claseElegida.equals("Guerrero") ? new Espada() :
                claseElegida.equals("Arquero") ? new Arco() :
                new Baculo();

        Jugador jugadorPrincipal = new Jugador(nombre, personajeHumano, armaHumano, true);
        jugadores.add(jugadorPrincipal);

        // crear el resto de humanos (guerreros)
        for (int i = 1; i < numHumanos; i++) {
            Roll pj = new Guerrero("Humano" + (i + 1));
            Jugador humanoExtra = new Jugador("Humano" + (i + 1), pj, new Espada(), true);
            jugadores.add(humanoExtra);
        }

        // crear bots
        for (int i = 0; i < numBots; i++) {

            String nombreBot = "Bot" + (i + 1);
            Roll personajeBot;
            Herramienta armaBot;

            // clase aleatoria para el bot
            int clase = (int) (Math.random() * 3);

            switch (clase) {
                case 0:
                    personajeBot = new Guerrero(nombreBot);
                    armaBot = new Espada();
                    break;
                case 1:
                    personajeBot = new Arquero(nombreBot);
                    armaBot = new Arco();
                    break;
                default:
                    personajeBot = new Mago(nombreBot);
                    armaBot = new Baculo();
                    break;
            }

            Jugador bot = new Jugador(nombreBot, personajeBot, armaBot, false);
            jugadores.add(bot);
        }

        // crear partida y jugar
        Partida partida = new Partida(mapa, jugadores, dificultad);
        partida.inicializar();

        // abrir interfaz grafica
        System.out.println("Abriendo VentanaJuego...");
        new VentanaJuego(mapa, jugadorPrincipal, partida);
        
        try {
            String[] config = GestorFicheros.leerConfig("C:/Users/pilar/git/BattleRoyale/src/juego/config.txt");
            System.out.println("ConfiguraciOn cargada:");
            for (String c : config) System.out.println(c);
        } catch (FicheroLecturaExcepcion e) {
            System.out.println("No se ha podido leer configuracion: " + e.getMessage());
        }

    }
}

