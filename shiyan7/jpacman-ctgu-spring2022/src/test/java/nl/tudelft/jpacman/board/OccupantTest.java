package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 *
 * @author Jeroen Roosen
 *
 */
class OccupantTest {

    /**
     * The unit under test.
     */
    private Unit unit;

    /**
     * Resets the unit under test.
     */
    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
    }

    /**
     * Asserts that a unit has no square to start with.
     * 题目2-1：验证刚初始化的 Unit 没有占据任何 Square
     */
    @Test
    @DisplayName("测试：Unit初始时没有Square")
    void noStartSquare() {
        // 验证 Unit 不为空，并且初始状态下没有占据方块
        assertThat(unit).isNotNull();
        assertThat(unit.hasSquare()).isFalse();
    }

    /**
     * Tests that the unit indeed has the target square as its base after
     * occupation.
     * 题目2-2：验证 occupy 操作建立的双向绑定关系
     */
    @Test
    @DisplayName("测试：占用操作后的双向容纳关系")
    void testOccupy() {
        Square square = new BasicSquare();

        // 执行占用操作
        unit.occupy(square);

        // 验证 Unit 拥有该方块
        assertThat(unit.getSquare()).isEqualTo(square);
        // 验证 该方块 容纳了该 Unit
        assertThat(square.getOccupants()).contains(unit);
    }

    /**
     * 题目2-3：连续两次调用 occupy，验证占用和容纳关系
     */
    @Test
    @DisplayName("测试：重复占用同一个Square")
    void testReoccupy() {
        Square square = new BasicSquare();

        // 执行两次连续的占用操作
        unit.occupy(square);
        unit.occupy(square);

        // 验证关系依然正确成立
        assertThat(unit.getSquare()).isEqualTo(square);
        assertThat(square.getOccupants()).contains(unit);

        // 核心验证：连续占用不应该导致该方格里出现“两个”相同的Unit（防重复添加测试）
        assertThat(square.getOccupants()).hasSize(1);
    }

}
