package com.yerti.snakeneuralnet;

import java.awt.*;
import java.util.Random;

public class Apple {

    private Rectangle location;

    public void delete() {
        location = null;
    }

    public void draw(Graphics g) {
        if (location == null) return;
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.PINK);
        g2.fill(location);
    }

    boolean contains(int x, int y) {
        if (location == null) return false;
        return location.contains(x, y);
    }

    public void spawn() {
        //
        location = new Rectangle(550, 550, 50, 50);
    }

    //for testing purposes
    public void spawn(boolean random) {
        location = new Rectangle(new Random().nextInt(42) * 50 + 100, new Random().nextInt(20) * 50 + 100, 50, 50);
    }

    public Rectangle getLocation() {
        return location;
    }
}
