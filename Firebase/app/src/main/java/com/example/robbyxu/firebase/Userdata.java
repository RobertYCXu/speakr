package com.example.robbyxu.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Userdata extends AppCompatActivity {

    Button mbuttonStop;
    TextView mspeedData;
    TextView mfillerwordData;
    TextView mscoreData;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mspeedRef = mRootRef.child("speed");
    DatabaseReference mfillerwordRef = mRootRef.child("fillerword");
    DatabaseReference mscoreRef = mRootRef.child("score");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout.activity_userdata);

        mbuttonStop = (Button) findViewById(R.id.buttonStop);
        mspeedData = (TextView) findViewById(R.id.speedData);
        mfillerwordData = (TextView) findViewById(R.id.fillerwordData);
        mscoreData = (TextView) findViewById(R.id.scoreData);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mspeedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mspeedData.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mfillerwordRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mfillerwordData.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mscoreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mscoreData.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mbuttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mspeedRef.setValue("10");
                mfillerwordRef.setValue("10%");
                mscoreRef.setValue("10%");
            }
        });

    }


}
