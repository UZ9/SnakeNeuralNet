package com.yerti.snakeneuralnet;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private GamePanel panel;
    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;

    GameFrame() {
        this.setTitle("Snake Game Neural Net");
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.panel = new GamePanel();
        this.add(panel);

        this.setVisible(true);
    }

    GamePanel getPanel() {
        return panel;
    }


}
