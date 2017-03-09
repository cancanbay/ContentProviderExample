package com.example.canbay.bim494_homework1;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.*;

import Model.Person;

/**
 * Created by canbay on 5.03.2017.
 */

public class NewUserActivity extends AppCompatActivity {
    Button btnCancel;
    Button btnSave;
    EditText etName;
    EditText etSurname;
    EditText etDescription;
    static FileOutputStream fos = null;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/text/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("Add New User");
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_action));
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSave = (Button) findViewById(R.id.btnSave);
        etName = (EditText) findViewById(R.id.name);
        etSurname = (EditText) findViewById(R.id.surname);
        etDescription = (EditText) findViewById(R.id.description);
        File directory = new File(path);
        directory.mkdirs();
        if(!preferences.getString("name","N/A").equals("N/A")){
            etName.setText(preferences.getString("name","N/A"));
            etSurname.setText(preferences.getString("surname","N/A"));
            etDescription.setText(preferences.getString("description","N/A"));
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                etName.getText();
                File file = new File(path +"file.txt");
                String saveText[] = String.valueOf(etName.getText() +"\n"+ etSurname.getText() +"\n"+ etDescription.getText()+"\n").split(System.getProperty("line.separator"));
                Save(file,saveText);
                Read(file);
                MainActivity.people.add(new Person(Read(file),"",""));
                if(MainActivity.people.size() !=3) {
                    MainActivity.adapter.notifyDataSetChanged();
                }




                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("name");
                editor.remove("surname");
                editor.remove("description");
                editor.commit();
                finish();
                etName.setText("");
                etSurname.setText("");
                etDescription.setText("");
            }
        });
    }



    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();

        if(!etName.getText().toString().equals("")  && !etSurname.getText().toString().equals("") && !etDescription.getText().toString().equals("")  ) {
            editor.putString("name", etName.getText().toString());
            editor.putString("surname", etSurname.getText().toString());
            editor.putString("description", etDescription.getText().toString());
            editor.commit();
        }

    }

    public static void Save(File file,String[] data){
        try {
             fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try
        {
            try
            {
                for (int i = 0; i<data.length; i++)
                {
                    fos.write(data[i].getBytes());
                    if (i < data.length-1)
                    {
                        fos.write("\n".getBytes());
                    }
                }
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }

    public static String[] Load(File file)
    {
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int anzahl=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                anzahl++;
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}

        String[] array = new String[anzahl];

        String line;
        int i = 0;
        try
        {
            while((line=br.readLine())!=null)
            {
                array[i] = line;
                i++;
            }
        }
        catch (IOException e) {e.printStackTrace();}
        return array;
    }

    public static String Read(File file){
        StringBuilder text = new StringBuilder();
        String line="";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));


            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');

            }
            br.close();
        }
        catch (IOException e) {

        }

        return text.toString();
    }



}
