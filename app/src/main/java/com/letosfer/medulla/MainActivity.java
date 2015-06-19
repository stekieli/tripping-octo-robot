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


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences mySP = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        Boolean isAuthorized = mySP.getBoolean("isAuthorized",false);
        String username = mySP.getString("username","guest");
        if(isAuthorized==true){
            startActivity(new Intent(this,SecureDataActivity.class));
            Toast.makeText(this,"Welcome back " + username,Toast.LENGTH_SHORT).show();
        }
    }

    public void goRegister(View v){
        startActivity(new Intent(this,RegisterActivity.class));
    }

    public void goLogin(View v){
        startActivity(new Intent(this,LoginActivity.class));
    }

    public void goGuest(View v){
        startActivity(new Intent(this,SecureDataActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
