package game;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

public class gamePanel extends JPanel {
    private final int WIDTH = 300;
    private final int HEIGHT = 500;

    private gameLogic logic;
    private gameRenderer renderer;

    public gamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        logic = new gameLogic(this);
        renderer = new gameRenderer(this, logic);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderer.render(g);
    }
}
