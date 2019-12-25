package com.yerti.snakeneuralnet;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class GamePanel extends JPanel implements KeyListener, ActionListener {


    private GameMode gameMode = GameMode.USER;
    private boolean started = false;
    private int score;
    private int generation;
    private boolean feelerDebug = false;
    private Snake snake;
    private Apple apple;

    GamePanel() {
        this.setOpaque(true);
        this.setBackground(new Color(51, 51, 51));
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
        this.snake = new Snake(this);


        this.apple = new Apple();
        this.apple.spawn();

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 50, 50);
        g.drawString("Size: " + snake.getSize(), 50, 60);
        g.drawString("Generation: " + generation, 50, 70);
        Graphics2D g2 = (Graphics2D) g;

        snake.draw(g);

        apple.draw(g);
        //apple.draw(g);

        if (feelerDebug) {
            g2.setColor(Color.PINK);

            //Right
            g2.drawLine((int) snake.getHead().getX() + 70, (int) snake.getHead().getY() + 20, 0, 0);
            g2.setColor(Color.LIGHT_GRAY);
            //Left
            g2.drawLine((int) snake.getHead().getX() - 5, (int) snake.getHead().getY() + 20, 0, 0);

            //Up
            g2.setColor(Color.CYAN);
            g2.drawLine((int) snake.getHead().getX() + 5, (int) snake.getHead().getY() - 5, 0, 0);

            //Down
            g2.setColor(Color.YELLOW);
            g2.drawLine((int) snake.getHead().getX() + 5, (int) snake.getHead().getY() + 70, 0, 0);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {







        if (gameMode.equals(GameMode.NEURAL)) return;


        if (e.getKeyChar() == KeyEvent.VK_SPACE) {
            System.out.println("yoink");

            if (snake.getSnakeColor().equals(Color.RED) || !started) {
                repaint();
                snake.buildBody();
                //snake.setSpeed(50);
                snake.setDirection(Direction.RIGHT);
                snake.setSnakeColor(Color.GREEN);
                started = true;
                return;

            }


        }


        if (snake.getHead() == null) return;
        int headX = snake.getHead().x;
        int headY = snake.getHead().y;

        if (e.getKeyChar() == 'w') {
            if (snake.containsCoHeadSnake(headX + 5, headY - 5)) {
                kill();
                return;
            } else if (snake.containsSnake(headX + 5, headY - 5)) {
                return;
            }

            snake.setDirection(Direction.UP);
        }
        if (e.getKeyChar() == 'a') {
            if (snake.containsCoHeadSnake(headX - 40, headY + 20)) {
                kill();
                return;
            } else if (snake.containsSnake(headX - 40, headY + 20)) return;
            snake.setDirection(Direction.LEFT);
        }

        if (e.getKeyChar() == 'd') {
            if (snake.containsCoHeadSnake(headX + 70, headY + 20)) {
                kill();
                return;
            } else if (snake.containsSnake(headX + 70, headY + 20)) return;
            snake.setDirection(Direction.RIGHT);
        }

        if (e.getKeyChar() == 's') {
            if (snake.containsCoHeadSnake(headX, headY + 70)) {
                kill();
                return;
            } else if (snake.containsSnake(headX, headY + 70)) {
                return;
            }
            snake.setDirection(Direction.DOWN);
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 't') {
            System.out.println("epic");
            if (gameMode == GameMode.USER) {
                gameMode = GameMode.NEURAL;
                snake.buildBody();
                //snake.setSpeed(10);
                snake.setDirection(Direction.RIGHT);
                snake.setSnakeColor(Color.GREEN);

            } else {
                gameMode = GameMode.USER;
            }

            System.out.println("Enabled " + gameMode.name());
        }
    }

    Snake getSnake() {
        return snake;
    }

    Apple getApple() {
        return apple;
    }

    void addPoints(int amount) {
        score += amount;
    }

    int getPoints() {
        return score;
    }

    GameMode getGameMode() {
        return gameMode;
    }

    private void kill() {

        snake.setSnakeColor(Color.RED);
        repaint();

    }

    void setPoints(int amount) {
        score = amount;
    }

    void setGeneration(int generation) {
        this.generation = generation;
    }




}
