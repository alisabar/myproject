package com.example.alisa.myproject.game;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by Alisa on 1/21/2017.
 */

public class BirdCreator extends GameObjectCreator {

    public BirdCreator(Context context, View view, Game game, long createIntervalMilli) {
        super(context, view, game, createIntervalMilli);
    }

    @NonNull
    @Override
    protected GameObject CreateInstance() {
        return new BirdObstecale(_context,_view,_game,getNewLocation());
    }
}
