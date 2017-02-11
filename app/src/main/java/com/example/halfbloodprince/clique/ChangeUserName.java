package com.example.halfbloodprince.clique;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.halfbloodprince.clique.database.SharedP;

public class ChangeUserName extends AppCompatActivity {

    EditText txtV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_name);

        txtV=(EditText)findViewById(R.id.txtuser);
    }
    public void changedate(View v){
        SharedP.setMyID(this, txtV.getText().toString());
    }
}
