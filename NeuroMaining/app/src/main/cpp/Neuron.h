// Neuron.h
#ifndef NEURON_H
#define NEURON_H

#include <vector>

class Neuron {
public:
    Neuron(int numInputs);
    double feedForward(const std::vector<double>& inputs);
    void backpropagate(const std::vector<double>& inputs, double target, double learningRate);

private:
    std::vector<double> weights;
    double bias;
    double output;
    double activationFunction(double x);
};

#endif // NEURON_H
