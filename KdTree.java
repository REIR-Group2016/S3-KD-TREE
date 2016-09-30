package P3;

/*************************************************************************
 *************************************************************************/

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.Arrays;

public class KdTree {

    private int size = 0; // Grimur: size of tree value
    private Node root; // root of the trees

    // constructing the node
    private static class Node {
        private Point2D p; // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this node
        private Node left, right; // the left and right/bottom subtree
        private boolean vertical; // the tree level

        public Node(Point2D p, Node left, Node right, RectHV rect, boolean vertical) {
            this.p = p;
            this.rect = rect;
            this.left = left;
            this.right = right;
            this.vertical = vertical;

        }
    }

    public SET<Point2D> pointSet;

    // construct an empty set of points
    public KdTree() {

        pointSet = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    // Grimur: See nothing about needing that as opposed to just for the tree as test main function shows?
    private int size(Node node) {
        int returnSize = size;

        return returnSize;
    }

    /*******************************************************************************
     * Insert - add the point p to the set (if it is not already in the set)
     ******************************************************************************/

    public void insert(Point2D p) {
        root = insertHelper(root, p, true);
    }

    private Node insertHelper(Node node, Point2D point, boolean vertical) {
        if (node == null) {
            RectHV newRect = new RectHV(0.0, 0.0, 1.0, 1.0);

            Node newNode = new Node(point, null, null, newRect, !vertical);
            size++;

            return newNode;
        }

        if (node.p.x() == point.x() && node.p.y() == point.y()) {
            return node;
        }
        // Grimur: Need to compare y values when vertical is true

        if (vertical) {
            if (node.p.x() > point.x()) {
                node.left = insertHelper(node.left, point, false);
            } else {
                node.right = insertHelper(node.right, point, false);
            }
        } else {
            if (node.p.y() > point.y()) {
                node.left = insertHelper(node.left, point, true);
            } else {
                node.right = insertHelper(node.right, point, true);
            }
        }
        return node;
    }

    /*******************************************************************************
     * Contains - does the set contain the point p?
     ******************************************************************************/

    //Grimur: Made it void and added helper, making helper determine the outcome
    public boolean contains(Point2D p) {
        return containsHelper(root, p, true);
    }

    private boolean containsHelper(Node node, Point2D point, boolean vertical) {
        if (node == null) {
            return false;
        }

        if (node.p.x() == point.x() && node.p.y() == point.y()) {
            return true;
        }

        if (vertical) {
            if (node.p.x() > point.x()) {
                return containsHelper(node.left, point, false);
            } else {
                return containsHelper(node.right, point, false);
            }
        } else {
            if (node.p.y() > point.y()) {
                return containsHelper(node.left, point, true);
            } else {
                return containsHelper(node.right, point, true);
            }
        }
    }

    /*******************************************************************************
     * Draw - draw all of the points to standard draw
     ******************************************************************************/

    public void draw() {
        drawHelper(root);
    }

    private void drawHelper(Node node) {
        if (node == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();

        double x = node.p.x();
        double y = node.p.y();

        if (node.vertical == true) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x, node.rect.ymin(), x, node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), y, node.rect.xmax(), y);
        }
        drawHelper(node.left);
        drawHelper(node.right);
    }

    /*******************************************************************************
     * Range - all points in the set that are inside the rectangle
     * Mooshak #1248
     ******************************************************************************/

    private ArrayList<Point2D> contain = new ArrayList<Point2D>();

    public Iterable<Point2D> range(RectHV rect) {
        contain.clear();
        rangeHelper(root, rect, true);
        return contain;
    }

    private void rangeHelper(Node node, RectHV rect, boolean vertical) {
        if (node == null)
            return;

        if (vertical) {
            // Check the x-coordinate
            if (node.p.x() > rect.xmax()) {
                rangeHelper(node.left, rect, false);

            } else if (node.p.x() < rect.xmin()) {
                rangeHelper(node.right, rect, false);

            } else {
                if (rect.contains(node.p)) {
                    contain.add(node.p);
                }
                rangeHelper(node.left, rect, false);
                rangeHelper(node.right, rect, false);
            }

        } else {
            // Check the y-coordinate
            if (node.p.y() > rect.ymax()) {
                rangeHelper(node.left, rect, true);
            } else if (node.p.y() < rect.ymin()) {
                rangeHelper(node.right, rect, true);

            } else {
                if (rect.contains(node.p)) {
                    contain.add(node.p);
                }
                rangeHelper(node.left, rect, true);
                rangeHelper(node.right, rect, true);
            }
        }
    }

    /*******************************************************************************
     * Nearest - a nearest neighbor in the set to p; null if set is empty
     * Mooshak # 1284
     ******************************************************************************/

    Point2D closestPoint;
    Point2D target;

    public Point2D nearest(Point2D p) {
        closestPoint = null;
        target = p;
        nearestHelper(root);
        return closestPoint;
    }

    private void nearestHelper(Node currentNode) {
        if (currentNode == null) return;

        if (closestPoint == null) {
            closestPoint = currentNode.p;
        } else {
            if (closestPoint.distanceTo(target) > currentNode.p.distanceTo(target)) {
                closestPoint = currentNode.p;
            }
        }
        nearestHelper(currentNode.left);
        nearestHelper(currentNode.right);
    }

    /*******************************************************************************
     * Test client
     ******************************************************************************/
    public static void main(String[] args) {
        In in = new In();
        Out out = new Out();
        int N = in.readInt(), C = in.readInt(), T = 20;
        KdTree tree = new KdTree();
        Point2D[] points = new Point2D[C];
        out.printf("Inserting %d points into tree\n", N);
        for (int i = 0; i < N; i++) {
            tree.insert(new Point2D(in.readDouble(), in.readDouble()));
        }
        out.printf("tree.size(): %d\n", tree.size());
        out.printf("Testing contains method, querying %d points\n", C);
        for (int i = 0; i < C; i++) {
            points[i] = new Point2D(in.readDouble(), in.readDouble());
            out.printf("%s: %s\n", points[i], tree.contains(points[i]));
        }
        for (int i = 0; i < T; i++) {
            for (int j = 0; j < C; j++) {
                tree.contains(points[j]);
            }
        }
    }
}


