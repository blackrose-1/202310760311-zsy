package com.lab;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals; // 或者用 .*
class YesterDateTest {

    @Test
    void testGeneralDay() {
        assertEquals("2025-3-9", YesterDate.getYesterDay(2025, 3, 10));
    }

    @Test
    void testNewYear() {
        assertEquals("2024-12-31", YesterDate.getYesterDay(2025, 1, 1));
    }

    @Test
    void testMonthBoundary() {
        assertEquals("2025-2-28", YesterDate.getYesterDay(2025, 3, 1));
    }

    @Test
    void testLeapYearFeb() {
        assertEquals("2024-2-29", YesterDate.getYesterDay(2024, 3, 1));
    }

    @Test
    void testCommonYearFeb() {
        assertEquals("1900-2-28", YesterDate.getYesterDay(1900, 3, 1));
    }

    @Test
    void testInvalidInput() {
        assertEquals("Invalid Input", YesterDate.getYesterDay(1899, 1, 1));
        assertEquals("Invalid Input", YesterDate.getYesterDay(2025, 2, 30));
    }
}