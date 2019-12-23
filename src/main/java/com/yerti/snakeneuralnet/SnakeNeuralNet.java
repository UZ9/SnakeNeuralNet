package com.yerti.snakeneuralnet;

import javax.swing.*;
import java.awt.*;

public class SnakeNeuralNet {

    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;
    private GameFrame frame;

    private static SnakeNeuralNet instance;

    private SnakeNeuralNet() {
        instance = this;
    }

    public static SnakeNeuralNet getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new SnakeNeuralNet();
        instance.frame = new GameFrame();

        Thread gameLoop = new Thread(new GameLoop(instance.frame.getPanel()));
        gameLoop.start();
    }




}
