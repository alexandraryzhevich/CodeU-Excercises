package assignment2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Alexandra on 23.05.2017.
 */
public class Assignment2Q1 {

    private static class TreePoint<T> {
        private TreeNode<T> node;
        private int checkedChildren; // keeps number of checked children for node

        public TreePoint(TreeNode<T> node, int checkedChildren) {
            this.node = node;
            this.checkedChildren = checkedChildren;
        }

        public TreeNode<T> getNode() {
            return node;
        }

        public void setNode(TreeNode<T> node) {
            this.node = node;
        }

        public int getCheckedChildren() {
            return checkedChildren;
        }

        public void setCheckedChildren(int checkedChildren) {
            this.checkedChildren = checkedChildren;
        }

        @Override
        public String toString() {
            return node.toString();
        }
    }

    private static <T> void printAncestors(TreeNode<T> root, T value) {
        if (root == null) {
            System.out.println("The root cannot be null.");
            return;
        }
        if (root.getValue().equals(value)) {
            System.out.println("No ancestors."); // this node is the root node
            return;
        }
        List<TreePoint<T>> ancestors = new ArrayList<>();

        ancestors.add(new TreePoint<>(root, 0));
        while (!ancestors.isEmpty()) {
            TreePoint<T> top = ancestors.get(ancestors.size() - 1);
            if (top.getNode().getValue().equals(value)) { // we've found the requested node
                ancestors.remove(ancestors.size() - 1);   // and now there is a chain of its ancestors in the ancestors stack
                break;
            }
            // firstly we check the left son and then the right son
            if (top.getCheckedChildren() == 0) {
                top.setCheckedChildren(top.getCheckedChildren() + 1);
                if (top.getNode().getLeft() != null) {
                    ancestors.add(new TreePoint<>(top.getNode().getLeft(), 0));
                }
            } else if (top.getCheckedChildren() == 1) {
                top.setCheckedChildren(top.getCheckedChildren() + 1);
                if (top.getNode().getRight() != null) {
                    ancestors.add(new TreePoint<>(top.getNode().getRight(), 0));
                }
            } else {
                ancestors.remove(ancestors.size() - 1);
            }
        }
        if (ancestors.isEmpty()) {
            System.out.println("No such value in the tree.");
            return;
        }
        Collections.reverse(ancestors);
        ancestors.forEach(System.out::print);
        System.out.println();
    }

    public static void main(String[] args) {
        TreeNode<Integer> node1 = new TreeNode<>(null, null, 1);
        TreeNode<Integer> node5 = new TreeNode<>(null, null, 5);
        TreeNode<Integer> node14 = new TreeNode<>(null, null, 14);
        TreeNode<Integer> node19 = new TreeNode<>(null, null, 19);
        TreeNode<Integer> node3 = new TreeNode<>(node1, node5, 3);
        TreeNode<Integer> node9 = new TreeNode<>(node3, node14, 9);
        TreeNode<Integer> node18 = new TreeNode<>(null, node19, 18);
        TreeNode<Integer> node16 = new TreeNode<>(node9, node18, 16);

        printAncestors(node16, 5);  // 3 9 16
        printAncestors(node16, 16); // No ancestors.
        printAncestors(node16, 14); // 9 16
        printAncestors(node16, 1);  // 3 9 16
        printAncestors(node16, 18); // 16
        printAncestors(node16, 20); // No such value in the tree.
    }
}
