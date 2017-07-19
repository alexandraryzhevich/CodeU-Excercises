package assignment4;


public class IslandCounter {

    private boolean[][] grid;

    public IslandCounter(boolean[][] grid) {
        if (grid == null || grid.length == 0) {
            throw new IllegalArgumentException("Grid cannot be empty.");
        }
        this.grid = grid;
    }

    public int countIslands() {
        DisjointGridSet set = new DisjointGridSet(grid);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (!grid[i][j]) {
                    continue;
                }
                // unite two horizontal neighbours
                if (isPositionValid(i, j - 1) && grid[i][j - 1]) {
                    set.union(set.convertIndexes(i, j), set.convertIndexes(i, j - 1));
                }
                // unite two vertical members
                if (isPositionValid(i - 1, j) && grid[i - 1][j]) {
                    set.union(set.convertIndexes(i, j), set.convertIndexes(i - 1, j));
                }
            }
        }
        // now every island is represented as one of sets
        return set.countSets();
    }

    private boolean isPositionValid(int i, int j) {
        return i >= 0 && i < grid.length && j >= 0 && j < grid[0].length;
    }
}
