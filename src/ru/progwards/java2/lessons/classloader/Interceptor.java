package ru.progwards.java2.lessons.classloader;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

public class Interceptor {
    public static void premain(String agentArgument, Instrumentation instrumentation) {
      //  instrumentation.addTransformer((ClassFileTransformer) new SystemProfiler());
    }
}