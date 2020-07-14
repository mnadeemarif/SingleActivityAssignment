package com.csgradqau.singleactivityassignment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.csgradqau.singleactivityassignment.data.model.DatabaseHelper;
import com.csgradqau.singleactivityassignment.data.model.user;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class RegisterFragment extends Fragment {

    Button register;
    EditText name,email,password,dob;
    TextView btmLogin;
    RadioGroup gender;
    ImageView profile;
    AutoCompleteTextView hobbies;
    View v;
    DatePickerDialog picker;
    private user a;
    private static final int PICK_IMAGE = 100;
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
        v = inflater.inflate(R.layout.fragment_register, container, false);
        a = new user();
        profile = (ImageView) v.findViewById(R.id.profilePicture);
        register = (Button) v.findViewById(R.id.register);
        name = (EditText) v.findViewById(R.id.name);
        email = (EditText) v.findViewById(R.id.email);
        password = (EditText) v.findViewById(R.id.password);
        dob = (EditText) v.findViewById(R.id.dob);
        gender = (RadioGroup) v.findViewById(R.id.genderGroup);
        hobbies = v.findViewById(R.id.hobbies);
        btmLogin = v.findViewById(R.id.btmLogin);
        db = new DatabaseHelper(getActivity());
        String[] HOBBIES = new String[] {"Book Reading", "Cricket", "Football", "Badminton", "TV Shows", "Movies", "Novels", "Gardening", "Coin Collection", "Story Writing", "Ludo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(),android.R.layout.simple_dropdown_item_1line, HOBBIES);
        hobbies.setAdapter(adapter);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(getActivity());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.setEmail(email.getText().toString());
                a.setPassword(password.getText().toString());
                a.setName(name.getText().toString());
                a.setDob(dob.getText().toString());
                int s = gender.getCheckedRadioButtonId();
                if (s == R.id.male)
                    a.setGender(1);
                else
                    a.setGender(0);
                a.setHobbies(hobbies.getText().toString());
                try {
                    a.setProfile(imageViewToByte(profile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                long id = db.registerUser(a.getEmail(),a.getPassword(),a.getName(),a.getDob(),a.getGender(),a.getHobbies(),a.getProfile());
                if (id!=-1)
                {
                    Toast.makeText(getActivity(), "User Registered !", Toast.LENGTH_LONG).show();
                }
            }
        });
        btmLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new LoginFragment()).commit();
            }
        });
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT A DATE");
        final MaterialDatePicker mdp = builder.build();
        dob.setKeyListener(null);
        mdp.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dob.setText(mdp.getHeaderText());
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mdp.show(getActivity().getSupportFragmentManager(),"DOB_PICKER");
            }
        });
        return v;
    }
    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        profile.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    /*if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                profile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }*/
                    if (resultCode == RESULT_OK){
                        imageUri = data.getData();
                        profile.setImageURI(imageUri);
                    }
                    break;
            }
        }
    }

    private byte [] imageViewToByte(ImageView img) throws IOException {
        //Bitmap bmp = ((BitmapDrawable)img.getDrawable()).getBitmap();
        Bitmap bmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
        ByteArrayOutputStream op =  new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,op);
        byte [] byteArray = op.toByteArray();
        return byteArray;

    }
}