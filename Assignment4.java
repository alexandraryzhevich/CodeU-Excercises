package assignment4;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class Assignment4 {

    @Test
    public void testNoIslands() {
        boolean[][] grid = new boolean[][] {
                {false, false, false},
                {false, false, false}
        };
        int result = new IslandCounter(grid).countIslands();
        assertEquals("Found some islands", 0, result);
    }

    @Test
    public void testSingleCellIsland() {
        boolean[][] grid1 = new boolean[][] {
                {true,  false, false},
                {false, false, false}
        };
        assertEquals("Found not one island", 1, new IslandCounter(grid1).countIslands());

        boolean[][] grid2 = new boolean[][] {
                {false, true,  false},
                {false, false, false}
        };
        assertEquals("Found not one island", 1, new IslandCounter(grid2).countIslands());

        boolean[][] grid3 = new boolean[][] {
                {false, false, true},
                {false, false, false}
        };
        assertEquals("Found not one island", 1, new IslandCounter(grid3).countIslands());

        boolean[][] grid4 = new boolean[][] {
                {false, false, false},
                {false, false, true}
        };
        assertEquals("Found not one island", 1, new IslandCounter(grid4).countIslands());
    }

    @Test
    public void testMultipleCellIsland() {
        boolean[][] grid1 = new boolean[][] {
                {true, false, false},
                {true, false, false}
        };
        assertEquals("Failed to find vertical island", 1, new IslandCounter(grid1).countIslands());

        boolean[][] grid2 = new boolean[][] {
                {true,  true,  false},
                {false, false, false}
        };
        assertEquals("Failed to find horizontal island", 1, new IslandCounter(grid2).countIslands());

        boolean[][] grid3 = new boolean[][] {
                {false, true,  true},
                {false, false, true}
        };
        assertEquals("Failed to find corner island", 1, new IslandCounter(grid3).countIslands());

        boolean[][] grid4 = new boolean[][] {
                {true, false, true},
                {true, true,  true}
        };
        assertEquals("Failed to find big island", 1, new IslandCounter(grid4).countIslands());
    }

    @Test
    public void testMultipleIslands() {
        boolean[][] grid = new boolean[][] {
                {true,  false, false, true,  false},
                {true,  true,  false, true,  true},
                {false, false, true,  false, true},
                {true,  true,  false, true,  true},
                {false, true,  false, false, false},
                {true,  true,  true,  true,  false},
                {true,  false, false, false, true},
        };
        assertEquals("Failed to count multiple islands", 5, new IslandCounter(grid).countIslands());
    }
}
