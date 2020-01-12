package com.example.acmod;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.acmod.utils.SharedPref;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
    RelativeLayout gpsLL, listLL, off_reportLL;
    CircleImageView dpIV;
    ImageView notifyIV, logoutIV;
    TextView usernameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //setting status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        //intiating UI
        initUI();

        //
        usernameTV.setText(SharedPref.getString(getApplicationContext(), "sp_username"));
//        Toast.makeText(this, SharedPref.getString(getApplicationContext(), "sp_username"), Toast.LENGTH_SHORT).show();

        //applying DP Image
        Picasso.get().load(SharedPref.getString(getApplicationContext(), "sp_image_url")).into(dpIV);

        //Onclick Listener for gps
        gpsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gpsintent = new Intent(getApplicationContext(), GpsActivity.class);
                startActivity(gpsintent);
            }
        });
        listLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listintent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(listintent);
            }
        });

        logoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignoutDialog();
            }
        });

    }

    private void initUI() {
        gpsLL = findViewById(R.id.sclfinderRL);
        listLL = findViewById(R.id.searchRL);
        off_reportLL = findViewById(R.id.reportsRL);
        dpIV = findViewById(R.id.dpIV);
        notifyIV = findViewById(R.id.notifications);
        usernameTV = findViewById(R.id.usernameTV);
        logoutIV = findViewById(R.id.logoutIV);
    }

    //logout alert box Display
    void SignoutDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_logout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        TextView noTV = dialogView.findViewById(R.id.noTV);
        TextView yesTV = dialogView.findViewById(R.id.yesTV);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();


        yesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.removeAll(getApplicationContext());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });
        noTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(startMain);
        finishAffinity();
        finish();
    }
}
