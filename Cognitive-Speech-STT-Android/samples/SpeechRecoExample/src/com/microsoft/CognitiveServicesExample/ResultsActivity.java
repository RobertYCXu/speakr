package com.microsoft.CognitiveServicesExample;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

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

        mRepeatedMap = (HashMap<String, Integer>) getIntent().getExtras().get(
                getString(R.string.results_intent_key_repeated_map));
        if (!mRepeatedMap.isEmpty()){
            Log.wtf("testing if map passed","DATA");
            for (String rep : mRepeatedMap.keySet()){
                Log.wtf("Repeated word", rep);
            }
        }
        mFiller = (FillerWords) intent.getExtras().get(getString(R.string.results_intent_key_filler));
        mAverageSpeed = intent.getExtras().getInt(getString(R.string.results_intent_key_speed));
        mScore = intent.getExtras().getInt(getString(R.string.results_intent_key_score));
    }
}
