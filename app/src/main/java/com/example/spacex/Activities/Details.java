package com.example.spacex.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.spacex.R;
import com.example.spacex.Room.Crew;
import com.example.spacex.databinding.ActivityDetailsBinding;

public class Details extends AppCompatActivity {

    private ActivityDetailsBinding binding;
    private Crew crew = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Object obj = getIntent().getSerializableExtra("detail");
        if (obj instanceof Crew){
            crew = (Crew) obj;
        }

        Glide.with(getApplicationContext()).load(crew.getImageUrl()).into(binding.ProfilePic);
        binding.CrewFullName.setText(crew.getName());
        binding.StatusInfo.setText(crew.getWikiInfo());
        binding.Agency.setText(crew.getAgency());
    }

    public void openWeb(View view) {
        String url = crew.getStatus();
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }
}