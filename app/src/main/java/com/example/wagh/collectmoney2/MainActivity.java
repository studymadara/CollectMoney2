package com.example.wagh.collectmoney2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {


   TextView tv1;
    EditText et1;
    String s1;
    Button b1,b2;
    Float collect,collect2=0.0f;


    SQLiteDatabase db= null;
    String tablename1="MONEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /** FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Learning This thing", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });**/

        tv1=(TextView)findViewById(R.id.txt3);
        et1=(EditText)findViewById(R.id.et1);
        b1=(Button)findViewById(R.id.b1);
        b2=(Button)findViewById(R.id.b2);

        b1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                s1=et1.getText().toString();
                collect = Float.parseFloat(s1);
                collect2=collect+collect2;
                tv1.setText(collect2.toString());

                databaseupdate(collect);
                et1.setText("");

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databasedelete();
            }
        });

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


       public void databaseupdate(Float collect)
       {
            try
            {


                db=this.openOrCreateDatabase("CollectMoney",MODE_PRIVATE,null);

                db.execSQL("CREATE TABLE IF NOT EXISTS "+ tablename1 + "(Amount FLOAT)");

                db.execSQL("INSERT INTO "+ tablename1 + "(Amount)"+ "VALUES ("+collect+");");


            }
            catch (Exception e)
            {
                Log.e("Error", "database: ERROR", e);
            }
           finally {
                db.close();
            }
       }


    public void databasedelete()
    {
        try
        {
            db=this.openOrCreateDatabase("CollectMoney",MODE_PRIVATE,null);

            db.execSQL("DELETE FROM " + tablename1 + ";");

            et1.setText("");
            tv1.setText("");
            s1=null;
            collect=0.0f;
            collect2=0.0f;
        }
        catch (Exception e)
        {
            Log.e("DELETE ERROR","CHECK DELETE DATABASE",e);
        }
        finally {
            db.close();
        }
    }




    public void onResume()
    {
        super.onResume();

        collect =0.0f;

        collect2=0.0f;

    //    public void databasefetch()  ********************************************DATABASE FETCH**************************************
   // {
        try
        {
            db=MainActivity.this.openOrCreateDatabase("CollectMoney",MODE_PRIVATE,null);

            Cursor c =db.rawQuery("SELECT Amount FROM " + tablename1, null);

            int i = c.getColumnIndex("Amount");

            c.moveToFirst();

            if(c!=null) {
                do {

                    collect2 = c.getFloat(i);
                    collect = collect + collect2;

                }
                while (c.moveToNext());


                Toast.makeText(MainActivity.this, "END FETCHING", Toast.LENGTH_LONG).show();

                tv1.setText(collect.toString());
            }
        }
        catch (Exception e)
        {
            Log.e("FETCHING ERROR", "ERROR FETCHING",e);
        }
        finally {
            db.close();
        }
    }

    //}



};