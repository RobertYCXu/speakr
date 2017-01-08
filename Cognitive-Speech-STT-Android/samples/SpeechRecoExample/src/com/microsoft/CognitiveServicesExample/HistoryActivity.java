package com.microsoft.CognitiveServicesExample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microsoft.CognitiveServicesExample.SpeechAnalysisLogic.SpeechAnalysis;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends ActionBarActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    List<SpeechAnalysis> mHistoryList;
    HistoryAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mHistoryList = new ArrayList<>();
        mAdapter = new HistoryAdapter(this, mHistoryList);
        mRecyclerView = (RecyclerView)findViewById(R.id.history_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);


        //TODO: add progress circle
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<SpeechAnalysis> historyList = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    SpeechAnalysis speechAnalysis = new SpeechAnalysis(
                            snapshot.child(getString(R.string.results_intent_key_filler)).getValue(String.class),
                            snapshot.child(getString(R.string.results_intent_key_score)).getValue(String.class),
                            snapshot.child(getString(R.string.results_intent_key_speed)).getValue(String.class),
                            snapshot.child(getString(R.string.results_intent_most_rep)).getValue(String.class)
                            );
                    historyList.add(speechAnalysis);
                }
                Log.wtf("ID of first returned", ""+historyList.get(0).getmPercentScore());
                mHistoryList.addAll(historyList);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.animate()
                        .alpha(0.6f)
                        .setDuration(150)
                        .setInterpolator(new LinearInterpolator())
                        .start();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
