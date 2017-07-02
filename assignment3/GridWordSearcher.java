package assignment3;

import java.util.*;

public class GridWordSearcher {
    private int rowCount;
    private int columnCount;
    private char[][] grid;
    private WordDictionary dict;
    private static List<Step> directions;

    static {
        directions = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                directions.add(new Step(i, j));
            }
        }
    }

    private static class Position {
        int row;
        int column;

        Position(int row, int column) {
            this.row = row;
            this.column = column;
        }

        boolean isValid(int maxRow, int maxColumn) {
            return row >= 0 && row < maxRow && column >= 0 && column < maxColumn;
        }
    }

    private static class Step {
        int rowStep;
        int columnStep;

        Step(int rowStep, int columnStep) {
            this.rowStep = rowStep;
            this.columnStep = columnStep;
        }

        Position step(Position original) {
            return new Position(original.row + rowStep, original.column + columnStep);
        }
    }

    private static class GridPosition {
        private Position position;
        private int direction;

        GridPosition(Position position, int direction) {
            this.position = position;
            this.direction = direction;
        }

        Position getPosition() {
            return position;
        }

        int getDirection() {
            return direction;
        }

        void setDirection(int direction) {
            this.direction = direction;
        }
    }

    public GridWordSearcher(char[][] grid, WordDictionary dict) {
        if (grid == null || grid.length == 0) {
            throw new IllegalArgumentException("Grid cannot be empty.");
        }
        this.grid = grid;
        this.dict = dict;
        this.rowCount = grid.length;
        this.columnCount = grid[0].length;
    }

    public Set<String> findWords() {
        boolean[][] marks = new boolean[rowCount][columnCount];
        Set<String> result = new HashSet<>();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                findWordsFromPosition(new Position(i, j), marks, result);
            }
        }
        return result;
    }

    private void findWordsFromPosition(Position startPosition, boolean[][] marks, Set<String> result) {
        String firstLetterString = Character.toString(grid[startPosition.row][startPosition.column]);
        if (!dict.isPrefix(firstLetterString)) {
            return;
        }
        if (dict.isWord(firstLetterString)) {
            result.add(firstLetterString);
        }
        StringBuilder currentPrefix = new StringBuilder(firstLetterString);
        List<GridPosition> stack = new ArrayList<>();
        stack.add(new GridPosition(startPosition, 0));
        marks[startPosition.row][startPosition.column] = true;

        // dfs from the current cell
        while (!stack.isEmpty()) {
            GridPosition top = stack.get(stack.size() - 1);
            boolean isStillTop = true;

            // if there are any unvisited neighbours
            if (top.getDirection() < directions.size()) {
                Position nextPosition = directions.get(top.getDirection()).step(top.getPosition());
                top.setDirection(top.getDirection() + 1);

                // check if this cell exists and it is not predecessor
                if (nextPosition.isValid(rowCount, columnCount) && !marks[nextPosition.row][nextPosition.column]) {
                    String newPrefix = currentPrefix.toString() + grid[nextPosition.row][nextPosition.column];

                    if (dict.isPrefix(newPrefix)) {
                        currentPrefix.append(grid[nextPosition.row][nextPosition.column]);
                        marks[nextPosition.row][nextPosition.column] = true;
                        stack.add(new GridPosition(nextPosition, 0));
                        if (dict.isWord(newPrefix)) {
                            result.add(newPrefix);
                        }
                        isStillTop = false;
                    }
                }
            }
            if (isStillTop && top.getDirection() == directions.size()) {
                stack.remove(stack.size() - 1);
                currentPrefix.deleteCharAt(currentPrefix.length() - 1);
                marks[top.getPosition().row][top.getPosition().column] = false;
            }
        }
    }
}
