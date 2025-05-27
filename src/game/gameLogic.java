package game;

import game.items.musicPlayer;
import java.io.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class gameLogic extends KeyAdapter {
    private gamePanel panel;
    private player player;
    private ArrayList<obstacle> obstacles;
    private ExtraLife extraLifeInstance;
    private musicPlayer music;

    private boolean gameOver = false;
    private boolean gameStarted = false;
    private boolean paused = false;

    private int score = 0;
    private int level = 1;
    private int speed = 2;

    private int maxScore = 0;
    private final String SCORE_FILE = "max_score.txt";

    private int extraLives = 0;

    public gameLogic(gamePanel panel) {
        this.panel = panel;
        player = new player(140, 400);
        obstacles = new ArrayList<>();
        music = new musicPlayer();

        panel.addKeyListener(this);
        panel.setFocusable(true);

        loadMaxScore();
        startGameLoop();
    }

    private void startGameLoop() {
        new Thread(() -> {
            while (true) {
                if (gameStarted && !gameOver && !paused) {
                    update();
                }
                panel.repaint();

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

        // Yeni engel oluştur
        if (obstacles.size() < 5) {
            int x = (int) (Math.random() * (panel.getWidth() - 40));
            obstacles.add(new obstacle(x, -40, speed));
        }

        // Kalbi rastgele zamanda çıkar 
        if (extraLifeInstance == null && Math.random() < 0.6) {
            int x = (int) (Math.random() * (panel.getWidth() - 40));
            extraLifeInstance = new ExtraLife(x, -40, 2);
        }

        // Kalbi güncelle
        if (extraLifeInstance != null) {
            extraLifeInstance.update();

            // Panel dışına çıktıysa sil
            if (extraLifeInstance.getY() > panel.getHeight()) {
                extraLifeInstance = null;
            }

            // Oyuncu kalple çarpıştıysa
            if (extraLifeInstance != null && extraLifeInstance.getBounds().intersects(player.getBounds())) {
                extraLives++;
                extraLifeInstance = null;
            }
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
                if (extraLives > 0) {
                    extraLives--;
                    iterator.remove();
                } else {
                    gameOver = true;
                    music.stopMusic();
                    if (score > maxScore) {
                        maxScore = score;
                        saveMaxScore();
                    }
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (!gameStarted && key == KeyEvent.VK_SPACE) {
            gameStarted = true;
            music.playMusic("/game/items/metin2.wav");
        } else if (gameStarted && !gameOver) {
            if (key == KeyEvent.VK_ESCAPE) {
                paused = !paused;
                if (paused) {
                    music.stopMusic();
                } else {
                    music.playMusic("/game/items/metin2.wav");
                }
            }

            if (!paused) {
                if (key == KeyEvent.VK_LEFT) {
                    player.setDx(-5);
                } else if (key == KeyEvent.VK_RIGHT) {
                    player.setDx(5);
                }
            } else {
                player.setDx(0);
            }
        } else if (gameOver && key == KeyEvent.VK_ENTER) {
            restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) && !paused) {
            player.setDx(0);
        }
    }

    private void restartGame() {
        gameOver = false;
        score = 0;
        level = 1;
        speed = 3;
        extraLives = 0;
        obstacles.clear();
        extraLifeInstance = null;
        player.setX(130);
        player.setDx(0);
        paused = false;
        music.playMusic("/game/items/metin2.wav");
    }

    private void saveMaxScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE))) {
            writer.write(String.valueOf(maxScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMaxScore() {
        File file = new File(SCORE_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                maxScore = Integer.parseInt(reader.readLine());
            } catch (IOException | NumberFormatException e) {
                maxScore = 0;
            }
        }
    }

    // Getter metodları
    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public boolean isPaused() {
        return paused;
    }

    public player getPlayer() {
        return player;
    }

    public ArrayList<obstacle> getObstacles() {
        return obstacles;
    }

    public ExtraLife getExtraLife() {
        return extraLifeInstance;
    }

    public int getExtraLives() {
        return extraLives;
    }
}