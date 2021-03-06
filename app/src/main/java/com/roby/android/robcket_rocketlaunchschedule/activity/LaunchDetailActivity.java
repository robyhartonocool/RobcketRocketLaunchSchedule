package com.roby.android.robcket_rocketlaunchschedule.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.roby.android.robcket_rocketlaunchschedule.R;
import com.roby.android.robcket_rocketlaunchschedule.utils.AppBarStateChangeListener;
import com.roby.android.robcket_rocketlaunchschedule.utils.URLSpanNoUnderline;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LaunchDetailActivity extends AppCompatActivity {

    // Variables for toolbar
    private AppBarLayout mAppBarLayout;
    private ImageView mLaunchRocketImageView;
    private TextView mLaunchTitleTextView;
    private FloatingActionButton mShareFloatingActionButton;
    private FloatingActionButton mWatchFloatingActionButton;


    // Variables for Details
    private TextView mLaunchDateTextView;
    private TextView mLaunchWindowTextView;
    // private Button mLaunchWatchButton;
    // private Button mLaunchShareButton;

    // Variables for Missions
    private TextView mLaunchMissionNameTextView;
    private TextView mLaunchMissionSummaryTextView;

    // Variables for Rocket
    private TextView mLaunchRocketNameTextView;

    // Variables for Pad
    private TextView mLaunchPadNameTextView;

    // Variables for Agency
    private TextView mLaunchAgencyNameTextView;

    // Variables for putExtra Intents
    private String ROCKET_IMAGE_EXTRA = "LAUNCH_IMAGE_URL";
    private String LAUNCH_TITLE_EXTRA = "LAUNCH_TITLE";
    private String MISSION_NAME_EXTRA = "LAUNCH_MISSION_NAME";
    private String MISSION_SUMMARY_EXTRA = "LAUNCH_MISSION_SUMMARY";
    private String LAUNCH_DATE_EXTRA = "LAUNCH_DATE";
    private String LAUNCH_WINDOW_EXTRA = "LAUNCH_WINDOW";
    private String LAUNCH_VID_URL_EXTRA = "LAUNCH_VID_URL";
    private String ROCKET_NAME_EXTRA = "LAUNCH_ROCKET_NAME";
    private String ROCKET_WIKI_URL_EXTRA = "LAUNCH_ROCKET_WIKI_URL";
    private String PAD_NAME_EXTRA = "LAUNCH_PAD";
    private String PAD_MAP_URL_EXTRA = "LAUNCH_PAD_MAP_URL";
    private String AGENCY_NAME_EXTRA = "LAUNCH_AGENCY_NAME";
    private String AGENCY_WIKI_URL_EXTRA = "LAUNCH_AGENCY_WIKI_URL";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Add back animation
        Animatoo.animateZoom(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // AppBarLayout
        mAppBarLayout = findViewById(R.id.detail_app_bar);

        // Display back button on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set toolbar collapsing image
        mLaunchRocketImageView = findViewById(R.id.launch_rocket_detail_image_view);
        Picasso.with(this)
                .load(getIntent().getStringExtra(ROCKET_IMAGE_EXTRA))
                .placeholder(R.drawable.ic_rocket)
                .into(mLaunchRocketImageView);

        // Set title of toolbar with launch name
        getSupportActionBar().setTitle(getIntent().getStringExtra(LAUNCH_TITLE_EXTRA));

        // Set Floating Action Button on the collapsing toolbar
        setFabButton();

        // Set Details CardView information
        setDetailInformation();

        // Set Mission CardView information
        setMissionInformation();

        // Set Rocket CardView information
        setRocketInformation();

        // Set Pad CardView information
        setPadInformation();

        // Set Agency CardView information
        setAgencyInformation();
    }

    /**
     * This Method sets the floating action buttons on the collapsing app toolbar
     */
    private void setFabButton() {
        // Set Floating Action Button Share
        mShareFloatingActionButton = findViewById(R.id.fab_share);

        // Set Floating Action Button Watch
        mWatchFloatingActionButton = findViewById(R.id.fab_watch);


        // Set onClickListener for share button
        mShareFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Share the launch
                setTapViewShare();
            }
        });

        // Set onClickListener for watch button
        mWatchFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Watch the launch
                setTapViewWatch();
            }
        });

        // Hide the LinearLayout that contains these buttons when app bar collapsed or expanded
        // These sequences below use the abstract class in utils folder AppBarStateChangeListener
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                switch (state) {
                    case COLLAPSED:
                        mWatchFloatingActionButton.hide();
                        mShareFloatingActionButton.hide();
                        break;
                    case EXPANDED:
                        mWatchFloatingActionButton.show();
                        mShareFloatingActionButton.show();
                        break;
                    case IDLE:
                        mWatchFloatingActionButton.show();
                        mShareFloatingActionButton.show();
                        break;
                }
            }
        });

    }

    /**
     * This Method sets all information in Mission CardView
     */
    private void setMissionInformation() {
        // Set Mission Title
        mLaunchMissionNameTextView = findViewById(R.id.textview_launch_mission_name);
        mLaunchMissionNameTextView.setText(getIntent().getStringExtra(MISSION_NAME_EXTRA));

        // Set Mission Summary
        mLaunchMissionSummaryTextView = findViewById(R.id.textview_launch_mission_details);
        mLaunchMissionSummaryTextView.setText(getIntent().getStringExtra(MISSION_SUMMARY_EXTRA));
    }

    /**
     * This method sets all information in Details CardView
     */
    private void setDetailInformation() {
        // Set Launch Date
        mLaunchDateTextView = findViewById(R.id.textview_date_value);
        mLaunchDateTextView.setText(getIntent().getStringExtra(LAUNCH_DATE_EXTRA));

        // Set Launch Window
        mLaunchWindowTextView = findViewById(R.id.textview_window_value);
        mLaunchWindowTextView.setText(getIntent().getStringExtra(LAUNCH_WINDOW_EXTRA));

    }

    /**
     * This method sets all information in Rocket CardView
     */
    private void setRocketInformation() {
        // Set Rocket Name
        mLaunchRocketNameTextView = findViewById(R.id.textview_launch_rocket_name);

        // Set HyperLink to Rocket Name TextView
        mLaunchRocketNameTextView.setMovementMethod(LinkMovementMethod.getInstance());

        // Set Rocket Name and Rocket Wiki URL with value in Intent Extra
        String rocketName = getIntent().getStringExtra(ROCKET_NAME_EXTRA);
        String rocketNameWikiUrl = getIntent().getStringExtra(ROCKET_WIKI_URL_EXTRA);

        // Set Rocket Name
        mLaunchRocketNameTextView.setText(rocketName);

        // Set OnClickListener on the TextView
        mLaunchRocketNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rocketWebViewIntent = new Intent(LaunchDetailActivity.this, WebViewActivity.class);
                rocketWebViewIntent.putExtra(ROCKET_WIKI_URL_EXTRA, getIntent().getStringExtra(ROCKET_WIKI_URL_EXTRA));
                rocketWebViewIntent.putExtra(ROCKET_NAME_EXTRA, getIntent().getStringExtra(ROCKET_NAME_EXTRA));
                startActivity(rocketWebViewIntent);

                // Animation to WebView Activity
                Animatoo.animateZoom(LaunchDetailActivity.this);
            }
        });

        // Set Text Color
        mLaunchRocketNameTextView.setTextColor(getResources().getColor(R.color.secondaryColor));


    }

    /**
     * This method sets all information in Pad CardView
     */
    private void setPadInformation() {
        // Set Pad Name
        mLaunchPadNameTextView = findViewById(R.id.textview_launch_pad);

        // Set HyperLink to Rocket Name TextView
        mLaunchPadNameTextView.setMovementMethod(LinkMovementMethod.getInstance());

        // Set Rocket Name and Rocket Map Url with value in Intent Extra
        String padName = getIntent().getStringExtra(PAD_NAME_EXTRA);
        String padMapUrl = getIntent().getStringExtra(PAD_MAP_URL_EXTRA);

        // Create HyperLinkText
        String padNameHyperLinkText = String.format("<a href='%s'> %s </a>", padMapUrl, padName);

        // Set Pad Name
        mLaunchPadNameTextView.setText(Html.fromHtml(padNameHyperLinkText));

        // Remove Underline from textview
        stripUnderlines(mLaunchPadNameTextView);
    }

    /**
     * This method sets all information in Agency CardView
     */
    private void setAgencyInformation() {
        // Set Agency Name
        mLaunchAgencyNameTextView = findViewById(R.id.textview_launch_agency);

        // Set HyperLink to Agency Name TextView
        mLaunchAgencyNameTextView.setMovementMethod(LinkMovementMethod.getInstance());

        // Set Agency Name and Agency WikiUrl with value in Intent Extra
        String agencyName = getIntent().getStringExtra(AGENCY_NAME_EXTRA);
        String agencyWikiUrl = getIntent().getStringExtra(AGENCY_WIKI_URL_EXTRA);

        // Set the OnClickListener
        mLaunchAgencyNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent agencyWebViewIntent = new Intent(LaunchDetailActivity.this, WebViewActivity.class);
                agencyWebViewIntent.putExtra(AGENCY_WIKI_URL_EXTRA, getIntent().getStringExtra(AGENCY_WIKI_URL_EXTRA));
                agencyWebViewIntent.putExtra(AGENCY_NAME_EXTRA, getIntent().getStringExtra(AGENCY_NAME_EXTRA));
                startActivity(agencyWebViewIntent);

                // Animation to WebView Activity
                Animatoo.animateZoom(LaunchDetailActivity.this);
            }
        });

        // Set Agency Name
        mLaunchAgencyNameTextView.setText(agencyName);

        // Set TextView Color
        mLaunchAgencyNameTextView.setTextColor(getResources().getColor(R.color.secondaryColor));

    }

    /**
     * This method removes underline on TextView Hyperlink and set it in TextView
     *
     * @param textView
     */
    private void stripUnderlines(TextView textView) {
        Spannable s = new SpannableString(textView.getText());
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for (URLSpan span : spans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            s.setSpan(span, start, end, 0);
        }
        textView.setText(s);
    }

    /**
     * This method sets TapView effect on the share button
     */
    private void setTapViewShare() {
        // TapTargetView Effect for the share button
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.fab_share), "Share", "Tell your friends about this launch.")
                        // All options below are optional
                        .outerCircleColor(R.color.secondaryLightColor)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)                            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.md_white_1000)           // Specify a color for the target circle
                        .titleTextSize(24)                                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.md_white_1000)              // Specify the color of the title text
                        .descriptionTextSize(12)                            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.md_white_1000)        // Specify the color of the description text
                        .textColor(R.color.md_white_1000)                   // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)                  // Specify a typeface for the text
                        .dimColor(R.color.md_black_1000)                    // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                                   // Whether to draw a drop shadow or not
                        .cancelable(true)                                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                                   // Whether to tint the target view's color
                        .transparentTarget(false)                           // Specify whether the target is transparent (displays the content underneath)
                        .icon(getApplicationContext().getResources().getDrawable(R.drawable.ic_share))                     // Specify a custom drawable to draw as the target
                        .targetRadius(60),                                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {                              // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional

                        // Share based on the launch information
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, String.format("Catch the launch of the %s by %s on %s",
                                getIntent().getStringExtra(LAUNCH_TITLE_EXTRA),
                                getIntent().getStringExtra(AGENCY_NAME_EXTRA),
                                getIntent().getStringExtra(LAUNCH_DATE_EXTRA)));
                        shareIntent.setType("text/plain");
                        startActivity(shareIntent);
                    }
                });
    }

    /**
     * This method sets TapView effect to the watch button
     */
    private void setTapViewWatch() {
        // TapTargetView Effect for the share button
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.fab_watch), "Watch Launch Live", "You will be redirected to a live stream if we found one.")
                        // All options below are optional
                        .outerCircleColor(R.color.secondaryLightColor)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)                            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.md_white_1000)           // Specify a color for the target circle
                        .titleTextSize(24)                                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.md_white_1000)              // Specify the color of the title text
                        .descriptionTextSize(12)                            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.md_white_1000)        // Specify the color of the description text
                        .textColor(R.color.md_white_1000)                   // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)                  // Specify a typeface for the text
                        .dimColor(R.color.md_black_1000)                    // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                                   // Whether to draw a drop shadow or not
                        .cancelable(true)                                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                                   // Whether to tint the target view's color
                        .transparentTarget(false)                           // Specify whether the target is transparent (displays the content underneath)
                        .icon(getApplicationContext().getResources().getDrawable(R.drawable.ic_notification_rocket))                     // Specify a custom drawable to draw as the target
                        .targetRadius(60),                                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {                              // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional

                        // Watch launch sequences
                        // Get Url from extra
                        String url = getIntent().getStringExtra(LAUNCH_VID_URL_EXTRA);

                        // If url string is empty then show Toast that says no stream available
                        if (!url.equals("empty")) {
                            // Set Intent to redirect to link
                            Intent watchIntent = new Intent(Intent.ACTION_VIEW);
                            watchIntent.setData(Uri.parse(url));
                            startActivity(watchIntent);
                        } else {
                            Toast.makeText(LaunchDetailActivity.this, "No live stream found for the launch", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


}
