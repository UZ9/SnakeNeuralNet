package com.yerti.snakeneuralnet;


import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.LinkedList;

class Snake {

    private final int STARTING_SIZE = 10;
    private Color snakeColor = Color.GREEN;
    private Robot imageDetection;
    private int lastScore;
    private NNGenetics trainer = new NNGenetics();
    private double speed = 20;
    private GamePanel panel;
    private NeuralNet net = new NeuralNet();
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

        trainer.getListeners().add(panel::setGeneration);

        //buildBody();


    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        g2.drawRect(100, 100, 2200, 1100);
        g2.setColor(snakeColor);
        if (body == null || body.isEmpty()) return;

        for (Rectangle rectangle : body) {

            g2.fill(rectangle);

        }
    }

    public void move() {

        panel.setPoints(body.size());

        //panel.addPoints(1);

        if (panel.getGameMode() == GameMode.NEURAL && getSnakeColor() == Color.RED) {
            buildBody();
            setDirection(Direction.RIGHT);
            setSnakeColor(Color.GREEN);
            panel.repaint();
        }

        if (snakeColor.equals(Color.RED)) return;
        if (body.isEmpty()) return;

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


        if (newHead.getX() > 2300 || newHead.getY() > 1200 || newHead.getX() < 100 || newHead.getY() < 100) {
            snakeColor = Color.RED;
        }


        if (containsSnake(newHead.getX(), newHead.getY())) snakeColor = Color.RED;
        Apple apple = panel.getApple();
        //Check if the center of the snake is inside of the apple


        if (apple.contains((int) newHead.getX() + 25, (int) newHead.getY() + 25)) {
            apple.delete();
            apple.spawn(true);
            //panel.addPoints(1000);

            Rectangle added = new Rectangle(body.getLast());


            switch (direction) {
                case UP:
                    added.setRect(added.getX(), added.getY() - 50, 50, 50);
                    break;
                case DOWN:
                    added.setRect(added.getX(), added.getY() + 50, 50, 50);
                    break;
                case RIGHT:
                    added.setRect(added.getX() + 50, added.getY(), 50, 50);
                    break;
                case LEFT:
                    added.setRect(added.getX() - 50, added.getY(), 50, 50);
                    break;
            }

            body.add(added);
            speed -= 1;
        } else {
            //panel.setPoints(panel.getPoints() - 1);
        }
        body.add(newHead);
        panel.repaint();

        if (panel.getGameMode() == GameMode.USER) return;

        /*if (lastScore >= panel.getPoints()) {
            trainer.updateFitnessOfCurrent(panel.getPoints());
            lastScore = panel.getPoints() + body.size();
        }*/

        Direction[] directions = new Direction[]{Direction.LEFT, Direction.DOWN, Direction.RIGHT, Direction.UP};
        Direction bestDirection = Direction.DOWN;
        double bestEval = -10;
        NeuralNet decider = trainer.getCurrent();

        for (Direction dir : directions) {
            if (areOppositeDirections(dir, direction)) {
                continue;
            }


            System.out.println("starting process for " + dir.name());



            Rectangle nextMove = moveRectangle(body.getLast(), dir);

            //TODO: Change to forward, back, left, right
            //All of this is just proof of theory, to be rebuilt more efficiently soon
            //int left = -2;
            //int right = -2;

            double forward = -2;
            double right = -2;
            double left = -2;

            for (Direction secondDir : directions) {
                if (areOppositeDirections(secondDir, dir)) {
                    System.out.println(secondDir.name() + " was found to be opposite");

                    continue;
                }


                Rectangle secondMove = moveRectangle(nextMove, secondDir);

                if (secondDir == dir) {
                    forward = squareStatus(secondMove);
                } else if (Direction.getRelativeRight(dir) == secondDir) {
                    right = squareStatus(secondMove);
                } else if (Direction.getRelativeLeft(dir) == secondDir) {
                    left = squareStatus(secondMove);
                }

            }

            double appleDistance = distance(nextMove.getX(), nextMove.getY(), panel.getApple().getLocation().x, panel.getApple().getLocation().y);

            System.out.println("Current direction is " + this.direction.name());

            double eval = decider.calculateOutput(new double[] {normalize(appleDistance), left, forward, right});

            System.out.println("Eval for " + Arrays.toString((new double[]{normalize(appleDistance) * -1, left, forward, right})) + ":"  + eval);


            if (eval > bestEval) {
                bestEval = eval;
                bestDirection = dir;
            }
            decider.reset();

        }

        System.out.println("Best value: " + bestEval);
        System.out.println("Best direction " + bestDirection);

        setDirection(bestDirection);

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
        if (body.isEmpty()) return null;
        return body.get(body.size() - 1);
    }

    void buildBody() {
        panel.setPoints(0);
        if (!body.isEmpty()) body.clear();

        for (int i = 0; i < STARTING_SIZE; i++) {
            body.add(new Rectangle(300, i * 50 + 300, 50, 50));
        }


    }

    Color getSnakeColor() {
        return snakeColor;
    }

    void setSnakeColor(Color snakeColor) {
        this.snakeColor = snakeColor;
    }

    double getSpeed() {
        return speed;
    }

    void setSpeed(int speed) {
        this.speed = speed;
    }

    int getSize() {
        return body.size();
    }

    private boolean areOppositeDirections(Direction direction, Direction otherDirection) {
        if ((direction == Direction.LEFT && otherDirection == Direction.RIGHT) || (direction == Direction.RIGHT && otherDirection == Direction.LEFT))
            return true;
        return (direction == Direction.UP && otherDirection == Direction.DOWN) || (direction == Direction.DOWN && otherDirection == Direction.UP);


    }

    private boolean insideArea(double x, double y) {
        return x <= 2200 && x >= 100 && y >= 100 && y <= 1100;
    }

    private double squareStatus(Rectangle rectangle) {

        if (containsSnake(rectangle.x, rectangle.y) || !insideArea(rectangle.x, rectangle.y)) {
            return -.5;
        } else if (panel.getApple().contains(rectangle.x, rectangle.y)) {
            return 1;
        } else {
            return 0.1;
        }
    }

    private Rectangle moveRectangle(Rectangle starting, Direction direction) {
        switch (direction) {
            case UP:
                return new Rectangle((int) starting.getX(), (int) starting.getY() - 50, 50, 50);
            case DOWN:
                return new Rectangle((int) starting.getX(), (int) starting.getY() + 50, 50, 50);
            case RIGHT:
                return new Rectangle((int) starting.getX() + 50, (int) starting.getY(), 50, 50);
            case LEFT:
                return new Rectangle((int) starting.getX() - 50, (int) starting.getY(), 50, 50);
            default:
                throw new NullPointerException("Move Rectangle returned null");

        }

    }

    private double distance(double x1, double y1, double x2, double y2) {
        return Point2D.distance(x1, y1, x2, y2);
    }

    private double normalize(double value) {
       double maxDistance = distance(100, 100, 2200, 1100);
       double minDistance = 50;
       double range = maxDistance - minDistance;
       double split = range / 2.;
       return value / split - 1;



    }

    void updateFitness() {
        if(lastScore >= panel.getPoints()) {
            trainer.updateFitnessOfCurrent(panel.getPoints());
        }
    }



}
