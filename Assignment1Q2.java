
public class Assignment1Q2 {

    public static class SimpleList <T> {
        private static class SimpleListNode <T> {
            private T value;
            private SimpleListNode next;

            SimpleListNode(T value) {
                this.value = value;
            }

            public T getValue() {
                return value;
            }

            public void setValue(T value) {
                this.value = value;
            }

            public SimpleListNode getNext() {
                return next;
            }

            public void setNext(SimpleListNode next) {
                this.next = next;
            }
        }

        private SimpleListNode head;

        public void push(T value) {
            SimpleListNode<T> node = new SimpleListNode<>(value);
            node.setNext(head);
            head = node;
        }

        public Object getKthToLast(int k) {
            // boundary cases
            if (k < 0 || head == null) {
                throw new IndexOutOfBoundsException();
            }
            // skip the first k nodes
            SimpleListNode lead = head;
            for (int i = 0; i < k; i++) {
                if (lead.getNext() == null) {
                    throw new IndexOutOfBoundsException();
                }
                lead = lead.getNext();
            }

            // now the kth node will be reached
            // when the lead node will reach the tail
            SimpleListNode kth = head;
            while (lead.getNext() != null) {
                lead = lead.getNext();
                kth = kth.getNext();
            }
            return kth.getValue();
        }
    }

    public static void main(String[] args) {
        SimpleList<Integer> list = new SimpleList<>();
        list.push(1);
        list.push(2);
        list.push(3);
        list.push(4);
        System.out.println(list.getKthToLast(1)); // 2
    }
}
