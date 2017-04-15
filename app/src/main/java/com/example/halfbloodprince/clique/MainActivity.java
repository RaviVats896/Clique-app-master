package com.example.halfbloodprince.clique;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button submitButton,retrieveButton, getChats;
    String TAG = "Firebase";
    String username=null;
    String inputdate=null;
    EditText usernameInput, dateInput;
    private DatabaseReference mDatabase;
    AsyncTask whole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        submitButton = (Button) findViewById(R.id.submitButton);
        retrieveButton =(Button) findViewById(R.id.retrieveButton);
        usernameInput= (EditText) findViewById(R.id.usernameInput);
        dateInput= (EditText) findViewById(R.id.dateInput);
        getChats = (Button) findViewById(R.id.getChatButton);
        retrieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChangeUserName.class));
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=usernameInput.getText().toString().trim();
                inputdate=dateInput.getText().toString().trim();
                if(username.isEmpty()||inputdate.isEmpty()){
                    username="U0000";
                    inputdate="18-02-2017";
                }
                Intent i=new Intent(MainActivity.this, GraphActivity.class);
                i.putExtra("user",username);
                i.putExtra("date",inputdate);
                startActivity(i);
            }
        });
        getChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=usernameInput.getText().toString().trim();
                inputdate=dateInput.getText().toString().trim();
                if(username.isEmpty()||inputdate.isEmpty()){
                    username="U0000";
                    inputdate="19-02-2017";
                }
                Intent intent=new Intent(MainActivity.this, GetChat.class);
                intent.putExtra("user",username);
                intent.putExtra("date",inputdate);
                startActivity(intent);
            }
        });

    }

    private void writeNewUser(String userId, String dateId, String timeId, Double positive, Double negative, Double nuetral) {
        TimeStamp timeStamp = new TimeStamp(positive, negative, nuetral);
        mDatabase.child("users").child(userId).child(dateId).child(timeId).setValue(timeStamp);
    }


    private String getDate() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    Calendar calendar = Calendar.getInstance();
    return sdf.format(calendar.getTime());
}

private String getTime() {
    SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-ss", Locale.ENGLISH);
    Calendar calendar = Calendar.getInstance();
    return sdf.format(calendar.getTime());
}
}
