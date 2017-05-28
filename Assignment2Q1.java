import java.util.LinkedList;

/**
 * Created by Alexandra on 23.05.2017.
 */
public class Assignment2Q1 {

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

        public void printAncestors(T value) {
            if (this.value.equals(value)) {
                System.out.println("No ancestors."); // this node is the root node
                return;
            }
            LinkedList<TreeNode<T>> ancestors = new LinkedList<>(); // stack of ancestors
            LinkedList<Integer> checkedChildren = new LinkedList<>(); //keeps number of checked children for each ancestor
            ancestors.add(this);
            checkedChildren.add(0);
            while (!ancestors.isEmpty()) {
                TreeNode<T> top = ancestors.getFirst();
                if (top.getValue().equals(value)) { // we've found the requested node
                    ancestors.removeFirst();        // and now there is a chain of its ancestors in the ancestors stack
                    break;
                }
                int checked = checkedChildren.removeFirst();
                // firstly we check the left son and then the right son
                if (checked == 0) {
                    checkedChildren.addFirst(checked + 1);
                    if (top.getLeft() != null) {
                        ancestors.addFirst(top.getLeft());
                        checkedChildren.addFirst(0);
                    }
                } else if (checked == 1) {
                    checkedChildren.addFirst(checked + 1);
                    if (top.getRight() != null) {
                        ancestors.addFirst(top.getRight());
                        checkedChildren.addFirst(0);
                    }
                } else {
                    ancestors.removeFirst();
                }
            }
            if (ancestors.isEmpty()) {
                System.out.println("No such value in the tree.");
                return;
            }
            ancestors.forEach(System.out::print);
            System.out.println();
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

        node16.printAncestors(5);  // 3 9 16
        node16.printAncestors(16); // No ancestors.
        node16.printAncestors(14); // 9 16
        node16.printAncestors(1);  // 3 9 16
        node16.printAncestors(18); // 16
        node16.printAncestors(20); // No such value in the tree.
    }
}
