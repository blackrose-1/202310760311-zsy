package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 测试 Direction 枚举类中各方向的坐标偏移量是否正确
 *
 * @author Arie van Deursen
 */
public class DirectionTest {

    /**
     * Do we get the correct delta when moving north?
     */
    @Test
    @DisplayName("测试北方向：X轴不变，Y轴减1")
    void testNorth() {
        Direction north = Direction.valueOf("NORTH"); // 也可以直接写 Direction.NORTH

        assertThat(north.getDeltaX()).isEqualTo(0);
        assertThat(north.getDeltaY()).isEqualTo(-1);
    }

    /**
     * 测试南方向：游戏界面中，向南(下)移动，Y轴坐标应该增加，X轴不变。
     */
    @Test
    @DisplayName("测试南方向：X轴不变，Y轴加1")
    void testSouth() {
        Direction south = Direction.valueOf("SOUTH");

        assertThat(south.getDeltaX()).isEqualTo(0);
        assertThat(south.getDeltaY()).isEqualTo(1);
    }

    /**
     * 测试东方向：游戏界面中，向东(右)移动，X轴坐标应该增加，Y轴不变。
     */
    @Test
    @DisplayName("测试东方向：X轴加1，Y轴不变")
    void testEast() {
        Direction east = Direction.valueOf("EAST");

        assertThat(east.getDeltaX()).isEqualTo(1);
        assertThat(east.getDeltaY()).isEqualTo(0);
    }

    /**
     * 测试西方向：游戏界面中，向西(左)移动，X轴坐标应该减小，Y轴不变。
     */
    @Test
    @DisplayName("测试西方向：X轴减1，Y轴不变")
    void testWest() {
        Direction west = Direction.valueOf("WEST");

        assertThat(west.getDeltaX()).isEqualTo(-1);
        assertThat(west.getDeltaY()).isEqualTo(0);
    }

}
