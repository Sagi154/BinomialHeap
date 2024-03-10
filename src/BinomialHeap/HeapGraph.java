package BinomialHeap;

import javax.swing.*;
import java.awt.*;

public class HeapGraph extends Canvas
{
    public BinomialHeap heap;

    public HeapGraph(BinomialHeap heap)
    {
        this.heap = heap;
    }


    public static int rankSum(BinomialHeap heap)
    {
        BinomialHeap.HeapNode node = heap.getLast().getNext();
        int sum = node.getRank();
        if (sum == 0)
            sum = 1;
        node = node.getNext();
        while (node != heap.getLast().getNext())
        {
            sum += node.getRank();
            node = node.getNext();
        }
        return sum;
    }

    public void paintHeap(BinomialHeap heap, Graphics g, int y)
    {
        int height = Math.min(this.getHeight() / (heap.getLast().getRank() + 1), 150);
        int unit = this.getWidth() / rankSum(heap);
        BinomialHeap.HeapNode tree = heap.getLast().getNext();
        int lastBorder = tree.getRank() * unit;
        if (lastBorder == 0)
            lastBorder = unit;
        paintTree(tree, g, 0, lastBorder, y, height);
        while (!tree.equals(heap.getLast()))
        {
            tree = tree.getNext();
            paintTree(tree, g, lastBorder, lastBorder + tree.getRank() * unit, y, height);
            lastBorder += tree.getRank() * unit;
        }
    }

    public static void retrieve(BinomialHeap.HeapNode node)
    {
        BinomialHeap.HeapNode last = node.getChild(), find = node.getChild();
        while (!find.getNext().equals(last))
            find = find.getNext();
        node.setChild(find);
        find.setNext(last.getNext());
        node.setRank(node.getRank() - 1);
    }

    public static void connect(BinomialHeap.HeapNode node, BinomialHeap.HeapNode branch)
    {
        node.getChild().setNext(branch);
        node.setChild(branch);
        node.setRank(node.getRank() + 1);
    }

    public void paintTree(BinomialHeap.HeapNode node, Graphics g, int in, int out, int y, int h)
    {
        int mid = (in + out) / 2;
        if (node.getRank() == 0)
        {
            g.setColor(new Color(153, 192, 227));
            g.fillOval(mid - 25, y, 50, 50);
            g.setColor(Color.BLACK);
            FontMetrics fm = g.getFontMetrics();
            String key = String.valueOf(node.getItem().getKey());
            g.drawString(key, mid - fm.stringWidth(key) / 2, y + 35);
        }
        else if (node.getRank() == 1)
        {
            BinomialHeap.HeapNode branch = node.getChild();
            paintTree(branch, g, in, out, y + h, h);
            retrieve(node);
            paintTree(node, g, in, out, y, h);
            connect(node, branch);
            g.drawLine(mid, y + 50, mid, y + h);
        }
        else
        {
            int hOffset = (out - in) / (int)Math.pow(2, node.getRank());
            BinomialHeap.HeapNode branch = node.getChild();
            g.drawLine(mid - hOffset, y + h + 25, out - hOffset, y + 25);
            paintTree(branch, g, in, mid, y + h, h);
            retrieve(node);
            paintTree(node, g, mid, out, y, h);
            connect(node, branch);

        }
    }

    @Override
    public void paint(Graphics g)
    {
        Font f = new Font("Cambria Math", Font.BOLD, 26);
        g.setFont(f);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        paintHeap(heap, g, 30);
    }

    public static void draw(BinomialHeap heap)
    {
        JFrame frame = new JFrame();
        HeapGraph heapGraph = new HeapGraph(heap);
        heapGraph.setSize(1500, 750);
        heapGraph.setBackground(Color.WHITE);
        frame.add(heapGraph);
        frame.pack();
        frame.setVisible(true);
    }
}
