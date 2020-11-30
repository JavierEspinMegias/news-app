package com.android.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.internal.DataHolderNotifier;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FragSearchMeets extends Fragment {

    private static final String USER_ID = "user_id";
    private String userId;

    private RecyclerView recycler;
    private SearchMeetAdapter adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();


    private ArrayList<CasualMeet> meets;


    public FragSearchMeets() {
    }

    public static FragSearchMeets newInstanceData(String userId) {
        FragSearchMeets fragment = new FragSearchMeets();
        Bundle args = new Bundle();
        args.putString(USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(USER_ID);
            meets = new ArrayList<>();
            meets.clear();
        }else{
            Toast.makeText(getContext(), R.string.bad_data, Toast.LENGTH_SHORT).show();
            List<Fragment> frags = getActivity().getSupportFragmentManager().getFragments();
            getActivity().getSupportFragmentManager().beginTransaction().remove(frags.get(0)).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_meets, container, false);

        recycler = (RecyclerView)v.findViewById(R.id.recyler);
        recycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter = new SearchMeetAdapter(meets, userId);
        recycler.setAdapter(adapter);
        reference.child("meets").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot pojo : dataSnapshot.getChildren()){
                    CasualMeet newMeet = pojo.getValue(CasualMeet.class);
                    meets.add(newMeet);
                    adapter.notifyItemInserted(meets.size()-1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
