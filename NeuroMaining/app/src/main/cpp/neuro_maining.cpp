#include <jni.h>
#include <string>
#include <vector>
#include "Neuron.h"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_neuro_1maining_MainActivity_Init_1cpp_1file(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++!";
    Neuron neuron(3);
    std::vector<double> inputs = {0.5, 0.3, 0.2};
    std::string output = std::to_string(neuron.feedForward(inputs) ) ;

    return env->NewStringUTF(output.c_str());
}