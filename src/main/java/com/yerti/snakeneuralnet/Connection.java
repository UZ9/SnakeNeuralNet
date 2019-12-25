package com.yerti.snakeneuralnet;

public class Connection {

    private Neuron reciever;

    private double weight;

    public Connection(Neuron reciever, double weight) {
        this.reciever = reciever;
        this.weight = weight;
    }


    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Neuron getReciever() {
        return reciever;
    }


}
