package ds2;

import algs31.ArrayST;
import stdlib.StdIn;
import stdlib.StdOut;
/*Lillian Hunt
 * CSC 301 section 602
 */


public class DNAFrequency {
	
	
	
	public static void main(String[] args) {
		StdIn.fromFile("data/largesequences.txt");
		String[] a = StdIn.readAll().split("\\s+");
		ArrayST<String, Integer> st = new ArrayST<>();
		String species = "";
		
		
		st.put("A", 0);
		st.put("C", 0);
		st.put("G", 0);
		st.put("T", 0); 
		
		for (String string : a) {
			
			
			if (!string.equals(string.toUpperCase())) {
				species = string;
				System.out.println(species);
				st.put("A", 0);
				st.put("C", 0);
				st.put("G", 0);
				st.put("T", 0); 
				
			}else { 
				for (int i = 0; i < string.length(); i++) {
		           char ch = string.charAt(i);
		           if (ch == 'A') {
		        	   st.put("A", st.get("A")+1);
		        	   
		        	   
		           }
		           
		           else if (ch == 'C') {
		        	   st.put("C", st.get("C")+1);
		        	   
		           }
		           
		           else if (ch == 'G') {
		        	   st.put("G", st.get("G")+1);
		        	   
		           }
		           
		           else if (ch == 'T') {
		        	   st.put("T", st.get("T")+1);
		        	   
		           }
		           
		          
			 } int total = st.get("A") + st.get("C") + st.get("G") + st.get("T");
	           if (total > 0) {
                   double percentageA = ((double) st.get("A") / total) * 100;
                   double percentageC = ((double) st.get("C") / total) * 100;
                   double percentageG = ((double) st.get("G") / total) * 100;
                   double percentageT = ((double) st.get("T") / total) * 100;

                   StdOut.printf("Percentage of 'A': %.2f%%\n", percentageA);
                   StdOut.printf("Percentage of 'C': %.2f%%\n", percentageC);
                   StdOut.printf("Percentage of 'G': %.2f%%\n", percentageG);
                   StdOut.printf("Percentage of 'T': %.2f%%\n", percentageT);
	              
				
				}
			}
			
			
			
			
		}
		
	}
	
	
	
}
