package com.android.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    public EditText email, pass, pass2, name, phone, city;
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference reference = database.getReference();
    public DatabaseReference newRef = database.getReference();
    public Switch isWoman;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        pass2 = (EditText) findViewById(R.id.pass2);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        city = (EditText) findViewById(R.id.city);
        isWoman = (Switch)findViewById(R.id.switch_sex);
    }




    public void createUser(View v){

        final String pName = name.getText().toString();
        final String pEmail = email.getText().toString().toLowerCase();
        final String pPass = pass.getText().toString();
        final String pPhone = phone.getText().toString();
        final String pCity = city.getText().toString();

        reference.child("users").orderByChild("email").equalTo(pEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    Toast.makeText(Register.this, R.string.email_registered, Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if (isValidEmail(pEmail)) {
                        if (isValidPass(pPass)) {
                            if (isValidName(pName)) {
                                if (isValidPhoneNumber(pPhone)) {
                                    if (!pCity.isEmpty()) {
                                        CasualUser newUser = new CasualUser("",pEmail,pPass,pName,pCity,pPhone,null, true, isWoman.isChecked());
                                        newRef = reference.child("users").push();
                                        String id = newRef.getKey();
                                        newUser.setId(id);
                                        reference.child("users").child(id).setValue(newUser);
                                        saveSharedData(pEmail, pPass);
                                    }else Toast.makeText(getApplicationContext(), R.string.no_city, Toast.LENGTH_SHORT).show();
                                }else Toast.makeText(getApplicationContext(),R.string.no_city, Toast.LENGTH_SHORT).show();
                            }else Toast.makeText(getApplicationContext(), R.string.no_name, Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(getApplicationContext(), R.string.no_pass, Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(getApplicationContext(), R.string.no_email, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void saveSharedData(String email, String password){
        SharedPreferences prefs = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        prefs.edit().putString("user_email",email).commit();
        prefs.edit().putString("user_password",password).commit();
        finish();
    }

    /////// Check fields of hell

    public final boolean isValidEmail(final CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public final static boolean isValidPass(CharSequence pass) {
        String regx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{7,}$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(pass);
        return matcher.find();
    }

    public final static boolean isValidName(CharSequence name){
        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }

    public static final boolean isValidPhoneNumber(CharSequence target) {
        if (target == null || TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

}
