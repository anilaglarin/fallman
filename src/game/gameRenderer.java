package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class gameRenderer {
    private gamePanel panel;
    private gameLogic logic;
    private Image backgroundImage;

    public gameRenderer(gamePanel panel, gameLogic logic) {
        this.panel = panel;
        this.logic = logic;

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/game/resources/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null;
        }
    }

    public void render(Graphics g) {
        
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, panel.getWidth(), panel.getHeight(), null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        }

        // Karakteri çizer
        logic.getPlayer().draw(g);

        // Engelleri çizer
        ArrayList<obstacle> obstacles = logic.getObstacles();
        for (obstacle obs : obstacles) {
            obs.draw(g);
        }

        // Skor ve level 
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Skor: " + logic.getScore(), 10, 20);
        g.drawString("Level: " + logic.getLevel(), 10, 40);

        
        if (logic.isGameOver()) {
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.setColor(Color.RED);
            g.drawString("Oyun Bitti!", panel.getWidth() / 2 - 70, panel.getHeight() / 2 - 20);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("Tekrar başlamak için ENTER'a bas", panel.getWidth() / 2 - 120, panel.getHeight() / 2 + 10);
        }
    }
}
