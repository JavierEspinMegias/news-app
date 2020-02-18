package com.android.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class MainActivity extends AppCompatActivity implements OnFragmentInterfaceCom {

    private BottomNavigationView menuBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        menuBottom = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        menuBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        FragSearchMeets frag = FragSearchMeets.newInstanceData(getSavedId());
                        loadFragment(frag);
                        break;
                    case R.id.action_add:
                        FragAddMeet frag2 = FragAddMeet.newInstance(getSavedId());
                        loadFragment(frag2);
                        break;
                    case R.id.action_camera:
                        Fragment3 frag3 = Fragment3.newInstance(getSavedId());
                        loadFragment(frag3);
                        break;
                    case R.id.action_navi:
                        Fragment4 frag4 = Fragment4.newInstance(getSavedId());
                        loadFragment(frag4);
                        break;
                    case R.id.action_settings:
                        FragSettings frag_settings = FragSettings.newInstance(getSavedId());
                        loadFragment(frag_settings);
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
