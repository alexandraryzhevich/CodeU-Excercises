import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Assignment1Q1 {

    private static final int CHARS_AMOUNT = 26;

    /**
     * Performs basic validation.
     */
    private static boolean validate(String str1, String str2) {
        if (str1 == null || str2 == null) { // null strings are not permutation of each other
            return false;
        }
        if (str1.length() != str2.length()) { // strings with different length are not permutations of each other
            return false;
        }
        return true;
    }

    /**
     * More general solution that works for any kind of characters.
     */
    private static boolean isPermutation(String str1, String str2) {
        if (!validate(str1, str2)) {
            return false;
        }
        if (str1.isEmpty() && str2.isEmpty()) { // simple case
            return true;                        // empty strings are permutation of each other
        }

        Function<String, Map<Character, Integer>> fillCounter = (String str) -> {
            str = str.toLowerCase();
            // create hash map to calculate the frequency of each character
            Map<Character, Integer> counter = new HashMap<>(str.length() * 2);
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                counter.put(c, counter.getOrDefault(c, 0) + 1);
            }
            return counter;
        };

        return fillCounter.apply(str1).equals(fillCounter.apply(str2));
    }

    /**
     * More efficient solution for English words.
     */
    private static boolean isEnglishAlphabetPermutation(String str1, String str2) {
        if (!validate(str1, str2)) {
            return false;
        }
        if (str1.isEmpty() && str2.isEmpty()) { // simple case
            return true;
        }
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();

        // we can use array instead of hash map
        // because we definitely know amount of characters
        int[] counter = new int[CHARS_AMOUNT];
        int startCode = Character.codePointAt("a", 0);
        for (int i = 0; i < str1.length(); i++) {
            int idx = str1.codePointAt(i) - startCode;
            if (idx < 0 || idx >= CHARS_AMOUNT) {
                throw new IllegalArgumentException("Unsupported character.");
            }
            counter[idx]++;
        }
        // no need to create another array for checking
        for (int i = 0; i < str2.length(); i++) {
            int idx = str2.codePointAt(i) - startCode;
            if (idx < 0 || idx >= CHARS_AMOUNT) {
                throw new IllegalArgumentException("Unsupported character.");
            }
            int checkValue = --counter[idx];
            if (checkValue < 0) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println(isEnglishAlphabetPermutation(null, null));            // false
        System.out.println(isEnglishAlphabetPermutation(null, ""));              // false
        System.out.println(isEnglishAlphabetPermutation("", ""));                // true
        System.out.println(isEnglishAlphabetPermutation("a", "a"));              // true
        System.out.println(isEnglishAlphabetPermutation("a", "ab"));             // false
        System.out.println(isEnglishAlphabetPermutation("a", "A"));              // true
        System.out.println(isEnglishAlphabetPermutation("abc", "ACB"));          // true
        System.out.println(isEnglishAlphabetPermutation("abc", "ABB"));          // false
        System.out.println(isEnglishAlphabetPermutation("abc", "abd"));          // false
        System.out.println();

        System.out.println(isPermutation(null, null));            // false
        System.out.println(isPermutation(null, ""));              // false
        System.out.println(isPermutation("", ""));                // true
        System.out.println(isPermutation("a", "a"));              // true
        System.out.println(isPermutation("a", "ab"));             // false
        System.out.println(isPermutation("a", "A"));              // true
        System.out.println(isPermutation("abc", "ACB"));          // true
        System.out.println(isPermutation("abc", "ABB"));          // false
        System.out.println(isPermutation("abc", "abd"));          // false
        System.out.println(isPermutation("ab.", "ab/"));          // false
        System.out.println(isPermutation("...55,,", ".5.5,.,"));  // true
    }
}
