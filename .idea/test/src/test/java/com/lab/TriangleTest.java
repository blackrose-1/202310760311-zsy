package com.lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TriangleTest {

    @Test
    @DisplayName("测试：输入错误 (越界或无效)")
    void parameters_error_test() {
        Triangle triangle = new Triangle();
        // 测试不在 1-100 范围内的数字
        assertEquals("输入错误", triangle.classify(0, 4, 5));
        assertEquals("输入错误", triangle.classify(101, 50, 50));
    }

    @Test
    @DisplayName("测试：非三角形")
    void not_triangle_test() {
        Triangle triangle = new Triangle();
        // 两边之和不大于第三边
        assertEquals("非三角形", triangle.classify(1, 2, 3));
    }

    @Test
    @DisplayName("测试：等边三角形")
    void equilateral_test() {
        Triangle triangle = new Triangle();
        assertEquals("等边三角形", triangle.classify(5, 5, 5));
    }

    @Test
    @DisplayName("测试：等腰三角形")
    void isosceles_test() {
        Triangle triangle = new Triangle();
        assertEquals("等腰三角形", triangle.classify(3, 3, 4));
        assertEquals("等腰三角形", triangle.classify(4, 3, 3));
    }

    @Test
    @DisplayName("测试：一般三角形")
    void scalene_test() {
        Triangle triangle = new Triangle();
        assertEquals("一般三角形", triangle.classify(3, 4, 5)); // 这里对应你之前的“不等边三角形”
    }
}