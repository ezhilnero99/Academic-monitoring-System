package com.example.acmod.adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acmod.R;
import com.example.acmod.SearchActivity;
import com.example.acmod.models.school_details;
import com.example.acmod.utils.CommonUtils;
import com.example.acmod.utils.SharedPref;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchSchoolAdapter extends RecyclerView.Adapter<SearchSchoolAdapter.SearchViewHolder> {

    private ArrayList<school_details> school_details;
    private Context context;
    private Activity parent;

    public SearchSchoolAdapter(Context context, ArrayList<school_details> school_details, Activity parent) {
        this.context = context;
        this.school_details = school_details;
        this.parent = parent;
    }

    @NonNull
    @Override
    public SearchSchoolAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem;
        listItem = layoutInflater.inflate(R.layout.item_school_search, parent, false);

        return new SearchSchoolAdapter.SearchViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchSchoolAdapter.SearchViewHolder holder, int position) {
        final school_details model = (school_details) school_details.get(position);

        Picasso.get().load(model.getLogourl()).into(holder.logoIV);
        Log.i("adp_test", "onBindViewHolder: "+model.getLogourl());
        holder.nameTV.setText(model.getName());
        holder.addressTV.setText(model.getAddress());

        //Onclick listener for making calls
        holder.phoneIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callintent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", model.getContact(), null));

                if (ActivityCompat.checkSelfPermission(parent, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(parent,
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                    return;
                }
                callintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(callintent);
            }
        });
        //Onclick listener for navigating to schools
        holder.gpsIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(parent, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(parent, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(parent,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            1);

                }
                else {
                if (!CommonUtils.alerter(context)) {
                    CommonUtils.openCustomBrowser(context, "https://www.google.com/maps/place/" + model.getLocation().getLatitude() + "+" + model.getLocation().getLongitude());
                } else {
                    Toast.makeText(context, "Please Check Network connection", Toast.LENGTH_SHORT).show();
                }

            }}
        });
    }

    @Override
    public int getItemCount() {
        return school_details.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        public ImageView logoIV, phoneIV, gpsIV;
        public TextView nameTV, addressTV;

        public SearchViewHolder(View itemView) {
            super(itemView);
            logoIV = itemView.findViewById(R.id.logoIV);
            phoneIV = itemView.findViewById(R.id.phoneIV);
            nameTV = itemView.findViewById(R.id.nameTV);
            addressTV = itemView.findViewById(R.id.addressTV);
            gpsIV = itemView.findViewById(R.id.gpsIV);


        }
    }
}
