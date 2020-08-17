package com.example.acmod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acmod.adapters.SearchSchoolAdapter;
import com.example.acmod.models.school_details;
import com.example.acmod.utils.CommonUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    ImageView backIV, searchIV, progressIV;
    EditText searchET;
    RecyclerView schooldetailsRV;

    //Variables for fetching Data from fireStore
    private static final String TAG = "search_act_test";
    ArrayList<String> schoolNames = new ArrayList<>();
    ArrayList<String> schoolLocation = new ArrayList<>();
    ArrayList<school_details> school_det = new ArrayList<>();
    ArrayList<school_details> result_school_det = new ArrayList<>();
    String key = "";

    //firestore Initialization
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference schoolcolref = db.collection("school");
    SearchSchoolAdapter adapter;

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
        setContentView(R.layout.activity_search);

        //setting status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        //initiate UI
        initUI();

        //fetch details
        fetchdata();

        //back button working
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchET.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    CommonUtils.hideKeyboard(SearchActivity.this);
                    searchOp();
                    return true;
                }
                return false;
            }
        });

        //search for school
        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchOp();
            }
        });
    }

    private void searchOp() {
        result_school_det.clear();
        CommonUtils.hideKeyboard(SearchActivity.this);
        key = searchET.getText().toString().toLowerCase().trim();
        if (!key.isEmpty()) {
            for (int i = 0; i < school_det.size(); i++) {
                if (school_det.get(i).getName().toLowerCase().contains(key) || school_det.get(i).getAddress().toLowerCase().contains(key)) {
                    result_school_det.add(school_det.get(i));
                }
            }
            if (result_school_det != null) {
                progressIV.setVisibility(View.GONE);
            }
            schooldetailsRV.setAdapter(new SearchSchoolAdapter(getApplicationContext(), result_school_det, SearchActivity.this));
        }
        else {
            Toast.makeText(this, "Search Key Empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchdata() {
        schoolcolref.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            school_details item = new school_details();
                            item.setLogourl(document.getString(slogourl));
                            item.setLocation(document.getGeoPoint(slocation));
                            item.setId(document.getString(sid));
                            item.setContact(document.getString(scontact));
                            item.setAddress(document.getString(saddress));
                            item.setName(document.getString(sname));

                            Log.i(TAG, "onSuccess: " + item.getLogourl() + "\n" + item.getAddress() + "\n" + item.getName() + "\n" + item.getLocation() + "\n" + item.getContact() + "\n");
                            school_det.add(item);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SearchActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initUI() {
        searchIV = findViewById(R.id.searchIV);
        backIV = findViewById(R.id.backIV);
        progressIV = findViewById(R.id.progressIV);
        searchET = findViewById(R.id.searchET);
        schooldetailsRV = findViewById(R.id.schooldetailsRV);
        //setting layout manager for recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        schooldetailsRV.setLayoutManager(layoutManager);
        //progress gif
        progressIV.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
