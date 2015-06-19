package com.letosfer.medulla;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {

    private static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void sendLogin(View v){
        EditText editText = (EditText) findViewById(R.id.editText4);
        username = String.valueOf(editText.getText());
        EditText editText2 = (EditText) findViewById(R.id.editText5);
        String psw = String.valueOf(editText2.getText());
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
        if(dbHelper.userExists(username)) {
            String[] projection = {"id", "name", "psw"};
            String[] selectArgs = {username, psw};

            String result = "";
            String respsw = "";
            try {
                Cursor c = dbRead.rawQuery("select id,name,psw from users where name=? and psw=?", selectArgs);
                c.moveToFirst();
                result = c.getString(c.getColumnIndex("id"));
                respsw = c.getString(c.getColumnIndex("psw"));
                result = result + ":" + respsw;
            } catch (Exception ex) {
                String res = ex.toString();
                Toast.makeText(this, res, Toast.LENGTH_LONG).show();
            }

            if (psw.equals(respsw)) {
                intermission();
            } else {
                Toast.makeText(this, "Usuccesfull Login. Try Again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User does not Exist. Try Again", Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(this, "Login result" + result, Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
