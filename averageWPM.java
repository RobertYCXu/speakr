import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {		
		System.out.println(overallTime(0, 1000, "wow this is a really cool string"));
	}
	
//	// sees changes in string and 
//	public ArrayList<Long> getTimeData (ArrayList<StringSpeed> input) { 
//		
//		
//		
//		
//		ArrayList<Long> data = ;
//		
//		
//
//		return data;
//	}
	
	// time is in milliseconds
	public static double overallTime(long start, long end, String speech) {
		
		int totalWPM = 0;
		
		double totalTimeMilliseconds  = (double) (end - start);
		double totalTimeSeconds = totalTimeMilliseconds * 0.001 /60;
		double nWords = countWords(speech);
		
		return nWords/ totalTimeSeconds;
	}
	
	public static int countWords(String s){

	    int wordCount = 0;

	    boolean word = false;
	    int endOfLine = s.length() - 1;

	    for (int i = 0; i < s.length(); i++) {
	        // if the char is a letter, word = true.
	        if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
	            word = true;
	            // if char isn't a letter and there have been letters before,
	            // counter goes up.
	        } else if (!Character.isLetter(s.charAt(i)) && word) {
	            wordCount++;
	            word = false;
	            // last word of String; if it doesn't end with a non letter, it
	            // wouldn't count without this.
	        } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
	            wordCount++;
	        }
	    }
	    return wordCount;
	}

}
