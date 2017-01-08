import java.util.HashMap;
public class ScoreLogic{
  
  public static final int DESIRED_SPEED = 112;

  public static void main (String args[]){
    //TODO: RUN TEST CODE
    String s = "yes I agree yes";
    HashMap<String, Integer> map = new HashMap<>();
    //map.put("yes", 2);
    int percFil = 0;
    int oveSpeed = 110;
    System.out.println(getScore(s, map, percFil, oveSpeed));
  }

  public static int getScore(
      String finalString, 
      HashMap<String, Integer> repeatedWordMap, 
      int percentFillerWords, 
      int overallSpeed){

    int percentScore = 100;

    int speedError = overallSpeed - DESIRED_SPEED;
    if (speedError < 0) {
      speedError = -speedError;
    }

    //subtract HALF the speedError
    percentScore -= speedError/2;
    
    int numOfRepeatedWords = 0;
    for (Integer repetition : repeatedWordMap.values()){
      numOfRepeatedWords += repetition;
    }

    //subtract the % of repeated words (to total words)
    percentScore -= (100*numOfRepeatedWords)/(countWords(finalString));

    //subtract % filler words (to total words)
    percentScore -= percentFillerWords;

    return percentScore;
    
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
