package ds2;

/*
 * Lillian hunt
 * 
 */

import stdlib.StdIn;
import stdlib.StdOut;

public class TestHT {
	
	
	public static void main(String[] args){ 

		MySeparateChainingHashST<String, Integer> st = new MySeparateChainingHashST<String, Integer>();
		
		while (!StdIn.isEmpty()) {
		String word = StdIn.readString();
		
		
			if (st.contains(word)) {
				st.put(word, st.get(word) + 1);
			}else {
				st.put(word, 1);
			}
			
			
		}
		double average = st.averageChainLength();
		StdOut.println("Average Chain Length: " + average);
		
	}
}
