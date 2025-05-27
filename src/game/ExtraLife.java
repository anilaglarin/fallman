package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ExtraLife {
    private int x, y;
    private final int width = 40;
    private final int height = 40;
    private Image image;

    public ExtraLife(int x, int y,int speed) {
        this.x = x;
        this.y = y;
        
        try {
            image = ImageIO.read(getClass().getResource("/game/resources/extralife.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getY() {
        return y;
    }

    public void update() {
        y += 5;  // aşağıya yavaşça düşsün
}
}