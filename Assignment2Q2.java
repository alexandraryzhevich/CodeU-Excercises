import java.util.LinkedList;

/**
 * Created by Alexandra on 23.05.2017.
 */
public class Assignment2Q2 {

    public static class TreeNode <T> {
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

        public T lowestCommonAncestor(T value1, T value2) {
            // since there is only one path from a node to the root
            // the lowest ancestor will be on this path
            // and stack of ancestors will also include it
            LinkedList<TreeNode<T>> stack = new LinkedList<>();
            LinkedList<Integer> checkedChildren = new LinkedList<>();
            // 0 - there is no any children equal to value1 or value2
            // 1 - there is one child equal to value1 or value2
            // 2 - there are both requested values as children
            // so we are looking for the first node with mark 2
            LinkedList<Integer> marks = new LinkedList<>();
            stack.add(this);
            checkedChildren.add(0);
            marks.add(0);
            // performing DFS
            while (!stack.isEmpty()) {
                TreeNode<T> top = stack.getLast();
                int checked = checkedChildren.removeLast();
                if (checked == 0) {
                    checkedChildren.add(checked + 1);
                    if (top.getLeft() != null) {
                        // check the left subtree
                        stack.add(top.getLeft());
                        checkedChildren.add(0);
                        marks.add(0);
                    }
                } else if (checked == 1) {
                    checkedChildren.add(checked + 1);
                    if (top.getRight() != null) {
                        // check the right subtree
                        stack.add(top.getRight());
                        checkedChildren.add(0);
                        marks.add(0);
                    }
                } else {
                    int mark = marks.removeLast();
                    T value = stack.removeLast().getValue();
                    if (value.equals(value1) || value.equals(value2)) {
                        mark++;
                    }
                    if (mark == 2) {
                        // stop since we've found the lowest ancestor
                        return value;
                    }
                    if (!marks.isEmpty()) {
                        // if a node has some mark then its parent also will have at least this mark
                        marks.add(marks.removeLast() + mark);
                    }
                }
            }
            return null;
        }
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

        System.out.println(node16.lowestCommonAncestor(5, 14));  // 9
        System.out.println(node16.lowestCommonAncestor(5, 1));   // 3
        System.out.println(node16.lowestCommonAncestor(5, 3));   // 3
        System.out.println(node16.lowestCommonAncestor(1, 19));  // 16
        System.out.println(node16.lowestCommonAncestor(16, 14)); // 16
        System.out.println(node16.lowestCommonAncestor(3, 18));  // 16
        System.out.println(node16.lowestCommonAncestor(3, 9));   // 9
        System.out.println(node16.lowestCommonAncestor(-1, 14)); // null
        System.out.println(node16.lowestCommonAncestor(-1, 20)); // null
    }
}
