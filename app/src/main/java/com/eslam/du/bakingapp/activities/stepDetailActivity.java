package com.eslam.du.bakingapp.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.eslam.du.bakingapp.aragments.stepDetailFragment;
import com.eslam.du.bakingapp.modules.Steps;
import com.eslam.du.bakingapp.R;
import com.eslam.du.bakingapp.databinding.ActivityStepDetailBinding;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;

public class stepDetailActivity extends AppCompatActivity {
    private ActivityStepDetailBinding B;
    private SimpleExoPlayer exoPlayer;
    private ArrayList<Steps> steps;
    private int position;
    private static long playerPosition = C.TIME_UNSET;
    private String URL;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        B = DataBindingUtil.setContentView(this, R.layout.activity_step_detail);        //Get Content of stepDetail Layout.

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            steps = intent.getParcelableArrayListExtra(getResources().getString(R.string.step_detail_activity));
            position = intent.getIntExtra("position", 0);
        }
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
        }
        if (B.exoPlayerViewFull != null) {
            landscapeMode();
        } else {
            portraitMode(savedInstanceState);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("position", position);
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    protected void onPause() {
        if (exoPlayer != null) {
            playerPosition = exoPlayer.getCurrentPosition();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer.stop();
        }
        super.onDestroy();
    }

    private void landscapeMode() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (getSupportFragmentManager().findFragmentById(R.id.step_detail_container) != null) {
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().
                    findFragmentById(R.id.step_detail_container)).commit();
        }
        if (steps.get(position).getVideoURL().isEmpty()) {
            B.placeholder.setVisibility(View.VISIBLE);
            B.placeholder.setAlpha(1f);

        } else {
            B.placeholder.setAlpha(0f);
            B.placeholder.setVisibility(View.GONE);

            try {

                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

                URL = steps.get(position).getVideoURL();
                Uri URI = Uri.parse(URL);

                DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exo_player_video");
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(URI, dataSourceFactory, extractorsFactory, null, null);

                B.exoPlayerViewFull.setPlayer(exoPlayer);
                exoPlayer.prepare(mediaSource);
                exoPlayer.setPlayWhenReady(true);
                if (playerPosition != C.TIME_UNSET) {
                    exoPlayer.seekTo(playerPosition);
                }

            } catch (Exception e) {
                Log.e("Detail_activity", "Exo player Error" + e.toString());
            }

        }

    }

    private void portraitMode(Bundle savedInstanceState) {

//Listener on the Next Button to change position
        B.Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position < steps.size() - 1) {
                    position = position + 1;

                    Bundle arguments = new Bundle();
                    arguments.putParcelable(getResources().getString(R.string.step_detail_fragment), steps.get(position));
                    stepDetailFragment fragment = new stepDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.step_detail_container, fragment)
                            .commit();
                }
            }
        });
        //Listener on the Previous Button to change position

        B.Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position > 0) {
                    position = position - 1;
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(getResources().getString(R.string.step_detail_fragment), steps.get(position));
                    stepDetailFragment fragment = new stepDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.step_detail_container, fragment)
                            .commit();
                }
            }
        });

        if (savedInstanceState == null || B.exoPlayerViewFull == null) {

            Bundle arguments = new Bundle();
            arguments.putParcelable(getResources().getString(R.string.step_detail_fragment), steps.get(position));
            arguments.putLong(getResources().getString(R.string.player_state), playerPosition);
            stepDetailFragment fragment = new stepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, fragment)
                    .commit();

        }

    }


    public void setPlayerPosition(Long playerPosition) {
        this.playerPosition = playerPosition;

    }
}
