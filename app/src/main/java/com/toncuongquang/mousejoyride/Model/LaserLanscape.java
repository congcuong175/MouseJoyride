package com.toncuongquang.mousejoyride.Model;

import static com.toncuongquang.mousejoyride.View.GameView.screenRatioX;
import static com.toncuongquang.mousejoyride.View.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.toncuongquang.mousejoyride.R;

import java.util.Random;

public class LaserLanscape {
    public Bitmap laser1, laser2,laser3,laser4,laser5;
    public int speed = 10;
    public int x, y, width, height;
    public boolean isActive = true;
    public int count = 0;
    public Random random;
    public int rad;
    public LaserLanscape(Resources res) {
        random = new Random();
        rad = random.nextInt((40-20)+1)+20;//1
        laser1 = BitmapFactory.decodeResource(res, R.drawable.lazer1);
        laser2 = BitmapFactory.decodeResource(res, R.drawable.laser2);
        laser3 = BitmapFactory.decodeResource(res, R.drawable.laser3);
        laser4 = BitmapFactory.decodeResource(res, R.drawable.laser4);
        laser5 = BitmapFactory.decodeResource(res, R.drawable.laser5);
        width = laser1.getWidth()/3;
        height = laser1.getHeight()/3;
        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);
        laser1 = Bitmap.createScaledBitmap(laser1, width, height, false);
        laser2 = Bitmap.createScaledBitmap(laser2, width, height, false);
        laser3 = Bitmap.createScaledBitmap(laser3, width, height, false);
        laser4 = Bitmap.createScaledBitmap(laser4, width, height, false);
        laser5 = Bitmap.createScaledBitmap(laser5, width, height, false);
    }
    int countlaserfasle=0;
    int countlasertrue=0;
    public Bitmap getLaser() {
        if (count <50) {
            isActive = false;
            count++;
            if(countlaserfasle==0){
                countlaserfasle++;
                return laser1;
            }

            countlaserfasle=0;
            return laser2;

            }
        else if(count<70)
        {
            count++;
            if(countlasertrue==0){
                countlasertrue++;
                return laser3;
            }
            if(countlasertrue==1){
                countlasertrue++;
                return laser4;
            }
            isActive = true;
            return laser5;

        }
        else
        {
            isActive = false;
            count=0;
            return laser1;
        }
    }
    public Rect getCollider() {
        return new Rect( x+100   , y +100, x + width-100, y + height-100);
    }
}
