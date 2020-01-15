package com.example.acmod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.acmod.adapters.ViewPagerAdapter;
import com.example.acmod.fragments.AcademicFragment;
import com.example.acmod.fragments.InfrastructureFragment;
import com.example.acmod.fragments.PaedogocicalFragment;
import com.example.acmod.utils.SharedPref;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class QuestionsActivity extends AppCompatActivity {

    ViewPager fragmentVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        //setting status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        fragmentVP = findViewById(R.id.viewPager);
        setupViewPager();

    }

    void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new InfrastructureFragment(), "Infrastucture");
        adapter.addFragment(new AcademicFragment(), "Academic");
        adapter.addFragment(new PaedogocicalFragment(), "Pedogogical");
        fragmentVP.setAdapter(adapter);
        fragmentVP.setOffscreenPageLimit(3);
    }
}
