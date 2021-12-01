package com.toncuongquang.mousejoyride.Model;

import static com.toncuongquang.mousejoyride.View.GameView.screenRatioX;
import static com.toncuongquang.mousejoyride.View.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.toncuongquang.mousejoyride.R;

public class BulletDown {
    public Bitmap fire1,fire2,fire3;
    public int speed=10;
    public boolean get = true;
    public int x,y,width,height;
    public BulletDown(Resources res){
        fire1 = BitmapFactory.decodeResource(res, R.drawable.fire1);
        fire2 = BitmapFactory.decodeResource(res, R.drawable.fire2);
        fire3 = BitmapFactory.decodeResource(res, R.drawable.fire3);
        width = 100;
        height = 120;
        width = (int)(width*screenRatioX);
        height = (int)(height*screenRatioY);
        fire1= Bitmap.createScaledBitmap(fire1,width,height,false);
        fire2= Bitmap.createScaledBitmap(fire2,width,height,false);
        fire3= Bitmap.createScaledBitmap(fire3,width,height,false);

    }
    int count=1;
    public Bitmap getbullet(){
        if(count==1){
            count++;
            return fire1;
        }
        if(count==2){
            count++;
            return fire2;
        }
            count = 1;
            return fire3;

    }
    public Rect getCollider(){
        return new Rect(x,y,x+width,y+height);
    }
}
