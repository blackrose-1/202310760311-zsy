public class PrimeNumbers {
    // 判断一个数是否为素数
    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int count = 0;
        long startTime = System.currentTimeMillis();
        for (int i = 2; i <= 20000; i++) {
            if (isPrime(i)) {
                System.out.print(i + " ");
                count++;
                if (count % 5 == 0) {
                    System.out.println();
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("\n总耗时：" + (endTime - startTime) + " 毫秒");
        // 分析：最费时的函数通常是isPrime，因为需要对每个数进行循环判断。
        // 改进方法：可以使用埃拉托斯特尼筛法，减少重复判断，提高效率。
    }
}