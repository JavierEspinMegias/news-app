package com.android.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


public class FragAccount extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER_ID = "user_id";
    private LinearLayout account, motos, contacts, rides;
    private String userId;

    // TODO: Rename and change types of parameters
    public FragAccount() {
        // Required empty public constructor
    }


    public static FragAccount newInstance(String userId) {
        FragAccount fragment = new FragAccount();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        account = (LinearLayout)v.findViewById(R.id.my_account);
        motos = (LinearLayout)v.findViewById(R.id.my_motos);
        contacts = (LinearLayout)v.findViewById(R.id.my_contacts);
        rides = (LinearLayout)v.findViewById(R.id.my_rides);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragProfile frag = FragProfile.newInstance(userId);
                loadFragment(frag);
            }
        });

        motos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragMyMotos frag = FragMyMotos.newInstance(userId);
                loadFragment(frag);
            }
        });

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragProfile frag = FragProfile.newInstance(userId);
                loadFragment(frag);
            }
        });

        rides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragProfile frag = FragProfile.newInstance(userId);
                loadFragment(frag);
            }
        });




        return v;
    }


    public void loadFragment(Fragment frag){
        getActivity().getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                .replace(R.id.frame_fragments, frag)
                .commit();
    }
}
