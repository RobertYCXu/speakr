package com.microsoft.CognitiveServicesExample.SpeechAnalysisLogic;

/**
 * Created by Akshay on 2017-01-07.
 */

public class StringSpeed {
    public String s;
    public long time;

    public StringSpeed (String s, Long time) {
        this.s = s;
        this.time = time;
    }

    // time is in milliseconds
    public static int overallSpeed(long start, long end, String speech) {

        double totalTimeMilliseconds  = (double) (end - start);
        double totalTimeMinutes = totalTimeMilliseconds * 0.001 /60;
        double nWords = countWords(speech);

        return (int)(nWords/ totalTimeMinutes);
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
