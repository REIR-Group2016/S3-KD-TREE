package P3;

/*************************************************************************
 *************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.*;

public class KdTree {

    private int size = 0; // Grimur: size of tree value
	private Node root; // root of the tree
    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;

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
    private int size(Node node) 
    {
        int returnSize = size;
        
        return returnSize;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        root = insertHelper(root, p, true);
    }

    private Node insertHelper(Node node, Point2D point, boolean vertical)
    {
        if (node == null) 
        {
            RectHV newRect = new RectHV(0.0, 0.0, 1.0, 1.0);
        	
        	Node newNode = new Node(point, null, null, newRect, !vertical);
        	size++;
        	
        	return newNode;
        }
        
        if (node.p.x() == point.x() && node.p.y() == point.y())
        {
        	return node;
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
        
        // Grimur: Need to compare y values when vertical is true

        if(vertical)
        {
            if(node.p.x() > point.x())
            {
            	node.right = insertHelper(node.right, point, !vertical);
            }
            else
            {
            	node.left = insertHelper(node.left, point, !vertical);
            } 
        }
        
        else if(!vertical)
        {
            if(node.p.y() > point.y())
            {
            	node.right = insertHelper(node.right, point, vertical);
            }
            else
            {
            	node.left = insertHelper(node.left, point, vertical);
            } 
        }
        return node;
    }

    // does the set contain the point p?
    //Grimur: Made it void and added helper, making helper determine the outcome
    public boolean contains(Point2D p) 
    { 	
    	return containsHelper(root, p, true);
    }

    /**
     * TODO: CREATE PRIVATE HELPER FOR contains()
     */
    
    private boolean containsHelper (Node node, Point2D point, boolean vertical)
    {
    	if (node == null)
    	{
    		return false;
    	}
    	
    	if(node.p.x() == point.x() && node.p.y() == point.y())
    	{
    		return true;
    	}
    	
    	if(vertical)
        {
            if(node.p.x() > point.x())
            {
            	return containsHelper(node.right, point, !vertical);
            }
            else
            {
            	 return containsHelper(node.left, point, !vertical);
            } 
        }
        else
        {
            if(node.p.y() > point.y())
            {
            	return containsHelper(node.right, point, vertical);
            }
            else
            {
            	return containsHelper(node.left, point, vertical);
            } 
        }
    }

    // draw all of the points to standard draw
    public void draw() 
    {
        drawHelper(root, null);
    }

    private void drawHelper(Node node, Node parent) 
    {
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
        int N = in.readInt(), C = in.readInt(), T = 20;
        KdTree tree = new KdTree();
        Point2D [] points = new Point2D[C];
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

