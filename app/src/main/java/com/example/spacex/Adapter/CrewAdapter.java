package com.example.spacex.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.spacex.Activities.Details;
import com.example.spacex.R;
import com.example.spacex.Room.Crew;
import com.example.spacex.databinding.CrewLayoutBinding;

import java.util.List;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewViewHolder> {

    private Context mContext;
    private List<Crew> mCrewList;

    public CrewAdapter(Context mContext, List<Crew> mCrewList) {
        this.mContext = mContext;
        this.mCrewList = mCrewList;
    }

    @NonNull
    @Override
    public CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.crew_layout, parent, false);
        return new CrewAdapter.CrewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(mContext).load(mCrewList.get(position).getImageUrl())
                .into(holder.binding.CrewProfilePic);

        holder.binding.CrewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Details.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("detail", mCrewList.get(position));
                mContext.startActivity(intent);
            }
        });

        holder.binding.CrewName.setText(mCrewList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mCrewList.size();
    }

    public class CrewViewHolder extends RecyclerView.ViewHolder{

        CrewLayoutBinding binding;

        public CrewViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CrewLayoutBinding.bind(itemView);
        }
    }
}
