package com.microsoft.CognitiveServicesExample.SpeechAnalysisLogic;

import java.util.Date;

/**
 * Created by Akshay on 2017-01-08.
 */

public class SpeechAnalysis {
    private int mPercentFiller;
    private int mPercentScore;
    private int mSpeed;
    private String mMostRepeated;
    private String mId;

    public SpeechAnalysis(int percFil, int percScore, int speed, String mostRep){
        mPercentFiller = percFil;
        mPercentScore = percScore;
        mSpeed = speed;
        mMostRepeated = mostRep;
        mId = getId(percScore);
    }

    public int getmPercentFiller() {
        return mPercentFiller;
    }

    public int getmPercentScore() {
        return mPercentScore;
    }

    public String getmMostRepeated() {
        return mMostRepeated;
    }

    public String getmId() {
        return mId;
    }

    public static String getId(int percScore){
        return ""+percScore+(new Date().getTime());
    }
}
