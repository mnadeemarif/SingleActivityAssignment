package com.csgradqau.singleactivityassignment;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.csgradqau.singleactivityassignment.data.model.DatabaseHelper;
import com.csgradqau.singleactivityassignment.data.model.user;

public class userFragment extends Fragment {

    Button back;
    EditText name,email,dob;
    EditText gender;
    ImageView profile;
    AutoCompleteTextView hobbies;
    View v;
    private user a;
    private DatabaseHelper db;
    Uri imageUri;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_user, container, false);

        a = new user();
        profile = (ImageView) v.findViewById(R.id.profilePicture);
        back = (Button) v.findViewById(R.id.back);
        name = (EditText) v.findViewById(R.id.name);
        email = (EditText) v.findViewById(R.id.email);
        dob = (EditText) v.findViewById(R.id.dob);
        gender =  v.findViewById(R.id.genderU);
        hobbies = v.findViewById(R.id.hobbies);
        name.setKeyListener(null);
        email.setKeyListener(null);
        dob.setKeyListener(null);
        gender.setKeyListener(null);
        hobbies.setKeyListener(null);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new homeFragment()).commit();
            }
        });

        db = new DatabaseHelper(getActivity());



        return v;
    }
}