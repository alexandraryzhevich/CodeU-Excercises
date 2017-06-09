package assignment2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandra on 23.05.2017.
 */
public class Assignment2Q2 {

    private static class TreePoint<T> {
        private TreeNode<T> node;
        private int checkedChildren;
        // 0 - there is no any children equal to value1 or value2
        // 1 - there is one child equal to value1 or value2
        // 2 - there are both requested values as children
        // so we are looking for the first node with mark 2
        private int mark;

        public TreePoint(TreeNode<T> node, int checkedChildren, int mark) {
            this.node = node;
            this.checkedChildren = checkedChildren;
            this.mark = mark;
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

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }
    }

    private static <T> T lowestCommonAncestor(TreeNode<T> root, T value1, T value2) {
        if (root == null) {
            return null;
        }
        // since there is only one path from a node to the root
        // the lowest ancestor will be on this path
        // and stack of ancestors will also include it
        List<TreePoint<T>> stack = new ArrayList<>();

        stack.add(new TreePoint<>(root, 0, 0));
        // performing DFS
        while (!stack.isEmpty()) {
            TreePoint<T> top = stack.get(stack.size() - 1);
            if (top.getCheckedChildren() == 0) {
                top.setCheckedChildren(top.getCheckedChildren() + 1);
                if (top.getNode().getLeft() != null) {
                    // check the left subtree
                    stack.add(new TreePoint<>(top.getNode().getLeft(), 0, 0));
                }
            } else if (top.getCheckedChildren() == 1) {
                top.setCheckedChildren(top.getCheckedChildren() + 1);
                if (top.getNode().getRight() != null) {
                    // check the right subtree
                    stack.add(new TreePoint<>(top.getNode().getRight(), 0, 0));
                }
            } else {
                TreePoint<T> last = stack.remove(stack.size() - 1);
                if (last.getNode().getValue().equals(value1) || last.getNode().getValue().equals(value2)) {
                    last.setMark(last.getMark() + 1);
                }
                if (last.getMark() == 2) {
                    // stop since we've found the lowest ancestor
                    return last.getNode().getValue();
                }
                if (!stack.isEmpty()) {
                    // if a node has some mark then its parent also will have at least this mark
                    stack.get(stack.size() - 1).setMark(stack.get(stack.size() - 1).getMark() + last.getMark());
                }
            }
        }
        return null;
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

        System.out.println(lowestCommonAncestor(node16, 5, 14));  // 9
        System.out.println(lowestCommonAncestor(node16, 5, 1));   // 3
        System.out.println(lowestCommonAncestor(node16, 5, 3));   // 3
        System.out.println(lowestCommonAncestor(node16, 1, 19));  // 16
        System.out.println(lowestCommonAncestor(node16, 16, 14)); // 16
        System.out.println(lowestCommonAncestor(node16, 3, 18));  // 16
        System.out.println(lowestCommonAncestor(node16, 3, 9));   // 9
        System.out.println(lowestCommonAncestor(node16, -1, 14)); // null
        System.out.println(lowestCommonAncestor(node16, -1, 20)); // null
    }
}
