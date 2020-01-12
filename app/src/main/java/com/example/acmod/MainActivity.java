package com.example.acmod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.acmod.utils.SharedPref;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    EditText emailET, passwordET;
    ImageView googleIV,progressIV;
    Button loginBT;

    private static final String TAG = "login_test";

    //Initiating Google Sign-In variables
    //Add SHA-1 to ur Firebase Project
    //enable different sign-in methods in Firebase-Authentication
    Intent intent;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 111;

    //On-click listener for image view
    private View.OnClickListener submit_pressed = new View.OnClickListener() {
        public void onClick(View v) {
            mAuth.signOut();
            mGoogleSignInClient.signOut();
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        //initiating UI
        initUI();

        googleIV.setOnClickListener(submit_pressed);
        intent = new Intent(MainActivity.this, HomeActivity.class);
        initGoogleSignIn();
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_option(emailET.getText().toString().trim(), passwordET.getText().toString().trim());
            }
        });

    }

    private void initUI() {
        emailET = findViewById(R.id.inputTV);
        passwordET = findViewById(R.id.passwordTV);
        googleIV = findViewById(R.id.googleIV);
        loginBT = findViewById(R.id.loginIV);
        progressIV =findViewById(R.id.progressIV);

    }

    public void initGoogleSignIn() {
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        progressIV.setVisibility(View.VISIBLE);

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                final GoogleSignInAccount acct = task.getResult(ApiException.class);
                SharedPref.putString(getApplicationContext(), "sp_username", acct.getDisplayName());
                SharedPref.putString(getApplicationContext(), "sp_image_url", acct.getPhotoUrl().toString());
                SharedPref.putString(getApplicationContext(), "sp_email", acct.getEmail());
                Log.i(TAG, "onActivityResult: " + SharedPref.getString(getApplicationContext(), "image_url"));

                AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
                mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            SharedPref.putBoolean(getApplicationContext(), "sp_loggedin", true);
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            progressIV.setVisibility(View.GONE);
                        } else {
                            progressIV.setVisibility(View.GONE);
                            Log.d(TAG, "signInWithCredential:failure  " + task.getException().toString());
                        }
                    }
                });
                //else
                //Toast.makeText(this, "Please use SSN mail ID", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                progressIV.setVisibility(View.GONE);
                Log.d(TAG, "error-1: " + e.toString());


            }
        }
    }

    public void login_option(String email, String password) {
        progressIV.setVisibility(View.VISIBLE);
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                progressIV.setVisibility(View.GONE);
                                SharedPref.putBoolean(getApplicationContext(), "sp_loggedin", true);
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            } else {
                                progressIV.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                            }

                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressIV.setVisibility(View.GONE);
                            Log.d(TAG, "error-1: " + e.toString());
                        }
                    });
        }else {
            progressIV.setVisibility(View.GONE);
            Toast.makeText(this, "Credentials Empty", Toast.LENGTH_SHORT).show();
        }
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

