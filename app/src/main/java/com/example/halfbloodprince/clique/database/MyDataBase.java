package com.example.halfbloodprince.clique.database;

/**
 * Created by halfbloodprince on 2/11/17.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by rsinha on 2/11/17.
 */

public class MyDataBase {
    public static SQLiteDatabase sdb;
    public static String T="MyDatabase";

    public static SQLiteDatabase initiate(Context cont){
        if(sdb==null)
            sdb=cont.openOrCreateDatabase("SalesApp",Context.MODE_PRIVATE,null);
        return sdb;
    }
    public static ArrayList<String> getAllTables(Context context){
        initiate(context);
        ArrayList<String> arrayList=new ArrayList<String>();
        String sql="SELECT name FROM sqlite_master WHERE type='table'";
        Cursor cursor=sdb.rawQuery(sql, null);
        if(cursor!=null&& cursor.moveToFirst()){
            do{
                String name=cursor.getString(0);
                if(!name.equals("DATA") && !name.equals("android_metadata"))
                    arrayList.add(name);
            }while(cursor.moveToNext());
        }
        return arrayList;
    }


    public static void createTable(Context cont){
        initiate(cont);
        String table=SharedP.getMyID(cont);
        try {
            String sql = "CREATE TABLE IF NOT EXISTS `"+table+"`(Data varchar)";
            sdb.execSQL(sql);
        }catch(Exception ex){
            Log.i(T, ex.getMessage());
        }
    }

    public static void insert(Context cont, String text){
        createTable(cont);
        String table=SharedP.getMyID(cont);
        try{
            String sql="INSERT INTO `"+table+"` VALUES('"+text+"')";
            sdb.execSQL(sql);
            Log.i(T, "It reached here . 11 :"+text);
            if(getTableSize(cont)>=2)
                getData(cont);
        }catch(Exception ex){
            Log.i(T, ex.getMessage());
        }
    }
    public static int getTableSize(Context cont){
        createTable(cont);
        String table=SharedP.getMyID(cont);
        try{
            String sql="SELECT COUNT(Data) FROM `"+table+"`";
            Cursor cursor = sdb.rawQuery(sql,null);

            if(cursor!=null && cursor.moveToFirst()){
                do{
                    return Integer.parseInt(cursor.getString(0));
                }while(cursor.moveToNext());
            }
        }catch(Exception ex){
            Log.i(T, ex.getMessage());
        }
        return 0;
    }
    public static void deleteAll(Context cont){
        createTable(cont);
        String table=SharedP.getMyID(cont);
        try{
            String sql="DELETE FROM `"+table+"`";
            sdb.execSQL(sql);
        }catch(Exception ex){
            Log.i(T, ex.getMessage());
        }
    }
    public static void getData(Context context){
        createTable(context);
        String table=SharedP.getMyID(context);

        try{
            String sql="SELECT * FROM `"+table+"`";
            Cursor cursor = sdb.rawQuery(sql,null);
            StringBuffer sb=new StringBuffer();
            if(cursor!=null && cursor.moveToFirst()){
                do{
                    sb.append(cursor.getString(0)+" ");
                }while(cursor.moveToNext());
            }
            Log.i(T, "It reached here . 22 :"+sb.toString());
            MyDataUnit mdb=new MyDataUnit(sb.toString(), context);
        }catch(Exception ex){
            Log.i(T, ex.getMessage());
        }
        deleteAll(context);
    }

    public static String getDataString(Context context){
        createTable(context);
        String table=SharedP.getMyID(context);

        try{
            String sql="SELECT * FROM `"+table+"`";
            Cursor cursor = sdb.rawQuery(sql,null);
            StringBuffer sb=new StringBuffer();
            if(cursor!=null && cursor.moveToFirst()){
                do{
                    sb.append(cursor.getString(0)+" ");
                }while(cursor.moveToNext());
            }
            Log.i(T, "It reached here . 22 :"+sb.toString());
            //MyDataUnit mdb=new MyDataUnit(sb.toString(), context);
            return sb.toString();
        }catch(Exception ex){
            Log.i(T, ex.getMessage());
        }
        //deleteAll(context);
        return "";
    }

}
