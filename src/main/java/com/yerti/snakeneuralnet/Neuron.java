package com.yerti.snakeneuralnet;

import java.util.ArrayList;
import java.util.List;

public class Neuron
{
    private double value = 0;
    private double threshold;
    private List<Connection> connections = new ArrayList<>();

    public Neuron(double threshold) {
        this.threshold = threshold;
    }



    public List<Connection> getConnections() {
        return connections;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public void passValues() {
        if (value > threshold) {
            for (Connection connection : connections) {
                connection.getReciever().setValue(connection.getReciever().getValue() + value * connection.getWeight());


            }
        }
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }
}