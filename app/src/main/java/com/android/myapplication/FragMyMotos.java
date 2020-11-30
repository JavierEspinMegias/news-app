package com.android.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FragMyMotos extends Fragment {

    private static final String USER_ID = "user_id";
    private String userId;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();


    private RecyclerView recycler;
    private MyMotosAdapter adapter;
    private ArrayList<CasualMoto> motos;

    public FragMyMotos() {

    }


    public static FragMyMotos newInstance(String userId) {
        FragMyMotos fragment = new FragMyMotos();
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
            motos = new ArrayList<>();
            motos.clear();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_motos, container, false);

        recycler = (RecyclerView)v.findViewById(R.id.recycler_motos);
        recycler.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapter = new MyMotosAdapter(motos, userId);
        recycler.setAdapter(adapter);
        reference.child("motos").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot pojo : dataSnapshot.getChildren()){
                        CasualMoto newMoto = pojo.getValue(CasualMoto.class);
                        motos.add(newMoto);
                        adapter.notifyItemInserted(motos.size()-1);
                    }
                    motos.add(new CasualMoto());
                    adapter.notifyItemInserted(motos.size()-1);

                }else{
                    Toast.makeText(getContext(), R.string.no_motos_registered, Toast.LENGTH_SHORT).show();
                    motos.add(new CasualMoto());
                    adapter.notifyItemInserted(motos.size()-1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }

}
