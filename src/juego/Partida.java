package juego;

import java.util.ArrayList;
import java.util.Random;

public class Partida {

    private ArrayList<Jugador> jugadores;
    private Mapa mapa;
    private Dificultad dificultad;
    private int turnos;
    private Logger logger;

    private Random rnd = new Random();

    // constructor
    public Partida(Mapa mapa, ArrayList<Jugador> jugadores, Dificultad dificultad) {
        this.mapa = mapa;
        this.jugadores = jugadores;
        this.dificultad = dificultad;
        this.turnos = 0;
        this.logger = new Logger();
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    // inicializar partida
    public void inicializar() {
        logger.add("Iniciando partida...");
        
        mapa.inicializarZonaSegura();

        // poner a los jugadores en posiciones aleatorias
        for (Jugador j : jugadores) {
            Posicion p = new Posicion(rnd.nextInt(mapa.getAncho()), rnd.nextInt(mapa.getAlto()));
            mapa.colocarJugador(j, p);
            logger.add("Jugador colocado: " + j.getNombre() + " en " + p);
            
         // generar 15 pociones aleatorias
            for (int i = 0; i < 15; i++) {

                int x = rnd.nextInt(mapa.getAncho());
                int y = rnd.nextInt(mapa.getAlto());

                TipoItem tipo = rnd.nextBoolean()
                        ? TipoItem.POCION_VIDA
                        : TipoItem.POCION_MANA;

                mapa.agregarItem(new Item(tipo, new Posicion(x, y)));
            }


        }
    }

    // turno del jugador
    public void turnoJugador(Jugador j) {
        if (!j.estaVivo()) return;

        logger.add("Turno de jugador humano: " + j.getNombre());
        System.out.println("Turno de jugador humano: " + j.getNombre());

        // solo atacar al primer enemigo vivo
        Jugador objetivo = buscarPrimerEnemigo(j);

        if (objetivo != null) {
            int dmg = j.getAtaque();
            objetivo.getPersonaje().recibirDanio(dmg);

            logger.add(j.getNombre() + " ha atacado a " + objetivo.getNombre() + " por " + dmg);
            System.out.println(j.getNombre() + " ha atacado a " + objetivo.getNombre() + " por -" + dmg);
        }
    }
    
    public void turnoCompletoJugador(Jugador jugador) {
    	turnoJugador(jugador);
    	turnoBots();
    	comprobarMuertes();
    	
    	if(turnos %5 == 0) {
    		cerrarZona();
    	}
    	turnos ++;
    }

    // turno de los bots
    public void turnoBots() {

        for (Jugador j : jugadores) {

            if (j.esHumano()) continue;
            if (!j.estaVivo()) continue;

            logger.add("Turno del bot: " + j.getNombre());
            System.out.println("Turno del bot: " + j.getNombre());

            // seleccion de dificultad
            Jugador objetivo = null;

            switch (dificultad) {

                case FACIL:
                    objetivo = IA.objetivoFacil(this, j);
                    break;

                case NORMAL:
                    objetivo = IA.objetivoNormal(this, j);
                    break;

                case DIFICIL:
                    objetivo = IA.objetivoDificil(this, j);
                    break;
            }

            if (objetivo == null) continue;

            // ataque de bot
            int dmg = j.getAtaque();
            objetivo.getPersonaje().recibirDanio(dmg);

            String mensaje = "[IA-" + dificultad + "] " + j.getNombre() + " ha atacado a " + objetivo.getNombre() + " por " + dmg;

            logger.add(mensaje);
            System.out.println(mensaje);
            
            // para que los bots no se suiciden saliendo de la zona segura
            if (Math.random() < 0.5) {
            	IA.moverBotAleatorio(j, mapa);
            }
        }
    }

    // buscar primer enemigo
    private Jugador buscarPrimerEnemigo(Jugador atacante) {
        for (Jugador j : jugadores) {
            if (j != atacante && j.estaVivo()) {
                return j;
            }
        }
        return null;
    }

    // comprobar muertes
    public void comprobarMuertes() {

    	ArrayList<Jugador> eliminar = new ArrayList<>();

        for (Jugador j : jugadores) {

            if (!j.estaVivo()) {
                logger.add(j.getNombre() + " HA MUERTO.");
                System.out.println(j.getNombre() + " ha muerto.");
                eliminar.add(j);
            }
        }

        jugadores.removeAll(eliminar);
    }

    // zona segura
    public void cerrarZona() {
        logger.add("La zona segura se está cerrando...");

        mapa.reducirZona();

        // hacer daño a los que estan fuera de la zona segura
        for (Jugador j : jugadores) {
        	if(!j.estaVivo()) {
        		continue;
        	}
            if (!mapa.dentroZonaSegura(j.getPos())) {
                int dmg = 15;
                j.getPersonaje().recibirDanio(dmg);

                logger.add(j.getNombre() + " ha recibido " + dmg + " de daño por estar fuera de la zona segura.");
                System.out.println(j.getNombre() + " ha recibido " + dmg + " de daño por zona.");
            }
        }
    }


   // ganador
    public Jugador getGanador() {
        for (Jugador j : jugadores) {
            if (j.estaVivo()) return j;
        }
        return null;
    }
    
    public Jugador getJugadorPrincipal() {
        for (Jugador j : jugadores) {
            if (j.esHumano()) return j;
        }
        return null;
    }

    // jugar
    public void jugar() {
    	// colocar a los jugadores
        inicializar(); 

        logger.add("Comienza la batalla...");

        // sigue mientras haya mas de un jugador vivo
        while (jugadoresVivos() > 1) {

            turnos++;
            logger.add("Turno " + turnos);

            // turnos de humanos
            for (Jugador j : jugadores) {
                if (j.esHumano()) turnoJugador(j);
            }

            // turnos de bots
            turnoBots();

            comprobarMuertes();

            if (turnos % 5 == 0) cerrarZona();
        }

        Jugador ganador = getGanador();

        if (ganador != null) {
            logger.add("GANADOR: " + ganador.getNombre());
            System.out.println("GANADOR: " + ganador.getNombre());
        } else {
            logger.add("No hubo ganador.");
            System.out.println("No hubo ganador.");
        }
        try {
            GestorFicheros.guardarLog("log_partida.txt", logger);
            System.out.println("Log guardado correctamente.");
        } catch (FicheroEscrituraExcepcion e) {
            System.out.println(e.getMessage());
        }

    }

    // contar jugadores vivos
    private int jugadoresVivos() {
        int vivos = 0;
        for (Jugador j : jugadores)
            if (j.estaVivo()) {
            	vivos++;
            }
        return vivos;
    }
}
