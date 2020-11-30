package com.android.myapplication;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class FragNews extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER_ID = "user_id";

    private RecyclerView recyclerMultiple;

    private NewsAdapter adapterRaces;
    private CardNewsAdapter adapterNews;

    private ArrayList<CasualRace> races;
    private ArrayList<CasualNew> news;

    private HashMap<String, String> newsSources;
    private RequestQueue queueRaces, queueNews;

    private TabLayout tabsNews;

    public FragNews() {
    }

    public static FragNews newInstance(String userId) {
        FragNews fragment = new FragNews();
        Bundle args = new Bundle();
        args.putString(USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        newsSources =  new HashMap<>();
        newsSources.clear();

        races = new ArrayList<>();
        races.clear();

        news = new ArrayList<>();
        news.clear();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);

        tabsNews = (TabLayout)v.findViewById(R.id.news_option_tab);

        recyclerMultiple = (RecyclerView) v.findViewById(R.id.recyler_news);
        recyclerMultiple.setLayoutManager(new GridLayoutManager(getActivity(),1));
        adapterRaces = new NewsAdapter(races);
        recyclerMultiple.setAdapter(adapterRaces);
        queueRaces = Volley.newRequestQueue(getActivity());


        String urlMotoGP ="https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4407";
        String urlMoto3 ="https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4437";
        String urlMoto2 ="https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4436";
        String urlMotoSBK ="https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4454";
        String urlMotoPro ="https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4469";
        String urlMotoMXGP ="https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4587";
        String urlMotoIsle ="https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4732";
        String urlMotoE ="https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4588";
        String urlMotoDakar ="https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4447";
        String urlMotoAMA ="https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4468";

        newsSources.put("MotoGP",urlMotoGP);
        newsSources.put("Moto3",urlMoto3);
        newsSources.put("Moto2",urlMoto2);
        newsSources.put("SBK",urlMotoSBK);
        newsSources.put("MotoPro",urlMotoPro);
        newsSources.put("MXGP",urlMotoMXGP);
        newsSources.put("Isle",urlMotoIsle);
        newsSources.put("MotoE",urlMotoE);
        newsSources.put("Dakar",urlMotoDakar);
        newsSources.put("Ama",urlMotoAMA);


        for (String source:newsSources.values()){
            loadEvents(source);
        }

        tabsNews.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    news.clear();
                    adapterNews.notifyDataSetChanged();

                    adapterRaces = new NewsAdapter(races);
                    recyclerMultiple.setAdapter(adapterRaces);

                    for (String source:newsSources.values()){
                        loadEvents(source);
                    }

                }else if (tab.getPosition()==1){
                    races.clear();
                    adapterRaces.notifyDataSetChanged();

                    adapterNews = new CardNewsAdapter(news);
                    recyclerMultiple.setAdapter(adapterNews);

                    loadNews();

                }else{

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void loadEvents(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsnobject = new JSONObject(response);
                            JSONArray jsonArray = jsnobject.getJSONArray("events");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject explrObject = jsonArray.getJSONObject(i);
                                CasualRace casualNew = new CasualRace(
                                        explrObject.get("strLeague").toString(),
                                        explrObject.get("strCircuit").toString(),
                                        explrObject.get("strCountry").toString(),
                                        explrObject.get("dateEvent").toString(),
                                        explrObject.get("strCity").toString(),
                                        new Date());


                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date = format.parse(casualNew.getDate());
                                    casualNew.setFullDate(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if (races.size()>0){
                                    for (int j = 0; j < races.size()-1; j++) {
                                        if (casualNew.getFullDate().before(races.get(j).getFullDate()) == true){
                                            races.add(j, casualNew);
                                            adapterRaces.notifyItemInserted(j);
                                            j= races.size();
                                        }
                                    }
                                    if (!races.contains(casualNew)){
                                        races.add(casualNew);
                                        adapterRaces.notifyItemInserted(races.size()-1);
                                    }

                                }else{
                                    races.add(casualNew);
                                    adapterRaces.notifyItemInserted(races.size()-1);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });

        queueRaces.add(stringRequest);
    }

    public void loadNews(){

        String url = "https://newsapi.org/v2/everything?language=es&q=motorcycle&sortBy=publishedAt&apiKey=e9f9c5175c374425b9e7d3a2fb5868a8";
        queueNews = Volley.newRequestQueue(getActivity());

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray newsJson = response.getJSONArray("articles");
                            for(int n = 0; n < newsJson.length(); n++){
                                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                                JSONObject object = newsJson.getJSONObject(n);
                                Log.println(Log.INFO, "WWW",object.toString());
                                CasualNew casualNew = new CasualNew(
                                        object.get("title").toString(),
                                        object.get("author").toString(),
                                        object.get("description").toString(),
                                        object.get("urlToImage").toString(),
                                        object.get("url").toString());
                                news.add(casualNew);
                                adapterNews.notifyItemInserted(news.size()-1);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        queueNews.add(jsonObjectRequest);
    }
}
