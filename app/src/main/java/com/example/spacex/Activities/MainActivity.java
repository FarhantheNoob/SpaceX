package com.example.spacex.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.spacex.Adapter.CrewAdapter;
import com.example.spacex.R;
import com.example.spacex.Room.Crew;
import com.example.spacex.Room.CrewDatabase;
import com.example.spacex.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<Crew> mCrewList;
    private CrewAdapter adapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.ToolBar);
        getSupportActionBar().setTitle("Crew Members");

        if (setUpDB().dao().checkCrew() == 0){
            fetchAndInsert();
        }
        setUpRecycleView();
    }

    public CrewDatabase setUpDB(){
        CrewDatabase database = Room.databaseBuilder(
                MainActivity.this, CrewDatabase.class, "CrewDatabase")
                .allowMainThreadQueries().build();

        return database;
    }

    public void fetchAndInsert(){
        String URL = "https://api.spacexdata.com/v4/crew";

        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);

                                String name = object.get("name").toString();
                                String agency = object.get("agency").toString();
                                String image = object.get("image").toString();
                                String wikipedia = object.get("wikipedia").toString();
                                String status = object.get("status").toString();

                                Crew crew = new Crew(name, agency, image, wikipedia, status);

                                setUpDB().dao().crewInsertion(crew);
                                setUpRecycleView();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SpaceXResponse", "Something went wrong");
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    public void setUpRecycleView(){
        mCrewList = new ArrayList<>();
        mCrewList = setUpDB().dao().getAllCrew();
        adapter = new CrewAdapter(MainActivity.this, mCrewList);
        binding.CrewMembers.setLayoutManager(new GridLayoutManager(this, 2));
        binding.CrewMembers.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:
                if (setUpDB().dao().checkCrew() == 0){
                    fetchAndInsert();
                }else {
                    Toast.makeText(MainActivity.this, "Already Up-To_Date", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.delete:
                setUpDB().dao().deleteAll();
                mCrewList.clear();
                adapter.notifyDataSetChanged();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}