package com.yerti.snakeneuralnet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


//Neural net util class
public class NeuralNet {
    private UUID netID;
    private List<List<Neuron>> neuralNet = new ArrayList<>();

    public NeuralNet() {
        netID = UUID.randomUUID();

        //4-3-4 neural net


        List<Neuron> outputLayer = new ArrayList<>();


        //Singular output node/neuron
        outputLayer.add(new Neuron(-2));


        List<Neuron> hiddenLayer = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Neuron neuron = new Neuron(randomValue());
            neuron.addConnection(new Connection(outputLayer.get(0), randomValue())); //connect hidden to output
            hiddenLayer.add(neuron);
        }


        List<Neuron> inputLayer = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Neuron neuron = new Neuron(-2);

            //Add connection to all 3 input layers
            for (int j = 0; j < 3; j++) {
                neuron.addConnection(new Connection(hiddenLayer.get(j), randomValue()));
            }
            inputLayer.add(neuron);
        }

        neuralNet.add(inputLayer);
        neuralNet.add(hiddenLayer);
        neuralNet.add(outputLayer);


    }

    public NeuralNet(List<Double> net) {
        netID = UUID.randomUUID();

        List<Neuron> outputLayer = new ArrayList<>();
        outputLayer.add(new Neuron(-2));

        List<Neuron> hiddenLayer = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Neuron neuron = new Neuron(net.get(12 + i));
            neuron.addConnection(new Connection(outputLayer.get(0), net.get(15 + i)));
            hiddenLayer.add(neuron);
        }

        List<Neuron> inputLayer = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Neuron neuron = new Neuron(-2);
            for (int j = 0; j < 3; j++)
                neuron.addConnection(new Connection(hiddenLayer.get(j), net.get(i * 3 + j)));
            inputLayer.add(neuron);
        }

        neuralNet.add(inputLayer);
        neuralNet.add(hiddenLayer);
        neuralNet.add(outputLayer);
    }

    public static double randomValue() {
        return new Random().nextDouble() * 2 - 1;
    }

    public double calculateOutput(double[] input) {
        for (int i = 0; i < neuralNet.size(); i++)
            for (int j = 0; j < neuralNet.get(i).size(); j++) {
                Neuron neuron = neuralNet.get(i).get(j);
                if(i == 0)
                    neuron.setValue(input[j]);

                neuron.passValues();
            }

        System.out.println(neuralNet.get(neuralNet.size() - 1).get(0).getValue());
        return neuralNet.get(neuralNet.size() - 1).get(0).getValue();

    }

    public void reset() {
        for (int i = 0; i < neuralNet.size(); i++) {
            for (int j = 0; j < neuralNet.get(i).size(); j++) {
                neuralNet.get(i).get(j).setValue(0);
            }
        }
    }

    public List<Double> toList() {
        List<Double> network = new ArrayList<>();
        for (List<Neuron> layer : neuralNet) {
            for (Neuron neuron : layer)
                if (neuron.getThreshold() != -2)
                    network.add(neuron.getThreshold());
            for (Neuron neuron : layer)
                for (Connection conn : neuron.getConnections())
                    network.add(conn.getWeight());
        }
        return network;
    }
}