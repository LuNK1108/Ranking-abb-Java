import javax.swing.*;
import java.awt.*;

public class TreePanel extends JPanel {
    private BinarySearchTree tree;

    public TreePanel(BinarySearchTree tree) {
        this.tree = tree;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (tree.getRoot() != null) {
            drawTree(g, tree.getRoot(), 2500, 50, 1200);
        }
    }

    private void drawTree(Graphics g, Node node, int x, int y, int horizontalGap) {
        if (node == null) {
            return;
        }

        g.setColor(Color.BLACK);

        if (node.left != null) {
            int childX = x - horizontalGap;
            int childY = y + 80;

            g.drawLine(x, y, childX, childY);
            drawTree(g, node.left, childX, childY, horizontalGap / 2);
        }

        if (node.right != null) {
            int childX = x + horizontalGap;
            int childY = y + 80;

            g.drawLine(x, y, childX, childY);
            drawTree(g, node.right, childX, childY, horizontalGap / 2);
        }

        g.setColor(new Color(180, 220, 255));
        g.fillOval(x - 35, y - 20, 70, 40);

        g.setColor(Color.BLACK);
        g.drawOval(x - 35, y - 20, 70, 40);

        String text = "x" + node.player.getNickname();

        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);

        g.drawString(text, x - textWidth / 2, y + 5);
    }
}