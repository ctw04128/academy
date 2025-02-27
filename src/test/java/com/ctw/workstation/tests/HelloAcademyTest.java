package com.ctw.workstation.tests;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloAcademyTest {
    public static Stream<Arguments> hello_academy_with_valid_name() {
        return Stream.of(
                Arguments.of("Hello João", "João"),
                Arguments.of("Hello Pedro", "Pedro"),
                Arguments.of("Hello", null),
                Arguments.of("Hello", "")
        );
    }

    @MethodSource
    @ParameterizedTest
    void hello_academy_with_valid_name(String expected, String name) {
        // When
        String result = new HelloAcademy().sayHello(name);
        // Then

    }

    @Test
    void hell_academy_try_list() {
//        List<String> list = List.of("João", "Pedro", "Luís", "", null);
//        List<String> results = new ArrayList<>();
//        list.forEach(s -> results.add(new HelloAcademy().sayHello(s)));
        /*Assertions.assertThat(results).allSatisfy(
        );*/
    }

    @ParameterizedTest
    @NullSource
    void hello_academy_with_null_name(String name) {
        // When
        String result = new HelloAcademy().sayHello(name);
        // Then
        Assertions.assertThat(result).isEqualTo("Hello");
    }


    @EmptySource
    @ParameterizedTest
    void hello_academy_with_empty_name(String name) {
        // When
        String result = new HelloAcademy().sayHello(name);
        // Then
        Assertions.assertThat("Hello").isEqualTo("Hello");
    }
}
