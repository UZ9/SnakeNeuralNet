package com.yerti.snakeneuralnet;

import com.sun.javafx.scene.traversal.Direction;

import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

class GamePanel extends JPanel implements KeyListener, ActionListener {

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
        //g.setColor(Color.WHITE);
        //g.drawString("Head: " + snake.getHead().getX() + ", " + snake.getHead().getY(), 50, 50);
        Graphics2D g2 = (Graphics2D) g;

        snake.draw(g);
        apple.draw(g);

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

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int headX = snake.getHead().x;
        int headY = snake.getHead().y;

        if (e.getKeyChar() == 'w') {
            System.out.println("pressed w");
            if (snake.containsCoHeadSnake(headX + 5, headY - 5)) {
                snake.setSnakeColor(Color.RED);
                repaint();
                return;
            } else if (snake.containsSnake(headX + 5, headY - 5)) {
                return;
            }

            snake.setDirection(Direction.UP);
        }
        if (e.getKeyChar() == 'a') {
            if (snake.containsCoHeadSnake(headX - 40, headY + 20)) {
                snake.setSnakeColor(Color.RED);
                repaint();
                return;
            } else if (snake.containsSnake(headX - 40, headY + 20)) return;
            snake.setDirection(Direction.LEFT);
        }

        if (e.getKeyChar() == 'd') {
            if (snake.containsCoHeadSnake(headX + 70, headY + 20)) {
                snake.setSnakeColor(Color.RED);
                repaint();
                return;
            } else if (snake.containsSnake(headX + 70, headY + 20)) return;
            snake.setDirection(Direction.RIGHT);
        }

        if (e.getKeyChar() == 's') {
            if (snake.containsCoHeadSnake(headX, headY + 70)) {
                snake.setSnakeColor(Color.RED);
                repaint();
                return;
            } else if (snake.containsSnake(headX, headY + 70)) {
                System.out.println("s cancel");
                return;
            }
            snake.setDirection(Direction.DOWN);
        }

        if (e.getKeyChar() == KeyEvent.VK_SPACE) {

            if (snake.getSnakeColor().equals(Color.RED)) {
                repaint();
                snake.buildBody();
                snake.setDirection(Direction.RIGHT);
                snake.setSnakeColor(Color.GREEN);
            }


        }
    }

    Snake getSnake() {
        return snake;
    }

    Apple getApple() {
        return apple;
    }



}
