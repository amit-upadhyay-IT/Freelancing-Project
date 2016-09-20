package com.amitupadhyay.clickin.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amitupadhyay.clickin.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private SignInButton signInButton;
    private Button googleSignOut;

    private ProgressDialog progress;

    private SharedPreferences sharedPreferences;

    private SharedPreferences userDetailsSP;

    private static final String KEY_SIGNIN = "SIGNIN";

    private DatabaseReference mUserRef;
    private DatabaseReference mEmailRef;
    private DatabaseReference mNameRef;
    private DatabaseReference mPhotoUrlRef;
    private DatabaseReference mPhoneRef;
    private DatabaseReference mAddressRef;
    private DatabaseReference mOrdersPlacedRef;
    private DatabaseReference mPendingOrdersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://clickinapp-ab9f7.firebaseio.com/Users");

        signInButton = (SignInButton) findViewById(R.id.googleSignIn);
        signInButton.setOnClickListener(this);
        signInButton.setVisibility(View.GONE);

        googleSignOut = (Button) findViewById(R.id.googleSignOut);
        googleSignOut.setOnClickListener(this);
        googleSignOut.setVisibility(View.GONE);

        sharedPreferences = getSharedPreferences("signin_info", Context.MODE_PRIVATE);
        userDetailsSP = getSharedPreferences("user_details", Context.MODE_PRIVATE);


        if (sharedPreferences.getString(KEY_SIGNIN, "").equals("") || sharedPreferences.getString(KEY_SIGNIN, "").equals("false"))
        {
            signInButton.setVisibility(View.VISIBLE);
        }
        if (sharedPreferences.getString(KEY_SIGNIN, "").equals("true"))
        {
            googleSignOut.setVisibility(View.VISIBLE);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_LONG).show();
    }


    public void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    public void handleSignInResult(GoogleSignInResult result)
    {
        if (result.isSuccess())
        {
            progress.dismiss();

            GoogleSignInAccount acct = result.getSignInAccount();
            String name = null;
            String email = null;
            String photoUrl = null;
            if (acct != null) {
                name = acct.getDisplayName();
                email = acct.getEmail();
                photoUrl = acct.getPhotoUrl().toString().trim();
            }


            sharedPreferences.edit().putString(KEY_SIGNIN, "true").apply();

            setDataIntoDatabase(email, name, photoUrl);

            Intent intent = new Intent(LoginActivity.this, EnterDetailsActivity.class);
            startActivity(intent);
            overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

            Toast.makeText(this, "Hello "+name, Toast.LENGTH_LONG).show();
        }
        else
        {

        }
    }


    public void signOut()
    {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Toast.makeText(LoginActivity.this, "Signed Out", Toast.LENGTH_LONG).show();
                sharedPreferences.edit().putString(KEY_SIGNIN, "false").apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.googleSignIn:
                progress = ProgressDialog.show(LoginActivity.this, "Please Wait", "Signing com...", true);
                signIn();
                break;

            case R.id.googleSignOut:
                signOut();
                break;
        }

    }

    public void setDataIntoDatabase(String email, String name, String photoUrl)
    {
        int emailLen = email.length();
        String emailWithoutGmail = email.substring(0, emailLen-10) ;

        mEmailRef = mUserRef.child(emailWithoutGmail);

        mNameRef = mEmailRef.child("Name");
        mNameRef.setValue(name);

        mPhotoUrlRef = mEmailRef.child("Photo Url");
        mPhotoUrlRef.setValue(photoUrl);

        mPhoneRef = mEmailRef.child("Phone");
        mPhoneRef.setValue(0);

        mAddressRef = mEmailRef.child("Address");
        mAddressRef.setValue("No Address");

        mOrdersPlacedRef = mEmailRef.child("Orders Placed");
        DatabaseReference mOrdersPlacedRef1 = mOrdersPlacedRef.child("Order 1");
        mOrdersPlacedRef1.setValue("No Order Placed");

        mPendingOrdersRef = mEmailRef.child("Pending Orders Placed");
        DatabaseReference mPendingOrdersRef1 = mPendingOrdersRef.child("Order 1");
        mPendingOrdersRef1.setValue("No Order Placed");

        userDetailsSP.edit().putString("USER_EMAIL", emailWithoutGmail).apply();
        userDetailsSP.edit().putString("USER_NAME", name).apply();
        userDetailsSP.edit().putString("USER_PHOTOURL", photoUrl).apply();
        userDetailsSP.edit().putLong("USER_PHONE", 0).apply();
        userDetailsSP.edit().putString("USER_ADDRESS", "No Address").apply();

    }

}
