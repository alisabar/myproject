package ac.shenkar.alisa.myproject.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.util.SparseIntArray;


import ac.shenkar.alisa.myproject.R;

/**
 * Created by study on 18/03/2017.
 */
class MySFxRunnable implements Runnable {
        Context appContext;
        SoundPool soundPool;
        SoundPool.Builder soundBuild;
        /**
         * like a hash map, but more efficient
         */
        SparseIntArray soundsMap = new SparseIntArray();
        private boolean prepared = false;

        public MySFxRunnable(Context c) {
            // be careful not to leak the activity context.
            // can keep the app context instead.
            appContext = c.getApplicationContext();
            soundBuild= new SoundPool.Builder();

            // init this object on a user thread.
            // The rest of the use of this class can be on the UI thread
    //        AsyncHandler.post(this);
        }

        /**
         * load and init the sound effects, so later I'll be able to play them instantly from the
         * UI thread.
         */
        @Override
        public void run() {

            soundPool=soundBuild.build();

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
       //     soundsMap.put(R.raw.doorbell_start, soundPool.load(appContext, R.raw.doorbell_start, 1));
        //    soundsMap.put(R.raw.doorbell_end, soundPool.load(appContext, R.raw.doorbell_end, 1));
        //    soundsMap.put(R.raw.door_knock, soundPool.load(appContext, R.raw.door_knock, 1));
        //    soundsMap.put(R.raw.door_lock, soundPool.load(appContext, R.raw.door_lock, 1));


        }

        public void play(int soundKey) {

            SharedPreferences sharedprep = appContext.getSharedPreferences("User_settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedprep.edit();
            boolean effects =(boolean) (sharedprep.getBoolean("effects", true));
            if (soundPool == null || !prepared) {
                return;
            }
            if(effects)
            soundPool.play(soundsMap.get(soundKey), 1.0f, 1.0f, 2, 0, 1.0f);
        }
    }

    /**
     * This can be an independent class.
     * It's here for convenience.
     */

