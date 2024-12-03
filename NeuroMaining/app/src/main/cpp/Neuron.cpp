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

void Neuron::backpropagate(const std::vector<double>& inputs, double target, double learningRate) {

    double error = target - output;
    double derivative = output * (1.0 - output);
    double delta = error * derivative;

    for (size_t i = 0; i < weights.size(); ++i) {
        weights[i] += learningRate * delta * inputs[i];
    }

    bias += learningRate * delta;

    LOGI("Weights updated");
    for (size_t i = 0; i < weights.size(); ++i) {
        LOGI("Weight[%zu]: %f", i, weights[i]);
    }
    LOGI("Bias updated: %f", bias);
}

