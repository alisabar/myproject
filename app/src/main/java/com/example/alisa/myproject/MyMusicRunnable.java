package com.example.alisa.myproject;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

class MyMusicRunnable implements Runnable, MediaPlayer.OnCompletionListener {
    Context appContext;
static     MediaPlayer mPlayer;
    boolean musicIsPlaying = false;

    public MyMusicRunnable(Context c) {
        // be careful not to leak the activity context.
        // can keep the app context instead.
        appContext = c.getApplicationContext();
    }

    public boolean isMusicIsPlaying() {
        return musicIsPlaying;
    }

    /**
     * MediaPlayer.OnCompletionListener callback
     *
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        // loop back - play again

        if(isStopMusic){
            cleanup();
            return;
        }

        loopBack();
    }

     private void loopBack() {
         try {

         if (musicIsPlaying && mPlayer != null) {
             mPlayer.start();
         }
         } catch (Exception e) {
             Log.e(getClass().getName(),"play music - onCompletion",e);
         }
     }

     //TODO: possibly need to use musicIsPlaying instead.
     public void stopMusic(){
         isStopMusic=true;
         cleanup();
     }

     private void cleanup() {
         try {
         mPlayer.stop();
         mPlayer.release();
         } catch (Exception e) {
             Log.e(getClass().getName(),"clenup",e);

         }

     }

     boolean isStopMusic=false;

    /**
     * toggles the music player state
     * called asynchronously every time the play/pause button is pressed
     */
    @Override
    public void run() {
        togglePlay();
    }

     private void togglePlay() {
         try {
             if (musicIsPlaying) {
                 stopPlay();
             } else {
                 startPlay();
             }
         } catch (Exception e) {
             Log.e(getClass().getName(),"play music",e);

         }
     }

    private void stopPlay() {
        try {
            mPlayer.stop();
            musicIsPlaying = false;
        } catch (Exception e) {
            Log.e(getClass().getName(),"play music - stop play",e);
        }
    }

    private void startPlay(){

        try {
            if (mPlayer == null) {
                mPlayer=createMediaPlayer();
            } else {
                mPlayer.prepare();
            }
            mPlayer.start();
            musicIsPlaying = true;
        } catch (Exception e) {
            Log.e(getClass().getName(),"play music - start play",e);
        }
    }

    private MediaPlayer createMediaPlayer() {
         mPlayer = MediaPlayer.create(appContext, R.raw.backgroundmusic);
         mPlayer.setOnCompletionListener(this); // MediaPlayer.OnCompletionListener
         return mPlayer;
     }

 }

