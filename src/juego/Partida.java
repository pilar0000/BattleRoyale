package juego;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Random;

// esta clase simula la partida

public class Partida {

    private ArrayList<Jugador> jugadores;
    private Mapa mapa;
    private Dificultad dificultad;
    public int turnos = 0;
    private Logger logger;
    
    // genera numeros aleatorios para posiciones iniciales y objetos
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
        
        // al principio todo el mapa es zona segura
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
        
     // intentar mejorar el arma
        Herramienta arma = j.getArma();
        if (arma.comprobarModificacion()) {
            arma.modifica();
            System.out.println("¡¡" + j.getNombre() + " ha mejorado su arma!! Ahora tiene +" + arma.getBonusAtaque() + " ATK");
        }

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
    
    // gestiona un turno entero y comprueba si el jugador humano ya esta muerto
    public void turnoCompletoJugador(Jugador jugador) {
    
    	// si el jugador muere, se acaba la partida
    	if (!jugador.estaVivo()) {
    	    JOptionPane.showMessageDialog(null, "Has muerto. Fin de la partida.", "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
    	    System.exit(0);
    	}

    	
        turnoJugador(jugador);
        turnoBots();
        comprobarMuertes();
        
        turnos++;
        
        if (turnos % 5 == 0) {
            cerrarZona();
        }

        if (jugadoresVivos() <= 1) {
            Jugador ganador = getGanador();

            JOptionPane.showMessageDialog(null,"GANADOR: " + (ganador != null ? ganador.getNombre() : "Nadie"),"FIN DE PARTIDA",JOptionPane.INFORMATION_MESSAGE);

            System.exit(0);
        }
    }


    // turno de los bots
    // eligen el objetivo segun la dificultad, atacan y se mueven aleatoriamente
    // tambien comprueban si alguien muere
    public void turnoBots() {
    	
    	if (jugadoresVivos() <= 1) return;

        for (Jugador j : jugadores) {
        	
        	

            if (j.esHumano()) continue;
            if (!j.estaVivo()) continue;

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

            if (objetivo == null || !objetivo.estaVivo()) continue;

            int dmg = j.getAtaque();
            objetivo.getPersonaje().recibirDanio(dmg);
            
            if (jugadoresVivos() <= 1) return;


            System.out.println("[BOT] " + j.getNombre() + " ataca a " + objetivo.getNombre() + " por -" + dmg);

            IA.moverBotAleatorio(j, mapa);
        }
    }


    // buscar primer enemigo que no sea el atacante
    public Jugador buscarPrimerEnemigo(Jugador atacante) {
        for (Jugador j : jugadores) {
            if (j != atacante && j.estaVivo()) {
                return j;
            }
        }
        return null;
    }

    // comprobar muertes
    public void comprobarMuertes() {
    	
    	ArrayList<Jugador> muertos = new ArrayList<>();

        for (Jugador j : jugadores) {

            if (!j.estaVivo()) {              
                System.out.println(j.getNombre() + " ha muerto.");
                logger.add(j.getNombre() + " ha muerto.");
                muertos.add(j);
            }
        }
        mapa.getJugadores().removeAll(muertos);
        jugadores.removeAll(muertos);    
    }

    // zona segura que se cierra cada 5 turnos
    public void cerrarZona() {
        logger.add("La zona segura se está cerrando...");

        mapa.reducirZona();

        // hacer daño a los que estan fuera de la zona segura
        for (Jugador j : jugadores) {
        	if(!j.estaVivo()) {
        		continue;
        	}
        	
        	// si no esta dentro de la zona segura, -15 de vida
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

    // jugar (no lo usamos en la gui pero sirve para la consola)
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
    public int jugadoresVivos() {
        int vivos = 0;
        for (Jugador j : jugadores)
            if (j.estaVivo()) {
            	vivos++;
            }
        return vivos;
    }
}
