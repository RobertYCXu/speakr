package com.microsoft.CognitiveServicesExample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //TODO: add progress circle
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<SpeechAnalysis> historyList = new ArrayList<>();
                
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
