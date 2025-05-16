package ds2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class FiveLetterWords {

	public static String[] readAllStrings(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            return new String[0];
        }

  
        String text = scanner.useDelimiter("\\A").next();
        String[] allStrings = text.split("\\s+");
        return allStrings;
    }
	
   
	public static void main(String[] args) {
        String[] words = null;
        String fileName = "data/tale.txt"; 

        try {
            Scanner input = new Scanner(new File(fileName));
            words = readAllStrings(input);
     
        } catch (FileNotFoundException e) {
            System.out.println("Error: The file " + fileName + " was not found.");
        } catch (Exception e) {
            System.out.println("Error reading from file.");
            return;
        }
        
     
        int counter = 0;
        ArrayList<String> fiveLetterWords = new ArrayList<>();
        
        System.out.println("Lillian Hunt ***");
        System.out.println("The number of words are " + words.length);
        
        
        for (String word : words) {
        	if (word.length() == 5) {
        		fiveLetterWords.add(word);
        		counter++;
        		System.out.println("five letter word: " + word);
            }
        }
        
        System.out.println("number of five letter words: "+ fiveLetterWords.size());
        for (String word : words) {
        	if (word.length() == 5) {
        		String lowerWord = word.toLowerCase();
        		if (lowerWord.startsWith("a") || lowerWord.startsWith("e") || lowerWord.startsWith("i") || lowerWord.startsWith("o") || lowerWord.startsWith("u")) {
        			System.out.println("Five Letter words starting with a vowel: " + word);
        		}
        	}
        }
        
        String firstFiveLetterWord = "z";
        for (String word : words) {
        	if (word.length() == 5) {
        		if(word.compareToIgnoreCase(firstFiveLetterWord) < 0) {
        			firstFiveLetterWord = word;
        		}
        	}
        }
       
	
        System.out.println("Alphabetically first word with five Letters: " + firstFiveLetterWord);
        
        String lastFiveLetterWord = "a"; 

        for (String word : words) {
            if (word.length() == 5) {
                if (word.compareToIgnoreCase(lastFiveLetterWord) > 0) {
                    lastFiveLetterWord = word;
                }
            }
        }

        System.out.println("Alphabetically last word with five Letters: " + lastFiveLetterWord);
	}
}
        	