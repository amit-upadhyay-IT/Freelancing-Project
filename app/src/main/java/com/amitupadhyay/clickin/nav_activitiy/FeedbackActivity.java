package com.amitupadhyay.clickin.nav_activitiy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amitupadhyay.clickin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnClickListener {

    private Toolbar feedback_toolbar;

    private DatabaseReference mFeedbackRef;
    private DatabaseReference mEmailRef;
    private DatabaseReference mRatingRef;
    private DatabaseReference mNameRef;
    private DatabaseReference mCommentRef;
    private SharedPreferences userDetailsSP;

    private Feedback obj;

    public void initToolbar()
    {
        feedback_toolbar = (Toolbar) findViewById(R.id.feedback_toolbar);
        feedback_toolbar.setTitle("Feedback");
        feedback_toolbar.setSubtitle("user_name");
        setSupportActionBar(feedback_toolbar);
    }

    private RadioGroup feedback_radio_group;
    private Button feedback_sumbit;
    private EditText feedback_comment;

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        finish();
    }

    public class Feedback
    {
        private int vote;
        private String comment;

        public int getVote() {
            return vote;
        }

        public void setVote(int vote) {
            this.vote = vote;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
    public void initViews()
    {
        feedback_radio_group = (RadioGroup) findViewById(R.id.feedback_radio_group);
        feedback_sumbit = (Button) findViewById(R.id.feedback_submit);
        feedback_comment = (EditText) findViewById(R.id.feedback_comment);

        feedback_sumbit.setOnClickListener(this);
    }

    AlertDialog.Builder builder;
    AlertDialog dialog;

    public void showAlertDialog()
    {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Feedback Submited Sucessfully !");
        builder.setMessage("Thank you for taking out your time and writing to us.");

        builder.setPositiveButton("OK", this);

        dialog = builder.create();

        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        mFeedbackRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://clickinapp-ab9f7.firebaseio.com/UsersFeedback");

        initToolbar();
        initViews();
    }

    public void storeFeedback()
    {
        userDetailsSP = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        String userEmail = userDetailsSP.getString("USER_EMAIL","");
        String userName = userDetailsSP.getString("USER_NAME","");

        mEmailRef = mFeedbackRef.child(userEmail);

        mNameRef = mEmailRef.child("Name");
        mNameRef.setValue(userName);

        mCommentRef = mEmailRef.child("Comment");
        mCommentRef.setValue("Good");
        Toast.makeText(this, obj.getComment(), Toast.LENGTH_SHORT).show();
        mCommentRef.setValue(obj.getComment());

        mRatingRef = mEmailRef.child("Rating");
        mRatingRef.setValue(obj.getVote());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feedback_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.feedback_close) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {

        obj = new Feedback();

        obj.setComment(feedback_comment.getText().toString().trim()); // setting the comment
        obj.setVote(0);// setting the vote to 0 so that at the end of this method we can check if the user has given feedback or not.

        switch (feedback_radio_group.getCheckedRadioButtonId())
        {
            case R.id.feedback_excellent:
                obj.setVote(5);     // setting the vote
                break;
            case R.id.feedback_very_good:
                obj.setVote(4);
                break;
            case R.id.feedback_good:
                obj.setVote(3);
                break;
            case R.id.feedback_average:
                obj.setVote(2);
                break;
            case R.id.feedback_poor:
                obj.setVote(1);
                break;
        }

        if(obj.getVote() == 0)
        {
            Toast.makeText(this, "Please Give the Rating", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            storeFeedback();
            showAlertDialog();
        }
    }
}
