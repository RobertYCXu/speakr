package fillerWords;

public class fillerWords{
	
	private int numUm = 0;
	private int numLike = 0;
	private int numSo = 0;
	private int numOk = 0;
	private int numBasically = 0;
	private int numYea = 0;
	private int percent = 0;
	
	fillerWords(String text){
		numUm = countFill("um", text);
		numLike = countFill("like", text);
		numSo = countFill("so", text);
		numOk = countFill("ok", text);
		numBasically = countFill("basically", text);
		numYea = countFill("yea", text);
		
		String trimmed = text.trim();
		int words = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
		if(words == 0) percent = 0;
		else percent = (int)(returnTot()/words *100);
	}
	int countFill(String word, String text){
		int index = 0;
		int count = 0;
		while(index != -1){

		    index = text.indexOf(word,index);

		    if(index != -1){
		        count ++;
		        index += word.length();
		    }
		}
		return count;
	}
	double returnTot(){
		return numUm + numLike + numSo + numOk + numBasically + numYea;
	}
	
	public static void main(String[] args){
		fillerWords test = new fillerWords("um um um hello hello like");
		System.out.println(test.numLike);
		System.out.println(test.percent);
	}
}
