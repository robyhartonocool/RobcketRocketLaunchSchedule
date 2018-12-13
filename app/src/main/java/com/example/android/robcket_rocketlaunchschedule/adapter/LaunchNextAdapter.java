package com.example.android.robcket_rocketlaunchschedule.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.robcket_rocketlaunchschedule.R;
import com.example.android.robcket_rocketlaunchschedule.activity.LaunchDetailActivity;
import com.example.android.robcket_rocketlaunchschedule.model.Launch;
import com.example.android.robcket_rocketlaunchschedule.model.LaunchNextList;
import com.example.android.robcket_rocketlaunchschedule.model.Location;
import com.example.android.robcket_rocketlaunchschedule.model.Rocket;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class LaunchNextAdapter extends RecyclerView.Adapter<LaunchNextAdapter.LaunchNextViewHolder> {

    // Private Variables for putExtra intents
    private String ROCKET_IMAGE_EXTRA = "LAUNCH_IMAGE_URL";
    private String LAUNCH_TITLE_EXTRA = "LAUNCH_TITLE";
    private String MISSION_NAME_EXTRA = "LAUNCH_MISSION_NAME";
    private String MISSION_SUMMARY_EXTRA = "LAUNCH_MISSION_SUMMARY";
    private String LAUNCH_DATE_EXTRA = "LAUNCH_DATE";
    private String LAUNCH_WINDOW_START_EXTRA = "LAUNCH_WINDOW_START";
    private String LAUNCH_WINDOW_END_EXTRA = "LAUNCH_WINDOW_END";

    // Private Variables for constructors
    private ArrayList<Launch> launchList;
    private Context mContext;

    public LaunchNextAdapter(Context context, ArrayList<Launch> launchList) {
        this.launchList = launchList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public LaunchNextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.launch_list_item, parent, false);
        return new LaunchNextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaunchNextViewHolder launchNextViewHolder, int position) {
        // Set the launch title text
        launchNextViewHolder.txtLaunchTitle.setText(launchList.get(position).getName());

        // Set the launch image
        // Set the ImageView based on String image Url
        Picasso.with(mContext)
                .load(launchList.get(position).getRocket().getImageURL())
                .placeholder(R.drawable.ic_rocket)
                .into(launchNextViewHolder.ivLaunchImage);

        // Set the launch date text
        launchNextViewHolder.txtLaunchDate.setText(launchList.get(position).getWindowstart());

        // Set the launch location text
        launchNextViewHolder.txtLaunchLocation.setText(launchList.get(position).getLocation().getName());
    }

    @Override
    public int getItemCount() {
        return launchList.size();
    }

    public class LaunchNextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtLaunchTitle;
        ImageView ivLaunchImage;
        TextView txtLaunchDate;
        TextView txtLaunchLocation;

        public LaunchNextViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLaunchTitle = itemView.findViewById(R.id.txt_launch_title);
            ivLaunchImage = itemView.findViewById(R.id.launch_rocket_image_view);
            txtLaunchDate = itemView.findViewById(R.id.txt_launch_date);
            txtLaunchLocation = itemView.findViewById(R.id.txt_launch_location);

            // Set the OnClickListener to entire view
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Image URL for the Rocket
            String currentRocketImageUrl = launchList.get(getAdapterPosition()).getRocket().getImageURL();

            // Launch Title
            String currentLaunchTitle = launchList.get(getAdapterPosition()).getName();

            // Launch Date
            String currentLaunchDate = launchList.get(getAdapterPosition()).getWindowstart();
            String currentLaunchDateLocal = getDate(currentLaunchDate);

            // Mission Name
            String currentMissionName = launchList.get(getAdapterPosition()).getMissions().get(0).getName();

            // Mission Summary
            String currentMissionSummary = launchList.get(getAdapterPosition()).getMissions().get(0).getDescription();

            //TODO putExtra Mission, Agency details

            Intent detailLaunchIntent = new Intent(mContext, LaunchDetailActivity.class);

            // PutExtra all information for LaunchDetailActivity.class
            detailLaunchIntent.putExtra(LAUNCH_TITLE_EXTRA, currentLaunchTitle);
            detailLaunchIntent.putExtra(ROCKET_IMAGE_EXTRA, currentRocketImageUrl);
            detailLaunchIntent.putExtra(MISSION_NAME_EXTRA, currentMissionName);
            detailLaunchIntent.putExtra(MISSION_SUMMARY_EXTRA, currentMissionSummary);
            detailLaunchIntent.putExtra(LAUNCH_WINDOW_START_EXTRA,currentLaunchDateLocal);

            mContext.startActivity(detailLaunchIntent);
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        launchList.clear();
        notifyDataSetChanged();
    }

    /**
     * This method parses JSON Time Response to Local Time
     * @param ourDate String of json time response, example: December 13, 2018 04:00:00 UTC
     * @return String of local time , example: December 13, 2018 10:00:00 CEST (If device in Germany)
     */
    private String getDate(String ourDate)
    {
        // Example Date String Response: "December 13, 2018 04:00:00 UTC"
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy HH:mm:ss z",Locale.ENGLISH);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(ourDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM d, yyyy HH:mm:ss z",Locale.ENGLISH); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            ourDate = dateFormatter.format(value);

            //Log.d("ourDate", ourDate);
        }
        catch (Exception e)
        {
            ourDate = "00-00-0000 00:00";
        }
        return ourDate;
    }

}
