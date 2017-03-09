package com.example.canbay.bim494_homework1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.TextView;

/**
 * Created by canbay on 9.03.2017.
 */

public class DetailUserActivity extends AppCompatActivity {

    TextView name,surname,description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        name = (TextView) findViewById(R.id.name);
        surname = (TextView) findViewById(R.id.surname);
        description = (TextView) findViewById(R.id.description);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("People Details");
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_action));



        name.setText( preferences.getString("peoplename", "N/A"));
        surname.setText( preferences.getString("peoplesurname", "N/A"));
        description.setText( preferences.getString("peopledescription", "N/A"));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

}
