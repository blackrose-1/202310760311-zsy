import java.util.Scanner;

// 定义系统的四种状态
enum State {
    STANDBY("待机"),      // 待机
    HEATING("加热"),      // 加热
    SLEEP("休眠"),        // 休眠
    MAINTENANCE("维修");  // 维修

    private String name;
    State(String name) { this.name = name; }
    @Override
    public String toString() { return this.name; }
}

public class WaterTankSystem {

    // 当前系统状态，初始为“待机”
    private State currentState = State.STANDBY;

    // 模拟传感器数据
    private double currentTemp = 25.0; // 当前温度
    private boolean hasWater = true;   // 是否有水
    private int currentHour = 12;      // 当前时间 (0-23小时)
    private boolean isBroken = false;  // 水箱是否烧坏

    /**
     * 核心逻辑：状态机运行引擎
     * 根据当前状态和传感器数据，决定是否切换状态
     */
    public void runStateMachine() {
        System.out.println("------------------------------------------------");
        System.out.println("当前状态: [" + currentState + "] | 温度:" + currentTemp + "° | 有水:" + hasWater + " | 时间:" + currentHour + ":00");

        switch (currentState) {
            // ================= 状态：待机 =================
            case STANDBY:
                // 1. 检查是否到晚上 23:00 -> 进入休眠
                if (currentHour == 23) {
                    System.out.println(">>> 事件触发：时间到达23:00，进入休眠模式。");
                    currentState = State.SLEEP;
                }
                // 2. 检查温度是否 < 20度 且 有水 -> 打开继电器，开始加热
                else if (currentTemp < 20) {
                    if (hasWater) {
                        System.out.println(">>> 事件触发：温度<20°且有水。动作：打开继电器电源。");
                        currentState = State.HEATING;
                    } else {
                        System.out.println(">>> 警告：温度低但无水，防止干烧，保持待机。");
                    }
                }
                break;

            // ================= 状态：加热 =================
            case HEATING:
                // 1. 优先检查故障 -> 维修
                if (isBroken) {
                    System.out.println(">>> 严重故障：水箱烧坏！动作：进行维修。");
                    currentState = State.MAINTENANCE;
                }
                // 2. 检查是否到晚上 23:00 -> 强制断电休眠
                else if (currentHour == 23) {
                    System.out.println(">>> 事件触发：时间到达23:00。动作：断开电源，停止加热，进入休眠。");
                    currentState = State.SLEEP;
                }
                // 3. 检查温度是否 >= 100度 -> 断开电源，回待机
                else if (currentTemp >= 100) {
                    System.out.println(">>> 事件触发：水已烧开(100°)。动作：断开电源。");
                    currentState = State.STANDBY;
                }
                // 4. 模拟加热过程（为了演示效果，每次运行温度+10）
                else {
                    System.out.println("...正在烧水中...");
                    currentTemp += 20; // 模拟升温
                    if (currentTemp > 100) currentTemp = 100;
                }
                break;

            // ================= 状态：休眠 =================
            case SLEEP:
                // 1. 检查是否到早上 7:00 -> 唤醒
                if (currentHour == 7) {
                    System.out.println(">>> 事件触发：时间到达7:00。动作：系统唤醒。");
                    currentState = State.STANDBY;
                } else {
                    System.out.println("...系统休眠中，省电模式...");
                }
                break;

            // ================= 状态：维修 =================
            case MAINTENANCE:
                System.out.println("...系统正在维修中，请修复后重启系统...");
                // 实际代码中可能需要人工重置标志位
                break;
        }
    }

    // ==========================================
    // 下面是测试用的辅助方法，用来手动改变环境数据
    // ==========================================

    public void setEnv(double temp, boolean water, int hour, boolean broken) {
        this.currentTemp = temp;
        this.hasWater = water;
        this.currentHour = hour;
        this.isBroken = broken;
        runStateMachine(); // 数据变化后，驱动一次状态机
    }

    public static void main(String[] args) {
        WaterTankSystem system = new WaterTankSystem();
        
        System.out.println("=== 恒温水箱控制系统启动 ===");

        // --- 场景 1: 正常待机，温度不够但没水 ---
        System.out.println("\n[场景1] 温度15度，无水：");
        system.setEnv(15, false, 10, false); 
        // 预期：保持待机，报警告

        // --- 场景 2: 正常待机，温度不够且有水 -> 启动加热 ---
        System.out.println("\n[场景2] 温度15度，有水，加水了：");
        system.setEnv(15, true, 10, false); 
        // 预期：切换到加热

        // --- 场景 3: 正在加热，温度达到100度 -> 停止加热 ---
        System.out.println("\n[场景3] 水烧开了：");
        system.setEnv(100, true, 10, false); 
        // 预期：切换回待机

        // --- 场景 4: 到了晚上23点 -> 休眠 ---
        System.out.println("\n[场景4] 时间到了晚上23点：");
        system.setEnv(90, true, 23, false); 
        // 预期：切换到休眠

        // --- 场景 5: 时间流逝到早上7点 -> 唤醒 ---
        System.out.println("\n[场景5] 时间到了早上7点：");
        system.setEnv(18, true, 7, false); 
        // 预期：切换回待机，且因为温度低(18度)，下一次循环应该会自动开始加热

        // --- 场景 6: 再次加热时发生故障 -> 维修 ---
        System.out.println("\n[场景6] 再次加热，但水箱突然坏了：");
        // 先让它进入加热状态
        system.setEnv(18, true, 8, false);
        // 模拟故障
        system.setEnv(40, true, 8, true); 
        // 预期：切换到维修
    }
}