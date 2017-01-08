package com.microsoft.CognitiveServicesExample;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import com.microsoft.CognitiveServicesExample.SpeechAnalysisLogic.FillerWords;

import java.util.HashMap;

public class ResultsActivity extends Activity {

    private FillerWords mFiller;
    private HashMap<String, Integer> mRepeatedMap;
    private int mAverageSpeed;
    private int mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        

    }

}
