package com.toncuongquang.mousejoyride.Model;

import static com.toncuongquang.mousejoyride.View.GameView.screenRatioX;
import static com.toncuongquang.mousejoyride.View.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.toncuongquang.mousejoyride.R;

public class Laser {
    public Bitmap laser1, laser2;
    public int speed = 10;
    public boolean get = true;
    public int x, y, width, height;
    public boolean isActive = true;

    public Laser(Resources res) {
        laser1 = BitmapFactory.decodeResource(res, R.drawable.laser_on);
        laser2 = BitmapFactory.decodeResource(res, R.drawable.laser_off);
        width = 50;
        height = 200;
        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);
        laser1 = Bitmap.createScaledBitmap(laser1, width, height, false);
        laser2 = Bitmap.createScaledBitmap(laser2, width, height, false);

    }

    public Bitmap getLaser() {
        if (isActive) {
            isActive = false;
            return laser1;
        } else {
            isActive = true;
            return laser2;

        }
    }

    public Rect getCollider() {
        return new Rect(x, y, x + width, y + height);
    }
}
