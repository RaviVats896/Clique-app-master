package com.example.halfbloodprince.clique;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by halfbloodprince on 2/11/17.
 */

public class TimeStamp {
    public Double positive, negative, nuetral;
    //public String timeId;
    public TimeStamp(){

    }
    public TimeStamp(Double positive, Double negative, Double nuetral){
        this.positive = positive;
        this.negative = negative;
        this.nuetral = nuetral;
        //this.timeId=timeId;

    }
}
