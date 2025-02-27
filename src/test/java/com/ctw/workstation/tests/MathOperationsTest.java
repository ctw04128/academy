package com.ctw.workstation.tests;

import io.quarkus.logging.Log;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MathOperationsTest {

    private static MathOperations mathOperations;

    @BeforeAll
    static void setup() {
        mathOperations = new MathOperations();
        Log.info("MathOperations setup");
    }

    @AfterAll
    static void teardown() {
        Log.info("Tests are over");
    }

    @BeforeEach
    void setupEach() {
        Log.info("New test");
    }

    @AfterEach
    void teardownEach() {
        Log.info("End of test");
    }

    @Order(1)
    @DisplayName("Adding two positives will result in a positive number")
    @Test
    void math_operation_add_two_positive_numbers() {
        // Given
        int a = 1;
        int b = 1;
        // When
        int result = mathOperations.add(a, b);
        // Then
        Assertions.assertEquals(2, result);
    }

    @Order(2)
    @DisplayName("Adding two negative numbers will result in a negative number")
    @Test
    void math_operation_add_two_negative_numbers() {
        // Given
        int a = -1;
        int b = -1;
        // When
        int result = mathOperations.add(a, b);
        // Then
        Assertions.assertEquals(-2, result);
    }

    @Order(3)
    @DisplayName("Adding the same numbers but with opposite signs will result in zero")
    @Test
    void math_operation_add_opposite_sign_numbers() {
        // Given
        int a = 1;
        int b = -1;
        // When
        int result = mathOperations.add(a, b);
        // Then
        Assertions.assertEquals(0, result);
    }

    @Order(4)
    @DisplayName("Adding zero with a positive number will result in the same positive number")
    @Test
    void math_operation_add_zero_with_positive_numbers() {
        // Given
        int a = 0;
        int b = 1;
        // When
        int result = mathOperations.add(a, b);
        // Then
        Assertions.assertEquals(1, result);
    }

    @Order(5)
    @DisplayName("Adding zero with a negative number will result in the same negative number")
    @Test
    void math_operation_add_zero_with_negative_numbers() {
        // Given
        int a = 0;
        int b = -1;
        // When
        int result = mathOperations.add(a, b);
        // Then
        Assertions.assertEquals(-1, result);
    }

    @Order(6)
    @DisplayName("Dividing two positive numbers will result in a positive number")
    @Test
    void math_operation_divide_two_positive_numbers() {
        // Given
        int a = 1;
        int b = 1;
        // When
        int result = mathOperations.divide(a, b);
        // Then
        Assertions.assertEquals(1, result);
    }

    @Order(7)
    @DisplayName("Dividing two negative numbers will result in a positive number")
    @Test
    void math_operation_divide_two_negative_numbers() {
        // Given
        int a = -1;
        int b = -1;
        // When
        int result = mathOperations.divide(a, b);
        // Then
        Assertions.assertEquals(1, result);
    }

    @Order(8)
    @DisplayName("Dividing a positive number with a negative number will result in a negative number")
    @Test
    void math_operation_divide_positive_with_negative_numbers() {
        // Given
        int a = 1;
        int b = -1;
        // When
        int result = mathOperations.divide(a, b);
        // Then
        Assertions.assertEquals(-1, result);
    }

    @Order(9)
    @DisplayName("Dividing a negative number with a positive number will result in a negative number")
    @Test
    void math_operation_divide_negative_with_positive_numbers() {
        // Given
        int a = -1;
        int b = 1;
        // When
        int result = mathOperations.divide(a, b);
        // Then
        Assertions.assertEquals(-1, result);
    }

    @Order(10)
    @DisplayName("Dividing zero with a positive number will result in zero")
    @Test
    void math_operation_divide_zero_with_positive_numbers() {
        // Given
        int a = 0;
        int b = 1;
        // When
        int result = mathOperations.divide(a, b);
        // Then
        Assertions.assertEquals(0, result);
    }

    @Order(11)
    @DisplayName("Dividing zero with a negative number will result in zero")
    @Test
    void math_operation_divide_zero_with_negative_numbers() {
        // Given
        int a = 0;
        int b = -1;
        // When
        int result = mathOperations.divide(a, b);
        // Then
        Assertions.assertEquals(0, result);
    }

    @Order(12)
    @DisplayName("Dividing by zero will throw an Arithmetic Exception")
    @Test
    void math_operation_divide_positive_with_zero() {
        // Given
        int a = 1;
        int b = 0;
        // Then                                                  When
        Assertions.assertThrows(ArithmeticException.class, () -> mathOperations.divide(a, b));
    }
}
