package assignment5;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class Assignment5 {

    private int findIndex(List<Character> alphabet, char ch) {
        for (int i = 0; i < alphabet.size(); i++) {
            if (alphabet.get(i).equals(ch)) {
                return i;
            }
        }
        fail(String.format("Character %c is not in alphabet.", ch));
        return -1;
    }

    private void checkCharactersInAlphabet(String word, List<Character> alphabet) {
        for (int i = 0; i < word.length(); i++) {
            findIndex(alphabet, word.charAt(i));
        }
    }

    private boolean checkOrder(List<Character> alphabet, char ch1, char ch2) {
        return findIndex(alphabet, ch1) < findIndex(alphabet, ch2);
    }

    private boolean checkOrder(List<String> words, List<Character> alphabet) {
        boolean isFirstWord = true;
        String previousWord = null;
        for (String currentWord : words) {
            checkCharactersInAlphabet(currentWord, alphabet);
            if (isFirstWord) {
                isFirstWord = false;
                previousWord = currentWord;
            }
            for (int i = 0; i < previousWord.length() && i < currentWord.length(); i++) {
                char ch1 = previousWord.charAt(i);
                char ch2 = currentWord.charAt(i);
                if (ch1 == ch2) {
                    continue;
                }
                if (!checkOrder(alphabet, previousWord.charAt(i), currentWord.charAt(i))) {
                    fail(String.format("Character %c is not before %c. Words: %s and %s",
                            previousWord.charAt(i), currentWord.charAt(i), previousWord, currentWord));
                    return false;
                } else {
                    break;
                }
            }
            previousWord = currentWord;
        }
        return true;
    }

    @Test
    public void testExample() {
        List<String> words = new ArrayList<String>() {{
            add("art");
            add("rat");
            add("cat");
            add("car");
        }};
        List<Character> alphabet = new AlphabetFinder(words).findAlphabet();
        alphabet.forEach(System.out::println);
        assertTrue(checkOrder(words, alphabet));
    }

    @Test
    public void testUndirectedCycle() {
        List<String> words = new ArrayList<String>() {{
            add("cat");
            add("car");
            add("ctr");
            add("tra");
            add("traf");
            add("traa");
            add("traaf");
            add("traac");
        }};
        List<Character> alphabet = new AlphabetFinder(words).findAlphabet();
        alphabet.forEach(System.out::println);
        assertTrue(checkOrder(words, alphabet));
    }

    @Test
    public void testDoubleRule() {
        List<String> words = new ArrayList<String>() {{
            add("cac");
            add("car");
            add("rar");
        }};
        List<Character> alphabet = new AlphabetFinder(words).findAlphabet();
        alphabet.forEach(System.out::println);
        assertTrue(checkOrder(words, alphabet));
    }

    @Test
    public void testEmpty() {
        List<String> words = new ArrayList<>();
        List<Character> alphabet = new AlphabetFinder(words).findAlphabet();
        alphabet.forEach(System.out::println);
        assertTrue(checkOrder(words, alphabet));
    }

    @Test
    public void testSingleWord() {
        List<String> words = new ArrayList<String>() {{
            add("car");
        }};
        List<Character> alphabet = new AlphabetFinder(words).findAlphabet();
        alphabet.forEach(System.out::println);
        assertTrue(checkOrder(words, alphabet));
    }
}
