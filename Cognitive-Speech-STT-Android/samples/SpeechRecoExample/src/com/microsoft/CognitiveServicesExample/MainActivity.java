/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license.
 * //
 * Project Oxford: http://ProjectOxford.ai
 * //
 * ProjectOxford SDK GitHub:
 * https://github.com/Microsoft/ProjectOxford-ClientSDK
 * //
 * Copyright (c) Microsoft Corporation
 * All rights reserved.
 * //
 * MIT License:
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * //
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * //
 * THE SOFTWARE IS PROVIDED ""AS IS"", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.microsoft.CognitiveServicesExample;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.microsoft.CognitiveServicesExample.SpeechAnalysisLogic.FillerWords;
import com.microsoft.CognitiveServicesExample.SpeechAnalysisLogic.RepeatedWords;
import com.microsoft.CognitiveServicesExample.SpeechAnalysisLogic.ScoreLogic;
import com.microsoft.CognitiveServicesExample.SpeechAnalysisLogic.StringSpeed;
import com.microsoft.bing.speech.SpeechClientStatus;
import com.microsoft.cognitiveservices.speechrecognition.DataRecognitionClient;
import com.microsoft.cognitiveservices.speechrecognition.ISpeechRecognitionServerEvents;
import com.microsoft.cognitiveservices.speechrecognition.MicrophoneRecognitionClient;
import com.microsoft.cognitiveservices.speechrecognition.RecognitionResult;
import com.microsoft.cognitiveservices.speechrecognition.RecognitionStatus;
import com.microsoft.cognitiveservices.speechrecognition.SpeechRecognitionMode;
import com.microsoft.cognitiveservices.speechrecognition.SpeechRecognitionServiceFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity implements ISpeechRecognitionServerEvents
{
    private long mRecordingStartTime;

    int m_waitSeconds = 200;
    ArrayList<StringSpeed> mSpeedList = new ArrayList<>();
    DataRecognitionClient dataClient = null;
    MicrophoneRecognitionClient micClient = null;
    FinalResponseStatus isReceivedResponse = FinalResponseStatus.NotReceived;
    Button mRecordButton;
    Button mHistoryButton;
    Button mLogoutButton;
    View mDimView;
    Button mViewResults;
    Boolean mCurrentlyDimmed = false;

    public enum FinalResponseStatus { NotReceived, OK, Timeout }

    /**
     * Gets the primary subscription key
     */
    public String getPrimaryKey() {
        return this.getString(R.string.primaryKey);
    }

    /**
     * Gets the LUIS application identifier.
     * @return The LUIS application identifier.
     */
    private String getLuisAppId() {
        return this.getString(R.string.luisAppID);
    }

    /**
     * Gets the LUIS subscription identifier.
     * @return The LUIS subscription identifier.
     */
    private String getLuisSubscriptionID() {
        return this.getString(R.string.luisSubscriptionID);
    }

    /**
     * Gets a value indicating whether or not to use the microphone.
     * @return true if [use microphone]; otherwise, false.
     */
    private Boolean getUseMicrophone() {
        return true;
    }

    /**
     * Gets the current speech recognition mode.
     * @return The speech recognition mode.
     */
    private SpeechRecognitionMode getMode() {
        //ALWAYS RETURN LONG DICTATION
        return SpeechRecognitionMode.LongDictation;
    }

    /**
     * Gets the default locale.
     * @return The default locale.
     */
    private String getDefaultLocale() {
        return "en-us";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mRecordButton = (Button) findViewById(R.id.button1);
        this.mHistoryButton = (Button)findViewById(R.id.button_history);
        this.mDimView = findViewById(R.id.dim_view);
        this.mViewResults = (Button)findViewById(R.id.button_view_results);
        this.mLogoutButton = (Button)findViewById(R.id.button_logout);

        if (getString(R.string.primaryKey).startsWith("Please")) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.add_subscription_key_tip_title))
                    .setMessage(getString(R.string.add_subscription_key_tip))
                    .setCancelable(false)
                    .show();
        }

        // setup the buttons
        final MainActivity This = this;
        this.mRecordButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                This.StartButton_Click(arg0);
            }
        });

    }

    /**
     * Handles the Click event of the mRecordButton control.
     */
    private void StartButton_Click(View arg0) {
        this.mRecordButton.setEnabled(false);

        Log.wtf("Recog", "recog started");//this.LogRecognitionStart();
        if (mSpeedList != null && !mSpeedList.isEmpty()){
            mSpeedList.clear();
        }
        //dim screen
        animateRecord(1);


        if (this.getUseMicrophone()) {
            if (this.micClient == null) {
                {
                    this.micClient = SpeechRecognitionServiceFactory.createMicrophoneClient(
                            this,
                            this.getMode(),
                            this.getDefaultLocale(),
                            this,
                            this.getPrimaryKey());
                }
            }

            this.micClient.startMicAndRecognition();
        }
        else
        {
            if (null == this.dataClient) { {
                    this.dataClient = SpeechRecognitionServiceFactory.createDataClient(
                            this,
                            this.getMode(),
                            this.getDefaultLocale(),
                            this,
                            this.getPrimaryKey());
                }
            }
        }
    }

    private void animateRecord(int inputNum) {
        //input 1 if starting recording
        //input 0 if ending
        AnimatorSet animationSet = new AnimatorSet();
        List<Animator> animationList = new ArrayList<>();

        ObjectAnimator viewDimmer, recordDimmer, historyDimmer, logoutDimmer;
        if (inputNum == 1){
            mCurrentlyDimmed = true;
            //here
            viewDimmer = ObjectAnimator.ofFloat(mDimView, "alpha", 0, 0.8f);
            recordDimmer = ObjectAnimator.ofFloat(mRecordButton, "alpha", 1f, 0.2f);
            historyDimmer = ObjectAnimator.ofFloat(mHistoryButton, "alpha", 1f, 0.2f);
            logoutDimmer = ObjectAnimator.ofFloat(mLogoutButton, "alpha", 1f, 0.2f);
        } else {
            mCurrentlyDimmed = false;
            //here
            viewDimmer = ObjectAnimator.ofFloat(mDimView, "alpha", 0.8f, 0);
            recordDimmer = ObjectAnimator.ofFloat(mRecordButton, "alpha", 0.2f, 1);
            historyDimmer = ObjectAnimator.ofFloat(mHistoryButton, "alpha", 0.2f, 1);
            logoutDimmer = ObjectAnimator.ofFloat(mLogoutButton, "alpha", 0.2f, 1f);
        }
        viewDimmer.setDuration(100);
        viewDimmer.setInterpolator(new LinearInterpolator());
        animationList.add(viewDimmer);

        recordDimmer.setDuration(100);
        recordDimmer.setInterpolator(new LinearInterpolator());
        animationList.add(recordDimmer);

        historyDimmer.setDuration(100);
        historyDimmer.setInterpolator(new LinearInterpolator());
        animationList.add(historyDimmer);

        logoutDimmer.setDuration(100);
        logoutDimmer.setInterpolator(new LinearInterpolator());
        animationList.add(logoutDimmer);

        animationSet.playTogether(animationList);
        animationSet.start();
    }

    public void onFinalResponseReceived(final RecognitionResult response) {
        boolean isFinalDicationMessage = this.getMode() == SpeechRecognitionMode.LongDictation &&
                (response.RecognitionStatus == RecognitionStatus.EndOfDictation ||
                        response.RecognitionStatus == RecognitionStatus.DictationEndSilenceTimeout);
        if (null != this.micClient && this.getUseMicrophone() && ((this.getMode() == SpeechRecognitionMode.ShortPhrase) || isFinalDicationMessage)) {
            // we got the final result, so it we can end the mic reco.  No need to do this
            // for dataReco, since we already called endAudio() on it as soon as we were done
            // sending all the data.
            this.micClient.endMicAndRecognition();
        }

        if (isFinalDicationMessage) {
            micClient.endMicAndRecognition();
            this.mRecordButton.setEnabled(true);
            this.isReceivedResponse = FinalResponseStatus.OK;
        }

        if (!isFinalDicationMessage) {
            Log.wtf("FINAL RESULTS:","********* Final n-BEST Results *********");
            for (int i = 0; i < response.Results.length; i++) {
                Log.wtf("Result "+(i+1),"[" + i + "]" + " Confidence=" + response.Results[i].Confidence +
                        " Text=\"" + response.Results[i].DisplayText + "\"");
            }
            //    This is where the DataProcessed

            micClient.endMicAndRecognition();
            mRecordButton.setEnabled(true);

            if (response.Results.length > 0){
                //    instantiate the repeated word, filler word, and speed logic objects
                //TODO: PUBLISH TO UI
                //TODO: get live speed graph data (array)

                String finalPredictedString = response.Results[0].DisplayText;
                final int speed = StringSpeed.overallSpeed(
                        mRecordingStartTime, new Date().getTime(),
                        finalPredictedString);
                Log.wtf("Overall speed", ""+speed);
                final FillerWords fillerWords = new FillerWords(finalPredictedString);
                Log.wtf("filler percentage", ""+fillerWords.getPercent());
                final HashMap<String, Integer> map = RepeatedWords.retWordFreq(finalPredictedString);
                if (map.isEmpty()){
                    Log.wtf("NO repeated words", "no repeated data");
                }
                for (String repWord : map.keySet()){
                    Log.wtf("REP WORD DATA", repWord+": "+map.get(repWord));
                }

                final int score = ScoreLogic.getScore(finalPredictedString, map, fillerWords.getPercent(), speed);
                Log.wtf("FINAL SCORE", "Score is: "+score);

                mViewResults.setVisibility(View.VISIBLE);
                mViewResults.setEnabled(true);
                mViewResults.animate()
                        .alpha(1)
                        .setDuration(100)
                        .setInterpolator(new AccelerateInterpolator()).start();
                mViewResults.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO: GO TO RESULTS ACTIVITY
                        Log.wtf("Results selected", "go to results");

                        Intent resultsIntent = new Intent(MainActivity.this, ResultsActivity.class);
                        resultsIntent.putExtra(getString(R.string.results_intent_key_score), score);
                        resultsIntent.putExtra(getString(R.string.results_intent_key_speed), speed);
                        resultsIntent.putExtra(getString(R.string.results_intent_key_repeated_map), map);
                        resultsIntent.putExtra(getString(R.string.results_intent_key_filler), fillerWords);

                        startActivityForResult(resultsIntent, 0000);
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCurrentlyDimmed){
            onBackPressed();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Called when a final response is received and its intent is parsed
     */
    public void onIntentReceived(final String payload) {
        Log.wtf("Intent: ", "--- Intent received by onIntentReceived() ---");
    }

    public void onPartialResponseReceived(final String response) {
        Log.wtf("PARTIAL RESPONSE:", "--- Partial result received by onPartialResponseReceived() ---");
        Log.wtf("RESPONSE: ", response);


        //create new stringspeed object. add response and current time. add to list
        StringSpeed speed = new StringSpeed(response, new Date().getTime());
        Log.wtf("Speed Data Added", "String with time = "+speed.time);
        mSpeedList.add(speed);
    }

    public void onError(final int errorCode, final String response) {
        this.mRecordButton.setEnabled(true);
        Log.wtf("Error", "--- Error received by onError() ---");
        Log.wtf("Error", "Error code: " + SpeechClientStatus.fromInt(errorCode) + " " + errorCode);
        Snackbar.make(null, "Error: "+response, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Called when the microphone status has changed.
     * @param recording The current recording state
     */
    public void onAudioEvent(boolean recording) {
        Log.wtf("Recording triggered", "--- Microphone status change received by onAudioEvent() ---");
        Log.wtf("!!","********* Microphone status: " + recording + " *********");
        if (recording) {
            Log.wtf("NOTE","Please start speaking.");
            // do event! SAVE START TIME
            mRecordingStartTime = new Date().getTime();
        }

        if (!recording) {
            //ENDED!
            this.micClient.endMicAndRecognition();
            this.mRecordButton.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (mCurrentlyDimmed){
            mViewResults.setEnabled(false);
            mViewResults.setAlpha(0);
            animateRecord(0);
        } else {
            super.onBackPressed();
        }
    }
}
