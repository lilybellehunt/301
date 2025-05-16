package ds2;

import algs31.BinarySearchST;
import algs32.BST;
import stdlib.StdIn;
import stdlib.StdOut;
import stdlib.Stopwatch;

/*
 * Lillian Hunt
 * CSC301 Section 502
 */

public class FillAndSearchTime {
	public static void main(String[] args) {
		StdIn.fromFile("data/tale.txt");
		String[] words = StdIn.readAllStrings();

		BinarySearchST<String, Integer> binaryST = new BinarySearchST<>();
        BST<String, Integer> bstST = new BST<>();
        
        Stopwatch binaryStStopwatch1 = new Stopwatch();
        for (String word : words) {
        	
			if (binaryST.contains(word)){
				
				binaryST.put(word, binaryST.get(word) + 1);
				
			}else binaryST.put(word, 1);
			
		}
        double putLoopST = binaryStStopwatch1.elapsedTime();
        
        
        Stopwatch bstSTStopwatch1 = new Stopwatch();
        for (String word : words) {
        	
        	if (bstST.contains(word)) {
        		
        		bstST.put(word, bstST.get(word)+ 1);
        		
        	}else bstST.put(word, 1);
        	
        }
        double putLoopBstST = bstSTStopwatch1.elapsedTime();
        
        
        Stopwatch binaryStStopwatch2 = new Stopwatch();
        for (String word : words) binaryST.get(word);
        double getLoopST = binaryStStopwatch2.elapsedTime();
        
        
        Stopwatch bstSTStopwatch2 = new Stopwatch();
        for (String word : words) bstST.get(word);
        double getLoopBstST = bstSTStopwatch2.elapsedTime();
        
        double binarySTTotalTime = putLoopST + getLoopST;
        double bstSTTotalTime = putLoopBstST + getLoopBstST;
        	
        StdOut.println("The total time for loops working with BinarySearchST: " + binarySTTotalTime);
        StdOut.println("The total time for loops working with BST: " + bstSTTotalTime);
        
	}
}
