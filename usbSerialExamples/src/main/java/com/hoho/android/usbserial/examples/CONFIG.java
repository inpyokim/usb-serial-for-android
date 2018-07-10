package com.hoho.android.usbserial.examples;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Region;
import android.os.Environment;

import java.util.ArrayList;
import java.util.TimeZone;

/**
 * Created by nabiesoft on 23/05/2017.
 */

public class CONFIG {

    public static final boolean SUPPORT_DEBUG = true;
    public static final boolean SUPPORT_FILE_LOGGER = false;

    public static TimeZone SEOUL_TIMEZONE = TimeZone.getTimeZone("GMT+09:00");
    public static TimeZone USER_TIMEZONE = SEOUL_TIMEZONE;
    public static final String REGION_ID = "regionId";

//    public static final Region ALL_BEACONS_REGION = new Region(CONFIG.REGION_ID, null, null, null);
    public static final String BEACON1_MAC = "EF:6A:8B:5E:68:EA";
    public static final String BEACON2_MAC = "CC:1B:59:60:2A:35";
//    public static final String[] BEACON_MAC_ADDRESSES = {BEACON1_MAC, BEACON2_MAC};

    public static ArrayList<String> BEACON_MAC_ADDRESSES = new ArrayList<>();
    public static String MIBAND_MAC_ADDRESSES;

    public static final String INTENT_ACTIVITY_FROM_WHERE = "intentActivityFromWhere";

    public enum FROM_WHERE {
        MENU,
        VOD_LIST,
        MIRRORING,
        SEAMLESS,
        HEALTH_MENU,
        HEALTH_EVENT,
        VOD,
        MIBAND,
        CHATTING,
        FRIEND_LIST,
    }

//    public static final String ACTIVITY_IS_FROM_VOD_LIST = "activityIsFromVodList";
//    public static final String ACTIVITY_IS_FROM_MIRRORING = "activityIsFromMirroring";
//    public static final String ACTIVITY_IS_FROM_SEAMLESS = "activityIsFromSeamless";
//    public static final String ACTIVITY_IS_FROM_HEALTH_MENU = "activityIsFromHealthMenu";
//    public static final String ACTIVITY_IS_FROM_HEALTH_EVENT = "activityIsFromHealthEvent";
//    public static final String ACTIVITY_IS_FROM_VOD = "activityIsFromVod";
//    public static final String ACTIVITY_IS_FROM_MIBAND = "activityIsFromMiband";

    public static final String INTENT_MAP_ORIGIN_LOCATION = "intentMapOriginLocation";
    public static final String INTENT_MAP_DESTINATION_LOCATION = "intentMapDestinationLocation";

    public static final String INTENT_SCHEDULE_IS_REGISTER = "intentScheduleIsRegister";
    public static final String INTENT_SCHEDULE_IS_TOMORROW = "intentScheduleIsTomorrow";

    public static final float ALPHA_POPUP_OPAQUE = 1.0f;
    public static final float ALPHA_POPUP_TRANSPARENT = 0.0f;

    public static final int DURATION_MSG_POPUP = 3000;
    public static final int DURATION_POPUP_SEARCH = 2000;
    public static final int DURATION_POPUP_FADE = 800;
    public static final int DURATION_TTS_POPUP_FADE = 400;
    public static final int DURATION_MSG_POPUP_FADE = 500;

    public static final int DURATION_POPUP_CLOTHES_SCROLL_AND_FADE = 1000;

    public static final String CAT_MUSIC_VIDEO = "0";
    public static final String CAT_DRAMA = "1";
    public static final String CAT_MOVIE = "2";

    public static final String TYPE_CLOTH_TOP = "top";
    public static final String TYPE_CLOTH_BOTTOM = "bottom";

    public static final String VOD_NUMBER_1 = "1";
    public static final String VOD_NUMBER_2 = "2";
    public static final String VOD_NUMBER_3 = "3";
    public static final String VOD_NUMBER_4 = "4";
    public static final String VOD_NUMBER_5 = "5";

    private static final String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String PID_DATA = ROOT + "/CES_PID_DATA";
    private static final String VOD_PATH = PID_DATA + "/vod/";
    public static final String MUSIC_PATH = PID_DATA + "/music/";
    public static final String MUSIC_COVER_PATH = PID_DATA + "/music/cover_img/";
    public static final String MOVIE_PATH = VOD_PATH + "movies/";
    public static final String THUMB_PATH = VOD_PATH + "thumb/";
    public static final String TIME_PIC_PATH = VOD_PATH + "time_pic/";
    public static final String CLOSET_PATH = VOD_PATH + "closet/";

    public static final String ABOUT_PATH = PID_DATA + "/about/";
    public static final String INTRO_PATH = PID_DATA + "/intro/";
    public static final String CLOSET_PHOTO_PATH = PID_DATA + "/closet_photo/";

    private static final String APP_FILE_PREFERENCE = "FILE_PREFERENCE";
    public static final String KEY_SCALE_FACTOR = "KEY_SCALE_FACTOR";

    public static int testIdx = 0;
    /**
     * Chating
     */
    public static class SoketMessageType {
        public static final int SOCKET_CONNECTED = 1000;
        public static final int SOCKET_DATA = 1001;
        public static final int SOCKET_DISCONNECTED = 1002;
        public static final int SOCKET_CONNECTION_FAILED = 1003;
    };

    public static final class ChatConstants {
        public static final int SOCKET_SERVER_PORT = 7777;
        public static final String SENDER = "0";
        public static final String RECEIVER = "1";
    }

    public static final class ChatInputMode {
        public static final int MODE_INACTIVE = 0;
        public static final int MODE_ACTIVE = 1;
        public static final int MODE_SEND = 2;
    }

    // jwpark add
    public static final int BEACON_CHOOSER_SERVER_PORT = 6666;

    /**************************************************************************************************************
     * Preference(File)
     **************************************************************************************************************/
    public static final class File {
        private static void setBoolean(Context context, String name, String key, boolean value) {
            PidDebug.Debug(">> Preference set( " + key + ", " + value + ")");
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(key, value);
            editor.apply();
        }

        private static void setInt(Context context, String name, String key, int value) {
            PidDebug.Debug(">> Preference set( " + key + ", " + value + ")");
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt(key, value);
            editor.apply();
        }

        private static void setLong(Context context, String name, String key, long value) {
            PidDebug.Debug(">> Preference set( " + key + ", " + value + ")");
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putLong(key, value);
            editor.apply();
        }

        private static void setFloat(Context context, String name, String key, float value) {
            PidDebug.Debug(">> Preference set( " + key + ", " + value + ")");
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putFloat(key, value);
            editor.apply();
        }

        private static void setString(Context context, String name, String key, String value) {
            PidDebug.Debug(">> Preference set( " + key + ", " + value + ")");
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(key, value);
            editor.apply();
        }

        private static boolean getBoolean(Context context, String name, String key) {
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            return pref.getBoolean(key, false);
        }

        private static int getInt(Context context, String name, String key) {
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            return pref.getInt(key, -1);
        }

        private static long getLong(Context context, String name, String key) {
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            return pref.getLong(key, -1);
        }

        private static float getFloat(Context context, String name, String key) {
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            return pref.getFloat(key, Float.NaN);
        }

        private static String getString(Context context, String name, String key) {
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            return pref.getString(key, "");
        }

        private static boolean getBoolean(Context context, String name, String key, boolean bDefault) {
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            return pref.getBoolean(key, bDefault);
        }

        private static int getInt(Context context, String name, String key, int nDefault) {
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            return pref.getInt(key, nDefault);
        }

        private static long getLong(Context context, String name, String key, long nDefault) {
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            return pref.getLong(key, nDefault);
        }

        private static float getFloat(Context context, String name, String key, float nDefault) {
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            return pref.getFloat(key, nDefault);
        }

        private static String getString(Context context, String name, String key, String strDefault) {
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            return pref.getString(key, strDefault);
        }

        private static boolean hasValue(Context context, String name, String key) {
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            return pref.contains(key);
        }

        private static void removeKey(Context context, String name, String key) {
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.remove(key);
            editor.apply();
        }

        private static void clear(Context context, String name) {
            SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.apply();
        }
    }


//    public static final AppConfig APP_PREFERENCE = new AppConfig();
//
//    public static final class AppConfig {
//
//        /**
//         * 값이 세팅되었는지 체크
//         *
//         * @param key
//         * @return
//         */
//        public synchronized boolean hasValue(String key) {
//            Context context = PidDemoApp.getContext();
//            return File.hasValue(context, APP_FILE_PREFERENCE, key);
//        }
//
//        public synchronized void setBoolean(String key, boolean value) {
//            Context context = PidDemoApp.getContext();
//            File.setBoolean(context, APP_FILE_PREFERENCE, key, value);
//        }
//
//        public synchronized void setInt(String key, int value) {
//            Context context = PidDemoApp.getContext();
//            File.setInt(context, APP_FILE_PREFERENCE, key, value);
//        }
//
//        public synchronized void setLong(String key, long value) {
//            Context context = PidDemoApp.getContext();
//            File.setLong(context, APP_FILE_PREFERENCE, key, value);
//        }
//
//        public synchronized void setFloat(String key, float value) {
//            Context context = PidDemoApp.getContext();
//            File.setFloat(context, APP_FILE_PREFERENCE, key, value);
//        }
//
//        public synchronized void setString(String key, String value) {
//            Context context = PidDemoApp.getContext();
//            File.setString(context, APP_FILE_PREFERENCE, key, value);
//        }
//
//        public synchronized boolean getBoolean(String key, boolean bDefault) {
//            Context context = PidDemoApp.getContext();
//            return File.getBoolean(context, APP_FILE_PREFERENCE, key, bDefault);
//        }
//
//        public synchronized int getInt(String key, int nDefault) {
//            Context context = PidDemoApp.getContext();
//            return File.getInt(context, APP_FILE_PREFERENCE, key, nDefault);
//        }
//
//        public synchronized long getLong(String key, long nDefault) {
//            Context context = PidDemoApp.getContext();
//            return File.getLong(context, APP_FILE_PREFERENCE, key, nDefault);
//        }
//
//        public synchronized float getFloat(String key, float nDefault) {
//            Context context = PidDemoApp.getContext();
//            return File.getFloat(context, APP_FILE_PREFERENCE, key, nDefault);
//        }
//
//        public synchronized String getString(String key, String strDefault) {
//            Context context = PidDemoApp.getContext();
//            String value = File.getString(context, APP_FILE_PREFERENCE, key, strDefault);
//
//            return value;
//        }
//
//        public synchronized boolean getBoolean(String key) {
//            Context context = PidDemoApp.getContext();
//            return File.getBoolean(context, APP_FILE_PREFERENCE, key);
//        }
//
//        public synchronized int getInt(String key) {
//            Context context = PidDemoApp.getContext();
//            return File.getInt(context, APP_FILE_PREFERENCE, key);
//        }
//
//        public synchronized long getLong(String key) {
//            Context context = PidDemoApp.getContext();
//            return File.getLong(context, APP_FILE_PREFERENCE, key);
//        }
//
//        public synchronized float getFloat(String key) {
//            Context context = PidDemoApp.getContext();
//            return File.getFloat(context, APP_FILE_PREFERENCE, key);
//        }
//
//        public synchronized String getString(String key) {
//            Context context = PidDemoApp.getContext();
//            String value = File.getString(context, APP_FILE_PREFERENCE, key);
//
//            return value;
//        }
//
//        public void clear(String key) {
//            Context context = PidDemoApp.getContext();
//            File.removeKey(context, APP_FILE_PREFERENCE, key);
//        }
//
//        public void clear() {
//            Context context = PidDemoApp.getContext();
//            File.clear(context, APP_FILE_PREFERENCE);
//        }
//
//    }

}
