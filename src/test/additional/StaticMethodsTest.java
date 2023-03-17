package test.additional;

import main.additional.StaticMethods;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class StaticMethodsTest {
    private static long suiteStartTime;
    private long testStartTime;

    @BeforeAll
    public static void initSuite() {
        System.out.println("Running All Tests");
        suiteStartTime = System.nanoTime();
    }

    @AfterAll
    public static void completeSuite() {
        System.out.println("All Tests completed in time: " + (System.nanoTime() - suiteStartTime));
    }

    @BeforeEach
    public void initTest() {
        System.out.println("Starting new test");
        testStartTime = System.nanoTime();
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("Test complete in time: " + (System.nanoTime() - testStartTime));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1014532", "259801012", "1103423"})
    public void checkIntTest(String argument) {
        assertTrue(StaticMethods.checkInt(argument), "Неверная проверка значения (Должно быть числом, а результат обратный)");
    }

    @ParameterizedTest
    @ValueSource(strings = {"13411112344L", "259801f012", "11034q23", "111_223_123"})
    public void checkInt2Test(String argument) {
        assertFalse(StaticMethods.checkInt2(argument), "Неверная проверка значения (Не должно быть числом, а результат обратный)");
    }
}