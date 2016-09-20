package com.amitupadhyay.clickin.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.amitupadhyay.clickin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class PlaceOrderActivity extends AppCompatActivity {

    private String serviceClicked;
    private CircleImageView service_image_on_orderpage;
    private TextView service_name_on_orderpage;
    private TextView service_description_on_orderpage;
    private ImageView header_cover_image;

    private TextView select_date;
    private TextView select_time;

    private DatePickerDialog datePickerDialog;// this will be used to display the Date Picker Options
    private DatePickerDialog.OnDateSetListener onDateSetListener;// this will be used as event listener after the user has choosen date from the date picker.

    private TimePickerDialog timePickerDialog;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;

    int dd, mm, yy;
    int h, m;


    private DatabaseReference mRootRef;
    private DatabaseReference mLogoRootRef;


    public void getServiceClicked()
    {
        Bundle bundle = getIntent().getExtras();
        serviceClicked = bundle.getString("SERVICE_CLICKED");
    }

    public void initViews()
    {
        service_name_on_orderpage = (TextView) findViewById(R.id.service_name_on_orderpage);
        service_description_on_orderpage = (TextView) findViewById(R.id.service_description_on_orderpage);
        service_image_on_orderpage = (CircleImageView) findViewById(R.id.service_image_on_orderpage);
        header_cover_image = (ImageView) findViewById(R.id.header_cover_image);
        select_date = (TextView) findViewById(R.id.select_date);
        select_time = (TextView) findViewById(R.id.select_time);

    }

    public void setDetails()
    {
        service_name_on_orderpage.setText(serviceClicked);
    }

    public void setDateAndTime()
    {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        dd = calendar.get(Calendar.DAY_OF_MONTH);
        mm = calendar.get(Calendar.MONTH);
        yy = calendar.get(Calendar.YEAR);

        h = calendar.get(Calendar.HOUR_OF_DAY);
        m = calendar.get(Calendar.MINUTE);

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                select_date.setText(String.format("%s/%s/%s", String.valueOf(dd), String.valueOf(mm + 1), String.valueOf(yy)));
            }
        };

        datePickerDialog = new DatePickerDialog(this, onDateSetListener, yy, mm, dd);

        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String min = String.valueOf(i1);
                if(i1<10)
                    min = "0"+min;
                select_time.setText(String.format("%s:%s", String.valueOf(i), min));
            }
        };
        timePickerDialog = new TimePickerDialog(this, onTimeSetListener, h, m, true);

        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });
    }

    public void setCoverImage()
    {
        DatabaseReference mChildRootRef;
        DatabaseReference mChildLogoRootRef;

        if (serviceClicked.equals("Event Organizing"))
        {
            mChildRootRef = mRootRef.child(serviceClicked);
            mChildLogoRootRef = mLogoRootRef.child(serviceClicked);

            mChildRootRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final String coverImageLink = dataSnapshot.getValue(String.class);
                    Picasso.with(PlaceOrderActivity.this).load(coverImageLink).networkPolicy(NetworkPolicy.OFFLINE).into(header_cover_image, new Callback() {
                        @Override
                        public void onSuccess() {                        }
                        @Override
                        public void onError() {
                            Picasso.with(PlaceOrderActivity.this).load(coverImageLink).into(header_cover_image);
                        }
                    });
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mChildLogoRootRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final String logoImageLink = dataSnapshot.getValue(String.class);
                    Picasso.with(PlaceOrderActivity.this).load(logoImageLink).networkPolicy(NetworkPolicy.OFFLINE).into(service_image_on_orderpage, new Callback() {
                        @Override
                        public void onSuccess() {                        }
                        @Override
                        public void onError() {
                            Picasso.with(PlaceOrderActivity.this).load(logoImageLink).into(service_image_on_orderpage);
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_design_profile_screen_xml_ui_design);

        mRootRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://clickinapp-ab9f7.firebaseio.com/ImageGallery");
        mLogoRootRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://clickinapp-ab9f7.firebaseio.com/AllServices/LogoImages");

        getServiceClicked();
        initViews();
        setDetails();
        setDateAndTime();

        setCoverImage();
    }
}
