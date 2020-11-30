package com.android.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Calendar;

public class FragNewMoto extends Fragment {

    private static final String USER_ID = "user_id";
    private String userId;

    public FragNewMoto() {

    }


    public static FragNewMoto newInstance(String userId) {
        FragNewMoto fragment = new FragNewMoto();
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
        final View v = inflater.inflate(R.layout.fragment_new_moto, container, false);
        final ChipGroup choiceChipGroup = v.findViewById(R.id.chip_group);
        final ImageView image_moto = v.findViewById(R.id.image_view_adapter_moto);
        final TextView textMoto = v.findViewById(R.id.text_type_moto);
        final MaterialButton sendMoto = v.findViewById(R.id.send_moto);
        final TextInputEditText name = (TextInputEditText)v.findViewById(R.id.tiet_moto_name);
        final TextInputEditText model = (TextInputEditText)v.findViewById(R.id.tiet_moto_model);
        final TextInputEditText power = (TextInputEditText)v.findViewById(R.id.tiet_moto_power);


        choiceChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                Chip chipSelec = v.findViewById(chipGroup.getCheckedChipId());
                switch (i){
                    case R.id.moto_casual_ride:
                        textMoto.setText(chipSelec.getText().toString());
                        image_moto.setImageResource(R.drawable.ic_casual);
                        image_moto.setColorFilter(getResources().getColor(R.color.colorCasual, getContext().getTheme()));
                        break;
                    case R.id.moto_enduro:
                        textMoto.setText(chipSelec.getText().toString());
                        image_moto.setImageResource(R.drawable.ic_enduro);
                        image_moto.setColorFilter(getResources().getColor(R.color.colorEnduro, getContext().getTheme()));
                        break;
                    case R.id.moto_speed:
                        textMoto.setText(chipSelec.getText().toString());
                        image_moto.setImageResource(R.drawable.ic_speed);
                        image_moto.setColorFilter(getResources().getColor(R.color.colorSpeed, getContext().getTheme()));
                        break;
                    case R.id.moto_travel:
                        textMoto.setText(chipSelec.getText().toString());
                        image_moto.setImageResource(R.drawable.ic_travel);
                        image_moto.setColorFilter(getResources().getColor(R.color.colorTravel, getContext().getTheme()));
                        break;
                }

            }
        });

        sendMoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFields(name.getText().toString(), model.getText().toString(), power.getText().toString(), textMoto.getText().toString())){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference();
                    DatabaseReference newRef = database.getReference();

                    CasualMoto newMoto = new CasualMoto("",model.getText().toString(),power.getText().toString(),textMoto.getText().toString(),name.getText().toString(),userId);

                    newRef = reference.child("motos").child(userId).push();
                    String motoId = newRef.getKey();
                    newMoto.setId(motoId);
                    reference.child("motos").child(userId).child(motoId).setValue(newMoto);
                }
            }
        });

        return v;
    }

    public boolean checkFields(String name, String model, String power, String type){
        if (!name.isEmpty()){
            if (!model.isEmpty()){
                if (!power.isEmpty()){
                    if (!type.equals(getString(R.string.add_new_moto))){
                        return true;
                    }else{
                        Toast.makeText(getContext(), R.string.need_type, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), R.string.need_power, Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getContext(), R.string.need_model, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), R.string.need_name, Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}
