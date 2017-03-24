package ac.shenkar.alisa.myproject.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.util.Log;
import android.util.SparseIntArray;


import ac.shenkar.alisa.myproject.R;

/**
 * Created by study on 18/03/2017.
 */
class MySFxRunnable implements Runnable {
        Context appContext;
        SoundPool soundPool;
        SoundPool.Builder soundBuilder;
        /**
         * like a hash map, but more efficient
         */
        SparseIntArray soundsMap = new SparseIntArray();
        private boolean prepared = false;

        public MySFxRunnable(Context c) {
            // be careful not to leak the activity context.
            // can keep the app context instead.
            appContext = c.getApplicationContext();
            soundBuilder = new SoundPool.Builder();
        }

        /**
         * load and init the sound effects, so later I'll be able to play them instantly from the
         * UI thread.
         */
        @Override
        public void run() {
    try {
    soundPool = soundBuilder.build();

    /**
     * a callback when prepared -- we need to prevent playing before prepared
     */
    soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
        @Override
        public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
            prepared = true;
        }
    });

    /**
     * the load() returns a stream id that can be used to play the sound.
     * I use the "R.raw.xyz" integer as key, because it's useless to invent new keys for
     * them
     */
    soundsMap.put(R.raw.collected, soundPool.load(appContext, R.raw.collected, 1));
    soundsMap.put(R.raw.birdcry, soundPool.load(appContext, R.raw.birdcry, 1));
    soundsMap.put(R.raw.pause, soundPool.load(appContext, R.raw.pause, 1));
    //    soundsMap.put(R.raw.door_knock, soundPool.load(appContext, R.raw.door_knock, 1));
    //    soundsMap.put(R.raw.door_lock, soundPool.load(appContext, R.raw.door_lock, 1));

    }catch(Exception ex) {
        Log.e("birdGame" + getClass().getName(), "soundfx failed", ex);
    }
        }

        public void play(int soundKey) {

            if (soundPool == null || !prepared) {
                return;
            }

            if(isEffects()) {
                soundPool.play(soundsMap.get(soundKey), 1.0f, 1.0f, 2, 0, 1.0f);
            }
        }

    private boolean isEffects() {
        SharedPreferences sharedprep = appContext.getSharedPreferences("User_settings", Context.MODE_PRIVATE);
        return (boolean) (sharedprep.getBoolean("effects", true));
    }
}

    /**
     * This can be an independent class.
     * It's here for convenience.
     */

