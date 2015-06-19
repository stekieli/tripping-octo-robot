package com.letosfer.medulla;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by letosfer on 19.06.15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME ="MedullaDB";
    private static final int DB_VERSION = 1;
    //private static SQLiteDatabase sqldb;


    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    public String addUser(String name,String psw){
        String res = "-";

        try {
            SQLiteDatabase dbWrite = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("psw", psw);
            res = res + String.valueOf(dbWrite.insert("users", "null", values));
        }catch(Exception e){
            return "Exception: "+e.toString();
        }

        return res;
    }

    public boolean userExists(String username){

        SQLiteDatabase dbRead = this.getReadableDatabase();
        String[] projection = {"id","name","psw"};
        String[] selectArgs = {username};

        try {
            Cursor c = dbRead.rawQuery("select id,name,psw from users where name=?",selectArgs);
            if(c.moveToFirst()){
                return true;
            }else{
                return false;
            }
        }catch(Exception ex){
            String res = ex.toString();
            return false;
        }
    };

    public boolean saveUserData(String username,int score){
        try {
            SQLiteDatabase dbWrite = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("topA",score);
            String[] selectArgs = {username};

            dbWrite.update("users",values,"name =?",selectArgs);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public int readTopA(String username){
        SQLiteDatabase dbRead = this.getReadableDatabase();
        String[] selectArgs = {username};

        try {
            Cursor c = dbRead.rawQuery("select id,topA from users where name=?",selectArgs);
            if(c.moveToFirst()){
                int score = c.getInt(c.getColumnIndex("topA"));
                return score;
            }else{
                return 0;
            }
        }catch(Exception ex){
            String res = ex.toString();
            return 0;
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY, name VARCHAR(32), psw VARCHAR(32),topA INTEGER,topB INTEGER,topC INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }

}
