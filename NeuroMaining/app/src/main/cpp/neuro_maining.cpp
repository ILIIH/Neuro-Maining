#include <jni.h>
#include <string>


extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_neuro_1maining_MainActivity_Init_1cpp_1file(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++!";
    return env->NewStringUTF(hello.c_str());
}