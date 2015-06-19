package com.letosfer.medulla;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class SecureDataActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure_data);
        SharedPreferences mySP = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        //Boolean isAuthorized = mySP.getBoolean("isAuthorized",false);
        String username = mySP.getString("username","guest");
        SharedPreferences.Editor eSP = mySP.edit();
        if(username.equals("guest")){
            eSP.putBoolean("isAuthorized",false);
        }else{
            eSP.putBoolean("isAuthorized",true);
        }
        eSP.commit();

        Intent menuInt = new Intent(this,MenuActivity.class);
        String varName="username";
        String varValue=username;
        menuInt.putExtra(varName,varValue);
        startActivity(menuInt);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_secure_data, menu);
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
