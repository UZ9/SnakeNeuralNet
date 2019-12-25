package com.yerti.snakeneuralnet;

import java.util.ArrayList;

class NeuronLayer
{
    private ArrayList<Neuron> neurons = new ArrayList<>();

    NeuronLayer(int numberOfInputs, int numberOfNeurons)
    {
        for (int i = 0; i < numberOfNeurons; i++)
        {
            neurons.add(new Neuron(numberOfInputs));
        }
    }

    int size()
    {
        return neurons.size();
    }

    Neuron getNeuronAt(int index)
    {
        return neurons.get(index);
    }
}