package com.microsoft.CognitiveServicesExample.SpeechAnalysisLogic;

import java.util.HashMap;

/**
 * Created by Akshay on 2017-01-07.
 */

public class ScoreLogic {

    public static final int DESIRED_SPEED = 112;

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
        if (StringSpeed.countWords(finalString) > 0){
            percentScore -= (100*numOfRepeatedWords)/(StringSpeed.countWords(finalString));
        }

        //subtract % filler words (to total words)
        percentScore -= percentFillerWords;

        return percentScore;

    }
}
