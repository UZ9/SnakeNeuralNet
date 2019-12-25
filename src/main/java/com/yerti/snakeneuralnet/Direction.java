package com.yerti.snakeneuralnet;

public enum Direction {
    UP, RIGHT, LEFT, DOWN;

    public static Direction getRelativeLeft(Direction d) {
        switch (d) {
            case UP:
                return LEFT;
            case LEFT:
                return DOWN;
            case DOWN:
                return RIGHT;
            case RIGHT:
                return UP;
        }
        return null;
    }

    public static Direction getRelativeRight(Direction d) {
        switch (d) {
            case UP:
                return RIGHT;
            case RIGHT:
                return DOWN;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
        }
        return null;
    }

}
