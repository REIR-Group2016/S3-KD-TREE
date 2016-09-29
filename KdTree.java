package P3;

/*************************************************************************
 *************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.*;

public class KdTree {

    private Node root; // root of the tree
    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;

    // constructing the node
    private static class Node {
        private Point2D p; // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this node
        private Node left, right; // the left and right/bottom subtree
        private int level; // the tree level

        public Node(Point2D p, Node left, Node right, RectHV rect, int level) {
            this.p = p;
            this.rect = rect;
            this.left = left;
            this.right = right;
            this.level = level;

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
    private int size(Node node) {
        if (node == null)
            return 0;
        else
            return size(node);
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        root = insertHelper(root, p, HORIZONTAL);
    }

    private Node insertHelper(Node node, Point2D point, boolean vertical){
        if (node == null) {
            return null;
        }

        /**
         *Check x and insert
         * if point to be inserted has a smaller x-coordinate than the point
         * in the ROOT, go left else go to right.
         *
         * Check y and insert
         * Then at the next level use the y-coordinates
         * if the point to be inserted has a smaller y-coordinate than the point
         * in the NODE, got to left else got to right
         */

        if(node.p.compareTo(point) > 0){
            node.left = insertHelper(node.left, point, !vertical);
        }else if(node.p.compareTo(point) < 0){
            node.right = insertHelper(node.right, point, !vertical);
        }else{
            node.p = point;
        }
        return node;
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {

        return false;
    }

    /**
     * TODO: CREATE PRIVATE HELPER FOR contains()
     */

    // draw all of the points to standard draw
    public void draw() {

        drawHelper(root, null);
    }

    private void drawHelper(Node node, Node parent) {
        if (node == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
        node.p.draw();

        /**
         * TODO: Implement how to draw the points and the splitting lines
         */
    }

    private ArrayList<Point2D> contain = new ArrayList<Point2D>();

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        contain.clear();
        rangeHelper(root, rect);
        return contain;
    }


    private void rangeHelper(Node node, RectHV rect) {
        if (node == null) return;

        // TO DO: we need to check if the query rectangle does not
        // intersect the rectangle corresponding to a node, no need to
        // explore that node (or its subtrees)

        // This implementation traversal all subtrees
        // and is NOT THE BEST optimization
        if (rect.contains(node.p)) {
            contain.add(node.p);
        }
        rangeHelper(node.left, rect);
        rangeHelper(node.right, rect);
    }

    Point2D closestPoint;
    Point2D target;
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {

        closestPoint = null;
        target = p;

        nearestHelper(root);
        return closestPoint;

    }

    private void nearestHelper(Node currentNode){
        if (currentNode == null) return;

        if(closestPoint == null){
            closestPoint = currentNode.p;
        }else{
            if (closestPoint.distanceTo(target) > currentNode.p.distanceTo(target)){
                closestPoint = currentNode.p;
            }
        }

        // Now we need to check the subtrees
        // This is not finished
        nearestHelper(currentNode.left);
        nearestHelper(currentNode.right);

    }


    /*******************************************************************************
     * Test client
     ******************************************************************************/
    public static void main(String[] args) {
        In in = new In();
        Out out = new Out();
        int nrOfRecangles = in.readInt();
        int nrOfPointsCont = in.readInt();
        int nrOfPointsNear = in.readInt();
        RectHV[] rectangles = new RectHV[nrOfRecangles];
        Point2D[] pointsCont = new Point2D[nrOfPointsCont];
        Point2D[] pointsNear = new Point2D[nrOfPointsNear];
        for (int i = 0; i < nrOfRecangles; i++) {
            rectangles[i] = new RectHV(in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble());
        }
        for (int i = 0; i < nrOfPointsCont; i++) {
            pointsCont[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        for (int i = 0; i < nrOfPointsNear; i++) {
            pointsNear[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        KdTree set = new KdTree();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble(), y = in.readDouble();
            set.insert(new Point2D(x, y));
        }
        for (int i = 0; i < nrOfRecangles; i++) {
            // Query on rectangle i, sort the result, and print
            Iterable<Point2D> ptset = set.range(rectangles[i]);
            int ptcount = 0;
            for (Point2D p : ptset)
                ptcount++;
            Point2D[] ptarr = new Point2D[ptcount];
            int j = 0;
            for (Point2D p : ptset) {
                ptarr[j] = p;
                j++;
            }
            Arrays.sort(ptarr);
            out.println("Inside rectangle " + (i + 1) + ":");
            for (j = 0; j < ptcount; j++)
                out.println(ptarr[j]);
        }
        out.println("Contain test:");
        for (int i = 0; i < nrOfPointsCont; i++) {
            out.println((i + 1) + ": " + set.contains(pointsCont[i]));
        }

        out.println("Nearest test:");
        for (int i = 0; i < nrOfPointsNear; i++) {
            out.println((i + 1) + ": " + set.nearest(pointsNear[i]));
        }

        out.println();
    }
}

