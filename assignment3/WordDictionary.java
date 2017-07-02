package assignment3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WordDictionary {

    private Set<String> words;
    private Set<String> prefixes;

    public WordDictionary(String... words){
        this.words = new HashSet<>(Arrays.asList(words));
        this.prefixes = new HashSet<>();
        for (String word : words) {
            for (int i = 1; i <= word.length(); i++) {
                this.prefixes.add(word.substring(0, i));
            }
        }
    }

    public boolean isWord(String word) {
        return words.contains(word);
    }

    public boolean isPrefix(String prefix) {
        return prefixes.contains(prefix);
    }
}
