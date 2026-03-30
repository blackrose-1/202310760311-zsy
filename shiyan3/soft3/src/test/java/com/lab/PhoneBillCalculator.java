package com.lab;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import static org.junit.jupiter.api.Assertions.*;

class PhoneBillCalculatorTest {

    private final PhoneBillCalculator calculator = new PhoneBillCalculator();

    // 设置北京时区
    private final ZoneId beijingZone = ZoneId.of("Asia/Shanghai");
    // 设置夏令时时区（用于模拟实验要求的转换逻辑）
    private final ZoneId dstZone = ZoneId.of("America/New_York");

    @Test
    @DisplayName("北京时间：普通短通话计费（不足1分钟按1分钟计）")
    void testNormalShortCall() {
        // 通话时长 1分10秒 -> 应按2分钟计费
        ZonedDateTime start = ZonedDateTime.of(2024, 5, 1, 10, 0, 0, 0, beijingZone);
        ZonedDateTime end = ZonedDateTime.of(2024, 5, 1, 10, 1, 10, 0, beijingZone);

        // 2 * 0.05 = 0.10
        assertEquals(0.10, calculator.calculateFee(start, end));
    }

    @Test
    @DisplayName("北京时间：刚好20分钟边界测试")
    void testBoundary20Minutes() {
        ZonedDateTime start = ZonedDateTime.of(2024, 5, 1, 10, 0, 0, 0, beijingZone);
        ZonedDateTime end = ZonedDateTime.of(2024, 5, 1, 10, 20, 0, 0, beijingZone);

        // 20 * 0.05 = 1.00
        assertEquals(1.00, calculator.calculateFee(start, end));
    }

    @Test
    @DisplayName("北京时间：长通话计费（超过20分钟）")
    void testLongCall() {
        // 25分钟 -> 1.00 + (25-20)*0.10 = 1.50
        ZonedDateTime start = ZonedDateTime.of(2024, 5, 1, 10, 0, 0, 0, beijingZone);
        ZonedDateTime end = ZonedDateTime.of(2024, 5, 1, 10, 25, 0, 0, beijingZone);

        assertEquals(1.50, calculator.calculateFee(start, end));
    }

    @Test
    @DisplayName("夏令时转换测试：春季跳过1小时")
    void testSpringDSTConversion() {
        /*
         * 实验需求描述：春季凌晨2:00跳到3:00
         * 模拟：从 01:50 开始，到 03:10 结束
         * 表面时间差为1小时20分，但因为跳过了1小时，实际通话只有20分钟
         */
        ZonedDateTime start = ZonedDateTime.of(2024, 3, 10, 1, 50, 0, 0, dstZone);
        ZonedDateTime end = ZonedDateTime.of(2024, 3, 10, 3, 10, 0, 0, dstZone);

        // 实际20分钟，费用应为 1.00
        assertEquals(1.00, calculator.calculateFee(start, end), "春季夏令时转换费用计算错误");
    }

    @Test
    @DisplayName("夏令时转换测试：秋季回拨1小时")
    void testAutumnDSTConversion() {
        /*
         * 实验需求描述：秋季从2:59:59调回2:00:00
         * 模拟：从 01:50 开始，到“第二个”02:10 结束
         * 实际通话时长为：10分钟(到2点) + 60分钟(回拨) + 10分钟 = 80分钟
         */
        // 使用 withEarlierOffsetAtOverlap 确保从回拨前开始
        ZonedDateTime start = ZonedDateTime.of(2024, 11, 3, 1, 50, 0, 0, dstZone);
        ZonedDateTime end = ZonedDateTime.of(2024, 11, 3, 2, 10, 0, 0, dstZone);

        // 80分钟：1.00 + (80-20)*0.10 = 7.00
        assertEquals(7.00, calculator.calculateFee(start, end), "秋季夏令时转换费用计算错误");
    }

    @Test
    @DisplayName("异常处理：通话超过30小时")
    void testOver30Hours() {
        ZonedDateTime start = ZonedDateTime.of(2024, 5, 1, 10, 0, 0, 0, beijingZone);
        ZonedDateTime end = start.plusHours(31);

        assertThrows(IllegalArgumentException.class, () -> calculator.calculateFee(start, end));
    }
}