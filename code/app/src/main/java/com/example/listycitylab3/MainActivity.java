package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener, EditCityFragment.OnCityEditedListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private ArrayAdapter<City> cityAdapter;

    @Override
    public  void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {
                "Edmonton", "Vancouver", "Toronto"
        };

        String[] provinces = {"AB", "BC", "ON"};

        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }
        
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(view -> {
            new AddCityFragment().show(getSupportFragmentManager(), "add_city");
        });

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City selected = dataList.get(position);
            EditCityFragment fragment = EditCityFragment.newInstance(selected, position);
                fragment.show(getSupportFragmentManager(), "edit_city");

        });
    }
    @Override
    public void onCityEdited(City updated, int position) {
        dataList.set(position, updated);
        cityAdapter.notifyDataSetChanged();
    }

}