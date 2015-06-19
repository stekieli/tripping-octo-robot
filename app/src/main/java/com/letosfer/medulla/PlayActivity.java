package com.letosfer.medulla;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;


public class PlayActivity extends Activity {

    private static String username;
    private static Button button1;
    private static Button button2;
    private static Button button3;
    private static TextView textView;

    private static int rand1;
    private static int rand2;
    private static int rand3;

    private static float maximum ;
    private static int clicks ;

    private static int preValue ;
    private static int posValue ;

    private static ArrayList<Integer> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intMain = getIntent();
        username = intMain.getStringExtra("username");
        button1 = (Button)findViewById(R.id.button3);
        button2 = (Button)findViewById(R.id.button4);
        button3 = (Button)findViewById(R.id.button5);
        textView = (TextView)findViewById(R.id.textView12);
        records = new ArrayList<Integer>();
        maximum = 100.00f;
        clicks = 0;

        preValue = -1;
        posValue = -1;
        setValues();
    }

    private void setValues(){
        rand1 = new Random().nextInt(10);
        rand2 = new Random().nextInt(10);
        rand3 = new Random().nextInt(10);
        textView.setText(String.valueOf(((float)(clicks/maximum))));

        button1.setText(String.valueOf(rand1));
        button2.setText(String.valueOf(rand2));
        button3.setText(String.valueOf(rand3));
    }

    public void goClick(View v){

        int itoRecord = 0;
        String value = "0";
        try {
            value = String.valueOf(((Button) v).getText());
            //Toast.makeText(this, value, Toast.LENGTH_SHORT).show();

            preValue = posValue;
            posValue = Integer.valueOf(value);
            String toRecord = String.valueOf(preValue) + String.valueOf(posValue);
            itoRecord = Integer.valueOf(toRecord);
        }catch(Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_LONG).show();
        }

        //*
        if(records.contains(itoRecord)){
            //*
            DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

            int readValue = dbHelper.readTopA(username);
            if(readValue<itoRecord){
                String.valueOf(dbHelper.saveUserData(username,clicks));
                Toast.makeText(this,"Your score has been recorded"+readValue+":"+clicks,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Your score was LAME!",Toast.LENGTH_SHORT).show();
            }

            finish();

            //Toast.makeText(this,"YYou Failed",Toast.LENGTH_LONG).show();
        }else {
            records.add(itoRecord);
            clicks++;
            setValues();
            //Toast.makeText(this,value,Toast.LENGTH_SHORT).show();
        }
        //*/
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
