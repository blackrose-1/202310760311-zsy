package com.lab;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * 电话账单计费程序
 */
public class PhoneBillCalculator {

    /**
     * 计算通话费用
     * @param start 通话开始时间（包含时区信息）
     * @param end 通话结束时间（包含时区信息）
     * @return 费用（美元）
     */
    public double calculateFee(ZonedDateTime start, ZonedDateTime end) {
        if (start == null || end == null) {
            return 0.0;
        }

        // 1. 计算实际流逝的秒数 (Duration会自动处理夏令时跳变导致的时差)
        long totalSeconds = Duration.between(start, end).getSeconds();

        // 2. 约束检查：通话结束不能早于开始
        if (totalSeconds < 0) {
            return 0.0;
        }

        // 3. 将秒数向上进位到分钟 (不足1分钟按1分钟计算)
        // 使用 Math.ceil 处理向上取整
        long minutes = (long) Math.ceil(totalSeconds / 60.0);

        // 4. 约束检查：通话时间不超过30小时 (30 * 60 = 1800 分钟)
        if (minutes > 1800) {
            throw new IllegalArgumentException("通话时间超过30小时上限");
        }

        // 5. 根据计费规则计算费用
        double fee;
        if (minutes <= 20) {
            // 通话时间小于等于20分钟时，每分钟0.05美元
            fee = minutes * 0.05;
        } else {
            // 通话时间大于20分钟时，前20分钟收1.00美元，超过部分每分钟0.10美元
            fee = 1.00 + (minutes - 20) * 0.10;
        }

        // 处理浮点数精度问题，保留两位小数
        return Math.round(fee * 100.0) / 100.0;
    }
}