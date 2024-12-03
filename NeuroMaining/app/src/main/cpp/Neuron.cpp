#include "Neuron.h"
#include <cmath>
#include <cstdlib>
#include <android/log.h>

#define LOG_TAG "Neuron"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

Neuron::Neuron(int numInputs) {
    for (int i = 0; i < numInputs; ++i) {
        weights.push_back((rand() % 100) / 100.0); // Random weights
    }
    bias = (rand() % 100) / 100.0;  // Random bias
}

double Neuron::activationFunction(double x) {
    return 1.0 / (1.0 + exp(-x)); // Sigmoid
}

double Neuron::feedForward(const std::vector<double>& inputs) {
    double sum = 0.0;
    for (size_t i = 0; i < inputs.size(); ++i) {
        sum += inputs[i] * weights[i]; // Weighted sum
    }
    sum += bias; // Add bias
    output = activationFunction(sum); // Apply activation function
    return output;
}

double Neuron::getOutput() const {
    return output;
}

void Neuron::logOutput() const {
    LOGI("Neuron output: %f", output);
}
