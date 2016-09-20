package com.amitupadhyay.clickin.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amitupadhyay.clickin.R;
import com.amitupadhyay.clickin.fragments.EntertainmentFragment;
import com.amitupadhyay.clickin.fragments.FashionFragment;
import com.amitupadhyay.clickin.fragments.HomeFragment;
import com.amitupadhyay.clickin.fragments.HomeServicesFragment;
import com.amitupadhyay.clickin.fragments.OthersFragment;
import com.amitupadhyay.clickin.fragments.TrendingFragment;
import com.amitupadhyay.clickin.fragments.VehicleServicesFragment;
import com.amitupadhyay.clickin.myadapters.ViewPagerAdapter;
import com.amitupadhyay.clickin.nav_activitiy.FeedbackActivity;
import com.amitupadhyay.clickin.nav_activitiy.ScrollingActivity;
import com.amitupadhyay.clickin.util.BitmapHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import java.util.Date;

import de.ecotastic.android.camerautil.lib.CameraIntentHelper;
import de.ecotastic.android.camerautil.lib.CameraIntentHelperCallback;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DialogInterface.OnClickListener {

    AlertDialog.Builder builder;
    AlertDialog dialog;

    // --------------------------------------- Capture image and display image from server code -------------------

    private StorageReference mUserRequestStorage;
    CameraIntentHelper mCameraIntentHelper;
    TextView messageView;
    private boolean checkIfImageCaptured = false;
    private EditText itemDescriptionEditText;
    private Uri userItemPhotoUri;
    private ProgressDialog imageUploadDialog;
    private Button submitRequestBtn;

    public void initCameraFeature()
    {
        mUserRequestStorage = FirebaseStorage.getInstance().getReference();
        submitRequestBtn = (Button) findViewById(R.id.urgentRequestSubmitBtn);
        messageView = (TextView) findViewById(R.id.activity_camera_intent_message);
        itemDescriptionEditText = (EditText) findViewById(R.id.itemDescriptionEditText);
        Button startCameraButton = (Button) findViewById(R.id.activity_camera_intent_start_camera_button);
        startCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCameraIntentHelper != null) {
                    mCameraIntentHelper.startCameraIntent();
                }
            }
        });

        setupCameraIntentHelper();

        class CreateSPFiles extends AsyncTask
        {
            @Override
            protected Object doInBackground(Object[] objects) {



                return null;
            }
        }

        final SharedPreferences isUserLogedIn = getSharedPreferences("signin_info", Context.MODE_PRIVATE);
        final SharedPreferences userDetailsSP = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        submitRequestBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                // check if image is captured or not
                if (!checkIfImageCaptured)
                {
                    return;
                }

                // first I'll check if your is loged in or not.
                if (isUserLogedIn.getString("SIGNIN", "").equals("true"))
                {
                    imageUploadDialog = new ProgressDialog(MainActivity.this);
                    imageUploadDialog.setMessage("Sending Data ...");
                    imageUploadDialog.show();
                    imageUploadDialog.setCancelable(false);
                    final String userItemDesc = itemDescriptionEditText.getText().toString().trim();
                    final String userName = userDetailsSP.getString("USER_NAME","");
                    final String userEmail = userDetailsSP.getString("USER_EMAIL","");
                    final Long userPhone = userDetailsSP.getLong("USER_PHONE",0);
                    final String[] imageUrl = new String[1];
                    // now upload the pic on server then store the data on the database

                    StorageReference filepath = mUserRequestStorage.child("photos").child(userItemPhotoUri.getLastPathSegment());

                    filepath.putFile(userItemPhotoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Uri downloadUri = taskSnapshot.getDownloadUrl();
                            if (downloadUri != null) {
                                imageUrl[0] = downloadUri.toString();
                            }
                            // now I need to store data to database
                            DatabaseReference mDBRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://clickinapp-ab9f7.firebaseio.com/UserUrgentRequest");

                            Toast.makeText(MainActivity.this, imageUrl[0], Toast.LENGTH_LONG).show();

                            DatabaseReference mEmailRef = mDBRef.child(userEmail);
                            DatabaseReference mNameRef = mEmailRef.child("Name");
                            mNameRef.setValue(userName);
                            DatabaseReference mImageUrlRef = mEmailRef.child("ImageUrl");
                            mImageUrlRef.setValue(imageUrl[0]);
                            DatabaseReference mPhoneRef = mEmailRef.child("Phone");
                            mPhoneRef.setValue(userPhone);

                            DatabaseReference mUserItemDesc = mEmailRef.child("ItemDescription");
                            mUserItemDesc.setValue(userItemDesc);

                            imageUploadDialog.dismiss();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            imageUploadDialog.dismiss();
                            return;
                        }
                    });

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Sign in please", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        });

    }

    private void setupCameraIntentHelper() {
        mCameraIntentHelper = new CameraIntentHelper(this, new CameraIntentHelperCallback() {
            @Override
            public void onPhotoUriFound(Date dateCameraIntentStarted, Uri photoUri, int rotateXDegrees) {
                //messageView.setText(getString(R.string.activity_camera_intent_photo_uri_found) + photoUri.toString());

                userItemPhotoUri = photoUri;

                Bitmap photo = BitmapHelper.readBitmap(MainActivity.this, photoUri);
                if (photo != null) {
                    photo = BitmapHelper.shrinkBitmap(photo, 300, rotateXDegrees);
                    ImageView imageView = (ImageView) findViewById(R.id.activity_camera_intent_image_view);
                    imageView.setImageBitmap(photo);
                    checkIfImageCaptured = true;
                }
            }

            @Override
            public void deletePhotoWithUri(Uri photoUri) {
                BitmapHelper.deleteImageWithUriIfExists(photoUri, MainActivity.this);
            }

            @Override
            public void onSdCardNotMounted() {
                Toast.makeText(getApplicationContext(), getString(R.string.error_sd_card_not_mounted), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCanceled() {
                Toast.makeText(getApplicationContext(), getString(R.string.warning_camera_intent_canceled), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCouldNotTakePhoto() {
                Toast.makeText(getApplicationContext(), getString(R.string.error_could_not_take_photo), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPhotoUriNotFound() {
                messageView.setText(getString(R.string.activity_camera_intent_photo_uri_not_found));
            }

            @Override
            public void logException(Exception e) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_sth_went_wrong), Toast.LENGTH_LONG).show();
                Log.d(getClass().getName(), e.getMessage());
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        mCameraIntentHelper.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCameraIntentHelper.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mCameraIntentHelper.onActivityResult(requestCode, resultCode, intent);
    }


    Toolbar toolbar;

    private SlidingUpPanelLayout mLayout;

    private LinearLayout bottomPannerLinearLayout;
    private LinearLayout serviceRowLinearLayout;

    public void initSlideUp()
    {
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

        mLayout.addPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, PanelState previousState, PanelState newState) {

            }
        });

        mLayout.setFadeOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(PanelState.COLLAPSED);
            }
        });

        TextView t = (TextView) findViewById(R.id.name);
        t.setText(Html.fromHtml(getString(R.string.hello)));
        Button f = (Button) findViewById(R.id.follow);
        f.setText(Html.fromHtml(getString(R.string.follow)));
        f.setMovementMethod(LinkMovementMethod.getInstance());

    }


    public void showAlertDialog()
    {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Confirm !");
        builder.setMessage("Do you want to exit?");

        builder.setPositiveButton("YES", this);
        builder.setNegativeButton("NO", this);

        dialog = builder.create();

        dialog.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i)
        {
            case DialogInterface.BUTTON_POSITIVE:
                finish();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                if (dialog.isShowing())
                    dialog.dismiss();
                break;
        }
    }

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    public void initTabLayout()
    {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragments(new HomeFragment(), "Home");
        viewPagerAdapter.addFragments(new TrendingFragment(), "Best Deals");
        viewPagerAdapter.addFragments(new HomeServicesFragment(), "Home Services");// includes Laundry, Electritian, Carpentry, Plumbing, Interior Designing. Housemaids, Bathroom Cleaning
        viewPagerAdapter.addFragments(new EntertainmentFragment(), "Entertainment"); //Event Organizing, Photography, Book a DJ, Travel Packages
        viewPagerAdapter.addFragments(new FashionFragment(), "Fashion"); //Salon & Parlour, Customised Apparel
        viewPagerAdapter.addFragments(new VehicleServicesFragment(), "Vehicle Services"); //Car Wash, Self Drive, Rent a Car, Automobile Sevicing
        viewPagerAdapter.addFragments(new OthersFragment(), "Other Services"); // Customised Cakes, Sofa Spa,Packers and Movers, Catering Services

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(6);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position)
                {
                    case 0:
                        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        bottomPannerLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        //serviceRowLinearLayout.setBackgroundColor(getResources().getColor(R.color.lightColorPrimary));
                        break;
                    case 1:
                        toolbar.setBackgroundColor(getResources().getColor(R.color.purple));
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.purple));
                        bottomPannerLinearLayout.setBackgroundColor(getResources().getColor(R.color.purple));
                        break;
                    case 2:
                        toolbar.setBackgroundColor(getResources().getColor(R.color.light_color));
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.light_color));
                        bottomPannerLinearLayout.setBackgroundColor(getResources().getColor(R.color.light_color));
                        break;
                    case 3:
                        toolbar.setBackgroundColor(getResources().getColor(R.color.blue));
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.blue));
                        bottomPannerLinearLayout.setBackgroundColor(getResources().getColor(R.color.blue));
                        break;
                    case 4:
                        toolbar.setBackgroundColor(getResources().getColor(R.color.whatsapp_color));
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.whatsapp_color));
                        bottomPannerLinearLayout.setBackgroundColor(getResources().getColor(R.color.whatsapp_color));
                        //serviceRowLinearLayout.setBackgroundColor(getResources().getColor(R.color.lightwhatsapp_color));
                        break;
                    case 5:
                        toolbar.setBackgroundColor(getResources().getColor(R.color.gray));
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.gray));
                        bottomPannerLinearLayout.setBackgroundColor(getResources().getColor(R.color.gray));
                        //serviceRowLinearLayout.setBackgroundColor(getResources().getColor(R.color.lightgray));
                        break;
                    case 6:
                        toolbar.setBackgroundColor(getResources().getColor(R.color.orange));
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.orange));
                        bottomPannerLinearLayout.setBackgroundColor(getResources().getColor(R.color.orange));
                        //serviceRowLinearLayout.setBackgroundColor(getResources().getColor(R.color.lightorange));
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomPannerLinearLayout = (LinearLayout) findViewById(R.id.bottomPannerLinearLayout);
        //serviceRowLinearLayout = (LinearLayout) findViewById(R.id.serviceRowLinearLayout);

        initSlideUp();
        initTabLayout();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initCameraFeature();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (mLayout != null &&
                (mLayout.getPanelState() == PanelState.EXPANDED || mLayout.getPanelState() == PanelState.ANCHORED)) {
            mLayout.setPanelState(PanelState.COLLAPSED);
        }
        else {
            showAlertDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_toggle);
        if (mLayout != null) {
            if (mLayout.getPanelState() == PanelState.HIDDEN) {
                item.setTitle(R.string.action_show);
            } else {
                item.setTitle(R.string.action_hide);
            }
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity com AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.login)// on click of this the user should get loged com
        {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            //overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );// this will Open the Activity from Bottom to Up.
            return true;
        }
        if (id == R.id.call_admin)
        {
            String posted_by = "7769942159";
            String uri = "tel:" + posted_by.trim() ;
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
            //overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );// this will Open the Activity from Bottom to Up.
            return true;
        }
        if (id == R.id.menu_item_share)
        {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Click In App");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, "The link of the app will be here");

            startActivity(Intent.createChooser(intent,"Share via"));
        }

        if (id == R.id.action_toggle)
        {
            if (mLayout != null) {
                if (mLayout.getPanelState() != PanelState.HIDDEN) {
                    mLayout.setPanelState(PanelState.HIDDEN);
                    item.setTitle(R.string.action_show);
                } else {
                    mLayout.setPanelState(PanelState.COLLAPSED);
                    item.setTitle(R.string.action_hide);
                }
            }
        }

        if (id == R.id.action_anchor)
        {
            if (mLayout != null) {
                if (mLayout.getAnchorPoint() == 1.0f) {
                    mLayout.setAnchorPoint(0.7f);
                    mLayout.setPanelState(PanelState.ANCHORED);
                    item.setTitle(R.string.action_anchor_disable);
                } else {
                    mLayout.setAnchorPoint(1.0f);
                    mLayout.setPanelState(PanelState.COLLAPSED);
                    item.setTitle(R.string.action_anchor_enable);
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home_id) // this will bring back the user to the HomeFragment.
        {
            viewPager.setCurrentItem(0);
        }
        else if (id == R.id.profile_id) // This will contain the user name and email.
        {
            startActivity(new Intent(MainActivity.this, ScrollingActivity.class));
            //overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
        }
        else if (id == R.id.cart_id) // this may be used by the user to check the products ordered by the user.
        {

        }
        else if (id == R.id.feedback_id) // user can give the feedback.
        {
            startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
            //overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );// this will Open the Activity from Bottom to Up.
        }
        else if (id == R.id.nav_trending_tab)
        {
            viewPager.setCurrentItem(1);
        }
        else if (id == R.id.nav_home_service_tab)
        {
            viewPager.setCurrentItem(2);
        }
        else if (id == R.id.nav_entertainment_tab)
        {
            viewPager.setCurrentItem(3);
        }
        else if (id == R.id.nav_fashion_tab)
        {
            viewPager.setCurrentItem(4);
        }
        else if (id == R.id.nav_vehical_service_tab)
        {
            viewPager.setCurrentItem(5);
        }
        else if (id == R.id.nav_other_service_tab)
        {
            viewPager.setCurrentItem(6);
        }
        else if (id == R.id.nav_share_app)
        {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Click In App");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, "The link of the app will be here");

            startActivity(Intent.createChooser(intent,"Share via"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
