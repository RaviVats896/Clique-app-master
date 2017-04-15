package com.example.halfbloodprince.clique;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.halfbloodprince.clique.database.SharedP;

public class ChangeUserName extends AppCompatActivity {

    EditText changeUserNameTxt;
    Button clearUserNameButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_name);
        changeUserNameTxt =(EditText)findViewById(R.id.txtuser);
        clearUserNameButton= (Button) findViewById(R.id.clearButton);
        clearUserNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUserNameTxt.setText("");
                changeUserNameTxt.requestFocus();
            }
        });
    }
    public void changeDate(View v){
        SharedP.setMyID(this, changeUserNameTxt.getText().toString());
        Toast.makeText(this, "Username changed to: "+ changeUserNameTxt.getText() , Toast.LENGTH_SHORT).show();
    }
}
