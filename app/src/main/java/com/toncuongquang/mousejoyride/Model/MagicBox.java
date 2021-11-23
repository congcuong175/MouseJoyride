package com.toncuongquang.mousejoyride.Model;

import static com.toncuongquang.mousejoyride.View.GameView.screenRatioX;
import static com.toncuongquang.mousejoyride.View.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.toncuongquang.mousejoyride.R;

public class MagicBox {
    public Bitmap box;
    public int speed=15;
    public int x,y,width,height;
    public MagicBox(Resources res){
        box = BitmapFactory.decodeResource(res, R.drawable.box);
        width = 60;
        height = 60;
        width = (int)(width*screenRatioX);
        height = (int)(height*screenRatioY);
        box= Bitmap.createScaledBitmap(box,width,height,false);

    }
    public Bitmap getbox(){
        return box;
    }
    public Rect getCollider(){
        return new Rect(x,y,x+width,y+height);
    }
}
