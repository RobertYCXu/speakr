package com.microsoft.CognitiveServicesExample.SpeechAnalysisLogic;

import java.io.Serializable;

/**
 * Created by Akshay on 2017-01-07.
 */

public class FillerWords implements Serializable {
    private int numUm = 0;
    private int numLike = 0;
    private int numSo = 0;
    private int numOk = 0;
    private int numBasically = 0;
    private int numYea = 0;
    private int percent = 0;

    public FillerWords(String text){
        text = text.toLowerCase();
        text = text.replace('.',' ');
        text = text.replace(',',' ');

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

    public int getPercent() {
        return percent;
    }
}
