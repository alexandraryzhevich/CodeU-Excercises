package assignment4;

import java.util.HashSet;
import java.util.Set;


public class DisjointGridSet {

    private int[] parents;
    private int initialColumnCount;

    public DisjointGridSet(boolean[][] grid) {
        this.initialColumnCount = grid[0].length;
        this.parents = new int[grid.length * grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // initially every island cell is marked as a separate island
                // -1 for water
                this.parents[convertIndexes(i, j)] = grid[i][j] ? convertIndexes(i, j) : -1;
            }
        }
    }

    public int find(int element) {
        if (element == parents[element]) {
            return element;
        } else {
            parents[element] = find(parents[element]);
            return parents[element];
        }
    }

    public void union(int element1, int element2) {
        element1 = find(element1);
        element2 = find(element2);
        if (element1 == element2) {
            return;
        }
        parents[element1] = element2;
    }

    public int convertIndexes(int row, int column) {
        return row * initialColumnCount + column;
    }

    public int countSets() {
        Set<Integer> uniqueParents = new HashSet<>();
        for (int i = 0; i < parents.length; i++) {
            if (parents[i] < 0) {
                continue;
            }
            uniqueParents.add(find(i));
        }
        return  uniqueParents.size();
    }

}
