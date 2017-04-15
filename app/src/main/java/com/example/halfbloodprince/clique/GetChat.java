package com.example.halfbloodprince.clique;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.halfbloodprince.clique.database.MyDataBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class GetChat extends AppCompatActivity {
    DatabaseReference chatDatabase;
    ArrayList<String> userTextLogs;
    private String TAG="GetChat";
    ListView userTextList;
    String username,date;
    ProgressDialog dialog;
    ArrayAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_chat);
        Bundle bundle=getIntent().getExtras();
        username=bundle.getString("user","U0000");
        date=bundle.getString("date","19-02-2017");
        chatDatabase = FirebaseDatabase.getInstance().getReference();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();
        userTextList= (ListView) findViewById(R.id.userTextsList);
        userTextLogs= new ArrayList<String>();
        chatDatabase.child("userTexts").child(username).child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> userTexts= dataSnapshot.getChildren();
                for (DataSnapshot userText1 : userTexts) {
                    UserTexts userText = userText1.getValue(UserTexts.class);
                    Log.d(TAG, "TextLog is: " + userText.userTextLog);
                    userTextLogs.add(userText.userTextLog);
                }
                mAdapter = new ArrayAdapter<String>(GetChat.this, android.R.layout.simple_list_item_1, userTextLogs);
                userTextList.setAdapter(mAdapter);
                dialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
                Toast.makeText(GetChat.this, "Textlog retrieval failed! Please check username and date.", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
