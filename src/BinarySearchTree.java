public class BinarySearchTree {
    Node root;

    public void insert(Player player) {
        root = insert(root, player);
    }

    private Node insert(Node current, Player player) {
        if (current == null) {
            return new Node(player);
        }

        if (player.getRanking() < current.player.getRanking()) {
            current.left = insert(current.left, player);
        } else {
            current.right = insert(current.right, player);
        }

        return current;
    }

    public boolean search(String name) {
        return search(root, name) != null;
    }

    private Node search(Node current, String name) {
        if (current == null) {
            return null;
        }

        if (current.player.getNickname().equalsIgnoreCase(name)) {
            return current;
        }

        Node leftResult = search(current.left, name);

        if (leftResult != null) {
            return leftResult;
        }

        return search(current.right, name);
    }

    public Player remove(String name) {
        Node found = search(root, name);

        if (found == null) {
            return null;
        }

        Player removedPlayer = found.player;
        root = removeByRanking(root, found.player.getRanking());

        return removedPlayer;
    }

    private Node remove(Node current, String name) {
        Node found = search(current, name);

        if (found == null) {
            return current;
        }

        return removeByRanking(current, found.player.getRanking());
    }

    private Node removeByRanking(Node current, int ranking) {
        if (current == null) {
            return null;
        }

        if (ranking < current.player.getRanking()) {
            current.left = removeByRanking(current.left, ranking);
        } else if (ranking > current.player.getRanking()) {
            current.right = removeByRanking(current.right, ranking);
        } else {
            if (current.left == null && current.right == null) {
                return null;
            }

            if (current.left == null) {
                return current.right;
            }

            if (current.right == null) {
                return current.left;
            }

            Player smallestPlayer = findSmallest(current.right);
            current.player = smallestPlayer;
            current.right = removeByRanking(current.right, smallestPlayer.getRanking());
        }

        return current;
    }

    private Player findSmallest(Node current) {
        while (current.left != null) {
            current = current.left;
        }

        return current.player;
    }

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node current) {
        if (current != null) {
            inOrder(current.left);
            System.out.println(current.player);
            inOrder(current.right);
        }
    }

    public Node getRoot() {
        return root;
    }
}