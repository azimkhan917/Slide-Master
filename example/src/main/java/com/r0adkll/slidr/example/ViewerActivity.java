package com.r0adkll.slidr.example;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.r0adkll.deadskunk.utils.Utils;
import com.r0adkll.deadskunk.views.AspectRatioImageView;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.example.model.AndroidOS;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by r0adkll on 1/11/15.
 */
public class ViewerActivity extends ActionBarActivity {

    public static final String EXTRA_OS = "extra_os_version";

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.cover)
    AspectRatioImageView mCover;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.description)
    TextView mDescription;
    @InjectView(R.id.date)
    TextView mDate;
    @InjectView(R.id.version)
    TextView mVersion;
    @InjectView(R.id.sdk)
    TextView mSdk;

    private AndroidOS mOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        ButterKnife.inject(this);

        // Get the status bar colors to interpolate between
        int primary = getResources().getColor(R.color.primaryDark);
        int secondary = getResources().getColor(R.color.accent);

        // Build the slidr config
        SlidrConfig config = new SlidrConfig.Builder()
                .primaryColor(primary)
                .secondaryColor(secondary)
                .position(SlidrPosition.LEFT)
                .touchSize(Utils.dpToPx(this, 32))
                .build();

        // Attach the Slidr Mechanism to this activity
        Slidr.attach(this, config);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mOS = getIntent().getParcelableExtra(EXTRA_OS);
        if(savedInstanceState != null) mOS = savedInstanceState.getParcelable(EXTRA_OS);

        // Set layout contents
        mTitle.setText(mOS.name);
        mDescription.setText(mOS.description);
        mDate.setText(String.valueOf(mOS.year));
        mVersion.setText(mOS.version);
        mSdk.setText(String.valueOf(mOS.sdk_int));

        // Load header image
        Picasso.with(this)
                .load(mOS.image_url)
                .into(mCover);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_OS, mOS);
    }
}
