package com.android.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnFragmentInterfaceCom {

    private CustomBottomNavigationView menuBottom;
    private FloatingActionButton fabSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationItemView itemInvi = (BottomNavigationItemView)findViewById(R.id.action_search);
        itemInvi.setVisibility(View.INVISIBLE);
        itemInvi.setClickable(false);

        fabSearch = (FloatingActionButton)findViewById(R.id.fab_search);
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragSearchMeets fragSearchMeets = FragSearchMeets.newInstanceData(getSavedId());
                loadFragment(fragSearchMeets);
            }
        });

        menuBottom = (CustomBottomNavigationView)findViewById(R.id.customBottomBar);
        menuBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_add:
                        FragAddMeet fragAdd = FragAddMeet.newInstance(getSavedId());
                        loadFragment(fragAdd);
                        break;
                    case R.id.action_news:
                        FragNews fragNews = FragNews.newInstance(getSavedId());
                        loadFragment(fragNews);
                        break;
                    case R.id.action_profile:
                        FragAccount fragAcc = FragAccount.newInstance(getSavedId());
                        loadFragment(fragAcc);
                        break;
                    case R.id.action_settings:
                        FragSettings fragSettings = FragSettings.newInstance(getSavedId());
                        loadFragment(fragSettings);
                        break;
                }
                return true;
            }
        });



    }



    public void loadFragment(Fragment frag){
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                .replace(R.id.frame_fragments, frag)
                .commit();
    }

    @Override
    public void onFragmentMessage(String TAG, Object data) {
        if (TAG.equals("TAGFragment1")){
            //MAIN GESTIONARA INFORMACION PROVENIENTE DEL Fragment 1
            Toast.makeText(this, "info recibida del frag1: "+data.toString(), Toast.LENGTH_SHORT).show();
        }
        else if (TAG.equals("TAGFragment2")){
            //MAIN GESTIONARA INFORMACION PROVENIENTE DEL Fragment 2
        }
        else if (TAG.equals("TAGFragment3")){
            //MAIN GESTIONARA INFORMACION PROVENIENTE DEL Fragment 3
        }
    }
    @Override
    public void onBackPressed() {
        List<Fragment> frags = getSupportFragmentManager().getFragments();
        if (frags.size() != 0){
            getSupportFragmentManager().beginTransaction().remove(frags.get(0)).commit();
        }else{
            super.onBackPressed();
        }
    }

    public String getSavedId(){
        SharedPreferences prefs = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        String id = prefs.getString("user_id", "");

        if (!id.isEmpty()){
            return id;
        }else{
            finish();
            return "";
        }
    }

}


