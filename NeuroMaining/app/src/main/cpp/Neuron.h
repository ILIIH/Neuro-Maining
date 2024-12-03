// Neuron.h
#ifndef NEURON_H
#define NEURON_H

#include <vector>

class Neuron {
public:
    Neuron(int numInputs);
    double feedForward(const std::vector<double>& inputs);
    double getOutput() const;
    void logOutput() const;

private:
    std::vector<double> weights;
    double bias;
    double output;
    double activationFunction(double x);
};

#endif // NEURON_H
