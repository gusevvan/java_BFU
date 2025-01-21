import java.util.Scanner;

// HelloWorld.java
public class Main {
    static Scanner scanner;
    public static void main(String[] args) {
        String encoding = System.getProperty("console.encoding", "utf-8");
        scanner = new Scanner(System.in, encoding);
        switch (args[0]) {
            case "1":
                task1();
                break;
            case "2":
                task2();
                break;
            case "3":
                task3();
                break;
            case "4":
                task4();
                break;
            case "5":
                task5();
                break;
            default:
                break;
        }
        scanner.close();
    }

    public static void task1() {
        int n = scanner.nextInt();
        int k = 0;
        while (n != 1) {
            if (n % 2 == 0) {
                n /= 2;
            } else {
                n = 3 * n + 1;
            }
            ++k;
        }
        System.out.println(k);
    }

    public static void task2() {
        int n = scanner.nextInt();
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                result += scanner.nextInt();
            } else {
                result -= scanner.nextInt();
            }
        }
        System.out.println(result);
    }

    public static void task3() {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int currentX = 0, currentY = 0;
        int numActions = 0, minNumActions = -1;
        String currentDirection = scanner.next();
        while (!currentDirection.equals("стоп")) {
            int numSteps = scanner.nextInt();
            switch (currentDirection) {
                case "север":
                    currentY += numSteps;
                    break;
                case "юг":
                    currentY -= numSteps;
                    break;
                case "запад":
                    currentX -= numSteps;
                    break;
                case "восток":
                    currentX += numSteps;
                    break;
                default:
                    break;
            }
            numActions++;
            currentDirection = scanner.next();
            if (currentX == x && currentY == y && minNumActions == -1) {
                minNumActions = numActions;
            }
        }
        System.out.println(minNumActions);
    }

    public static void task4() {
        int n = scanner.nextInt();
        int maxMin = 0;
        for (int i = 0; i < n; ++i) {
            int m = scanner.nextInt();
            int minValue = scanner.nextInt();
            for (int j = 1; j < m; ++j) {
                minValue = Math.min(minValue, scanner.nextInt());
            }
            maxMin = Math.max(maxMin, minValue);
        }
        System.out.println(maxMin);
    }

    public static void task5() {
        int n = scanner.nextInt();
        int sumOfDigits = 0, multOfDigits = 1;
        while (n > 0) {
            sumOfDigits += n % 10;
            multOfDigits *= n % 10;
            n /= 10;
        }
        System.out.println((sumOfDigits % 2 == 0) && (multOfDigits % 2 == 0));
    }
}

