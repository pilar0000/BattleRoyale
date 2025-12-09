package juego;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// ventana principal del juego

public class VentanaJuego extends JFrame {

    private PanelMapa panelMapa;
    private PanelHUD panelHUD;

    private Mapa mapa;
    private Jugador jugadorPrincipal;

    public VentanaJuego(Mapa mapa, Jugador jugadorPrincipal) {
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

        setVisible(true);
    }

    public void refrescar() {
        panelHUD.actualizar(jugadorPrincipal);
        panelMapa.repaint();
    }
}


// panel del mapa
class PanelMapa extends JPanel {

    private Mapa mapa;
    private final int cellSize = 50;

    public PanelMapa(Mapa mapa) {
        this.mapa = mapa;
        setPreferredSize(new Dimension(10 * cellSize, 10 * cellSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // fondo del mapa
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        // dibujar cuadrícula
        g.setColor(Color.GRAY);
        for (int i = 0; i <= 10; i++) {
            g.drawLine(i * cellSize, 0, i * cellSize, 10 * cellSize);
            g.drawLine(0, i * cellSize, 10 * cellSize, i * cellSize);
        }

        // dibujar cada elemento del mapa
        for (Jugador j : mapa.getJugadores()) {
            dibujarJugador(g, j);
        }

        // items en el suelo
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Posicion p = new Posicion(x, y);
                if (mapa.hayItem(p)) {
                    dibujarItem(g, mapa.getItem(p));
                }
            }
        }

        // zonas seguras/mortales
        dibujarZonas(g);
    }


    // dibujar zonas mortales en gris
    private void dibujarZonas(Graphics g) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Posicion p = new Posicion(x, y);
                if (!mapa.dentroZonaSegura(p)) {
                    g.setColor(new Color(60, 60, 60, 120));
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }
    }


    // diujo del jugador o bot
    private void dibujarJugador(Graphics g, Jugador j) {

        int px = j.getPos().getX() * cellSize;
        int py = j.getPos().getY() * cellSize;

        if (j.esHumano()) {
            // Jugador: círculo azul
            g.setColor(Color.BLUE);
            g.fillOval(px + 5, py + 5, cellSize - 10, cellSize - 10);
        } else {
            // Bot: triángulo rojo
            g.setColor(Color.RED);
            int[] xs = {px + cellSize / 2, px + 5, px + cellSize - 5};
            int[] ys = {py + 5, py + cellSize - 5, py + cellSize - 5};
            g.fillPolygon(xs, ys, 3);
        }
    }

    // dibujo de pociones
    private void dibujarItem(Graphics g, Item item) {
        Posicion p = item.getPos();
        int px = p.getX() * cellSize;
        int py = p.getY() * cellSize;

        switch (item.getTipo()) {
            case POCION_VIDA:
                g.setColor(Color.GREEN);
                g.fillRect(px + 10, py + 10, cellSize - 20, cellSize - 20);
                break;

            case POCION_MANA:
                g.setColor(Color.CYAN);
                int[] xs = {px + cellSize/2, px + cellSize - 5, px + cellSize/2, px + 5};
                int[] ys = {py + 5, py + cellSize/2, py + cellSize - 5, py + cellSize/2};
                g.fillPolygon(xs, ys, 4);
                break;
        }
    }
}



// informacion del jugador
class PanelHUD extends JPanel {

    private JLabel lblVida;
    private JLabel lblMana;
    private JLabel lblArma;

    private Jugador jugador;

    public PanelHUD(Jugador jugador) {
        this.jugador = jugador;

        setLayout(new GridLayout(10, 1));
        setPreferredSize(new Dimension(200, 700));

        lblVida = new JLabel("Vida: ");
        lblMana = new JLabel("Maná: ");
        lblArma = new JLabel("Arma: ");

        add(new JLabel("=== ESTADO DEL JUGADOR ==="));
        add(lblVida);
        add(lblMana);
        add(lblArma);

        actualizar(jugador);
    }

    public void actualizar(Jugador j) {
    	lblVida.setText("Vida: " + j.getVida());
    	lblMana.setText("Maná: " + j.getMana());
    	lblArma.setText("Arma: " + (j.getArma() != null ? j.getArma().getNombre() : "Ninguna"));
    }
}