package com.csgradqau.singleactivityassignment;

import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csgradqau.singleactivityassignment.data.model.DatabaseHelper;
import com.csgradqau.singleactivityassignment.data.model.MyDividerItemDecoration;
import com.csgradqau.singleactivityassignment.data.model.user;
import com.csgradqau.singleactivityassignment.data.model.userAdapter;

import java.util.ArrayList;
import java.util.List;


public class homeFragment extends Fragment {
    View v;
    private userAdapter adapter;
    private List<user> userList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noUserView;

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
        recyclerView = v.findViewById(R.id.recycler_view);
        noUserView = v.findViewById(R.id.empty);
        db = new DatabaseHelper(getActivity());
        userList.addAll(db.getAllUsers());

        adapter = new userAdapter(getActivity(), userList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(adapter);
        return v;
    }
    private void toggleEmptyUsers() {
        // you can check notesList.size() > 0

        if (db.getUserCount() > 0) {
            noUserView.setVisibility(View.GONE);
        } else {
            noUserView.setVisibility(View.VISIBLE);
        }
    }
}