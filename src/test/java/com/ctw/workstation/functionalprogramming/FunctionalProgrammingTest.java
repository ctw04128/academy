package com.ctw.workstation.functionalprogramming;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class FunctionalProgrammingTest {

    private static final List<String> WORDS = Arrays.asList("Java", "FS", "Academy", "CTW", "BMW", "/", "-");
    private static final List<String> NUMBERS = Arrays.asList("43", "21", "54", "89", "137", "142", "751", "89", "137");
    private static final List<Optional<String>> OPTIONALS = Arrays.asList(Optional.of("Critical Techworks"), Optional.empty());
    private static final Predicate<String> ONLY_ALPHABETICALLY = e -> e.matches("[a-zA-Z]+");
    private static final UnaryOperator<String> TO_UPPER = String::toUpperCase;
    private static final Function<String, Integer> PARSE_INT = Integer::parseInt;
    private static final Function<String, Integer> TO_LENGTH = String::length;
    private static final Function<Integer, Integer> SQUARE = e -> (e * e);
    private static final Function<Integer, String> WRAP_IN_BRACKETS = e -> "(" + e + ")";
    private static final Function<Integer, String> EVEN_OR_ODD = i -> (i%2==0) ? "EVEN" : "ODD";
    private static final Function<Map.Entry<String, List<Integer>>, Integer> SUM_THE_LIST_OF_THE_ENTRY = e -> e.getValue().stream().reduce(0, Integer::sum);

    @Test
    public void testJavaStreams() {
//      List<String> alphabeticalStrings = words.stream().filter(w -> w.matches("[a-zA-Z]+")).toList();
//      List<String> upperWords = words.stream().map(String::toUpperCase).toList();
        List<String> result = WORDS.stream()
                .filter(ONLY_ALPHABETICALLY)
                .map(String::toUpperCase)
                .toList();
        System.out.println(result);
    }

    @Test
    public void testCollectorsAPI() {
        Map<String, Integer> result = NUMBERS.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toSet()).stream()
                .collect(Collectors.groupingBy(EVEN_OR_ODD))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, SUM_THE_LIST_OF_THE_ENTRY));
        System.out.println(result);
    }

    @Test
    public void testOptional() {
        OPTIONALS.forEach( e -> {
            System.out.println(e.orElse("Value is not contained."));
            Optional<Integer> length = e.map(String::length);
            System.out.println(length);
            length.ifPresent(System.out::println);
        });
    }

    @Test
    public void testFunctionComposition() {
        Function<String, String> composed = TO_UPPER.andThen(TO_LENGTH.andThen(WRAP_IN_BRACKETS));

        List<String> list = WORDS.stream()
                .filter(ONLY_ALPHABETICALLY)
                .map(composed)
                .toList();
        System.out.println(list);
    }

    @Test
    public void testHighOrderFunctions() {
        List<Integer> list = NUMBERS.stream()
                .map(PARSE_INT)
                .map(SQUARE)
                .toList();
        System.out.println(list);
    }
}
