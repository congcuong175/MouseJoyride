package com.toncuongquang.mousejoyride.Model;

import static com.toncuongquang.mousejoyride.View.GameView.screenRatioX;
import static com.toncuongquang.mousejoyride.View.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.toncuongquang.mousejoyride.R;

public class Coin {

    public Bitmap coin;
    public int speed=10;
    public boolean get = true;
    public int x,y,width,height;
    public Coin(Resources res){
        coin = BitmapFactory.decodeResource(res,R.drawable.coin);
        width = 60;
        height = 60;
        width = (int)(width*screenRatioX);
        height = (int)(height*screenRatioY);
        coin= Bitmap.createScaledBitmap(coin,width,height,false);

    }
    public Bitmap getCoin(){
        return coin;
    }
    public Rect getCollider(){
        return new Rect(x,y,x+width,y+height);
    }
}
