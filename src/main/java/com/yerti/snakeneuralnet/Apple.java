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
        location = new Rectangle(new Random().nextInt(1920), new Random().nextInt(1080), 50, 50);
    }
}
