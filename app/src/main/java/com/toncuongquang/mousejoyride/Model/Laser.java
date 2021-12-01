package com.toncuongquang.mousejoyride.Model;

import static com.toncuongquang.mousejoyride.View.GameView.screenRatioX;
import static com.toncuongquang.mousejoyride.View.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.toncuongquang.mousejoyride.R;

import java.util.Random;

public class Laser {
    public Bitmap laser1, laser2;
    public int speed = 10;
    public int x, y, width, height;
    public boolean isActive = true;
    public int count = 0;
    public Random random;
    public int rad;
    public Laser(Resources res) {
        random = new Random();
        rad = random.nextInt((40-20)+1)+20;//1
        laser1 = BitmapFactory.decodeResource(res, R.drawable.laser_on);
        laser2 = BitmapFactory.decodeResource(res, R.drawable.laser_off);
        width = 100;
        height = 400;
        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);
        laser1 = Bitmap.createScaledBitmap(laser1, width, height, false);
        laser2 = Bitmap.createScaledBitmap(laser2, width, height, false);

    }

    public Bitmap getLaser() {
        if (count == 0) {
            isActive = false;
            count++;
            return laser2;
        }
        if(count>=1 &&count<=rad){
            count++;
            return laser2;
        }
        else
        {
            count=0;
            isActive = true;
            return laser1;

        }
    }

    public Rect getCollider() {
        return new Rect( x+30   , y +30, x + width-30, y + height-30);
    }
}
