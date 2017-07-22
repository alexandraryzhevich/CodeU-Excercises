package assignment6;

import java.util.ArrayList;
import java.util.List;

public class CarRearranger {

    private int[] currentOrder;
    private int[] destOrder;
    private int[] placesByCarIndex;
    private final int EMPTY = 0;

    public CarRearranger(Integer[] sourceOrder, Integer[] destOrder) {
        validateParameters(sourceOrder, destOrder);
        init(sourceOrder, destOrder);
    }

    /**
     * Validates input data.
     */
    private void validateParameters(Integer[] sourceOrder, Integer[] destOrder) {
        if (sourceOrder == null || destOrder == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        if (sourceOrder.length != destOrder.length) {
            throw new IllegalArgumentException("Source and destination orders have not equal length.");
        }
        if (!validateOrder(sourceOrder) || !validateOrder(destOrder)) {
            throw new IllegalArgumentException("Source or destination order is invalid.");
        }
    }

    /**
     * Checks if array contains numbers from 0 to N-1 and only once.
     */
    private boolean validateOrder(Integer[] order) {
        boolean[] checkExistence = new boolean[order.length];
        for (int item : order) {
            if (item < 0 || item >= order.length || checkExistence[item]) {
                return false;
            }
            checkExistence[item] = true;
        }
        return true;
    }

    /**
     * Initializes data to work with
     * so that algorithm will not change input data.
     */
    private void init(Integer[] sourceOrder, Integer destOrder[]) {
        this.destOrder = new int[destOrder.length];
        this.placesByCarIndex = new int[destOrder.length];
        this.currentOrder = new int[destOrder.length];
        for (int i = 0; i < sourceOrder.length; i++) {
            this.placesByCarIndex[sourceOrder[i]] = i;
            this.currentOrder[i] = sourceOrder[i];
            this.destOrder[i] = destOrder[i];
        }
    }

    public static class Move {

        private int from;
        private int to;

        public Move(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }
    }

    /**
     * Takes O(n) operations.
     */
    public List<Move> rearrangeCars() {
        List<Move> moves = new ArrayList<>();
        int nextCar = nextCarNotOnPlace(1);
        while (nextCar < currentOrder.length) {
            if (isOnPlace(EMPTY)) {
                move(moves, nextCar, placesByCarIndex[EMPTY]);
            }
            rearrangeCarsInCycle(moves);
            nextCar = nextCarNotOnPlace(nextCar);
        }
        printMoves(moves);
        return moves;
    }

    /**
     * Moves cars that form a cycle
     * so that all the cars in this cycle will be on their place.
     * Takes O(n) operations for all its calls.
     */
    private void rearrangeCarsInCycle(List<Move> moves) {
        while(!isOnPlace(EMPTY)) {
            int emptyIdx = placesByCarIndex[EMPTY];
            int destCar = destOrder[emptyIdx];
            move(moves, destCar, emptyIdx);
        }
    }

    /**
     * Checks if car is on its place in destination order.
     * Takes O(1) operations.
     */
    private boolean isOnPlace(int car) {
        int currentIdx = placesByCarIndex[car];
        return destOrder[currentIdx] == car;
    }

    /**
     * Finds next car that is not on its place
     * starting with the previous one.
     * Takes O(n) operations for all its calls.
     */
    private int nextCarNotOnPlace(int nextCar) {
        for (; nextCar < currentOrder.length; nextCar++) {
            if (!isOnPlace(nextCar)) {
                break;
            }
        }
        return nextCar;
    }

    /**
     * Moves car to empty place.
     * Takes O(1) operations.
     * @param moves    list of moves already made
     * @param car      car to move
     * @param emptyIdx index of empty place
     */
    private void move(List<Move> moves, int car, int emptyIdx) {
        int currentIdx = placesByCarIndex[car];
        currentOrder[currentIdx] = EMPTY;
        currentOrder[emptyIdx] = car;
        moves.add(new Move(currentIdx, emptyIdx));
        placesByCarIndex[car] = emptyIdx;
        placesByCarIndex[EMPTY] = currentIdx;
    }

    /**
     * Prints moves on console.
     * Takes O(n) operations.
     */
    private void printMoves(List<Move> moves) {
        System.out.println("\nMoves:");
        for (Move move : moves) {
            System.out.println(String.format("%d -> %d", move.getFrom(), move.getTo()));
        }
    }
}
