package assignment6;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class Assignment6 {

    private void checkSolution(Integer[] sourceOrder, Integer[] destOrder, List<CarRearranger.Move> moves) {
        System.out.println("Check solution");
        for (CarRearranger.Move move : moves) {
            assertTrue("From index is out of range.", move.getFrom() < sourceOrder.length && move.getFrom() >= 0);
            assertTrue("To index is out of range.", move.getTo() < sourceOrder.length && move.getTo() >= 0);
            assertEquals("Dest place is not empty.", 0, (int) sourceOrder[move.getTo()]);
            sourceOrder[move.getTo()] = sourceOrder[move.getFrom()];
            sourceOrder[move.getFrom()] = 0;
            System.out.println(String.format("%d -> %d", move.getFrom(), move.getTo()));
            System.out.println(Arrays.deepToString(sourceOrder));
        }
        assertArrayEquals("Result order is not equal to dest order.", destOrder, sourceOrder);
    }

    private void shuffleArray(Integer[] arr) {
        Random rand = new Random();
        for (int i = arr.length; i > 1; i--) {
            int j = rand.nextInt(i);
            int temp = arr[i - 1];
            arr[i - 1] = arr[j];
            arr[j] = temp;
        }
    }

    @Test
    public void testExample() {
        Integer[] sourceOrder = new Integer[] {1, 2, 0, 3};
        Integer[] destOrder = new Integer[] {3, 1, 2, 0};
        checkSolution(sourceOrder, destOrder, new CarRearranger(sourceOrder, destOrder).rearrangeCars());
    }

    @Test
    public void testSameOrder() {
        Integer[] sourceOrder = new Integer[] {1, 2, 0, 3};
        Integer[] destOrder = new Integer[] {1, 2, 0, 3};
        checkSolution(sourceOrder, destOrder, new CarRearranger(sourceOrder, destOrder).rearrangeCars());
    }

    @Test
    public void testLoopWithoutEmpty() {
        Integer[] sourceOrder = new Integer[] {1, 2, 0, 3};
        Integer[] destOrder = new Integer[] {2, 3, 0, 1};
        checkSolution(sourceOrder, destOrder, new CarRearranger(sourceOrder, destOrder).rearrangeCars());
    }

    @Test
    public void testNoPlaces() {
        Integer[] sourceOrder = new Integer[] {};
        Integer[] destOrder = new Integer[] {};
        checkSolution(sourceOrder, destOrder, new CarRearranger(sourceOrder, destOrder).rearrangeCars());
    }

    @Test
    public void testOneEmpty() {
        Integer[] sourceOrder = new Integer[] {0};
        Integer[] destOrder = new Integer[] {0};
        checkSolution(sourceOrder, destOrder, new CarRearranger(sourceOrder, destOrder).rearrangeCars());
    }

    @Test
    public void testSimple() {
        Integer[] sourceOrder = new Integer[] {0, 1};
        Integer[] destOrder = new Integer[] {1, 0};
        checkSolution(sourceOrder, destOrder, new CarRearranger(sourceOrder, destOrder).rearrangeCars());
    }

    @Test
    public void testReversedSimple() {
        Integer[] sourceOrder = new Integer[] {1, 0};
        Integer[] destOrder = new Integer[] {0, 1};
        checkSolution(sourceOrder, destOrder, new CarRearranger(sourceOrder, destOrder).rearrangeCars());
    }

    @Test
    public void testRandom() {
        for (int k = 0; k < 20; k++) {
            Integer[] sourceOrder = new Integer[10];
            Integer[] destOrder = new Integer[10];
            for (int i = 0; i < 10; i++) {
                sourceOrder[i] = i;
                destOrder[i] = i;
            }
            shuffleArray(sourceOrder);
            shuffleArray(destOrder);
            checkSolution(sourceOrder, destOrder, new CarRearranger(sourceOrder, destOrder).rearrangeCars());
        }
    }
}
