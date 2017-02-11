package com.example.halfbloodprince.clique.database;

/**
 * Created by halfbloodprince on 2/11/17.
 */

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rsinha on 2/11/17.
 */

public class SharedP {
    public static SharedPreferences sp;

    public static SharedPreferences createSharedPref(Context cont){
        if(sp==null)
            sp=cont.getSharedPreferences("MyUserName",Context.MODE_PRIVATE);

        return sp;
    }
    public static String getMyID(Context cont){
        createSharedPref(cont);
        return sp.getString("MyID", "U0000");
    }
    public static void setMyID(Context cont,String id){
        createSharedPref(cont);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString("MyID", id);
        spe.apply();
    }

}
