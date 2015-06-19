package com.letosfer.medulla;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends Activity {

    private static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void sendRegister(View v){
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
            EditText editText = (EditText) findViewById(R.id.editText);
            EditText editText2 = (EditText) findViewById(R.id.editText2);
            EditText editText3 = (EditText) findViewById(R.id.editText3);
            username = String.valueOf(editText.getText());
            String psw1 = String.valueOf(editText2.getText());
            String psw2 = String.valueOf(editText3.getText());
            if(!dbHelper.userExists(username)) {
                if (psw1.equals(psw2)) {
                    String res = dbHelper.addUser(username, psw1);
                    Toast.makeText(this, "Add User Result: " + res, Toast.LENGTH_SHORT).show();
                    intermission();
                } else {
                    Toast.makeText(this, "Passwords don't much. Try again", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "User Exists. Try Again", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void intermission(){
        SharedPreferences mySP = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor eSP = mySP.edit();
        eSP.putBoolean("isAuthorized",true);
        eSP.putString("username",username);
        eSP.commit();

        Intent menuInt = new Intent(this,MenuActivity.class);
        String varName="username";
        String varValue=username;
        menuInt.putExtra(varName,varValue);
        startActivity(menuInt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
