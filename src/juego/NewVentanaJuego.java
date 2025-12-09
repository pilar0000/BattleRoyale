package juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

// =====================================================
//                VENTANA PRINCIPAL DEL JUEGO
// =====================================================
public class NewVentanaJuego extends JFrame {

    private PanelMapa panelMapa;
    private PanelHUD panelHUD;

    private Mapa mapa;
    private Jugador jugadorPrincipal;

    private long ultimoMovimiento = 0;
    private final long retrasoMovimiento = 120;

    public NewVentanaJuego(Mapa mapa, Jugador jugadorPrincipal) {

        this.mapa = mapa;
        this.jugadorPrincipal = jugadorPrincipal;

        setTitle("Battle Royale");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panelMapa = new PanelMapa(mapa);
        panelHUD = new PanelHUD(jugadorPrincipal);

        add(panelMapa, BorderLayout.CENTER);
        add(panelHUD, BorderLayout.EAST);

        // Listener de movimiento
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

                    case KeyEvent.VK_A:
                        if (x > 0) {
                            jugadorPrincipal.moverIzquierda();
                            panelMapa.setSpriteDireccion("IZQUIERDA");
                        }
                        break;

                    case KeyEvent.VK_D:
                        if (x < mapa.getAncho() - 1) {
                            jugadorPrincipal.moverDerecha();
                            panelMapa.setSpriteDireccion("DERECHA");
                        }
                        break;

                    case KeyEvent.VK_W:
                        if (y > 0) jugadorPrincipal.moverArriba();
                        break;

                    case KeyEvent.VK_S:
                        if (y < mapa.getAlto() - 1) jugadorPrincipal.moverAbajo();
                        break;
                }

                refrescar();
            }
        });

        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);
    }

    public void refrescar() {
        panelHUD.actualizar(jugadorPrincipal);
        panelMapa.actualizarCamara(jugadorPrincipal);
        panelMapa.repaint();
    }
}



// =======================================================
// ===============    PANEL MAPA (CÁMARA + ZONAS)  =========
// =======================================================
class PanelMapa extends JPanel {

    private Mapa mapa;
    private final int cellSize = 50;

    private int camX = 0;
    private int camY = 0;

    private final int vistaAncho = 12;
    private final int vistaAlto = 12;

    private Image tilePasto;
    private Image tilePiedra;

    private Image spriteJugadorDer;
    private Image spriteJugadorIzq;

    private Image spriteJugadorActual;


    public PanelMapa(Mapa mapa) {
        this.mapa = mapa;

        tilePasto = new ImageIcon("C:/Users/ppan5/git/BattleRoyale/src/juego/Sprites/Pasto1.png").getImage();
        tilePiedra = new ImageIcon("C:/Users/ppan5/git/BattleRoyale/src/juego/Sprites/Pasto2.png").getImage();

        spriteJugadorDer = new ImageIcon("C:/Users/ppan5/git/BattleRoyale/src/juego/Sprites/Mago_der.png").getImage();
        spriteJugadorIzq = new ImageIcon("C:/Users/ppan5/git/BattleRoyale/src/juego/Sprites/Mago_izq.png").getImage();

        spriteJugadorActual = spriteJugadorDer;

        setPreferredSize(new Dimension(vistaAncho * cellSize, vistaAlto * cellSize));
    }


    public void setSpriteDireccion(String dir) {
        if (dir.equals("IZQUIERDA")) spriteJugadorActual = spriteJugadorIzq;
        if (dir.equals("DERECHA")) spriteJugadorActual = spriteJugadorDer;
    }


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


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ===== DIBUJAR TILES =====
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

        // ===== DIBUJAR ÍTEMS =====
        for (Item item : mapa.getItems()) {
            dibujarItem(g, item);
        }

        // ===== DIBUJAR ZONAS =====
        dibujarZonas(g);

        // ===== DIBUJAR JUGADORES =====
        for (Jugador j : mapa.getJugadores()) {
            int px = j.getPos().getX();
            int py = j.getPos().getY();

            if (px >= camX && px < camX + vistaAncho &&
                py >= camY && py < camY + vistaAlto)
            {
                dibujarJugador(g, j, px, py);
            }
        }

        // cuadrícula
        g.setColor(Color.GRAY);
        for (int x = 0; x <= vistaAncho; x++)
            g.drawLine(x * cellSize, 0, x * cellSize, vistaAlto * cellSize);

        for (int y = 0; y <= vistaAlto; y++)
            g.drawLine(0, y * cellSize, vistaAncho * cellSize, y * cellSize);
    }



    // =============================
    //      DIBUJAR JUGADOR
    // =============================
    private void dibujarJugador(Graphics g, Jugador j, int px, int py) {

        int dx = (px - camX) * cellSize;
        int dy = (py - camY) * cellSize;

        g.drawImage(spriteJugadorActual, dx, dy, cellSize, cellSize, null);
    }


    // =============================
    //      DIBUJAR ÍTEMS
    // =============================
    private void dibujarItem(Graphics g, Item item) {

        int x = item.getPos().getX();
        int y = item.getPos().getY();

        if (x < camX || x >= camX + vistaAncho ||
            y < camY || y >= camY + vistaAlto)
            return;

        int dx = (x - camX) * cellSize;
        int dy = (y - camY) * cellSize;

        switch (item.getTipo()) {
            case POCION_VIDA:
                g.setColor(Color.GREEN);
                g.fillRect(dx + 10, dy + 10, cellSize - 20, cellSize - 20);
                break;

            case POCION_MANA:
                g.setColor(Color.CYAN);
                int[] xs = {dx + cellSize / 2, dx + cellSize - 5, dx + cellSize / 2, dx + 5};
                int[] ys = {dy + 5, dy + cellSize / 2, dy + cellSize - 5, dy + cellSize / 2};
                g.fillPolygon(xs, ys, 4);
                break;
        }
    }



    // =============================
    //      DIBUJAR ZONAS
    // =============================
    private void dibujarZonas(Graphics g) {

        for (int x = camX; x < camX + vistaAncho; x++) {
            for (int y = camY; y < camY + vistaAlto; y++) {

                Posicion p = new Posicion(x, y);

                if (!mapa.dentroZonaSegura(p)) {

                    int dx = (x - camX) * cellSize;
                    int dy = (y - camY) * cellSize;

                    g.setColor(new Color(60, 60, 60, 120));
                    g.fillRect(dx, dy, cellSize, cellSize);
                }
            }
        }
    }
}



// =======================================================
// =======================   HUD   ========================
// =======================================================
class PanelHUD extends JPanel {

    private JLabel lblVida;
    private JLabel lblMana;
    private JLabel lblArma;
    private JLabel lblnombre;

    private Jugador jugador;

    public PanelHUD(Jugador jugador) {
        this.jugador = jugador;

        setLayout(new GridLayout(10, 1));
        setPreferredSize(new Dimension(200, 700));

        lblVida = new JLabel("Vida: ");
        lblMana = new JLabel("Maná: ");
        lblArma = new JLabel("Arma: ");
        lblnombre = new JLabel("Jugador: ");

        add(new JLabel("=== ESTADO DEL JUGADOR ==="));

        add(lblnombre);
        add(lblVida);
        add(lblMana);
        add(lblArma);

        actualizar(jugador);
    }

    public void actualizar(Jugador j) {
        lblnombre.setText("Jugador: " + j.getNombre());
        lblVida.setText("Vida: " + j.getVida());
        lblMana.setText("Maná: " + j.getMana());
        lblArma.setText("Arma: " + (j.getArma() != null ? j.getArma().getNombre() : "Ninguna"));
    }
}
