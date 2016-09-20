package com.amitupadhyay.clickin.nav_activitiy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amitupadhyay.clickin.R;
import com.amitupadhyay.clickin.ui.EnterDetailsActivity;
import com.amitupadhyay.clickin.ui.LoginActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import static com.squareup.picasso.Picasso.with;

public class ScrollingActivity extends AppCompatActivity {

    private ImageView userProfileImageView;
    private TextView userContactTextView;
    private TextView userAddressTextView;
    private TextView userEmailTextView;
    private SharedPreferences userDetailsSP;
    private Toolbar toolbar;

    public void initViews()
    {
        userDetailsSP = getSharedPreferences("user_details", Context.MODE_PRIVATE);

        userProfileImageView = (ImageView) findViewById(R.id.userProfileImageView);
        userContactTextView = (TextView) findViewById(R.id.tvUserNumber1);
        userAddressTextView = (TextView) findViewById(R.id.tvUserAddress1);
        userEmailTextView = (TextView) findViewById(R.id.tvUserEmail1);
    }

    public void setUserDetails()
    {
        String userName = userDetailsSP.getString("USER_NAME","");
        toolbar.setTitle(userName);

        Long userPhone = userDetailsSP.getLong("USER_PHONE",0);
        userContactTextView.setText(String.valueOf(userPhone));

        String userEmail = userDetailsSP.getString("USER_EMAIL","");
        userEmail = userEmail+"@gmail.com";
        userEmailTextView.setText(userEmail);

        String userAddress = userDetailsSP.getString("USER_ADDRESS","");
        userAddressTextView.setText(userAddress);

    }
    public void setUserProfileImage()
    {
        final String photoUrl = userDetailsSP.getString("USER_PHOTOURL", "");

        Picasso.with(ScrollingActivity.this).load(photoUrl).networkPolicy(NetworkPolicy.OFFLINE).into(userProfileImageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                with(ScrollingActivity.this).load(photoUrl).into(userProfileImageView);
            }
        });
    }

    public void checkIfSignedIn()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("signin_info", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString("SIGNIN", "").equals("true"))
        {
            Toast.makeText(this, "Please Signin", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ScrollingActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("User Name");
        setSupportActionBar(toolbar);

        initViews();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        checkIfSignedIn();

        setUserDetails();
        setUserProfileImage();

        Button updateContactDetailsButton = (Button) findViewById(R.id.updateContactDetailsButton);
        updateContactDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScrollingActivity.this, EnterDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}
