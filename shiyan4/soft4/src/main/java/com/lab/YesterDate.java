package com.lab; // 必须与测试类中的包名一致

public class YesterDate {
    // 注意：方法名必须是 getYesterDay (D大写)，与你的测试类一致
    public static String getYesterDay(int year, int month, int day) {
        // 实验规则验证
        if (year < 1900 || year > 2050 || month < 1 || month > 12 || day < 1 || day > 31) {
            return "Invalid Input";
        }

        // 月份天数表（下标0不用，1-12月）
        int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // 处理闰年
        if (isLeapYear(year)) {
            daysInMonth[2] = 29;
        }

        // 检查当前月份日期是否合法（例如 2月30日 是非法的）
        if (day > daysInMonth[month]) {
            return "Invalid Input";
        }

        int y = year, m = month, d = day;

        if (d > 1) {
            // 情况1：不是该月第一天，直接减1
            d--;
        } else {
            // 情况2：是该月第一天
            if (m == 1) {
                // 1月1日 -> 前一天是去年的12月31日
                y--;
                m = 12;
                d = 31;
            } else {
                // 其他月份的1日 -> 前一天是上个月的最后一天
                m--;
                d = daysInMonth[m];
            }
        }

        // 再次检查计算后的年份是否跌出边界
        if (y < 1900) return "Invalid Input";

        // 返回格式必须是 "年-月-日"，中间不补0，与测试类匹配
        return y + "-" + m + "-" + d;
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}