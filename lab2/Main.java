import java.util.Arrays;
import java.util.Scanner;

// HelloWorld.java
public class Main {
    static Scanner scanner;
    public static void main(String[] args) {
        String encoding = System.getProperty("console.encoding", "utf-8");
        scanner = new Scanner(System.in, encoding);
        switch (args[0]) {
            case "1":
                String string1 = scanner.nextLine();
                String result1 = task1(string1);
                System.out.println(result1);
                break;
            case "2":
                int n2, m2;
                n2 = scanner.nextInt();
                m2 = scanner.nextInt();
                int[] arr21 = getIntArrFromConsole(n2);
                int[] arr22 = getIntArrFromConsole(m2);
                int[] result2 = task2(arr21, arr22);
                for (int elem: result2) {
                    System.out.print(elem + " ");
                }
                System.out.println();
                break;
            case "3":
                int n3;
                n3 = scanner.nextInt();
                int[] arr3 = getIntArrFromConsole(n3);
                int result3 = task3(arr3);
                System.out.println(result3);
                break;
            case "4":
                int n4, m4;
                n4 = scanner.nextInt();
                m4 = scanner.nextInt();
                int[][] arr4 = getIntTwoDimArrFromConsole(n4, m4);
                int[][] result4 = task4(arr4);
                printIntTwoDimArrFromConsole(result4);
                break;
            case "5":
                int n5;
                n5 = scanner.nextInt();
                int[] arr5 = getIntArrFromConsole(n5);
                int target = scanner.nextInt();
                int[] result5 = task5(arr5, target);
                if (result5 == null) {
                    System.out.println("null");
                } else {
                    System.out.println(result5[0] + " " + result5[1]);
                }
                break;
            case "6":
                int n6, m6;
                n6 = scanner.nextInt();
                m6 = scanner.nextInt();
                int[][] arr6 = getIntTwoDimArrFromConsole(n6, m6);
                int result6 = task6(arr6);
                System.out.println(result6);
                break;
            case "7":
                int n7, m7;
                n7 = scanner.nextInt();
                m7 = scanner.nextInt();
                int[][] arr7 = getIntTwoDimArrFromConsole(n7, m7);
                int[] result7 = task7(arr7);
                for (int elem: result7) {
                    System.out.println(elem);
                }
                break;
            case "8":
                int n8, m8;
                n8 = scanner.nextInt();
                m8 = scanner.nextInt();
                int[][] arr8 = getIntTwoDimArrFromConsole(n8, m8);
                int[][] result8 = task8(arr8);
                printIntTwoDimArrFromConsole(result8);
                break;
            default:
                break;
        }
        scanner.close();
    }

    public static int[] getIntArrFromConsole(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; ++i) {
            arr[i] = scanner.nextInt();
        }
        return arr;
    }

    public static int[][] getIntTwoDimArrFromConsole(int dim1Size, int dim2Size) {
        int[][] arr = new int[dim1Size][dim2Size];
        for (int i = 0; i < dim1Size; ++i) {
            for (int j = 0; j < dim2Size; ++j) {
                arr[i][j] = scanner.nextInt();
            }
        }
        return arr;
    }

    public static void printIntTwoDimArrFromConsole(int[][] arr) {
        for (int i = 0; i < arr.length; ++i) {
            for (int j = 0; j < arr[i].length; ++j) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }


    public static String task1(String string) {
        int maxSymb = 0;
        for (int i = 0; i < string.length(); ++i) {
            maxSymb = Math.max(maxSymb, (int)string.charAt(i));
        }
        int[] symbCount = new int[maxSymb + 1];
        for (int i = 0; i <= maxSymb; ++i) {
            symbCount[i] = 0;
        }
        int leftIndex = 0;
        int maxLen = 0;
        int maxRight = 0;
        int maxLeft = 0;
        for (int i = 0; i < string.length(); ++i) {
            while (symbCount[(int)string.charAt(i)] == 1) {
                symbCount[(int)string.charAt(leftIndex++)] -= 1;
            }
            if (maxLen < i - leftIndex) {
                maxLen = i - leftIndex;
                maxRight = i;
                maxLeft = leftIndex;
            }
            symbCount[(int)string.charAt(i)] += 1;
        }
        if (maxLen < string.length() - leftIndex) {
            maxRight = string.length() - 1;
            maxLeft = leftIndex;
        }
        return string.substring(maxLeft, maxRight + 1);
    }

    public static int[] task2(int[] firstArray, int[] secondArray) {
        int[] resultArray = new int[firstArray.length + secondArray.length];
        int firstIndex = 0;
        int secondIndex = 0;
        while (firstIndex < firstArray.length || secondIndex < secondArray.length) {
            if (secondIndex >= secondArray.length || (firstIndex < firstArray.length && firstArray[firstIndex] < secondArray[secondIndex])) {
                resultArray[firstIndex + secondIndex] = firstArray[firstIndex++];
            } else {
                resultArray[firstIndex + secondIndex] = secondArray[secondIndex++];
            }
        }
        return resultArray;
    }

    public static int task3(int[] arr) {
        int curSum = 0;
        int maxSum = 0;
        for (int i = 0; i < arr.length; ++i) {
            curSum += arr[i];
            maxSum = Math.max(curSum, maxSum);
            if (curSum < 0) {
                curSum = 0;
            }
        }
        return maxSum;
    }

    public static int[][] task4(int[][] A) {
        int[][] newA = new int[A[0].length][A.length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                newA[j][A.length - i - 1] = A[i][j];
            }
        }
        return newA;
    }

    public static int[] task5(int[] arr, int target) {
        int[] arrCopy = arr.clone();
        Arrays.sort(arrCopy);
        for (int elem: arrCopy) {
            int secondIndex = Arrays.binarySearch(arrCopy, target - elem);
            if (secondIndex >= 0) {
                return new int[]{elem, arrCopy[secondIndex]};
            }
        }
        return null;
    }

    public static int task6(int[][] A) {
        int sum = 0;
        for (int i = 0; i < A.length; ++i) {
            for (int j = 0; j < A.length; ++j) {
                sum += A[i][j];
            }
        }
        return sum;
    }

    public static int[] task7(int[][] A) {
        int[] result = new int[A.length];
        for (int i = 0; i < A.length; ++i) {
            int maxEl = A[i][0];
            for (int j = 0; j < A.length; ++j) {
                maxEl = Math.max(maxEl, A[i][j]);
            }
            result[i] = maxEl;
        }
        return result;
    }

    public static int[][] task8(int[][] A) {
        int[][] newA = new int[A[0].length][A.length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                newA[A[i].length - j - 1][i] = A[i][j];
            }
        }
        return newA;
    }
}

