package com.lab;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TriangleTest {

    private Triangle triangle;

    @BeforeEach
    void setUp() {
        triangle = new Triangle();
    }

    // 1. 一般边界值
    @DisplayName("1. 一般边界值分析方法测试")
    @ParameterizedTest(name = "a={0}, b={1}, c={2} 预期={3}")
    @CsvFileSource(resources = "/general_bva.csv", numLinesToSkip = 1)
    void testGeneralBVA(int a, int b, int c, String expected) {
        assertEquals(expected, triangle.classify(a, b, c));
    }

    // 2. 健壮性边界值
    @DisplayName("2. 健壮性边界值分析方法测试")
    @ParameterizedTest(name = "a={0}, b={1}, c={2} 预期={3}")
    @CsvFileSource(resources = "/robustness_bva.csv", numLinesToSkip = 1)
    void testRobustnessBVA(int a, int b, int c, String expected) {
        assertEquals(expected, triangle.classify(a, b, c));
    }

    // 3. 最坏情况一般边界值 —— 文件名修复！
    @DisplayName("3. 最坏情况一般边界值测试")
    @ParameterizedTest(name = "a={0}, b={1}, c={2} 预期={3}")
    @CsvFileSource(resources = "/worstcase_general_bva.csv", numLinesToSkip = 1)
    void testWorstCaseGeneralBVA(int a, int b, int c, String expected) {
        assertEquals(expected, triangle.classify(a, b, c));
    }

    // 4. 最坏情况健壮性边界值 —— 文件名修复！
    @DisplayName("4. 最坏情况健壮性边界值测试")
    @ParameterizedTest(name = "a={0}, b={1}, c={2} 预期={3}")
    @CsvFileSource(resources = "/worstcase_robustness_bva.csv", numLinesToSkip = 1)
    void testWorstCaseRobustnessBVA(int a, int b, int c, String expected) {
        assertEquals(expected, triangle.classify(a, b, c));
    }
}