package com.yerti.snakeneuralnet;

import javax.swing.*;

public class GameLoop implements Runnable {


    private final int SNAKE_SPEED = 500;
    private final int FPS = 60;
    private final int UPS = 60;
    private final boolean RENDER_TIME = true;
    private boolean running = false;

    private GamePanel panel;

    protected GameLoop(GamePanel panel) {
        this.panel = panel;
        running = true;
    }

    private void update() {
        //panel.getSnake().move();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        long initialTime = System.nanoTime();
        final double timeU = 1000000000. / UPS;
        final double timeF = 1000000000. / FPS;
        double deltaU = 0, deltaF = 0;
        int frames = 0, ticks = 0;
        long timer = System.currentTimeMillis();
        long snakeInterval = System.currentTimeMillis();

        while (running) {

            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;

            if (deltaU >= 1) {
                //getInput();
                update();
                ticks++;
                deltaU--;
            }

            if (deltaF >= 1) {
                //render();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                if (RENDER_TIME) {
                    //panel.getSnake().move();
                    System.out.println(String.format("UPS: %s, FPS: %s", ticks, frames));
                }
                frames = 0;
                ticks = 0;
                timer += 1000;
            }

            if (System.currentTimeMillis() - snakeInterval > SNAKE_SPEED) {
                panel.getSnake().move();
                snakeInterval += SNAKE_SPEED;
            }
        }
    }
}
