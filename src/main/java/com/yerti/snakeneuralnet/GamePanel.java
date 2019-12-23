package com.yerti.snakeneuralnet;

import com.sun.javafx.scene.traversal.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class GamePanel extends JPanel implements KeyListener, ActionListener {

    private Snake snake;

    GamePanel() {
        this.setOpaque(true);
        this.setBackground(new Color(51, 51, 51));
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
        this.snake = new Snake(this);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.setColor(Color.WHITE);
        //g.drawString("Head: " + snake.getHead().getX() + ", " + snake.getHead().getY(), 50, 50);
        snake.draw(g);


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
            if (snake.containsCoHeadSnake(headX, headY - 70)) {
                snake.setSnakeColor(Color.RED);
                repaint();
                return;
            }
            snake.setDirection(Direction.UP);
        }

        if (e.getKeyChar() == 'a') {
            if (snake.containsCoHeadSnake(headX - 70, headY)) {
                snake.setSnakeColor(Color.RED);
                repaint();
                return;
            }
            snake.setDirection(Direction.LEFT);
        }

        if (e.getKeyChar() == 'd') {
            if (snake.containsCoHeadSnake(headX + 70, headY)) {
                snake.setSnakeColor(Color.RED);
                repaint();
                return;
            }
            snake.setDirection(Direction.RIGHT);
        }

        if (e.getKeyChar() == 's') {
            if (snake.containsCoHeadSnake(headX, headY + 70)) {
                snake.setSnakeColor(Color.RED);
                repaint();
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

}
