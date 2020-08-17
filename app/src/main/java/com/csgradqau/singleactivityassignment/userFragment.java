package com.csgradqau.singleactivityassignment;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.csgradqau.singleactivityassignment.data.model.DatabaseHelper;
import com.csgradqau.singleactivityassignment.data.model.GlideApp;
import com.csgradqau.singleactivityassignment.data.model.user;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class userFragment extends Fragment {

    Button back;
    EditText name,email,dob;
    EditText gender;
    ImageView profile;
    EditText hobbies;
    View v;
    private user a;
    //private DatabaseHelper db;
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
        assert data != null;
        user current = (user) data.get("user");
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

        //db = new DatabaseHelper(getActivity());
        //a  = db.getUser(data.getString("email"));
        //profile.setImageBitmap((Bitmap)a.getProfile());
        name.setText(current.getName().toString());
        email.setText(current.getEmail().toString());
        dob.setText(current.getDob().toString());
        hobbies.setText(current.getHobbies());
        if (current.getGender() == 1)
            gender.setText("Male");
        else
            gender.setText("Female");

//        byte[] blob=a.getProfile();
        //Bitmap bmp= BitmapFactory.decodeByteArray(blob,0,blob.length);
        //ImageView image=new ImageView(getActivity());
        //profile.setImageBitmap(bmp);

        FirebaseStorage fs = FirebaseStorage.getInstance();
        String img = "profile_pics/"+current.getId();
        StorageReference stRef = fs.getReference(img);
        GlideApp.with(this).load(stRef).into(profile);
//        final long ONE_MEGABYTE = 1024 * 1024;
//        stRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                // Data for "images/island.jpg" is returns, use this as needed
//                Toast.makeText(getActivity(), "in onSuccess", Toast.LENGTH_LONG).show();
//                Bitmap bmp= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                profile.setImageBitmap(bmp);
//                Toast.makeText(getActivity(), "finished job", Toast.LENGTH_LONG).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//                Toast.makeText(getActivity(), "failed", Toast.LENGTH_LONG).show();
//            }
//        });


        return v;
    }
}