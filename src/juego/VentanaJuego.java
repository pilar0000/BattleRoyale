package juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

//ventana principal del juego

public class VentanaJuego extends JFrame {

    private PanelMapa panelMapa;
    private PanelHUD panelHUD;

    private Mapa mapa;
    private Jugador jugadorPrincipal;
    
    private Partida partida;

    // velocidad del jugador
    private long ultimoMovimiento = 0;
    private final long retrasoMovimiento = 120;

    public VentanaJuego(Mapa mapa, Jugador jugadorPrincipal, Partida partida) {

        this.mapa = mapa;
        this.partida = partida;
        this.jugadorPrincipal = partida.getJugadorPrincipal();
        

        setTitle("Battle Royale");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panelMapa = new PanelMapa(mapa);
        panelHUD = new PanelHUD(partida, jugadorPrincipal, this);

        add(panelMapa, BorderLayout.CENTER);
        add(panelHUD, BorderLayout.EAST);

        // keylistener para mover al jugador
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                long tiempoActual = System.currentTimeMillis();
                if (tiempoActual - ultimoMovimiento < retrasoMovimiento)
                    return;

                ultimoMovimiento = tiempoActual;

                int key = e.getKeyCode();

                int x = jugadorPrincipal.getPos().getX();
                int y = jugadorPrincipal.getPos().getY();

                switch (key) {

                    case KeyEvent.VK_A: // izquierda
                        if (x > 0) {
                            jugadorPrincipal.moverIzquierda();
                            partida.turnoCompletoJugador(jugadorPrincipal);
                            panelMapa.setSpriteDireccion("IZQUIERDA", jugadorPrincipal);
                            Posicion p = jugadorPrincipal.getPos();
                            // si hay un item se recoge
                            if (mapa.hayItem(p)) {
                                Item it = mapa.recogerItem(p);
                                it.aplicar(jugadorPrincipal.getPersonaje());
                                System.out.println(jugadorPrincipal.getNombre() + " ha recogido " + it);
                            }       
                        }
                        break;

                    case KeyEvent.VK_D: // derecha
                        if (x < mapa.getAncho() - 1) {
                            jugadorPrincipal.moverDerecha();
                            partida.turnoCompletoJugador(jugadorPrincipal);
                            panelMapa.setSpriteDireccion("DERECHA", jugadorPrincipal);
                            Posicion p = jugadorPrincipal.getPos();

                            if (mapa.hayItem(p)) {
                                Item it = mapa.recogerItem(p);
                                it.aplicar(jugadorPrincipal.getPersonaje());
                                System.out.println(jugadorPrincipal.getNombre() + " ha recogido " + it);
                            }

                           
                        }
                        break;

                    case KeyEvent.VK_W: // arriba
                        if (y > 0) {
                            jugadorPrincipal.moverArriba();
                            partida.turnoCompletoJugador(jugadorPrincipal);
                            Posicion p = jugadorPrincipal.getPos();

                            if (mapa.hayItem(p)) {
                                Item it = mapa.recogerItem(p);
                                it.aplicar(jugadorPrincipal.getPersonaje());
                                System.out.println(jugadorPrincipal.getNombre() + " ha recogido " + it);
                            }

                        }
                        break;

                    case KeyEvent.VK_S: // abajo
                        if (y < mapa.getAlto() - 1) {
                            jugadorPrincipal.moverAbajo();
                            partida.turnoCompletoJugador(jugadorPrincipal);
                            Posicion p = jugadorPrincipal.getPos();

                            if (mapa.hayItem(p)) {
                                Item it = mapa.recogerItem(p);
                                it.aplicar(jugadorPrincipal.getPersonaje());
                                System.out.println(jugadorPrincipal.getNombre() + " ha recogido " + it);
                            }

                        }
                        break;
                }

                refrescar();
            }
        });

        setFocusable(true);
        requestFocusInWindow();

        setVisible(true);
        
        // chatgpt:
        // garantiza que se inicialice la ventana, la camara y los jugadores
        SwingUtilities.invokeLater(() -> {
            refrescar();
        });

       
    }

    // actualizar
    public void refrescar() {
        panelHUD.actualizar();
        panelMapa.actualizarCamara(jugadorPrincipal);
        panelMapa.repaint();
    }
}



// panel con camara y tiles
class PanelMapa extends JPanel {

    private Mapa mapa;
    private final int cellSize = 50;

    // camara
    private int camX = 0;
    private int camY = 0;

    // tamaño visible
    private final int vistaAncho = 12;
    private final int vistaAlto = 12;

    // tiles del mapa
    private Image tilePasto;
    private Image tilePiedra;
    
    // sprites de pociones
    private Image spritePocionVida;
    private Image spritePocionMana;

    // sprites del jugador 
    private Image spriteJugadorActual;
    private Image spriteGuerreroDer;
    private Image spriteGuerreroIzq;

    private Image spriteArqueroDer;
    private Image spriteArqueroIzq;

    private Image spriteMagoDer;
    private Image spriteMagoIzq;



    public PanelMapa(Mapa mapa) {
        this.mapa = mapa;

        // cargar tiles individuales
        tilePasto = new ImageIcon("C:/Users/pilar/git/BattleRoyale/src/juego/Sprites/Pasto1.png").getImage();
        tilePiedra = new ImageIcon("C:/Users/pilar/git/BattleRoyale/src/juego/Sprites/Pasto2.png").getImage();
        
        // carcar sprites de pociones
        spritePocionVida = new ImageIcon("C:/Users/pilar/git/BattleRoyale/src/juego/Sprites/PocionVida.jpg").getImage();
        spritePocionMana = new ImageIcon("C:/Users/pilar/git/BattleRoyale/src/juego/Sprites/PocionMana.jpg").getImage();
        
        // cargar sprites del jugador 
        spriteGuerreroDer = new ImageIcon("C:/Users/pilar/git/BattleRoyale/src/juego/Sprites/Guerr_der.jpg").getImage();
        spriteGuerreroIzq = new ImageIcon("C:/Users/pilar/git/BattleRoyale/src/juego/Sprites/Guerr_izq.jpg").getImage();

        spriteArqueroDer  = new ImageIcon("C:/Users/pilar/git/BattleRoyale/src/juego/Sprites/Arq_der.jpg").getImage();
        spriteArqueroIzq  = new ImageIcon("C:/Users/pilar/git/BattleRoyale/src/juego/Sprites/Arq_izq.jpg").getImage();

        spriteMagoDer     = new ImageIcon("C:/Users/pilar/git/BattleRoyale/src/juego/Sprites/Mago_der.jpg").getImage();
        spriteMagoIzq     = new ImageIcon("C:/Users/pilar/git/BattleRoyale/src/juego/Sprites/Mago_izq.jpg").getImage();


        
        setPreferredSize(new Dimension(
                vistaAncho * cellSize,
                vistaAlto * cellSize
        ));
        spriteJugadorActual = spriteMagoDer;
    }

    // actualizar sprite segun dirección
    public void setSpriteDireccion(String dir, Jugador jugador) {

        switch (jugador.getClase()) {

            case "Guerrero":
                if (dir.equals("IZQUIERDA")) 
                	spriteJugadorActual = spriteGuerreroIzq;
                else 
                    spriteJugadorActual = spriteGuerreroDer;
                break;

            case "Arquero":
                if (dir.equals("IZQUIERDA")) 
                	spriteJugadorActual = spriteArqueroIzq;
                else 
                	spriteJugadorActual = spriteArqueroDer;
                break;

            case "Mago":
            default:
                if (dir.equals("IZQUIERDA")) 
                	spriteJugadorActual = spriteMagoIzq;
                else 
                	spriteJugadorActual = spriteMagoDer;
                break;
        }
    }


    // actualizar camara
    public void actualizarCamara(Jugador jugador) {

        camX = jugador.getPos().getX() - vistaAncho / 2;
        camY = jugador.getPos().getY() - vistaAlto / 2;

        if (camX < 0) camX = 0;
        if (camY < 0) camY = 0;

        if (camX > mapa.getAncho() - vistaAncho)
            camX = mapa.getAncho() - vistaAncho;

        if (camY > mapa.getAlto() - vistaAlto)
            camY = mapa.getAlto() - vistaAlto;
    }
    
    private void dibujarZonaSegura(Graphics g) {

        // obtener limites de la zona segura desde el mapa
        int xMin = mapa.getZonaXMin();
        int xMax = mapa.getZonaXMax();
        int yMin = mapa.getZonaYMin();
        int yMax = mapa.getZonaYMax();

        // convertir a coordenadas de la pantalla
        int x = (xMin - camX) * cellSize;
        int y = (yMin - camY) * cellSize;
        int width  = (xMax - xMin + 1) * cellSize;
        int height = (yMax - yMin + 1) * cellSize;

        // borde azul de la zona segura
        g.setColor(new Color(0, 150, 255, 180)); 
        g.drawRect(x, y, width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // dibujar tiles
        for (int vx = 0; vx < vistaAncho; vx++) {
            for (int vy = 0; vy < vistaAlto; vy++) {

                int mx = camX + vx;
                int my = camY + vy;

                int dx = vx * cellSize;
                int dy = vy * cellSize;

                if ((mx + my) % 2 == 0)
                    g.drawImage(tilePasto, dx, dy, cellSize, cellSize, null);
                else
                    g.drawImage(tilePiedra, dx, dy, cellSize, cellSize, null);
            }
        }

        // dibujar jugadores
        for (Jugador j : mapa.getJugadores()) {

            int px = j.getPos().getX();
            int py = j.getPos().getY();

            if (px >= camX && px < camX + vistaAncho &&
                py >= camY && py < camY + vistaAlto)
            {
                dibujarJugador(g, j, px, py);
            }
        }

        // cuadricula
        g.setColor(Color.GRAY);
        for (int x = 0; x <= vistaAncho; x++)
            g.drawLine(x * cellSize, 0, x * cellSize, vistaAlto * cellSize);

        for (int y = 0; y <= vistaAlto; y++) {
        	g.drawLine(0, y * cellSize, vistaAncho * cellSize, y * cellSize);
        }
        dibujarZonaSegura(g);
        dibujarItems(g);
    }
    
    // dibujar items
    private void dibujarItems(Graphics g) {

        for (Item it : mapa.getItems()) {

            int px = it.getPos().getX();
            int py = it.getPos().getY();

            if (px >= camX && px < camX + vistaAncho &&
                py >= camY && py < camY + vistaAlto)
            {
                Image icon = (it.getTipo() == TipoItem.POCION_VIDA)
                            ? spritePocionVida
                            : spritePocionMana;

                g.drawImage(icon, 
                            (px - camX) * cellSize, 
                            (py - camY) * cellSize, 
                            cellSize, cellSize, 
                            null);
            }
        }
    }


    private void dibujarJugador(Graphics g, Jugador j, int px, int py) {
        int dx = (px - camX) * cellSize;
        int dy = (py - camY) * cellSize;

        // si es el jugador principal se usa el sprite animado
        if (j.esHumano())
            g.drawImage(spriteJugadorActual, dx, dy, cellSize, cellSize, null);
        else {
            // para bots usamos sprite base (mirando derecha)
            switch(j.getClase()) {
                case "Guerrero": 
                	g.drawImage(spriteGuerreroDer, dx, dy, cellSize, cellSize, null); 
                	break;
                case "Arquero":  
                	g.drawImage(spriteArqueroDer, dx, dy, cellSize, cellSize, null); 
                	break;
                case "Mago":     
                default:         
                	g.drawImage(spriteMagoDer, dx, dy, cellSize, cellSize, null); 
                	break;
            }
        }
    }

}



// panel hud
class PanelHUD extends JPanel {

    private JLabel lblVida;
    private JLabel lblMana;
    private JLabel lblArma;
    private JLabel lblNombre;

    JButton btnAtacar;
    JButton btnHab1;
    JButton btnHab2;

    private Partida partida;
    private Jugador jugadorPrincipal;
    private VentanaJuego ventana;

    public PanelHUD(Partida partida, Jugador jugadorPrincipal, VentanaJuego ventana) {

        this.partida = partida;
        this.jugadorPrincipal = jugadorPrincipal;
        this.ventana = ventana;

        setLayout(new GridLayout(12, 1));
        setPreferredSize(new Dimension(200, 700));

        lblNombre = new JLabel();
        lblVida = new JLabel();
        lblMana = new JLabel();
        lblArma = new JLabel();

        btnAtacar = new JButton("Atacar");
        btnHab1 = new JButton("Habilidad Normal");
        btnHab2 = new JButton("Habilidad Especial");

        add(new JLabel("=== ESTADO DEL JUGADOR ==="));
        add(lblNombre);
        add(lblVida);
        add(lblMana);
        add(lblArma);
        add(new JLabel("----------------------"));
        add(btnAtacar);
        add(btnHab1);
        add(btnHab2);

        // eventos
        btnAtacar.addActionListener(e -> {
            partida.turnoJugador(jugadorPrincipal);
            partida.turnoBots();
            ventana.refrescar();
        });

        btnHab1.addActionListener(e -> {
            jugadorPrincipal.getPersonaje().habilidadNormal();
            partida.turnoBots();
            ventana.refrescar();
        });

        btnHab2.addActionListener(e -> {
            jugadorPrincipal.getPersonaje().habilidadEspecial();
            partida.turnoBots();
            ventana.refrescar();
        });

        actualizar();
    }

    public void actualizar() {
        lblNombre.setText("Jugador: " + jugadorPrincipal.getNombre());
        lblVida.setText("Vida: " + jugadorPrincipal.getVida());
        lblMana.setText("Maná: " + jugadorPrincipal.getMana());
        lblArma.setText("Arma: " + jugadorPrincipal.getArma().getNombre());
    }
}
