package com.hoho.android.usbserial.examples;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;

import java.io.IOException;


/**
 * Created by nabie on 2017. 10. 27..
 */

public class ExoPlayerView extends LinearLayout {
    private static final String TAG = ExoPlayerView.class.getSimpleName();
    private SimpleExoPlayer player;

    public ExoPlayerView(Context context) {
        this(context, null);
    }

    public ExoPlayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public interface VodEndListener {
        void endVod();
        void catchError();
    }

    private VodEndListener vodEndListener;

    private String path;
    private boolean isLoop;
    private SimpleExoPlayerView simpleExoPlayerView;
    private int volume = 10; // 플레이어에서 사용할 볼륨 10으로 나눠 사용

    public void setVodEndListener(VodEndListener vodEndListener) {
        this.vodEndListener = vodEndListener;
    }

    public void removeVodEndListener() {
        vodEndListener = null;
    }

    private void init() {
        PidDebug.d(TAG, "ExoView init");
        inflate(getContext(), R.layout.view_exo_player, this);

        simpleExoPlayerView = (SimpleExoPlayerView)findViewById(R.id.view_simple_exo_player);
        setPlayer();
    }

    public void setResize(int mode) {
        simpleExoPlayerView.setResizeMode(mode);
    }

    private void setPlayer() {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        player.addListener(eventListener);
        player.setRepeatMode(Player.REPEAT_MODE_ALL);
        simpleExoPlayerView.setPlayer(player);
    }

    private Player.EventListener eventListener = new Player.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            String state = "none";
            switch (playbackState) {
                case 1: state = "idle"; break;
                case 2: state = "buffering"; break;
                case 3: state = "ready"; break;
                case 4:
                    state = "end";
                    if (vodEndListener != null) {
//                        PidDebug.d(TAG, "state end VOD");
                        vodEndListener.endVod();
                    }
                    break;
            }

//            PidDebug.d(TAG, "onPlayerStateChanged playWhenReady " + playWhenReady +  ", playbackState " + state + " vodListener is null " + vodEndListener == null);
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            switch (error.type) {
                case ExoPlaybackException.TYPE_SOURCE:
//                    PidDebug.e(TAG, "exoPlayer error!!!!!! TYPE_SOURCE");
                    reInitOnError();
                    break;
                case ExoPlaybackException.TYPE_RENDERER:
//                    PidDebug.e(TAG, "exoPlayer error!!!!!! TYPE_RENDERER");
                    reInitOnError();
                    break;
                case ExoPlaybackException.TYPE_UNEXPECTED:
//                    PidDebug.e(TAG, "exoPlayer error!!!!!! TYPE_UNEXPECTED");
                    reInitOnError();
                    break;
            }
        }

        @Override
        public void onPositionDiscontinuity() {

        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

        }
    };

    /**
     * 플레이어 내에서 internal 에러가 날경우 재초기화
     */
    private void reInitOnError() {
        vodEndListener.catchError();
//        stop();
//        setPlayer();
//        setPath(path, isLoop);
    }

    public void setPath(String path) {
        setPath(path, true);
    }

    public void setPath(String path, boolean isLoop) {
        this.path = path;
        this.isLoop = isLoop;
        simpleExoPlayerView.setVisibility(VISIBLE);
        PidDebug.i(TAG, "path is " + path);
        prepare(isLoop);

    }

    private ExtractorMediaSource.EventListener listener = new ExtractorMediaSource.EventListener() {
        @Override
        public void onLoadError(IOException error) {
            error.printStackTrace();
            PidDebug.e(TAG, "error on exoPlayer!!!!!!!!!!!");
//            player.removeListener(eventListener);
//            setPlayer();
        }
    };

    private void prepare(boolean isLoop) {
        PidDebug.d(TAG, "ExoView prepare");

        try {

            PidDebug.d(TAG, "path="+path);

//            DataSpec dataSpec = new DataSpec(Uri.parse(path));
//
//            final FileDataSource fileDataSource = new FileDataSource();
//            fileDataSource.open(dataSpec);
//
//            DataSource.Factory factory = new DataSource.Factory() {
//                @Override
//                public DataSource createDataSource() {
//                    return fileDataSource;
//                }
//            };
//
//            // Produces Extractor instances for parsing the Data data.
//            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//            // This is the MediaSource representing the Data to be played.
//
//            MediaSource videoSource = new ExtractorMediaSource(fileDataSource.getUri(),
//                    factory, extractorsFactory, null, listener);

            DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.sensor_test));
            final RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(getContext());
            rawResourceDataSource.open(dataSpec);
            DataSource.Factory factory = new DataSource.Factory() {
                @Override
                public DataSource createDataSource() {
                    return rawResourceDataSource;
                }
            };

            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource videoSource = new ExtractorMediaSource(rawResourceDataSource.getUri(),
                    factory, extractorsFactory, null, null);



            if (isLoop) {
                LoopingMediaSource loopingMediaSource = new LoopingMediaSource(videoSource);
                player.prepare(loopingMediaSource);
            } else {
                player.prepare(videoSource);
                player.setRepeatMode(Player.REPEAT_MODE_OFF);
            }
            // Prepare the player with the source.

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getCurrent() {
        long currentPos = player.getCurrentPosition();
        PidDebug.d(TAG, "current position is " + currentPos);
        return currentPos;
    }

    public void play() {
        PidDebug.d(TAG, "play");
        player.setPlayWhenReady(true);
    }

    public void pause() {
        PidDebug.d(TAG, "pause");
        player.setPlayWhenReady(false);
    }

    public void startOnSeekPosition(long pos) {
        player.seekTo(pos);
        play();
    }

    public boolean isPlaying() {
        return player.getPlayWhenReady();
    }

    public void setVolumeMin() {
        player.setVolume(0.1f);
    }

    public void setVolumeDown() {
        if (volume > 0) {
            player.setVolume(((float)(volume -= 1)/10));
        } else {
            player.setVolume(0.0f);
        }
    }

    public void setVolumeUp() {
        if (volume < 10) {
            player.setVolume(((float)(volume += 1)/10));
        } else {
            player.setVolume(1.0f);
        }
    }

    public void setVolumeRestore() {
        if (volume > 10) {
            player.setVolume(1f);
        } else if (volume < 0) {
            player.setVolume(0f);
        } else {
            player.setVolume(((float) (volume) / 10));
        }
    }

    public void seekTo(long pos) {
        player.seekTo(pos);
    }

    /**
     * 플레이어를 해제
     */
    public void stop() {
        PidDebug.d(TAG, "stop");
        simpleExoPlayerView.setVisibility(INVISIBLE);
        player.removeListener(eventListener);
        player.release();
    }
}
