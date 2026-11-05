import java.util.Arrays;
import java.util.Random;

public class SortingTest {

    private static final int[] SIZES = { 10, 100, 1000, 10000 };
    private static final int WARMUP_RUNS = 2;
    private static final int REPEATS = 3;

    public static void main(String[] args) {

        Random random = new Random(42);

        testEdgeCases();

        System.out.println("Sorting Algorithms Comparison");
        System.out.println();

        System.out.printf("%-10s %-15s %-15s %-15s %-15s%n",
                "Size", "Bubble", "Selection", "Insertion", "Quick");

        for (int size : SIZES) {

            int[] original = generateRandomArray(size, random);

            for (int i = 0; i < WARMUP_RUNS; i++) {
                SortingAlgorithms.bubbleSort(generateRandomArray(1000, random));
                SortingAlgorithms.selectionSort(generateRandomArray(1000, random));
                SortingAlgorithms.insertionSort(generateRandomArray(1000, random));

                int[] warmup = generateRandomArray(1000, random);
                SortingAlgorithms.quickSort(warmup, 0, warmup.length - 1);
            }

            long bubbleTime = measure(original, 1);
            long selectionTime = measure(original, 2);
            long insertionTime = measure(original, 3);
            long quickTime = measure(original, 4);

            System.out.printf("%-10d %-15d %-15d %-15d %-15d%n",
                    size, bubbleTime, selectionTime, insertionTime, quickTime);
        }

        System.out.println();
        System.out.println("All sorting algorithms tested successfully.");
    }

    private static int[] generateRandomArray(int size, Random random) {
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(10000);
        }

        return array;
    }

    private static long measure(int[] original, int algorithm) {

        long[] times = new long[REPEATS];

        int[] expected = original.clone();
        Arrays.sort(expected);

        for (int i = 0; i < REPEATS; i++) {

            int[] array = original.clone();

            long startTime = System.nanoTime();

            sort(array, algorithm);

            long endTime = System.nanoTime();

            if (!Arrays.equals(array, expected)) {
                throw new RuntimeException("Sorting error!");
            }

            times[i] = endTime - startTime;
        }

        Arrays.sort(times);

        return times[REPEATS / 2];
    }

    private static void sort(int[] array, int algorithm) {

        switch (algorithm) {
            case 1:
                SortingAlgorithms.bubbleSort(array);
                break;
            case 2:
                SortingAlgorithms.selectionSort(array);
                break;
            case 3:
                SortingAlgorithms.insertionSort(array);
                break;
            case 4:
                SortingAlgorithms.quickSort(array, 0, array.length - 1);
                break;
        }
    }

    private static void testEdgeCases() {

        int[][] testArrays = {
                {},
                { 5 },
                { 7, 7, 7, 7 },
                { 9, 8, 7, 6, 5, 4 },
                { 4, 2, 4, 1, 2, 1 }
        };

        for (int[] array : testArrays) {
            for (int algorithm = 1; algorithm <= 4; algorithm++) {

                int[] copy = array.clone();
                sort(copy, algorithm);

                int[] expected = array.clone();
                Arrays.sort(expected);

                if (!Arrays.equals(copy, expected)) {
                    throw new RuntimeException("Edge case test failed!");
                }
            }
        }

        System.out.println("Edge cases tested successfully.");
        System.out.println();
    }
}
