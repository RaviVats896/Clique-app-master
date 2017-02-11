package com.example.halfbloodprince.clique;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class GraphActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String user, date;
    private String TAG="GraphActivty";
    private LineChart lineG;
    private ArrayList<TimeStamp> myArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        lineG=(LineChart)findViewById(R.id.main_graph_view);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        myArray=new ArrayList<TimeStamp>();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            user=bundle.getString("user","C000");
            date=bundle.getString("date","10-02-2017");
        }
        mDatabase.child("users").child(user).child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Iterable<DataSnapshot> times= dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator=times.iterator();
                while(iterator.hasNext()){
                    TimeStamp value=iterator.next().getValue(TimeStamp.class);
                    Log.d(TAG, "Value is: " + value.positive + " " + value.negative);
                    myArray.add(value);
                }

                refreshMyGraph();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    public void refreshMyGraph(){
        LineData lineData=new LineData(getXAxis(),getYAxis());
        Log.i(TAG,"2");
        lineG.setData(lineData);
        Log.i(TAG,"3");
        lineG.setDescription("Showing Info");
        Log.i(TAG,"4");
        lineG.invalidate();
        Log.i(TAG,"5");
        lineG.animateXY(2000, 2000);
        Log.i(TAG,"6");
    }


    private ArrayList<LineDataSet> getYAxis(){
        int colors[]={Color.GREEN,Color.BLUE,Color.RED,Color.YELLOW, Color.LTGRAY};
        String nameT[]={"Positive", "Neutral", "Negative"};
        //Log.i(TAG,"10");
        ArrayList<LineDataSet> lineDataSets=new ArrayList<LineDataSet>();
        //lineDataSets.add();
        Log.i(TAG,"11");
        try {
            for (int i=0; i < 3; i++) {
                //Log.i(TAG,"12");
                ArrayList<Entry> entryArrayList = new ArrayList<Entry>();
                for (int j = 0; j < myArray.size(); j++) {
                    if(i==0){
                        entryArrayList.add(new Entry(myArray.get(j).positive.floatValue(), j));
                    }else if(i==1){
                        entryArrayList.add(new Entry(myArray.get(j).nuetral.floatValue(), j));
                    }else{
                        entryArrayList.add(new Entry(myArray.get(j).negative.floatValue(), j));
                    }
                }
                Log.i(TAG,"13");
                LineDataSet lineDataSet = new LineDataSet(entryArrayList, nameT[i]);
                lineDataSet.setColor(colors[i]);
                lineDataSet.setDrawCircles(false);
                lineDataSets.add(lineDataSet);
                Log.i(TAG,"14");
            }
            Log.i(TAG,"15");
        }catch (Exception ex){
            Log.e(TAG,ex.getMessage());
        }
        //Log.i(TAG,"16");
        return lineDataSets;
    }

    private ArrayList<String> getXAxis(){
        ArrayList<String> arrayList=new ArrayList<String>();
        for(int i=0;i<myArray.size();i++){
            arrayList.add(i+"");
        }
        return arrayList;
    }
}
