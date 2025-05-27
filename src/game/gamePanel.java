package game;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

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

    // Skor bilgilerini çiz
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD, 16));

    int y = 30;  // Başlangıç yüksekliği
    int spacing = 25;  // Her satır arası boşluk

    g.drawString("Score: " + logic.getScore(), 10, 35);
    y += spacing;
    
    y += spacing;
    g.drawString("Level: " + logic.getLevel(), 10, 65);

    // Oyun bittiyse ekran ortasına "Game Over" mesajı yaz
    if (logic.isGameOver()) {
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.RED);
        
        
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.setColor(Color.WHITE);
        

}
}
}