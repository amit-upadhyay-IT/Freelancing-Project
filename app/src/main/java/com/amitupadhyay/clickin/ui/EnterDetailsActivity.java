package com.amitupadhyay.clickin.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amitupadhyay.clickin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnterDetailsActivity extends FragmentActivity implements View.OnClickListener {

    private DatabaseReference mEmailRef;
    private DatabaseReference mPhoneRef;
    private DatabaseReference mAddressRef;
    private String myEmail;

    private SharedPreferences signinSP, userDetailsSP;

    private static final String KEY_SIGNIN = "SIGNIN";

    private EditText userPhoneNumber, userAddress;
    private Button sumbitButtonForDetails;

    private Long user_phone_number;
    private String user_address;

    public void initViews()
    {
        userPhoneNumber = (EditText) findViewById(R.id.userPhoneNumber);
        userAddress = (EditText) findViewById(R.id.userAddress);
        sumbitButtonForDetails = (Button) findViewById(R.id.sumbitButtonForDetails);

        sumbitButtonForDetails.setOnClickListener(this);

    }

    public void initSharedPreferences()
    {
        signinSP = getSharedPreferences("signin_info", Context.MODE_PRIVATE);
        userDetailsSP = getSharedPreferences("user_details", Context.MODE_PRIVATE);

        if (!userDetailsSP.getString("USER_EMAIL","").equals(""))
        {
            myEmail = userDetailsSP.getString("USER_EMAIL","");
        }
        else {
            myEmail = "empty";
        }

        if (signinSP.getString(KEY_SIGNIN, "").equals("true"))
        {
            userDetailsSP.edit().putLong("USER_PHONE", user_phone_number).apply();
            userDetailsSP.edit().putString("USER_ADDRESS", user_address).apply();
        }
        else
        {
            Toast.makeText(this, "User Not Signed com", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean setDataToDB()
    {
        if (!myEmail.equals("empty"))
        {
            String userRef = "https://clickinapp-ab9f7.firebaseio.com/Users/";
            String myEmailRef = userRef+myEmail;
            mEmailRef = FirebaseDatabase.getInstance().getReferenceFromUrl(myEmailRef);

            mPhoneRef = mEmailRef.child("Phone");
            mPhoneRef.setValue(user_phone_number);

            mAddressRef = mEmailRef.child("Address");
            mAddressRef.setValue(user_address);
            return true;
        }
        else
        {
            Toast.makeText(this, "Error com saving data on Server", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_details);

        initViews();



    }

    @Override
    public void onClick(View view) {

        if (!userPhoneNumber.getText().toString().equals("") && !userAddress.getText().toString().equals(""))
        {
            user_phone_number = Long.parseLong(userPhoneNumber.getText().toString().trim());
            user_address = userAddress.getText().toString().trim();

            initSharedPreferences();

            boolean b = setDataToDB();
            if (b)
            {
                startActivity(new Intent(EnterDetailsActivity.this, MainActivity.class));
                finishAffinity();
            }

        }
        else
        {
            Toast.makeText(this, "Empty Feild can't be Accepted", Toast.LENGTH_SHORT).show();
        }
    }
}
