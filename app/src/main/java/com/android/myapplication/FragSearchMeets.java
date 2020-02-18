package com.android.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class FragSearchMeets extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String USER_ID = "user_id";
    private TextView text;
    private RecyclerView recycler;
    private GroupAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;

    private OnFragmentInterfaceCom mListener;

    public FragSearchMeets() {
    }

    public static FragSearchMeets newInstanceData(String param1) {
        FragSearchMeets fragment = new FragSearchMeets();
        Bundle args = new Bundle();
        args.putString(USER_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && !(getArguments().getString(USER_ID)).isEmpty()) {
            mParam1 = getArguments().getString(USER_ID);
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
        recycler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));


//        adapter = new GroupAdapter(v.getContext(), personas);
//        recycler.setAdapter(adapter);


        text = (TextView)v.findViewById(R.id.frag_text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed("Yeah");
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String data) {
        if (mListener != null) {
            mListener.onFragmentMessage("TAGFragment1", data);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInterfaceCom) {
            mListener = (OnFragmentInterfaceCom) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
