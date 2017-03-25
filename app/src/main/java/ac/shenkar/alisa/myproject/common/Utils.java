package ac.shenkar.alisa.myproject.common;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Alisa on 3/24/2017.
 */

public class Utils {

    public static final String CACHE_BIRD_FEATHER_BITMAP = "CACHE_BIRD_FEATHER_BITMAP";
    static ExecutorService threadPool=null;
    public static ExecutorService ThreadPool(){
        if(threadPool==null){
            threadPool= Executors.newFixedThreadPool(5);
        }
        return threadPool;
    }

    public static void logError(Exception ex,String message){
        Log.e(LOG_TAG,message+" "+ex.getClass().getName()+" "+ex.getMessage(),ex);
    }

    public static final String LOG_TAG="birdGame";

    public static <T>  T GetOrCreate (String key,Creator<T> creator){
        synchronized (cache){
            Object result = cache.get(key);
            if(result!=null){
                return (T)result;
            }

            T value = creator.create();
            cache.put(key,value);
            return value;
        }
    }

    static HashMap<String,Object> cache=new HashMap<>();

        public static abstract class Creator<T>{
            public abstract T create();
        }
}
