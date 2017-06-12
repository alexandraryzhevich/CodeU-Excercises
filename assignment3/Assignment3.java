package assignment3;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

public class Assignment3 {

    @Test
    public void testEmptyDict() {
        char[][] grid = new char[][] {
            {'a', 'a', 'r'},
            {'t', 'c', 'd'}
        };
        WordDictionary dict = new WordDictionary();
        Set<String> expected = new HashSet<>();
        Set<String> words = new GridWordSearcher(grid.length, grid[0].length, grid, dict).findWords();
        assertEquals("Set of words is not empty", words, expected);
    }

    @Test
    public void testGivenExample() {
        char[][] grid = new char[][] {
                {'a', 'a', 'r'},
                {'t', 'c', 'd'}
        };
        WordDictionary dict = new WordDictionary("car", "card", "cat", "cart");
        Set<String> expected = new HashSet<String>() {{
            add("car");
            add("card");
            add("cat");
        }};
        Set<String> words = new GridWordSearcher(grid.length, grid[0].length, grid, dict).findWords();
        assertEquals("Failed the example test", words, expected);
    }

    @Test
    public void testSingleLetterWords() {
        char[][] grid = new char[][] {
                {'a', 'a', 'r'},
                {'t', 'c', 'd'}
        };
        WordDictionary dict = new WordDictionary("d", "b", "a");
        Set<String> expected = new HashSet<String>() {{
            add("d");
            add("a");
        }};
        Set<String> words = new GridWordSearcher(grid.length, grid[0].length, grid, dict).findWords();
        assertEquals("Failed find single letter words", words, expected);
    }
}
