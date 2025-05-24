package game;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;

public class player {
    private int x, y;
    private int dx = 0;
    private final int width = 40;
    private final int height = 40;
    private Image playerImage;

    public player(int x, int y) {
        this.x = x;
        this.y = y;

        try {
            playerImage = ImageIO.read(getClass().getResource("/game/resources/char.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        x += dx;
        if (x < 0) x = 0;
        if (x > 260) x = 260; // panel genişliği 300, player genişliği 40
    }

    public void draw(Graphics g) {
        g.drawImage(playerImage, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setX(int x) {
        this.x = x;
    }
}
