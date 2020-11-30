package com.android.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FragProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER_ID = "user_id";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private TextInputEditText name, email, password, pass2, city, phone;
    private Button sendChanges;
    private SwitchMaterial isWoman;

    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference reference = database.getReference();
    private OnFragmentInterfaceCom mListener;
    public FragProfile(){}


    public static FragProfile newInstance(String userId) {
        FragProfile fragment = new FragProfile();
        Bundle args = new Bundle();
        args.putString(USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        email = (TextInputEditText) v.findViewById(R.id.email);
        name = (TextInputEditText) v.findViewById(R.id.name);
        password = (TextInputEditText) v.findViewById(R.id.pass);
        pass2 = (TextInputEditText) v.findViewById(R.id.pass2);
        phone = (TextInputEditText) v.findViewById(R.id.phone);
        city = (TextInputEditText) v.findViewById(R.id.city);
        isWoman = (SwitchMaterial)v.findViewById(R.id.switch_sex);
        sendChanges = (Button)v.findViewById(R.id.send_changes);


        reference.child("users").child(mParam1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CasualUser myUser = dataSnapshot.getValue(CasualUser.class);
                email.setText(myUser.getEmail());
                name.setText(myUser.getName());
                password.setText(myUser.getPass());
                pass2.setText(myUser.getPass());
                phone.setText(myUser.getPhone());
                city.setText(myUser.getCity());
                isWoman.setSelected(myUser.isWoman);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sendChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CasualUser myNewUser = new CasualUser(
                        mParam1,
                        email.getText().toString(),
                        password.getText().toString(),
                        name.getText().toString(),
                        city.getText().toString(),
                        phone.getText().toString(),
                        "",
                        true,
                        isWoman.isChecked());
                reference.child("users").child(mParam1).setValue(myNewUser);
                Toast.makeText(getContext(), R.string.user_modified, Toast.LENGTH_SHORT).show();
            }
        });


        return v;
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
