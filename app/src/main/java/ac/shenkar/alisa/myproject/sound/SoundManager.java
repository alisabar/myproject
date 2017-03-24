package ac.shenkar.alisa.myproject.sound;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.util.Log;
import android.util.SparseIntArray;

import ac.shenkar.alisa.myproject.common.Utils;

/**
 * Created by Alisa on 3/24/2017.
 */

public class SoundManager {

    private static final int NO_FOUND_KEY = -1;
    private final Context _context;
    private static SoundManager _soundMgr;
    private static Object instance_lock=new Object();
    private final SoundPool _soundPool;

    //use this to store already loaded sounds
    SparseIntArray resToSoundMap= new SparseIntArray();
    SparseIntArray loadedSoundsIds= new SparseIntArray();


    private SoundManager(Context ctx){
        _context=ctx.getApplicationContext();
        _soundPool = createSoundPool();
        initSettings();
    }

    private void initSettings() {
        getUser_settings().registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                isEffectLoaded=false;
            }
        });
    }

    private SoundPool createSoundPool() {

        SoundPool soundPool = new SoundPool.Builder().build();
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int soundId, int status) {
                if(status==0 /*complete*/){
                    loadedSoundsIds.append(soundId,1);
                    playBackground(soundId);
                }
            }
        });

        return soundPool;
    }

    public static SoundManager Instance(Context ctx){
        if(_soundMgr==null) {
            synchronized (instance_lock) {
                _soundMgr = _soundMgr!=null? _soundMgr: new SoundManager(ctx);
            }
        }
        return _soundMgr;
    }

    boolean isEffect;
    boolean isEffectLoaded;
    private boolean isEffects() {
        if(isEffectLoaded){
            return isEffect;
        }
        SharedPreferences sharedprep = getUser_settings();
        isEffect=sharedprep.getBoolean("effects", true);
        isEffectLoaded=true;
        return isEffect;
    }

    private SharedPreferences getUser_settings() {
        return _context.getSharedPreferences("User_settings", Context.MODE_PRIVATE);
    }

    public void playSound(final int soundResId)
    {
        Utils.ThreadPool().submit(new Runnable() {
            @Override
            public void run() {
                playSoundOnCurrentThread(soundResId);
            }
        });

    }

    private void playSoundOnCurrentThread(int soundResId) {
        try {
            if (!isEffects()) {
                return;
            }
            if (isLoadedSound(soundResId)) {
                playSound(loadedSoundsIds.get(soundResId,NO_FOUND_KEY ));
            }

            resToSoundMap.append(soundResId
                    , _soundPool.load(_context, soundResId, 1)
            );

        }catch(Exception ex)
        {
            Utils.logError(ex,"playSound");
        }
    }

    private boolean isLoadedSound(int soundResId) {

        int soundId;
        if( (soundId = resToSoundMap.get(soundResId,NO_FOUND_KEY))==NO_FOUND_KEY){
            return false;
        }
        if(loadedSoundsIds.get(soundId,NO_FOUND_KEY )==NO_FOUND_KEY){
            return false;
        }
        return true;
    }

    private void playBackground(final int soundId) {

        Utils.ThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    _soundPool.play(soundId, getLeftVolume(), getRightVolume(), 2, 0, 1.0f);
                } catch (Exception ex) {
                    Utils.logError(ex,"play sound");
                }
            }
        });
    }

    private float getRightVolume() {
        return 1.0f;
    }

    private float getLeftVolume() {
        return 1.0f;
    }

}
