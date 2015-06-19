package com.letosfer.medulla;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MenuActivity extends Activity {

    private static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intMain = getIntent();
        username = intMain.getStringExtra("username");
        //Toast.makeText(this, username, Toast.LENGTH_LONG).show();
        /*
        SharedPreferences mySP = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor eSP = mySP.edit();
        eSP.putBoolean("isAuthorized",false);
        eSP.putString("username","guest");
        eSP.commit();
        //*/

        /*
        String result = "";
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        result = result + String.valueOf(dbHelper.saveUserData(username,10));
        result = result + String.valueOf(dbHelper.readTopA(username));

        Toast.makeText(this,username + "'s TopA result:" + result,Toast.LENGTH_SHORT).show();
        //*/
    }

    public void goPlay(View v){
        Intent menuInt = new Intent(this,PlayActivity.class);
        String varName="username";
        String varValue=username;
        menuInt.putExtra(varName,varValue);
        startActivity(menuInt);
    }

    public void goHiScore(View v){
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        String result = "error";
        try {
            result = String.valueOf(dbHelper.readTopA(username));
        }catch(Exception e){
            result = e.toString();
        }
        Toast.makeText(this,"Your Highest Score is: "+result,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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
        if (id == R.id.action_logout) {
            SharedPreferences mySP = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor eSP = mySP.edit();
            eSP.putBoolean("isAuthorized",false);
            eSP.putString("username","guest");
            eSP.commit();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
