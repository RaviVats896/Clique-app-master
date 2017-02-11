package com.example.halfbloodprince.clique.database;

/**
 * Created by halfbloodprince on 2/11/17.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

//import com.aylien.textapi.TextAPIClient;
//import com.aylien.textapi.parameters.SentimentParams;
//import com.aylien.textapi.responses.Sentiment;
//import com.inithack.rsinha.tellyoursentiments.MainActivity;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONArray;

import com.example.halfbloodprince.clique.MainActivity;
import com.example.halfbloodprince.clique.TimeStamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by rsinha on 2/11/17.
 */

public class MyDataUnit {
    private String text;
    private double positive, negative, neutral;
    private ArrayList<Emotions> emotions;
    private String TAG = "MyDatabase";
    private Context context;
    private DatabaseReference mDatabase;

    public MyDataUnit(String text, Context cont) {
        emotions = new ArrayList<Emotions>();
        this.text = text;
        context = cont;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getSentiments();
    }

    public void getSentiments() {
        new GetData(true, context).execute();
    }

    public void getEmotions() {

    }

    public void updateOnFireBase() {

    }

    public void getEmotionsID(String s) {

    }

    public class Emotions {
        String emotions;
        int value;
    }

    public class GetData extends AsyncTask<Void, Void, Void> {
        private String link = "https://api.theysay.io/v1/";
        private Context cont;

        public GetData(boolean sentiment, Context context) {
            Log.i(TAG, "It got in initialisation");
            cont = context;
            if (sentiment)
                link = link + "sentiment";
            else
                link = link + "emotion";
            Log.i(TAG, "It got initialised");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
//                Log.i(TAG, "Backgroud Work");
//                HttpClient httpClient = new DefaultHttpClient();
//                HttpPost httpGet = new HttpPost(link);
//                List<NameValuePair> ln = new ArrayList<NameValuePair>();
//                ln.add(new BasicNameValuePair("text", text));
//                ln.add(new BasicNameValuePair("level", "sentence"));
//                httpGet.setEntity(new UrlEncodedFormEntity(ln));
//                //Log.i("Check", "Connecting...");
//                HttpResponse httpResponse = httpClient.execute(httpGet);
//                //publishProgress("Entity Creating...");
//                //Log.i("Check","Entity Creating..." );
//                HttpEntity httpEntity = httpResponse.getEntity();
//
//                //Log.i("Check","1st Place");
//                String para = EntityUtils.toString(httpEntity);
//                Log.i(TAG, "Data got : "+para);


//                Log.i(TAG,"Here 223 : "+text);
//                TextAPIClient client = new TextAPIClient("2ba72280", "15637380186be8e356ff0b7e564e5ad2");
//                SentimentParams.Builder builder = SentimentParams.newBuilder();
//                builder.setText(text);
//                Log.i(TAG,"Here 224 ");
//                Sentiment sentiment = client.sentiment(builder.build());
//                Log.i(TAG,"Here 225 ");
//                Log.i(TAG,sentiment.toString());

                String list1[]= text.split(" ");
                for (String aList1 : list1) {
                    switch (aList1) {
                        case "happy":
                        case "good":
                        case "positive":
                        case "qualified":
                            positive += 0.2;
                            negative -= 0.2;
                            neutral -= 0.2;
                            break;
                        case "awesome":
                        case "exited":
                        case "love":
                        case "won":
                            positive += 0.4;
                            negative -= 0.4;
                            neutral -= 0.4;
                            break;
                        case "frustrated":
                        case "depressed":
                        case "fight":
                        case "lost":
                        case "fought":
                        case "failed":
                            positive -= 0.4;
                            negative += 0.4;
                            neutral -= 0.4;
                            break;
                        case "sad":
                        case "lonely":
                        case "bad":
                        case "negative":
                            positive -= 0.2;
                            negative += 0.2;
                            neutral -= 0.2;
                            break;
                        case "satisfied":
                        case "ok":
                            positive -= 0.2;
                            negative -= 0.2;
                            neutral += 0.5;
                            break;
                        case "screwed":
                        case "angry":
                            positive -= 0.3;
                            negative += 0.3;
                            neutral -= 0.3;
                            break;
                    }

                }
                if(positive<0.0){
                    positive=0.0;
                };
                if(negative<0.0){
                    negative=0.0;
                };
                if(neutral<0.0){
                    neutral=0.0;
                };
                writeNewUser(SharedP.getMyID(cont),positive,negative,neutral);
            } catch (Exception e) {
                Log.i(TAG,"Here 226 "+e.getMessage());
            }
            //Log.i("Check",para);
            //JSONArray jsonArray = new JSONArray(para);
            Log.i(TAG, "Done");
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
    private void callFromServer(){
        //                Log.i(TAG,"Here 223 : "+text);
//                TextAPIClient client = new TextAPIClient("2ba72280", "15637380186be8e356ff0b7e564e5ad2");
//                SentimentParams.Builder builder = SentimentParams.newBuilder();
//                builder.setText(text);
//                Log.i(TAG,"Here 224 ");
//                Sentiment sentiment = client.sentiment(builder.build());
//                Log.i(TAG,"Here 225 ");
//                Log.i(TAG,sentiment.toString());

    }
    private void writeNewUser(String userId, Double positive, Double negative, Double nuetral) {
        TimeStamp timeStamp = new TimeStamp(positive, negative, nuetral);
        mDatabase.child("users").child(userId).child(getDate()).child(getTime()).setValue(timeStamp);
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
