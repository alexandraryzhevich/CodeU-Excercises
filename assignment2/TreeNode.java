package assignment2;

import java.util.LinkedList;

/**
 * Created by Alexandra on 09.06.2017.
 */
public class TreeNode <T> {
    private TreeNode<T> left;
    private TreeNode<T> right;
    private T value;

    public TreeNode(TreeNode<T> left, TreeNode<T> right, T value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public T getValue() {
        return value;
    }

    public String toString() {
        return value.toString() + '\t';
    }
}
