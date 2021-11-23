package com.toncuongquang.mousejoyride.Model;

import static com.toncuongquang.mousejoyride.View.GameView.screenRatioX;
import static com.toncuongquang.mousejoyride.View.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.toncuongquang.mousejoyride.R;

public class Rocket {
    public Bitmap rocket1,rocket2,rocket3,rocket4,boom;
    public int speed=30;
    public int x,y,width,height;
    int count = 0;
    public boolean isBoom;
    public boolean isShoot;
    public Rocket(Resources res){
        rocket1 = BitmapFactory.decodeResource(res, R.drawable.rocket1);
        rocket2 = BitmapFactory.decodeResource(res, R.drawable.rocket2);
        rocket3 = BitmapFactory.decodeResource(res, R.drawable.rocket3);
        rocket4 = BitmapFactory.decodeResource(res, R.drawable.rocket4);
        boom = BitmapFactory.decodeResource(res, R.drawable.boom);
        width = 200;
        height = 200;
        width = (int)(width*screenRatioX);
        height = (int)(height*screenRatioY);
        rocket1= Bitmap.createScaledBitmap(rocket1,width,height,false);
        rocket2= Bitmap.createScaledBitmap(rocket2,width,height,false);
        rocket3= Bitmap.createScaledBitmap(rocket3,width,height,false);
        rocket4= Bitmap.createScaledBitmap(rocket4,width,height,false);
        boom= Bitmap.createScaledBitmap(boom,width,height,false);
    }
    public Bitmap getRocket(){
        if(isBoom){
            return boom;
        }
        if(count==0){
            count++;
            return rocket1;
        }if(count==1){
            count++;
            return rocket2;
        }
        if(count==2){
            count++;
            return rocket3;
        }
        count = 0;
        return rocket4;
    }
    public Rect getCollider(){
        return new Rect(x,y,x+width,y+height);
    }
}
