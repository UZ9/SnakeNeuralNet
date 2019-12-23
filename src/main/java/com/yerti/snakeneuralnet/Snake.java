package com.yerti.snakeneuralnet;

import com.sun.javafx.scene.traversal.Direction;

import java.awt.*;
import java.util.LinkedList;
import java.util.Optional;

class Snake {

    private Color snakeColor = Color.GREEN;
    private Robot imageDetection;
    private final int STARTING_SIZE = 10;
    private GamePanel panel;
    private Direction direction;
    private LinkedList<Rectangle> body;

    public Snake(GamePanel panel) {
        try {
            this.imageDetection = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        this.direction = Direction.RIGHT;
        this.panel = panel;
        this.body = new LinkedList<>();

        buildBody();


    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        g2.drawRect(100, 100, 2200, 1100);
        g2.setColor(snakeColor);
        for (Rectangle rectangle : body) {
                g2.fill(rectangle);

        }
    }

    public void move() {
        if (snakeColor.equals(Color.RED)) return;

        body.removeFirst();
        Rectangle newHead = new Rectangle(body.getLast());


        switch (direction) {
            case UP:
                newHead.setRect(newHead.getX(), newHead.getY() - 50, 50, 50);
                break;
            case DOWN:
                newHead.setRect(newHead.getX(), newHead.getY() + 50, 50, 50);
                break;
            case RIGHT:
                newHead.setRect(newHead.getX() + 50, newHead.getY(), 50, 50);
                break;
            case LEFT:
                newHead.setRect(newHead.getX() - 50, newHead.getY(), 50, 50);
                break;
        }

        if (newHead.getX() > 2300 || newHead.getY() > 1200 || newHead.getX() < 100 || newHead.getY() < 100) snakeColor = Color.RED;

        if (containsSnake(newHead.getX(), newHead.getY())) snakeColor = Color.RED;
        //Check if the center of the snake is inside of the apple
        if (panel.getApple().contains((int) newHead.getX() + 25, (int) newHead.getY() + 25)) {
            panel.getApple().delete();
            panel.getApple().spawn();
            panel.addPoints(1);
        }

        body.add(newHead);
        panel.repaint();




    }

    void setDirection(Direction direction) {
        this.direction = direction;
    }

    boolean containsSnake(double x, double y) {
        return body.stream().anyMatch(rectangle -> rectangle.contains(x, y));
    }

    boolean containsCoHeadSnake(double x, double y) {
        return body.stream().anyMatch(rectangle -> rectangle.contains(x, y) && body.indexOf(rectangle) != body.size() - 2);


    }

    Rectangle getHead() {
        return body.get(body.size() - 1);
    }

    void setSnakeColor(Color snakeColor) {
        this.snakeColor = snakeColor;
    }

    void buildBody() {
        if (!body.isEmpty()) body.clear();

        for (int i = 0; i < STARTING_SIZE; i++) {
            body.add(new Rectangle(i * 50 + 100, 100, 50, 50));
        }
    }

    Color getSnakeColor() {
        return snakeColor;
    }
}
