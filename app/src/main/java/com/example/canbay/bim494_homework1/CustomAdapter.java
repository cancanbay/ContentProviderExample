package com.example.canbay.bim494_homework1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import Model.Person;

/**
 * Created by canbay on 5.03.2017.
 */

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Person> personList;

    public CustomAdapter(Activity activity, List<Person> people){
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        personList = people;

    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int position) {
        return personList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;

        rowView = inflater.inflate(R.layout.listview_row_layout,null);
        TextView name = (TextView) rowView.findViewById(R.id.isim);
        TextView surname = (TextView) rowView.findViewById(R.id.soyisim);
        TextView description = (TextView) rowView.findViewById(R.id.aciklama);
        Person person = personList.get(position);
        name.setText(person.getName());
        surname.setText(person.getSurname());
        description.setText(person.getDescription());

        return rowView;
    }
}
