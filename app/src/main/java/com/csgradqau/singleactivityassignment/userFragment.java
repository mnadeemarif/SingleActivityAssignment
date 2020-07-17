package com.csgradqau.singleactivityassignment;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    EditText hobbies;
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
        Bundle data = this.getArguments();
        //a = new user();
        profile = (ImageView) v.findViewById(R.id.user_pic);
        back = (Button) v.findViewById(R.id.user_back);
        name = (EditText) v.findViewById(R.id.user_name);
        email = (EditText) v.findViewById(R.id.user_email);
        dob = (EditText) v.findViewById(R.id.user_dob);
        gender =  v.findViewById(R.id.user_gender);
        hobbies = v.findViewById(R.id.user_hobbies);
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
        a  = db.getUser(data.getString("email"));
        //profile.setImageBitmap((Bitmap)a.getProfile());
        name.setText(a.getName().toString());
        email.setText(a.getEmail().toString());
        dob.setText(a.getDob().toString());
        hobbies.setText(a.getHobbies());
        if (a.getGender() == 1)
            gender.setText("Male");
        else
            gender.setText("Female");

        byte[] blob=a.getProfile();
        Bitmap bmp= BitmapFactory.decodeByteArray(blob,0,blob.length);
        //ImageView image=new ImageView(getActivity());
        profile.setImageBitmap(bmp);

        return v;
    }
}