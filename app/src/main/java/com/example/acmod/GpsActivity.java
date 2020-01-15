package com.example.acmod;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.acmod.models.school_details;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class GpsActivity extends AppCompatActivity {
    ImageView backIV;
    RelativeLayout animeRL;
    LinearLayout animationLL;
    RecyclerView schooldetailsRV;
    private static final String TAG = "gps_act_test";

    //firestore Initialization
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference schoolcolref = db.collection("school");
    FirestoreRecyclerAdapter adapter;

    //Static firestore keys
    final static String sname = "name";
    final static String saddress = "address";
    final static String scontact = "contact";
    final static String slocation = "location";
    final static String sid = "id";
    final static String slogourl = "logo_url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        //setting status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        //initiating UI elements
        intiUI();

        //animation
        animationLL.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animationLL.setVisibility(View.INVISIBLE);
                schooldetailsRV.setVisibility(View.VISIBLE);
            }
        }, 2000);

        //backbutton
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //setting up firestore data
        setupFireStore();
    }

    private void intiUI() {
        animeRL = findViewById(R.id.animeRL);
        backIV = findViewById(R.id.backIV);
        schooldetailsRV = findViewById(R.id.schooldetailsRV);
        animationLL = findViewById(R.id.animationLL);
        //setting layout manager for recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        schooldetailsRV.setLayoutManager(layoutManager);
    }

    void setupFireStore() {

        FirestoreRecyclerOptions<school_details> options = new FirestoreRecyclerOptions.Builder<school_details>().setQuery(schoolcolref, new SnapshotParser<school_details>() {
            @NonNull
            @Override
            public school_details parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                final school_details post = new school_details();
                post.setName(snapshot.getString(sname));
                post.setAddress(snapshot.getString(saddress));
                post.setContact(snapshot.getString(scontact));
                post.setId(snapshot.getString(sid));
                post.setLocation(snapshot.getGeoPoint(slocation));
                post.setLogourl(snapshot.getString(slogourl));
                return post;
            }
        }).build();

        adapter = new FirestoreRecyclerAdapter<school_details, FeedViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FeedViewHolder holder, int i, @NonNull final school_details post_det) {
                //setting values for holder elements
                Log.i(TAG, "onBindViewHolder: " + i);
                Picasso.get().load(post_det.getLogourl()).into(holder.logoIV);
                holder.nameTV.setText(post_det.getName());
                holder.addressTV.setText(post_det.getAddress());

                //Onclick listener for making calls
                holder.phoneIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callintent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", post_det.getContact(), null));

                        if (ActivityCompat.checkSelfPermission(GpsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(GpsActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    1);
                            return;
                        }
                        startActivity(callintent);
                    }
                });
                holder.searchRL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),QuestionsActivity.class);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public FeedViewHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_school_details, group, false);
                return new FeedViewHolder(view);
            }

            @Override
            public int getItemCount() {
                return super.getItemCount();
            }
        };

        schooldetailsRV.setAdapter(adapter);
    }

    /*********************************************************/

    public class FeedViewHolder extends RecyclerView.ViewHolder {
        public ImageView logoIV, phoneIV;
        public TextView nameTV, addressTV;
        public RelativeLayout searchRL;

        public FeedViewHolder(View itemView) {
            super(itemView);
            logoIV = itemView.findViewById(R.id.logoIV);
            phoneIV = itemView.findViewById(R.id.phoneIV);
            nameTV = itemView.findViewById(R.id.nameTV);
            addressTV = itemView.findViewById(R.id.addressTV);
            searchRL = itemView.findViewById(R.id.searchRL);


        }
    }

    /*********************************************************/

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null)
            adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
