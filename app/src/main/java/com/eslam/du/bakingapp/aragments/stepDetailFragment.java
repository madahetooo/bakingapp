package com.eslam.du.bakingapp.aragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eslam.du.bakingapp.R;
import com.eslam.du.bakingapp.activities.stepDetailActivity;
import com.eslam.du.bakingapp.modules.Steps;
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
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;


public class stepDetailFragment extends Fragment {

    private ImageView placeholder;
    private ImageView playButton;
    @SuppressWarnings("deprecation")
    private SimpleExoPlayerView exoPlayerView;
    private SimpleExoPlayer exoPlayer;
    private static long playerPosition = C.TIME_UNSET;
    private Steps step;
    private String videoURL;

    public stepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        step = getArguments().getParcelable(getContext().getResources().getString(R.string.step_detail_fragment));
        playerPosition = getArguments().getLong(getContext().getResources().getString(R.string.player_state), C.TIME_UNSET);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.step_detail_fragment, container, false);

        if (step != null) {
            loadData(rootView);
        }

        return rootView;

    }

    @Override
    public void onPause() {

        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer.stop();
            playerPosition = exoPlayer.getCurrentPosition();
            if( getActivity() instanceof stepDetailActivity) {
                stepDetailActivity stepdetailActivity = (stepDetailActivity) getActivity();
                stepdetailActivity.setPlayerPosition(playerPosition);
            }
        }


        super.onPause();

    }


    @Override
    public void onDestroy() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer.stop();
        }
        super.onDestroy();
    }

    private void loadData(View rootView) {

        videoURL = step.getVideoURL();
        TextView stepDescription = rootView.findViewById(R.id.stepDescription);
        exoPlayerView = rootView.findViewById(R.id.exo_player_view);
        placeholder = rootView.findViewById(R.id.player_placeholder);
        playButton = rootView.findViewById(R.id.play_button);
        stepDescription.setText(step.getDescription());
        if (step.getThumbnailURL().isEmpty()) {

            handleTheVideo();

        } else {

            playButton.setAlpha(1f);
            placeholder.setAlpha(1f);
            playButton.setVisibility(View.VISIBLE);
            placeholder.setVisibility(View.VISIBLE);
            placeholder.setClickable(true);


            try {
                Picasso.get()
                        .load(step.getThumbnailURL())
                        .into(placeholder);
            } catch (Exception e) {
                e.printStackTrace();
            }

            placeholder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleTheVideo();
                }
            });

        }


    }


    private void handleTheVideo() {

        if (step.getVideoURL().isEmpty()) {
            placeholder.setImageResource(R.drawable.place_holder);
            placeholder.setAlpha(1f);
            placeholder.setVisibility(View.VISIBLE);

        } else {
            loadPlayer();
        }

    }

    private void loadPlayer() {

        playButton.setAlpha(0f);
        placeholder.setAlpha(0f);
        playButton.setVisibility(View.GONE);
        placeholder.setVisibility(View.GONE);
        placeholder.setClickable(false);

        try {


            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

            Uri videoURI = Uri.parse(videoURL);
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exo_player_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            @SuppressWarnings("deprecation") MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
            if (playerPosition != C.TIME_UNSET) {
                exoPlayer.seekTo(playerPosition);
            }
        } catch (Exception e) {
            Log.e("playerFragment", "playerError" + e.toString());
        }


    }


}
