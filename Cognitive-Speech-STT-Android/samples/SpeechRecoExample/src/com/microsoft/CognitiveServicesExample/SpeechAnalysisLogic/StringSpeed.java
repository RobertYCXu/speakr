package com.microsoft.CognitiveServicesExample.SpeechAnalysisLogic;

import java.util.ArrayList;

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

    public static ArrayList<Integer> getTimeData (ArrayList<StringSpeed> input) {
        // handle if input is empty
        if (input.size() == 0) {
            return new ArrayList<Integer>();
        }

        // var: total time in milliseconds
        long totalTime = (long) (input.get(input.size() - 1).time - input.get(0).time);

        // ver: time per interval
        int timePerDiv = 3000;
        // var: number of intervals to analyze
        long nDiv = totalTime / 2000 + 1; // 3 seconds

        // removes corrections generated
        // kinda bad because considers the first word recognised as the right one and not the last
        // not too bad tho because corrections have approx same number of characters
        for (int i = 0; i <= input.size() - 1; i++) {
            while (i != input.size()-1 && countWords(input.get(i + 1).s) <= countWords(input.get(i).s)) {
                input.remove(i + 1);
            }
            input.set(i, new StringSpeed(input.get(i).s.replaceAll("\\s+",""), input.get(i).time)); //removes spaces and periods
        }

        // characters in each interval is 0 before analyzing;
        ArrayList<Integer> charInInterval = new ArrayList<Integer>();
        for (int i = 0; i < nDiv; i++) {
            charInInterval.add(0);
        }

        String lastCountedString = "";
        for (int i = 0; i < input.size(); i++) {
            if (!input.get(i).s.equals("")) {
                charInInterval.set((int)(input.get(i).time - input.get(0).time)/timePerDiv, charInInterval.get((int)(input.get(i).time - input.get(0).time)/timePerDiv) + (input.get(i).s.length() - lastCountedString.length()));
                lastCountedString = input.get(i).s;
            }
        }

        return charInInterval;
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
