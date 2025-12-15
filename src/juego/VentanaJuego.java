package juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class VentanaJuego extends JFrame {
	
	// ventana grafica del juego
	// es la interfaz que muestra el mapa, juagdor, items, bots, zona segura y el hud con los botones

    private PanelMapa panelMapa;
    private PanelHUD panelHUD;

    private Mapa mapa;
    private Jugador jugadorPrincipal;
    private Partida partida;
    
    // cuando se ha movido el jugador por ultima vez y el tiempo minimo entre movimientos
    private long ultimoMovimiento = 0;
    private final long retrasoMovimiento = 120;
    
    // constructor
    public VentanaJuego(Mapa mapa, Jugador jugadorPrincipal, Partida partida) {

        this.mapa = mapa;
        this.partida = partida;
        this.jugadorPrincipal = jugadorPrincipal;

        setTitle("Battle Royale");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // crear los paneles
        panelMapa = new PanelMapa(mapa);
        panelHUD = new PanelHUD(partida, jugadorPrincipal, this);

        add(panelMapa, BorderLayout.CENTER);
        add(panelHUD, BorderLayout.EAST);

        // movimiento del jugador
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            	
            	// si esta muerto no permite moverse
            	if (!jugadorPrincipal.estaVivo()) {
            		return;
            	}
            	
            	// evita movimientos demasiado rapidos
                long tiempoActual = System.currentTimeMillis();
                if (tiempoActual - ultimoMovimiento < retrasoMovimiento)
                    return;

                ultimoMovimiento = tiempoActual;
                
                // obtener las coordenadas
                int key = e.getKeyCode();
                int x = jugadorPrincipal.getPos().getX();
                int y = jugadorPrincipal.getPos().getY();
                
                // controla uno de los 4 movimientos y ajusta el sprite hacia esa direccion
                switch (key) {

                    case KeyEvent.VK_A: // izquierda
                        if (x > 0) {
                            jugadorPrincipal.moverIzquierda();
                            panelMapa.setSpriteDireccion("IZQUIERDA", jugadorPrincipal);                           
                        }
                        break;

                    case KeyEvent.VK_D: // derecha
                        if (x < mapa.getAncho() - 1) {
                            jugadorPrincipal.moverDerecha();
                            panelMapa.setSpriteDireccion("DERECHA", jugadorPrincipal);                           
                        }
                        break;

                    case KeyEvent.VK_W: // arriba
                        if (y > 0) {
                            jugadorPrincipal.moverArriba();                          
                        }
                        break;

                    case KeyEvent.VK_S: // abajo
                        if (y < mapa.getAlto() - 1) {
                            jugadorPrincipal.moverAbajo();
                            
                        }
                        break;
                }
                recogerItemSiHay();
                refrescar();
            }
        });

        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);

        SwingUtilities.invokeLater(this::refrescar);
    }

    // recoger items del suelo
    private void recogerItemSiHay() {
        Posicion p = jugadorPrincipal.getPos();

        if (mapa.hayItem(p)) {
            Item it = mapa.recogerItem(p);
            it.aplicar(jugadorPrincipal.getPersonaje());
            System.out.println(jugadorPrincipal.getNombre() + " ha recogido " + it);
        }
    }

    // actualizar gui
    public void refrescar() {
        panelHUD.actualizar();
        panelMapa.actualizarCamara(jugadorPrincipal);
        panelMapa.repaint();
        requestFocusInWindow();
    }
    
    // permite seguir moviendose despues de pulsar un boton del hud
    public void resetearMovimiento() {
    	ultimoMovimiento = 0;
    }
    
    // se llama despues de cada accion para comprobar si la partida ha terminado
    public void verificarFinPartida() {
        if (!jugadorPrincipal.estaVivo()) {

            panelHUD.desactivarBotones();

            JOptionPane.showMessageDialog(this, "Has muerto... Fin de la partida.", "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
            
            System.exit(0);
        }
        if (partida.jugadoresVivos() <= 1) {

            Jugador ganador = partida.getGanador();

            panelHUD.desactivarBotones();

            JOptionPane.showMessageDialog(this, "GANADOR: " + ganador.getNombre(), "FIN DE PARTIDA", JOptionPane.INFORMATION_MESSAGE);

            System.exit(0);
        }
    }
}

class PanelMapa extends JPanel {
	
	// representa el mapa en sí y los sprites

    private Mapa mapa;
    private final int cellSize = 50; // tamaño de cada celda en pixeles
    
    // parte del mapa que se esta mostrando
    private int camX = 0;
    private int camY = 0;
    
    // el jugador ve 12 x 12 casillas
    private final int vistaAncho = 12;
    private final int vistaAlto = 12;

    // imagenes del mapa 
    private Image tilePasto;
    private Image tilePiedra;

    private Image spritePocionVida;
    private Image spritePocionMana;

    private Image spriteJugadorActual;

    private Image spriteGuerreroDer, spriteGuerreroIzq;
    private Image spriteArqueroDer, spriteArqueroIzq;
    private Image spriteMagoDer, spriteMagoIzq;

    // constructor
    public PanelMapa(Mapa mapa) {
        this.mapa = mapa;

        String ruta = "C:/Users/pilar/git/BattleRoyale/src/juego/Sprites/";

        tilePasto = new ImageIcon(ruta + "Pasto1.png").getImage();
        tilePiedra = new ImageIcon(ruta + "Pasto2.png").getImage();

        spritePocionVida = new ImageIcon(ruta + "PocionVida.png").getImage();
        spritePocionMana = new ImageIcon(ruta + "PocionMana.png").getImage();

        spriteGuerreroDer = new ImageIcon(ruta + "Guerr_der.png").getImage();
        spriteGuerreroIzq = new ImageIcon(ruta + "Guerr_izq.png").getImage();

        spriteArqueroDer = new ImageIcon(ruta + "Arq_der.png").getImage();
        spriteArqueroIzq = new ImageIcon(ruta + "Arq_izq.png").getImage();

        spriteMagoDer = new ImageIcon(ruta + "Mago_der.png").getImage();
        spriteMagoIzq = new ImageIcon(ruta + "Mago_izq.png").getImage();

        spriteJugadorActual = spriteMagoDer;
        
        // tamaño del panel = numero de casillas visibles x su tamaño
        setPreferredSize(new Dimension(vistaAncho * cellSize, vistaAlto * cellSize));
    }
    
    // cambiar el sprite segun la direccion, actualiza la imagen segun la clase y a donde mira
    public void setSpriteDireccion(String dir, Jugador j) {

        switch (j.getClase()) {
            case "Guerrero":
                spriteJugadorActual = dir.equals("IZQUIERDA") ? spriteGuerreroIzq : spriteGuerreroDer;
                break;

            case "Arquero":
                spriteJugadorActual = dir.equals("IZQUIERDA") ? spriteArqueroIzq : spriteArqueroDer;
                break;

            case "Mago":
                spriteJugadorActual = dir.equals("IZQUIERDA") ? spriteMagoIzq : spriteMagoDer;
                break;
        }
    }
    
    // camara siguiendo al jugador
    public void actualizarCamara(Jugador jugador) {

        camX = jugador.getPos().getX() - vistaAncho / 2;
        camY = jugador.getPos().getY() - vistaAlto / 2;

        // evitar que la camara salga fuera del mapa
        if (camX < 0) camX = 0;
        if (camY < 0) camY = 0;

        if (camX > mapa.getAncho() - vistaAncho)
            camX = mapa.getAncho() - vistaAncho;

        if (camY > mapa.getAlto() - vistaAlto)
            camY = mapa.getAlto() - vistaAlto;
    }

    // dibujar todo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int vx = 0; vx < vistaAncho; vx++) {
            for (int vy = 0; vy < vistaAlto; vy++) {

                int mx = camX + vx;
                int my = camY + vy;

                int dx = vx * cellSize;
                int dy = vy * cellSize;
                
                // dibujar el suelo
                // alterna los dos tipos de suelo
                g.drawImage((mx + my) % 2 == 0 ? tilePasto : tilePiedra, dx, dy, cellSize, cellSize, null);
            }
        }

        // dibujar items solo si estan dentro de la camara
        for (Item it : mapa.getItems()) {
            int px = it.getPos().getX();
            int py = it.getPos().getY();

            if (px >= camX && px < camX + vistaAncho &&
                py >= camY && py < camY + vistaAlto) {
                Image icon = it.getTipo() == TipoItem.POCION_VIDA ? spritePocionVida : spritePocionMana;

                g.drawImage(icon, (px - camX) * cellSize, (py - camY) * cellSize, cellSize, cellSize, null);
            }
        }

        // dibujar jugadores
        for (Jugador j : mapa.getJugadores()) {
            int px = j.getPos().getX();
            int py = j.getPos().getY();

            if (px >= camX && px < camX + vistaAncho &&
                py >= camY && py < camY + vistaAlto) {
                Image sprite;

                if (j.esHumano()) sprite = spriteJugadorActual;
                else {
                    switch (j.getClase()) {
                        case "Guerrero": 
                        	sprite = spriteGuerreroDer; 
                        break;
                        case "Arquero": 
                        	sprite = spriteArqueroDer; 
                        break;
                        default: 
                        	sprite = spriteMagoDer; 
                        break;
                    }
                }

                g.drawImage(sprite, (px - camX) * cellSize, (py - camY) * cellSize, cellSize, cellSize, null);
            }
        }

        // zona segura
        dibujarZonaSegura(g);
    }
    
    // se dibuja un rectangulo
    private void dibujarZonaSegura(Graphics g) {

        int xMin = mapa.getZonaXMin();
        int xMax = mapa.getZonaXMax();
        int yMin = mapa.getZonaYMin();
        int yMax = mapa.getZonaYMax();

        int x = (xMin - camX) * cellSize;
        int y = (yMin - camY) * cellSize;
        int w = (xMax - xMin + 1) * cellSize;
        int h = (yMax - yMin + 1) * cellSize;

        g.setColor(new Color(0, 150, 255, 180));
        g.drawRect(x, y, w, h);
    }
}
class PanelHUD extends JPanel {
	
	// representa todo lo que ve el jugador en la pantalla
	// vida, mana, nombre, arma, botones

    private JLabel lblVida;
    private JLabel lblMana;
    private JLabel lblArma;
    private JLabel lblNombre;

    JButton btnAtacar, btnHab1, btnHab2;
    
    // elementos a los que necesita acceder
    private Partida partida;
    private Jugador jugadorPrincipal;
    private VentanaJuego ventana;

    // constructor
    public PanelHUD(Partida partida, Jugador jugadorPrincipal, VentanaJuego ventana) {

        this.partida = partida;
        this.jugadorPrincipal = jugadorPrincipal;
        this.ventana = ventana;

        // diseño del panel
        setLayout(new GridLayout(12, 1));
        setPreferredSize(new Dimension(200, 700));

        // crear etiquetas
        lblNombre = new JLabel();
        lblVida = new JLabel();
        lblMana = new JLabel();
        lblArma = new JLabel();

        // crear botones
        btnAtacar = new JButton("Atacar");
        btnHab1 = new JButton("Habilidad Normal");
        btnHab2 = new JButton("Habilidad Especial");

        // interfaz visual
        add(new JLabel("=== ESTADO DEL JUGADOR ==="));
        add(lblNombre);
        add(lblVida);
        add(lblMana);
        add(lblArma);
        add(new JLabel("----------------------"));
        add(btnAtacar);
        add(btnHab1);
        add(btnHab2);

        // boton de atacar
        btnAtacar.addActionListener(e -> {

        	// busva un enemigo y selecciona el primero que este vivo
            Jugador objetivo = partida.buscarPrimerEnemigo(jugadorPrincipal);

            if (objetivo != null) {
            	// ataca segun su ataque total de base + arma
                int dmg = jugadorPrincipal.getAtaque();
                objetivo.getPersonaje().recibirDanio(dmg);
                System.out.println(jugadorPrincipal.getNombre() + " ataca a " + objetivo.getNombre() + " por -" + dmg);
            }
            
            partida.comprobarMuertes();
            partida.turnoBots();
            partida.comprobarMuertes();

            ventana.verificarFinPartida();
            ventana.resetearMovimiento();
            ventana.refrescar();
        });

        // boton de habilidad normal
        btnHab1.addActionListener(e -> {

            Jugador objetivo = partida.buscarPrimerEnemigo(jugadorPrincipal);
            
            // ejecuta la habilidad normal
            jugadorPrincipal.getPersonaje().habilidadNormal();

            if (objetivo != null) {
            	// la habilidad hace menos daño que el ataque normal
                int dmg = jugadorPrincipal.getAtaque() / 2;
                objetivo.getPersonaje().recibirDanio(dmg);
                System.out.println("Habilidad normal causa -" + dmg
                        + " a " + objetivo.getNombre());
            }

            partida.comprobarMuertes();
            partida.turnoBots();
            partida.comprobarMuertes();

            ventana.verificarFinPartida();
            ventana.resetearMovimiento();
            ventana.refrescar();
        });


        // boton de habilidad especial
        btnHab2.addActionListener(e -> {

            Jugador objetivo = partida.buscarPrimerEnemigo(jugadorPrincipal);

            jugadorPrincipal.getPersonaje().habilidadEspecial();

            if (objetivo != null) {
            	// hace mas daño que el ataque y la habilidad normal
                int dmg = jugadorPrincipal.getAtaque() + 30;
                objetivo.getPersonaje().recibirDanio(dmg);
                System.out.println("Habilidad especial causa -" + dmg
                        + " a " + objetivo.getNombre());
            }

            partida.comprobarMuertes();
            partida.turnoBots();
            partida.comprobarMuertes();

            ventana.verificarFinPartida();
            ventana.resetearMovimiento();
            ventana.refrescar();
        });

        actualizar();
    }
    
    // cuando mueres, ya no puedes interactuar con el hud
    public void desactivarBotones() {
        btnAtacar.setEnabled(false);
        btnHab1.setEnabled(false);
        btnHab2.setEnabled(false);
    }

    // cada vez que algo camba¡ia, el hud se actualiza
    public void actualizar() {
        lblNombre.setText("Jugador: " + jugadorPrincipal.getNombre());
        lblVida.setText("Vida: " + jugadorPrincipal.getVida());
        lblMana.setText("Maná: " + jugadorPrincipal.getMana());
        lblArma.setText("Arma: " + jugadorPrincipal.getArma().getNombre());
    
        // activar o desactivar botones si el jugador esta vivo o muerto
        boolean vivo = jugadorPrincipal.estaVivo();
        btnAtacar.setEnabled(vivo);
        btnHab1.setEnabled(vivo);
        btnHab2.setEnabled(vivo);
    }
}
