package com.hoho.android.usbserial.examples;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;

/**
 * Created by nabie on 2017. 12. 14..
 */

public class VodFragment extends Fragment implements ExoPlayerView.VodEndListener {
    private static final String TAG = VodFragment.class.getSimpleName();
    public VodFragment() {
        // Required empty public constructor
    }

    private static final String NUMBER = "number"; // 심리스 미러링 가능한 vod 넘버
    private static final String TIME = "time"; // 심리스 미러링 가능한 vod의 재생시간

    private static final String ONLY_PATH = "onlyPath"; // 동영상 경로 reflesh, vision 동영상 경로만 들어올때 사용
    private static final String LOOP = "loop"; // 영상이 계속 반복되는지 여부

    private String number = null;
    private long time = -1;
    private String onlyPath = "";
    private boolean isLooping = true;

    private ExoPlayerView exoPlayerView;
    private RelativeLayout vodLayout;

    public interface VodListener {
        void onInit();
        void onEndVod();
    }

    @Override
    public void endVod() {
        listener.onEndVod();
    }

    @Override
    public void catchError() {
        initExoPlayer();
    }

    private VodListener listener;

    public static VodFragment newInstance(String number, long time) {
        VodFragment fragment = new VodFragment();
        Bundle args = new Bundle();
        args.putString(NUMBER, number);
        args.putLong(TIME, time);
        fragment.setArguments(args);
        return fragment;
    }

    public static VodFragment newInstance(String onlyPath, boolean isLoop) {
        VodFragment fragment = new VodFragment();
        Bundle args = new Bundle();
        args.putString(ONLY_PATH, onlyPath);
        args.putBoolean(LOOP, isLoop);
        fragment.setArguments(args);
        return fragment;
    }

    public static VodFragment newInstance() {
        return new VodFragment();
    }

    public void setNumberAndTime(String number, long time) {
        this.number = number;
        this.time = time;
        initParam();
    }

    public void setSeekToZero() {
        exoPlayerView.seekTo(0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof VodListener) {
            listener = (VodListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement VodListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            number = getArguments().getString(NUMBER);
            time = getArguments().getLong(TIME);
            onlyPath = getArguments().getString(ONLY_PATH, "");
            isLooping = getArguments().getBoolean(LOOP, true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vodFragmentView;
        if (!onlyPath.equals("")) {
            // 경로만 사용하는 영상 refresh, vision
            vodFragmentView = inflater.inflate(R.layout.fragment_vod_fill, container, false);
        } else {
            // vod
            vodFragmentView = inflater.inflate(R.layout.fragment_vod, container, false);
        }
        vodLayout = (RelativeLayout) vodFragmentView.findViewById(R.id.layout_vod);
        firstInit();
        return vodFragmentView;
    }

    public void releaseExoPlayer() {
        if (exoPlayerView != null) {
            exoPlayerView.stop();
            exoPlayerView = null;
        }
    }

    // 만약 에러가 난다면 엑소플레이어를 다시 만들어 재실행하기 위해 다시 호출할수도 있을듯
    public void initExoPlayer() {
        releaseExoPlayer();
        setParam();
        initParam();
        if (!onlyPath.equals("")) {
            setOnyPath();
        }
        playExoPlayer();
    }

    public void playExoSeek(int time) {
        exoPlayerView.seekTo(time);
        playExoPlayer();
    }

    private void setOnyPath() {
        exoPlayerView.setPath(onlyPath, isLooping);
    }

    public void firstInit() {
        setParam();
        initParam();
        listener.onInit();
    }

    private void setParam() {
        RelativeLayout.LayoutParams vodParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        exoPlayerView = new ExoPlayerView(getContext());
        exoPlayerView.setVodEndListener(this);
        vodLayout.addView(exoPlayerView, vodParam);
//        exoPlayerView.setZ(0);
    }

    public void setPlayerVolumeMin() {
        exoPlayerView.setVolumeMin();
    }

    public void setPlayerVolumeRestore() {
        exoPlayerView.setVolumeRestore();
    }

    private void initParam() {
        if (number != null && !number.equals("") && time != -1) {
            exoPlayerView.setPath(findVodPath());
            exoPlayerView.seekTo(time);
            exoPlayerView.setResize(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        } else if (!onlyPath.equals("")) {
            exoPlayerView.setResize(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        }
    }

    private String findVodPath() {
//        Data.Media.VodList vodList = DataManager.getInstance().getData().findVod(number);
//        if(vodList != null) {
//            if (vodList.cat.equals(CONFIG.CAT_MUSIC_VIDEO)) {
//                return CONFIG.MOVIE_PATH + DataManager.getInstance().getData().findVodMusicVideo(number).name;
//            } else if (vodList.cat.equals(CONFIG.CAT_DRAMA)) {
//                return CONFIG.MOVIE_PATH + DataManager.getInstance().getData().findVodDrama(number).name;
//            } else if (vodList.cat.equals(CONFIG.CAT_MOVIE)) {
//                return CONFIG.MOVIE_PATH + DataManager.getInstance().getData().findVodMovie(number).name;
//            }
//        }
        return null;
    }

    /**
     * VodInfo를 가져옴
     * @return index 0: fileName, 1: currentTime
     */
    public String[] getVodInfo() {
        return new String[] {number, exoPlayerView.getCurrent() + ""};
    }

    public void playExoPlayer() {
        if (exoPlayerView != null) {
            exoPlayerView.play();
        }
    }

    public void pauseExoPlayer() {
        if (exoPlayerView != null) {
            exoPlayerView.pause();
        }
    }

    public boolean isPlaying() {
        return exoPlayerView.isPlaying();
    }

    @Override
    public void onPause() {
        super.onPause();
//        pauseExoPlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
//        playExoPlayer();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (exoPlayerView != null) {
            exoPlayerView.removeVodEndListener();
        }
        releaseExoPlayer();

    }
}
