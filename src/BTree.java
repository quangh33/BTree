public class BTree {
    private static int M;
    /*
        M children
        M-1 keys

        [1][2][9][20]

        node[0]->next: key < 2
        node[1]->next: 1 <= key < 9
        node[2]->next: 9 <= key < 20
        node[3]->next: key >=20
     */
    private Node root;
    private int height;
    private int n;


    public BTree(int m) {
        M = m;
        root = new Node(0);
    }

    public String get(int key) {
        return search(root, key, height);
    }

    private String search(Node x, int key, int h) {
        Entry[] children = x.children;
        if (h == 0) {
            // can be improved by Binary search
            for (int i = 0; i < x.m; i++)
                if (children[i].key == key)
                    return children[i].value;
        } else {
            // can be improved by Binary search
            for (int i = 0; i < x.m; i++) {
                if (i == x.m - 1 || key < children[i + 1].key) {
                    return search(children[i].next, key, h - 1);
                }
            }
        }
        return null;
    }

    public void put(int key, String value) {
        Node u = insert(root, key, value, height);
        n++;
        if (u == null) {
            return;
        }
        // need to split root
        Node newRoot = new Node(2);
        newRoot.children[0] = new Entry(root.children[0].key, null, root);
        newRoot.children[1] = new Entry(u.children[0].key, null, u);
        root = newRoot;
        height++;
    }

    private Node insert(Node x, int key, String value, int h) {
        Entry newEntry = new Entry(key, value, null);
        int insertPosition = x.m;

        if (h == 0) {
            // can be improved by Binary search
            for (int i = 0; i < x.m; i++) {
                if (key < x.children[i].key) {
                    insertPosition = i;
                    break;
                }
            }
        } else {
            // can be improved by Binary search
            for (int i = 0; i < x.m; i++) {
                if (i + 1 == x.m || key < x.children[i + 1].key) {
                    Node u = insert(x.children[i].next, key, value, h - 1);
                    insertPosition = i + 1;
                    if (u == null) {
                        return null;
                    }
                    newEntry.key = u.children[0].key;
                    newEntry.next = u;
                    break;
                }
            }
        }
        for (int j = x.m; j > insertPosition; j--) {
            x.children[j] = x.children[j - 1];
        }
        x.children[insertPosition] = newEntry;
        x.m++;
        // fit in the max size of each node
        if (x.m < M) {
            return null;
        }
        // split
        return split(x);
    }

    private Node split(Node x) {
        Node newNode = new Node(M / 2);
        // cut the size of x by half
        x.m = M / 2;
        for (int i = 0; i < M / 2; i++) {
            newNode.children[i] = x.children[M / 2 + i];
        }
        return newNode;
    }

    public int size() {
        return n;
    }

    public int height() {
        return height;
    }


    private static final class Node {
        private int m;
        private Entry[] children = new Entry[M];

        private Node(int k) {
            m = k;
        }
    }


    private static final class Entry {
        private final String value;
        private int key;
        private Node next;

        Entry(int key, String value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
