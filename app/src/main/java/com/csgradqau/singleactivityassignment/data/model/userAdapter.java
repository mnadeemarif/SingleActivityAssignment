package com.csgradqau.singleactivityassignment.data.model;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.csgradqau.singleactivityassignment.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class userAdapter extends RecyclerView.Adapter<userAdapter.UserViewHolder>{
    private Context context;
    private List<user> userList;

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView email;
        public TextView dob;
        public ImageView profile;

        public UserViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.nameR);
            email = view.findViewById(R.id.emailR);
            profile = view.findViewById(R.id.picR);
            dob = view.findViewById(R.id.dobR);

        }
    }


    public userAdapter(Context context, List<user> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_row, parent, false);

        return new UserViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        user usr = userList.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(usr.getProfile(), 0, usr.getProfile().length);
        holder.profile.setImageBitmap((bitmap));
        holder.name.setText(usr.getName());
        holder.email.setText(usr.getEmail());
        holder.dob.setText(usr.getDob());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


}
