package com.microsoft.CognitiveServicesExample;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.microsoft.CognitiveServicesExample.SpeechAnalysisLogic.FillerWords;

import java.util.HashMap;

public class ResultsActivity extends Activity {

    private FillerWords mFiller;
    private HashMap<String, Integer> mRepeatedMap;
    private int mAverageSpeed;
    private int mScore;

    private TextView mMostRepeatedTV, mScoreTV, mAvgSpeedTV, mFillerTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();

        mRepeatedMap = (HashMap<String, Integer>) getIntent().getExtras().get(
                getString(R.string.results_intent_key_repeated_map));

        LinearLayout repeatedLayout = (LinearLayout)findViewById(R.id.activity_results_repeated_layout);
        mMostRepeatedTV = (TextView)findViewById(R.id.results_repeated_textview);
        mScoreTV = (TextView)findViewById(R.id.results_score_textview);
        mAvgSpeedTV = (TextView)findViewById(R.id.results_avg_speed_textview);
        mFillerTV = (TextView)findViewById(R.id.results_filler_textview);

        if (!mRepeatedMap.isEmpty()){
            Log.wtf("testing if map passed","DATA");
            for (String rep : mRepeatedMap.keySet()){
                Log.wtf("Repeated word", rep);
            }

            String val = "";
            int maxVar = 0;
            for (String repWord : mRepeatedMap.keySet()){
                if(mRepeatedMap.get(repWord) > maxVar){
                    maxVar = mRepeatedMap.get(repWord);
                    val = repWord;
                }
            }
            mMostRepeatedTV.setText(val);

        } else {
            repeatedLayout.setVisibility(View.GONE);
        }

        mFiller = (FillerWords) intent.getExtras().get(getString(R.string.results_intent_key_filler));
        mAverageSpeed = intent.getExtras().getInt(getString(R.string.results_intent_key_speed));
        mScore = intent.getExtras().getInt(getString(R.string.results_intent_key_score));

        mFillerTV.setText(""+mFiller.getPercent()+"%");
        mAvgSpeedTV.setText(mAverageSpeed+" WPM");
        mScoreTV.setText(""+mScore+"%");
    }
}
