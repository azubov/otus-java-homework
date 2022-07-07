package ru.otus;

import com.google.common.collect.Lists;
import com.google.common.primitives.Chars;

import java.util.List;

/**
 *
 * To start the application:
 * ./gradlew build
 * java -jar ./hw01-gradle/build/libs/HelloOtus-0.1.jar
 *
 * To unzip the jar:
 * unzip -l hw01-gradle.jar
 * unzip -l HelloOtus-0.1.jar
 *
 */
public class HelloOtus {
    public static void main(String... args) {
        final String hello = "Hello OTUS";
        final List<Character> chars = Chars.asList(hello.toCharArray());

        System.out.println(Lists.reverse(chars));
    }
}
