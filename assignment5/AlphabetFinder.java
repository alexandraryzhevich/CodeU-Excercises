package assignment5;

import java.util.*;

public class AlphabetFinder {

    private List<String> words;                         // sorted list of words
    private Map<Character, Integer> indexesByCharacter; // indexes of characters in graph
    private List<List<Integer>> charOrderGraph;         // graph of characters where arcs are rules of sorting
    private List<Character> foundCharacters;            // characters sorted by their index

    public AlphabetFinder(List<String> words) {
        if (words == null) {
            throw new IllegalArgumentException("Initial dictionary cannot be null.");
        }
        this.words = words;
        this.indexesByCharacter = new HashMap<>();
        this.charOrderGraph = new ArrayList<>();
        this.foundCharacters = new ArrayList<>();
    }

    /**
     * Finds alphabet of unknown language.
     * Takes O(n + m) operations where
     * n is sum of word lengths
     * m is number of characters in alphabet
     * @return sorted list of characters
     */
    public List<Character> findAlphabet() {
        boolean isFirstWord = true;
        String previousWord = null;

        // build graph
        // takes O(n) operations since addCharactersToAlphabet()
        // and addOrderEdge() take O(n) operations.
        for (String currentWord : words) {
            addCharactersToAlphabet(currentWord);
            if (isFirstWord) {
                isFirstWord = false;
                previousWord = currentWord;
                continue;
            }
            // compare two neighbour words in order to find the first difference
            for (int i = 0; i < currentWord.length() && i < previousWord.length(); i++) {
                if (addOrderEdge(previousWord.charAt(i), currentWord.charAt(i))) {
                    break;
                }
            }
            previousWord = currentWord;
        }

        // takes O(m) + O(n + m) = O(n + m) operations
        return charListFromIdxList(topSort());
    }

    /**
     * Adds all characters from word in the alphabet.
     * Takes O(n) operations for all its calls where n is sum of word lengths.
     * if they are not added yet.
     */
    private void addCharactersToAlphabet(String word) {
        for (Character c : word.toCharArray()) {
            if (indexesByCharacter.containsKey(c)) {
                continue;
            }
            indexesByCharacter.put(c, indexesByCharacter.size());
            charOrderGraph.add(new ArrayList<>());
            foundCharacters.add(c);
        }
    }

    /**
     * Compares two characters and adds a new sorting rule
     * if they are different.
     * Takes O(n) operations for all its calls where n is sum of word lengths.
     * @return true if the sorting rule was added
     */
    private boolean addOrderEdge(Character previousWordChar, Character currentWordChar) {
        if (previousWordChar.equals(currentWordChar)) {
            return false;
        }
        charOrderGraph.get(indexesByCharacter.get(previousWordChar)).add(indexesByCharacter.get(currentWordChar));
        return true;
    }

    /**
     * Topologically sorts vertices of alphabet graph.
     * Takes O(n + m) operation since dfs takes O(V + E) operations
     * and here V = m (number of characters in alphabet)
     * and E = O(n) (we can add 0 or 1 arc at every addOrderEdge() call and we call it O(n) times)
     * @return topologically sorted list of graph vertices
     */
    private List<Integer> topSort() {
        List<Integer> sortedNodes = new ArrayList<>();
        boolean[] visited = new boolean[charOrderGraph.size()];
        // perform dfs from each unvisited vertex
        for (int i = 0 ; i < visited.length; i++) {
            if (!visited[i]) {
                topSortInternal(i, sortedNodes, visited);
            }
        }
        Collections.reverse(sortedNodes);
        return sortedNodes;
    }

    /**
     * Performs dfs from the idx vertex.
     */
    private void topSortInternal(int idx, List<Integer> sortedNodes, boolean[] visited) {
        List<Integer> stack = new ArrayList<>();
        List<Integer> nextNeighbour = new ArrayList<>(Collections.nCopies(visited.length, 0));
        stack.add(idx);
        visited[idx] = true;
        while(!stack.isEmpty()) {
            int currentIdx = stack.get(stack.size() - 1);
            int next = nextNeighbour.get(currentIdx);
            if (next == charOrderGraph.get(currentIdx).size()) {
                stack.remove(stack.size() - 1);
                sortedNodes.add(currentIdx); // add a vertex to result stack when it is removed from dfs stack
                continue;
            }
            nextNeighbour.set(currentIdx, next + 1);
            int nextIdx = charOrderGraph.get(currentIdx).get(next);
            if (visited[nextIdx]) {
                continue;
            }
            visited[nextIdx] = true;
            stack.add(nextIdx);
        }
    }

    /**
     * Converts list of vertexes to list of characters.
     * Takes O(m) operations since we iterate of list of all vertexes in graph.
     * @return list of characters
     */
    private List<Character> charListFromIdxList(List<Integer> indexes) {
        List<Character> chars = new ArrayList<>();
        for (Integer idx : indexes) {
            chars.add(foundCharacters.get(idx));
        }
        return chars;
    }
}
