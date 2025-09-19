package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class EditCityFragment extends DialogFragment {
    private static final String ARG_CITY = "arg_city";  //serializable city
    private static final String ARG_POSITION = "arg_position";  //position of city in list

    public interface OnCityEditedListener {  // LISTEN TO ME
        void onCityEdited(City updated, int position);  // send edited city in its proper postion
    }

    private OnCityEditedListener callback;
    private City city;
    private int position;

    //serialization of city such that when we create a fragment when editing a city we bundle its names and list pos
    public static EditCityFragment newInstance(City city, int position) {
        Bundle b = new Bundle();
        //the stuff in da bundle
        b.putSerializable(ARG_CITY, city);
        b.putInt(ARG_POSITION, position);
        // when we are editing we create a new fragment, pass the bundle to this new fragment
        EditCityFragment f = new EditCityFragment();
        f.setArguments(b);
        return f;
    }

    //Reports the edited city to the activity
    @Override public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCityEditedListener) callback = (OnCityEditedListener) context;
    }

    //gets city input and position from bundle
    @Override public void onCreate(@Nullable Bundle s) {
        super.onCreate(s);
        Bundle a = getArguments();
        city = (City) a.getSerializable(ARG_CITY);
        position = a.getInt(ARG_POSITION);
    }

    // preee much the same from the addcityfragment
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = requireActivity().getLayoutInflater()
                .inflate(R.layout.fragment_edit_city, null);

        EditText editCityname = view.findViewById(R.id.edit_city_name);
        EditText editProvinceName = view.findViewById(R.id.edit_city_province);
        editCityname.setText(city.getName());
        editProvinceName.setText(city.getProvince());

        return new MaterialAlertDialogBuilder(requireContext()).setView(view).setTitle("Edit City").setNegativeButton("Cancel", (d, w) -> d.dismiss()).setPositiveButton("Save", (d, w) -> {
                    City updated = new City(
                            editCityname.getText().toString(),
                            editProvinceName.getText().toString()
                    );
                    if (callback != null && position >= 0) {
                        callback.onCityEdited(updated, position);
                    }
                })
                .create();
    }
}