package com.toncuongquang.mousejoyride.Model;

import static com.toncuongquang.mousejoyride.View.GameView.screenRatioX;
import static com.toncuongquang.mousejoyride.View.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.toncuongquang.mousejoyride.R;

public class Bullet {
    public Bitmap bullet;
    public int speed=10;
    public boolean get = true;
    public int x,y,width,height;
    public Bullet(Resources res){
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
        width = 70;
        height = 70;
        width = (int)(width*screenRatioX);
        height = (int)(height*screenRatioY);
        bullet= Bitmap.createScaledBitmap(bullet,width,height,false);

    }
    public Bitmap getbullet(){
        return bullet;
    }
    public Rect getCollider(){
        return new Rect(x,y,x+width,y+height);
    }
}
