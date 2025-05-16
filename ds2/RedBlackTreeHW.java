package ds2;

import java.util.Scanner;

import algs13.Queue;
import stdlib.GraphvizBuilder;
import stdlib.StdDraw;
import stdlib.StdIn;
import stdlib.StdOut;

/* 
 * Lillian Hunt
 * CSC 302 
 */


public class RedBlackTreeHW<K extends Comparable<? super K>, V> {

	private static final boolean RED   = true;
	private static final boolean BLACK = false;

	private Node<K,V> root; // root of the BST
	private int N;          // number of key-value pairs in BST

	// BST helper node data type
	private static class Node<K,V> {
		public final K key;         // key
		public V val;         // associated data
		public Node<K,V> left, right;  // links to left and right subtrees
		public boolean color;     // color of parent link

		public Node(K key, V val, boolean color) {
			this.key = key;
			this.val = val;
			this.color = color;
		}
	}

	/* ***********************************************************************
	 *  Standard BST search
	 *************************************************************************/

	// return value associated with the given key, or null if no such key exists
	public V get(K key) { return get(root, key); }
	public V get(Node<K,V> x, K key) {
		while (x != null) {
			int cmp = key.compareTo(x.key);
			if      (cmp < 0) x = x.left;
			else if (cmp > 0) x = x.right;
			else              return x.val;
		}
		return null;
	}

	// is there a key-value pair in the symbol table with the given key?
	public boolean contains(K key) {
		return (get(key) != null);
	}


	/* ***********************************************************************
	 *  Red-black insertion
	 *************************************************************************/

	public void put(K key, V val) {
		root = insert(root, key, val);
		root.color = BLACK;
		assert check();
	}

	private Node<K,V> insert(Node<K,V> h, K key, V val) {
		if (h == null) {
			N++;
			return new Node<>(key, val, RED);
		}

		int cmp = key.compareTo(h.key);
		if      (cmp < 0) h.left  = insert(h.left,  key, val);
		else if (cmp > 0) h.right = insert(h.right, key, val);
		else              h.val   = val;

		// fix-up any right-leaning links
		if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
		if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
		if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);

		return h;
	}

	/* ***********************************************************************
	 *  red-black tree helper functions
	 *************************************************************************/

	// is node x red (and non-null) ?
	private boolean isRed(Node<K,V> x) {
		if (x == null) return false;
		return (x.color == RED);
	}

	// rotate right
	private Node<K,V> rotateRight(Node<K,V> h) {
		assert (h != null) && isRed(h.left);
		Node<K,V> x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = h.color;
		h.color = RED;
		return x;
	}

	// rotate left
	private Node<K,V> rotateLeft(Node<K,V> h) {
		assert (h != null) && isRed(h.right);
		Node<K,V> x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = h.color;
		h.color = RED;
		return x;
	}

	// precondition: two children are red, node is black
	// postcondition: two children are black, node is red
	private void flipColors(Node<K,V> h) {
		assert !isRed(h) && isRed(h.left) && isRed(h.right);
		h.color = RED;
		h.left.color = BLACK;
		h.right.color = BLACK;
	}


	/* ***********************************************************************
	 *  Utility functions
	 *************************************************************************/
	// return number of key-value pairs in symbol table
	public int size() { return N; }

	// is the symbol table empty?
	public boolean isEmpty() { return N == 0; }

	// height of tree (empty tree height = 0)
	public int height() { return height(root); }
	private int height(Node<K,V> x) {
		if (x == null) return 0;
		return 1 + Math.max(height(x.left), height(x.right));
	}

	// return the smallest key; null if no such key
	public K min() { return min(root); }
	private K min(Node<K,V> x) {
		K key = null;
		while (x != null) {
			key = x.key;
			x = x.left;
		}
		return key;
	}

	// return the largest key; null if no such key
	public K max() { return max(root); }
	private K max(Node<K,V> x) {
		K key = null;
		while (x != null) {
			key = x.key;
			x = x.right;
		}
		return key;
	}


	/* *********************************************************************
	 *  Iterate using an inorder traversal.
	 *  Iterating through N elements takes O(N) time.
	 ***********************************************************************/
	public Iterable<K> keys() {
		Queue<K> queue = new Queue<>();
		keys(root, queue);
		return queue;
	}

	private void keys(Node<K,V> x, Queue<K> queue) {
		if (x == null) return;
		keys(x.left, queue);
		queue.enqueue(x.key);
		keys(x.right, queue);
	}


	/* ***********************************************************************
	 *  Check integrity of red-black BST data structure
	 *************************************************************************/
	private boolean check() {
		if (!isBST())            StdOut.println("Not in symmetric order");
		if (!is23())             StdOut.println("Not a 2-3 tree");
		if (!isBalanced())       StdOut.println("Not balanced");
		return isBST() && is23() && isBalanced();
	}

	// does this binary tree satisfy symmetric order?
	// Note: this test also ensures that data structure is a binary tree since order is strict
	private boolean isBST() {
		return isBST(root, null, null);
	}

	// is the tree rooted at x a BST with all keys strictly between min and max
	// (if min or max is null, treat as empty constraint)
	// Credit: Bob Dondero's elegant solution
	private boolean isBST(Node<K,V> x, K min, K max) {
		if (x == null) return true;
		if (min != null && x.key.compareTo(min) <= 0) return false;
		if (max != null && x.key.compareTo(max) >= 0) return false;
		return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
	}

	// Does the tree have no red right links, and at most one (left)
	// red links in a row on any path?
	private boolean is23() { return is23(root); }
	private boolean is23(Node<K,V> x) {
		if (x == null) return true;
		if (isRed(x.right)) return false;
		if (x != root && isRed(x) && isRed(x.left))
			return false;
		return is23(x.left) && is23(x.right);
	}

	// do all paths from root to leaf have same number of black edges?
	private boolean isBalanced() {
		int black = 0;     // number of black links on path from root to min
		Node<K,V> x = root;
		while (x != null) {
			if (!isRed(x)) black++;
			x = x.left;
		}
		return isBalanced(root, black);
	}

	// does every path from the root to a leaf have the given number of black links?
	private boolean isBalanced(Node<K,V> x, int black) {
		if (x == null) return black == 0;
		if (!isRed(x)) black--;
		return isBalanced(x.left, black) && isBalanced(x.right, black);
	}
	
	public void toGraphviz(String filename) {
		GraphvizBuilder gb = new GraphvizBuilder ();
		toGraphviz (gb, null, root);
		gb.toFileUndirected (filename, "ordering=\"out\"");
	}
	
	private void toGraphviz (GraphvizBuilder gb, Node<K, V> parent, Node<K, V> n) {
		if (n == null) { gb.addNullEdge (parent); return; }
		String nodeProperties = n.color ? "color=\"red\"" : "";
		String edgeProperties = n.color ? "color=\"red\",style=\"bold\"" : "";
		gb.addLabeledNode (n, n.key.toString (), nodeProperties);
		if (parent != null) gb.addEdge (parent, n, edgeProperties);
		toGraphviz (gb, n, n.left);
		toGraphviz (gb, n, n.right);
	} 
	
	public void drawTree() {
		if (root != null) {
			StdDraw.setCanvasSize(1200,700);
			drawTree(root, .5, 1, .25, 0);
		}
	}
	private void drawTree (Node<K,V> n, double x, double y, double range, int depth) {
		int CUTOFF = 10;
		StdDraw.setPenColor (StdDraw.BLACK);
		StdDraw.text (x, y, n.key.toString ());
		StdDraw.setPenRadius (.005);
		if (n.left != null && depth != CUTOFF) {
			if (n.left.color == RED) {
				StdDraw.setPenRadius (.005);
				StdDraw.setPenColor (StdDraw.RED);
			}
			StdDraw.line (x-range, y-.08, x-.01, y-.01);
			drawTree (n.left, x-range, y-.1, range*.5, depth+1);
		}
		if (n.right != null && depth != CUTOFF) {
		    if (n.right.color == RED) {
		        StdDraw.setPenRadius (.005);
		        StdDraw.setPenColor (StdDraw.RED);
		    } else {
		        StdDraw.setPenRadius (.005);
		        StdDraw.setPenColor (StdDraw.BLACK);
		    }
			StdDraw.line (x+range, y-.08, x+.01, y-.01);
			drawTree (n.right, x+range, y-.1, range*.5, depth+1);
		}
	}

	

	/* ***********************************************************************
	 *  Test client
	 *************************************************************************/
	public static void main(String[] args) throws InterruptedException  {

		StdIn.fromString ("T C A G B D W");


	    String[] inputs = StdIn.readAllStrings();
		RedBlackTreeHW<String, Integer> st = new RedBlackTreeHW<>();
		
	
			for (int i = 0; i < inputs.length; i++) {
			
				String key = inputs[i];
				st.toGraphviz ("g.png");
			
			
				st.put(key, i);
				st.drawTree();
			
				while (!StdDraw.hasNextKeyTyped()) {
				  
				}
				StdDraw.nextKeyTyped();
				  
			}
		
	}
}
	
	
	
	



