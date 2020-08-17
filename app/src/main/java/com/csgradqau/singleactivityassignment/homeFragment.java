package com.csgradqau.singleactivityassignment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.csgradqau.singleactivityassignment.data.model.DatabaseHelper;
import com.csgradqau.singleactivityassignment.data.model.MyDividerItemDecoration;
import com.csgradqau.singleactivityassignment.data.model.RecyclerTouchListener;
import com.csgradqau.singleactivityassignment.data.model.user;
import com.csgradqau.singleactivityassignment.data.model.userAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;


public class homeFragment extends Fragment {
    View v;
    private userAdapter adapter;
    private List<user> userList = new ArrayList<>();
    private RecyclerView recyclerView;
    EditText email;
    private TextView noUserView;
    sendMessage SM;
    Bundle data;
    userFragment uf;

    private DatabaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        //db = new DatabaseHelper(getActivity());
        recyclerView = v.findViewById(R.id.recycler_view);
        noUserView = v.findViewById(R.id.empty);
        //userList = db.getAllUsers();
        adapter = new userAdapter(getActivity(), userList);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference ref = fd.getReference("users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot dt : dataSnapshot.getChildren()){
                    Toast.makeText(getActivity(), "in Loop", Toast.LENGTH_LONG).show();
                    user u = (user) dt.getValue(user.class);
                    userList.add(u);
                    //Toast.makeText(getActivity(), "user : " +u.getEmail(), Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
                toggleEmptyUsers();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Toast.makeText(getActivity(), "Size : " +userList.size(), Toast.LENGTH_LONG).show();

        Toast.makeText(getActivity(), "Size : "+userList.size(), Toast.LENGTH_LONG).show();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(adapter);
        data = new Bundle();
        //email = v.findViewById(R.id.email);
        uf = new userFragment();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Toast.makeText(getActivity(),"Pressed", Toast.LENGTH_LONG).show();
                user a = userList.get(position);
                data.putString("email",a.getEmail().toString());
                data.putSerializable("user",a);
                uf.setArguments(data);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,uf).commit();

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        //stoggleEmptyUsers();
        return v;
    }
    private void toggleEmptyUsers() {
        // you can check notesList.size() > 0

        if (userList.size() > 0) {
            noUserView.setVisibility(View.GONE);
        } else {
            noUserView.setVisibility(View.VISIBLE);
        }
    }

}

interface sendMessage{
    String sendData(String data);
}