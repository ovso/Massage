package io.github.ovso.massage.view.ui.player;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import butterknife.ButterKnife;
import io.github.ovso.massage.R;
import io.github.ovso.massage.ad.AdsActivity;

public class PortraitVideoActivity extends AdsActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_video);
        ButterKnife.bind(this);
        if (getIntent().hasExtra("video_id")) {
            YouTubePlayerFragment youTubePlayerFragment =
                    (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
            youTubePlayerFragment.initialize(getString(R.string.youtube_developer_key),
                    new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            YouTubePlayer youTubePlayer, boolean b) {
                            youTubePlayer.loadVideo(getIntent().getStringExtra("video_id"));
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult youTubeInitializationResult) {
                        }
                    });
        } else {
            Toast.makeText(this, R.string.no_videos_found, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
