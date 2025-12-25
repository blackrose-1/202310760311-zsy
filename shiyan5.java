import java.util.Scanner;

// 1. 实体类：用户 (保持非 public，以便放入同一个文件)
class User {
    private String username;
    private boolean isLoggedIn = false;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() { return username; }
    public boolean isLoggedIn() { return isLoggedIn; }
    public void setLoggedIn(boolean loggedIn) { isLoggedIn = loggedIn; }
}

// 2. 服务类：认证服务
class AuthService {
    public void register(String username, String password) {
        System.out.println("[系统] 用户 " + username + " 注册成功。");
    }

    public boolean login(User user) {
        System.out.println("[系统] 正在验证用户 " + user.getUsername() + " 的身份...");
        user.setLoggedIn(true);
        System.out.println("[系统] 登录成功！");
        return true;
    }
}

// 3. 服务类：支付服务
class PaymentService {
    public void processPayment(double amount) {
        System.out.println("    >> [支付模块] 正在连接支付网关...");
        System.out.println("    >> [支付模块] 支付金额: " + amount + "元。支付成功！");
    }
}

// 4. 服务类：预约挂号服务
class AppointmentService {
    private AuthService authService = new AuthService();
    private PaymentService paymentService = new PaymentService();

    // 辅助方法：检查登录状态 (对应 <<Include>> 关系)
    private boolean checkLogin(User user) {
        if (!user.isLoggedIn()) {
            System.out.println("[错误] 操作失败：请先登录 (<<Include>> Login)");
            return false;
        }
        return true;
    }

    // 用例：预约挂号
    public void makeAppointment(User user, String doctor, boolean needOnlinePayment) {
        if (!checkLogin(user)) return; // 包含登录检查

        System.out.println("[业务] 用户 " + user.getUsername() + " 正在预约 " + doctor + "...");

        // 扩展点 (Extension Point): 在线支付
        if (needOnlinePayment) {
            System.out.println("    (检测到扩展点: 在线支付 <<Extend>>)");
            paymentService.processPayment(50.00); 
        } else {
            System.out.println("    (用户选择线下支付，跳过在线支付模块)");
        }

        System.out.println("[系统] 预约挂号成功！");
        sendNotification(user, "挂号成功");
    }

    // 用例：代人挂号
    public void makeAppointmentForOthers(User user, String patientName) {
        if (!checkLogin(user)) return;

        System.out.println("[业务] 用户 " + user.getUsername() + " 正在为 [" + patientName + "] 代挂号...");
        System.out.println("[系统] 代人挂号成功！");
    }

    // 用例：取消预约
    public void cancelAppointment(User user, int appointmentId) {
        if (!checkLogin(user)) return;

        System.out.println("[业务] 用户正在取消订单号: " + appointmentId);
        System.out.println("[系统] 预约已取消。");
    }

    private void sendNotification(User user, String message) {
        System.out.println("[通知] 发送给 " + user.getUsername() + ": " + message);
    }
    
    // 用例：管理通知设置
    public void manageNotificationSettings(User user) {
        if(!checkLogin(user)) return;
        System.out.println("[设置] 用户修改了通知偏好。");
    }
}

// 5. 服务类：管理员服务
class AdminService {
    public void manageUsers() {
        System.out.println("[管理员] 正在查看/修改用户列表...");
    }

    public void broadcastNotification(String message) {
        System.out.println("[管理员] 群发系统通知: " + message);
    }
}

// ================= 主类名必须与文件名“实验五”一致 =================
public class 实验五 {
    public static void main(String[] args) {
        // 初始化服务
        AuthService auth = new AuthService();
        AppointmentService appointmentApp = new AppointmentService();
        AdminService adminApp = new AdminService();

        // 模拟场景
        User user = new User("ZhangSan");

        System.out.println("========== 场景1: 未登录尝试挂号 ==========");
        appointmentApp.makeAppointment(user, "李医生", false);

        System.out.println("\n========== 场景2: 用户登录 ==========");
        auth.login(user);

        System.out.println("\n========== 场景3: 预约挂号 (包含在线支付 <<Extend>>) ==========");
        appointmentApp.makeAppointment(user, "王专家", true);

        System.out.println("\n========== 场景4: 预约挂号 (不支付) ==========");
        appointmentApp.makeAppointment(user, "普通门诊", false);

        System.out.println("\n========== 场景5: 代人挂号 (<<Include>> 登录) ==========");
        appointmentApp.makeAppointmentForOthers(user, "张三的父亲");

        System.out.println("\n========== 场景6: 取消预约 ==========");
        appointmentApp.cancelAppointment(user, 10086);

        System.out.println("\n========== 场景7: 管理员操作 ==========");
        adminApp.manageUsers();
        adminApp.broadcastNotification("系统将于今晚维护");
    }
}