package com.csgradqau.singleactivityassignment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.csgradqau.singleactivityassignment.data.model.DatabaseHelper;
import com.csgradqau.singleactivityassignment.data.model.user;
import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class LoginFragment extends Fragment {

    private Button login,register;
    private TextInputLayout email,password;
    private View view;
    private DatabaseHelper db;
    String f_pass;
    Boolean flag;
    Bundle usr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        login = (Button) view.findViewById(R.id.loginf);
        register = (Button) view.findViewById(R.id.registerf);
        email = (TextInputLayout) view.findViewById(R.id.emailL);
        password = (TextInputLayout) view.findViewById(R.id.password);
        //l = new user();
        //db = new DatabaseHelper(getActivity());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Clicked !", Toast.LENGTH_LONG).show();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         for (DataSnapshot dt : dataSnapshot.getChildren()){
                             //Toast.makeText(getActivity(), "help : "+dt.child("email").getValue().toString(), Toast.LENGTH_LONG).show();
                             if(dt.child("email").getValue().toString().equals(email.getEditText().getText().toString()))
                             {
                                 f_pass = dt.child("password").getValue().toString().trim();
                                 //Toast.makeText(getActivity(), "help : "+f_pass, Toast.LENGTH_LONG).show();
                                 if (f_pass.equals(password.getEditText().getText().toString().trim())){
                                     Toast.makeText(getActivity(), "Logged In!!!", Toast.LENGTH_LONG).show();
                                     getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new homeFragment()).commit();
                                 }
                                 else {
                                     Toast.makeText(getActivity(), "User Doesn't Exist Please Register !", Toast.LENGTH_LONG).show();
                                 }

                             }


                            //flag = true;
                            //f_pass = dataSnapshot.child(email.getEditText().getText().toString()).child("password").getValue(String.class);
                            //usr.putString("email",email.getEditText().getText().toString().trim());
                            //usr.putString("email",email.getEditText().getText().toString().trim());
                            //usr.putString("email",email.getEditText().getText().toString().trim());
                         }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //Query checkuser = myRef.orderByChild("email").equalTo(email.getEditText().getText().toString());

               /* if (db.checkUserExist(email.getEditText().getText().toString()))
                {
                    l = db.getUser(email.getEditText().getText().toString());
                    //Toast.makeText(getActivity(), email.getEditText().getText().toString(), Toast.LENGTH_LONG).show();
                    if (l.getPassword().toString().equals(password.getEditText().getText().toString()))
                    {
                        Toast.makeText(getActivity(), "Logged In!!!", Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new homeFragment()).commit();
                    }
                }
                else {
                    Toast.makeText(getActivity(), "User Doesn't Exist Please Register !", Toast.LENGTH_LONG).show();
                }*/
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new RegisterFragment()).commit();
                // display a message by using a Toast
                //Toast.makeText(getActivity(), "First Fragment", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new RegisterFragment()).commit();
            }
        });
        return view;
    }
}