package com.example.acmod;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
    LinearLayout gpsLL , listLL , off_reportLL , ana_reportLL;
    CircleImageView dpIV;
    ImageView settingsIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //setting status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.dull_blue));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //intiating UI
        initUI();
        //applying DP Image
        Picasso.get().load(SharedPref.getString(getApplicationContext(), "sp_image_url")).into(dpIV);

    }

    private void initUI() {
        gpsLL = findViewById(R.id.LL1_L1);
        listLL = findViewById(R.id.LL1_L2);
        off_reportLL = findViewById(R.id.LL1_L3);
        ana_reportLL = findViewById(R.id.LL1_L4);
        dpIV = findViewById(R.id.dpIV);
        settingsIV = findViewById(R.id.settingIV);

    }
}
