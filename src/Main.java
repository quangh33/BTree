public class Main {

    public static void main(String[] args) {
        System.out.println("Hello BTree!");
        BTree tree = new BTree(4);
        tree.put(1, "1");
        tree.put(4, "4");
        tree.put(6, "6");
        tree.put(7, "7");
        tree.put(0, "0");
        tree.put(-1, "-1");
        tree.put(8, "8");
        tree.put(9, "9");

        System.out.println(tree.get(0));
        System.out.println(tree.get(-1));
        System.out.println(tree.get(8));
        System.out.println(tree.get(7));
        System.out.println(tree.get(9));
        System.out.println(tree.height());
    }
}
