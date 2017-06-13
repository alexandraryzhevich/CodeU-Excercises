package assignment3;

import java.util.*;

public class GridWordSearcher {
    private int n;
    private int m;
    private char[][] grid;
    private WordDictionary dict;
    private static List<List<Integer>> directions;

    static {
        directions = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                directions.add(Arrays.asList(i, j));
            }
        }
    }

    private static class GridPosition {
        private int i;
        private int j;
        private int direction;

        GridPosition(int i, int j, int direction) {
            this.i = i;
            this.j = j;
            this.direction = direction;
        }

        int getI() {
            return i;
        }

        int getJ() {
            return j;
        }

        int getDirection() {
            return direction;
        }

        void setDirection(int direction) {
            this.direction = direction;
        }
    }

    public GridWordSearcher(int n, int m, char[][] grid, WordDictionary dict) {
        this.n = n;
        this.m = m;
        this.grid = grid;
        this.dict = dict;
    }

    public Set<String> findWords() {
        boolean[][] marks = new boolean[n][m];
        Set<String> result = new HashSet<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                findWordsFromPosition(i, j, marks, result);
            }
        }
        return result;
    }

    private void findWordsFromPosition(int i, int j, boolean[][]marks, Set<String> result) {
        String firstLetterString = Character.toString(grid[i][j]);
        if (!dict.isPrefix(firstLetterString)) {
            return;
        }
        if (dict.isWord(firstLetterString)) {
            result.add(firstLetterString);
        }
        StringBuilder currentPrefix = new StringBuilder(firstLetterString);
        List<GridPosition> stack = new ArrayList<>();
        stack.add(new GridPosition(i, j, 0));
        marks[i][j] = true;

        // dfs from the current cell
        while (!stack.isEmpty()) {
            GridPosition top = stack.get(stack.size() - 1);
            boolean isStillTop = true;

            // if there are any unvisited neighbours
            if (top.getDirection() < directions.size()) {
                int nextI = top.getI() + directions.get(top.getDirection()).get(0);
                int nextJ = top.getJ() + directions.get(top.getDirection()).get(1);
                top.setDirection(top.getDirection() + 1);

                // check if this cell exists and it is not predecessor
                if (isValidIndexes(nextI, nextJ) && !marks[nextI][nextJ]) {
                    String newPrefix = currentPrefix.toString() + grid[nextI][nextJ];

                    if (dict.isPrefix(newPrefix)) {
                        currentPrefix.append(grid[nextI][nextJ]);
                        marks[nextI][nextJ] = true;
                        stack.add(new GridPosition(nextI, nextJ, 0));
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
                marks[top.getI()][top.getJ()] = false;
            }
        }
    }

    private boolean isValidIndexes(int i, int j) {
        return i >=0 && i < n
                && j >= 0 && j < m;
    }
}
