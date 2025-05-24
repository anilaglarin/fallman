package game;

import game.items.musicPlayer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class gameLogic extends KeyAdapter {
    private gamePanel panel;
    private player player;
    private ArrayList<obstacle> obstacles;
    private musicPlayer music;

    private boolean gameOver = false;
    private int score = 0;
    private int level = 1;
    private int speed = 3;

    public gameLogic(gamePanel panel) {
        this.panel = panel;
        player = new player(140, 400);
        obstacles = new ArrayList<>();
        music = new musicPlayer();

        panel.addKeyListener(this);
        panel.setFocusable(true);

        startGame();
    }

    public void startGame() {
        music.playMusic("/game/items/metin2.wav");

        new Thread(() -> {
            while (true) {
                if (!gameOver) {
                    update();
                    panel.repaint();
                }
                try {
                    Thread.sleep(17);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void update() {
        player.update();

        if (obstacles.size() < 5) {
            int x = (int) (Math.random() * (panel.getWidth() - 40));
            obstacles.add(new obstacle(x, -40, speed));
        }

        Iterator<obstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            obstacle obs = iterator.next();
            obs.update();

            if (obs.getY() > panel.getHeight()) {
                iterator.remove();
                score++;
                if (score % 10 == 0) {
                    level++;
                    speed++;
                }
            }

            if (obs.getBounds().intersects(player.getBounds())) {
                gameOver = true;
                music.stopMusic();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!gameOver) {
            if (key == KeyEvent.VK_LEFT) {
                player.setDx(-5);
            } else if (key == KeyEvent.VK_RIGHT) {
                player.setDx(5);
            }
        } else {
            if (key == KeyEvent.VK_ENTER) {
                restartGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            player.setDx(0);
        }
    }

    private void restartGame() {   //oyun bitince müzik tekrar başlasın diye
        gameOver = false;
        score = 0;
        level = 1;
        speed = 3;
        obstacles.clear();
        player.setX(130);
        player.setDx(0);
        music.playMusic("/game/items/metin2.wav");
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public player getPlayer() {
        return player;
    }

    public ArrayList<obstacle> getObstacles() {
        return obstacles;
    }
}
