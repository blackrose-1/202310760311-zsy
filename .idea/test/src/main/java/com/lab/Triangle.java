package com.lab;

/**
 * @Description: 测试三角形类型
 */
public class Triangle {
    public String classify(int a, int b, int c) {
        // 1. 判断是否满足条件1、2、3 (边长在1-100之间)
        if (a < 1 || a > 100 || b < 1 || b > 100 || c < 1 || c > 100) {
            return "输入错误";
        }
        // 2. 判断是否满足条件4 (任意两边之和大于第三边)
        if (!((a + b > c) && (a + c > b) && (b + c > a))) {
            return "非三角形";
        } else if (a == b && a == c && b == c) {
            return "等边三角形";
        } else if (a != b && a != c && b != c) {
            return "一般三角形"; // 根据你的需求说明，这里改为"一般三角形"
        } else {
            return "等腰三角形";
        }
    }
}