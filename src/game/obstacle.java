package game;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;

public class obstacle {
    private int x, y;
    private int speed;
    private final int width = 40;
    private final int height = 40;
    private Image obstacleImage;

    public obstacle(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        try {
            obstacleImage = ImageIO.read(getClass().getResource("/game/resources/meteor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        y += speed;
    }

    public void draw(Graphics g) {
        g.drawImage(obstacleImage, x, y, width, height, null);
    }

    
    public Rectangle getBounds() {
        return new Rectangle(x + 5, y + 5, width - 10, height - 10);
    }

    public int getY() {
        return y;
       
      }
}