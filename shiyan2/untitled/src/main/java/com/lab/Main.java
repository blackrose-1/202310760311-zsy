package com.lab;

import java.io.File;
import java.io.PrintWriter;

public class Main {
    // 模拟被测程序的逻辑，用来生成正确的预期结果
    public static String getExpected(int a, int b, int c) {
        if (a < 1 || a > 100 || b < 1 || b > 100 || c < 1 || c > 100) return "输入错误";
        if (!((a + b > c) && (a + c > b) && (b + c > a))) return "非三角形";
        if (a == b && a == c && b == c) return "等边三角形";
        if (a != b && a != c && b != c) return "不等边三角形";
        return "等腰三角形";
    }

    public static void main(String[] args) throws Exception {
        // 确保 resources 文件夹存在
        File dir = new File("src/test/resources");
        if (!dir.exists()) dir.mkdirs();

        // ===================== 1. 生成一般边界值测试用例 =====================
        // 边界值：min, min+1, nominal, max-1, max
        int[] genVals = {1, 2, 50, 99, 100};
        try (PrintWriter pw = new PrintWriter("src/test/resources/general_bva.csv", "UTF-8")) {
            pw.println("a,b,c,expected");
            // 单变量变化，其余保持标称值50
            for (int val : genVals) pw.println(val + ",50,50," + getExpected(val, 50, 50));
            for (int val : genVals) if(val!=50) pw.println("50," + val + ",50," + getExpected(50, val, 50));
            for (int val : genVals) if(val!=50) pw.println("50,50," + val + "," + getExpected(50, 50, val));
        }

        // ===================== 2. 生成健壮性边界值测试用例 =====================
        // 健壮值：比一般边界多 超出下界(0)、超出上界(101) → 彻底修复robustVals初始化
        int[] robustVals = {0, 1, 2, 50, 99, 100, 101};
        try (PrintWriter pw = new PrintWriter("src/test/resources/robustness_bva.csv", "UTF-8")) {
            pw.println("a,b,c,expected");
            // 单变量变化，覆盖边界+越界值
            for (int val : robustVals) pw.println(val + ",50,50," + getExpected(val, 50, 50));
            for (int val : robustVals) if(val!=50) pw.println("50," + val + ",50," + getExpected(50, val, 50));
            for (int val : robustVals) if(val!=50) pw.println("50,50," + val + "," + getExpected(50, 50, val));
        }

        // ===================== 3. 生成最坏情况 - 一般边界值测试用例 =====================
        // 多变量组合所有一般边界值，全覆盖
        try (PrintWriter pw = new PrintWriter("src/test/resources/worstcase_general_bva.csv", "UTF-8")) {
            pw.println("a,b,c,expected");
            for (int a : genVals) {
                for (int b : genVals) {
                    for (int c : genVals) {
                        pw.println(a + "," + b + "," + c + "," + getExpected(a, b, c));
                    }
                }
            }
        }

        // ===================== 4. 生成最坏情况 - 健壮性边界值测试用例 =====================
        // 多变量组合所有健壮值（含越界），最全面测试
        try (PrintWriter pw = new PrintWriter("src/test/resources/worstcase_robustness_bva.csv", "UTF-8")) {
            pw.println("a,b,c,expected");
            for (int a : robustVals) {
                for (int b : robustVals) {
                    for (int c : robustVals) {
                        pw.println(a + "," + b + "," + c + "," + getExpected(a, b, c));
                    }
                }
            }
        }

        System.out.println("✅ 所有 CSV 文件生成完成！路径：src/test/resources/");
        System.out.println("📄 生成文件列表：");
        System.out.println("   1. general_bva.csv (一般边界值)");
        System.out.println("   2. robustness_bva.csv (健壮性边界值)");
        System.out.println("   3. worstcase_general_bva.csv (最坏情况-一般)");
        System.out.println("   4. worstcase_robustness_bva.csv (最坏情况-健壮性)");
    }
}