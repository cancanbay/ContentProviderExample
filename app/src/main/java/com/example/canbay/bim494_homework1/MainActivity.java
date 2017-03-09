package com.example.canbay.bim494_homework1;

import android.app.ActionBar;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.Person;

public class MainActivity extends AppCompatActivity {

    static CustomAdapter adapter;
    private String[] isimler= {"Linus","Bill","Alan"};
    private String[] soyadlar= {"Torvalds","Gates","Turing"};
    private String[] aciklamalar= {"Linus Benedict Torvalds born December 28, 1969) is a Finnish software engineer" +
            " who is the creator and, for a long time, principal developer of the Linux kernel.",
            "Bill Gates born October 28, 1955) is an American business magnate, investor, author, and philanthropist. In 1975," +
            " Gates and Paul Allen co-founded Microsoft, which became the world's largest PC software company.",
            "Alan Mathison Turing (23 June 1912 – 7 June 1954) was an English computer scientist, mathematician, logician," +
            " cryptanalyst and theoretical biologist."};
    static final List<Person> people = new ArrayList<Person>();
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/text/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView list = (ListView) findViewById(R.id.listView);
        people.add(new Person(isimler[0],soyadlar[0],aciklamalar[0]));
        people.add(new Person(isimler[1],soyadlar[1],aciklamalar[1]));
        people.add(new Person(isimler[2],soyadlar[2],aciklamalar[2]));
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,isimler);
        adapter = new CustomAdapter(this,people);
        list.setAdapter(adapter);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("Provider Example");
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_action));

        File file = new File(path +"file.txt");

        String text = NewUserActivity.Read(file);
        String textStr[] = text.split("\\r\\n|\\n|\\r");
        MainActivity.people.add(new Person(textStr[0],textStr[1],textStr[2]));
        if(MainActivity.people.size() !=3) {
            MainActivity.adapter.notifyDataSetChanged();
        }

       list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Person item = (Person)list.getItemAtPosition(position);
               Intent intent = new Intent(getApplicationContext(),DetailUserActivity.class);
               SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
               SharedPreferences.Editor editor = preferences.edit();

               editor.putString("peoplename", item.getName());
               editor.putString("peoplesurname", item.getSurname());
               editor.putString("peopledescription", item.getDescription());
               editor.commit();
               startActivity(intent);
           }
       });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_new:
                Intent intent = new Intent(this,NewUserActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
