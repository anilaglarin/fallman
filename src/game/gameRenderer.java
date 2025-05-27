package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;

public class gameRenderer {
    private gamePanel panel;
    private gameLogic logic;
    private Image backgroundImage;

    public gameRenderer(gamePanel panel, gameLogic logic) {
        this.panel = panel;
        this.logic = logic;

        // Arka plan resmini yükle
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/game/resources/background.jpg"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Background image not found!");
            backgroundImage = null;
        }
    }

    public void render(Graphics g) {
        // Arka plan resmi
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, panel.getWidth(), panel.getHeight(), null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        }

        // Oyuncu ve engeller
        logic.getPlayer().draw(g);
        for (obstacle obs : logic.getObstacles()) {
            obs.draw(g);
        }

        // Extra life varsa çiz
        if (logic.getExtraLife() != null) {
            logic.getExtraLife().draw(g);
        }

        // Skor, max skor ve kalan canlar
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Max Score: " + logic.getMaxScore(), 10, 50);
        
        g.drawString("Extra Lives: " + logic.getExtraLives(), 10, 80); // ➕ Ekstra can sayısı

        // Başlangıç mesajı
        if (!logic.isGameStarted()) {
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.setColor(Color.YELLOW);
            g.drawString("Press SPACE to Start", 65, panel.getHeight() / 2);
        }

        // Oyun duraklatıldıysa
        if (logic.isPaused()) {
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.setColor(Color.YELLOW);
            g.drawString("PAUSE", panel.getWidth() / 2 - 60, panel.getHeight() / 2);
        }

        // Oyun bittiyse
        if (logic.isGameOver()) {
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.setColor(Color.RED);
            g.drawString("Game Over! ", 80, panel.getHeight() / 2 - 20);
            g.drawString("Press ENTER to Restart", 40, panel.getHeight() / 2 + 10);
 }
}
}