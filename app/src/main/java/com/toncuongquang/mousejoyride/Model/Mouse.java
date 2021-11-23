package com.toncuongquang.mousejoyride.Model;

import static com.toncuongquang.mousejoyride.View.GameView.screenRatioX;
import static com.toncuongquang.mousejoyride.View.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.toncuongquang.mousejoyride.R;
import com.toncuongquang.mousejoyride.View.GameView;

public class Mouse {
    public boolean isFly = false;
    public int x, y, width, heght;
    public int runCounter = 0;
    public int flyCounter = 0;
    public int dieCounter = 0;
    public Bitmap run1, run2, run3, run4;
    public Bitmap die1, die2;
    public Bitmap fly1, fly2;
    private GameView gameView;
    private int screenY;
    int toShoot = 0;
    public Mouse(GameView gameView, int screenY, Resources res) {
        this.gameView = gameView;
        this.screenY = screenY;
        run1 = BitmapFactory.decodeResource(res, R.drawable.mouse_run1);
        run2 = BitmapFactory.decodeResource(res, R.drawable.mouse_run2);
        run3 = BitmapFactory.decodeResource(res, R.drawable.mouse_run3);
        run4 = BitmapFactory.decodeResource(res, R.drawable.mouse_run4);
        width = run1.getWidth() / 3;
        heght = run1.getHeight() / 3;
        width = (int) (width * screenRatioX);
        heght = (int) (heght * screenRatioY);
        //scale lại object
        run1 = Bitmap.createScaledBitmap(run1, width, heght, false);
        run2 = Bitmap.createScaledBitmap(run2, width, heght, false);
        run3 = Bitmap.createScaledBitmap(run3, width, heght, false);
        run4 = Bitmap.createScaledBitmap(run4, width, heght, false);


        die1 = BitmapFactory.decodeResource(res, R.drawable.mouse_die_0);
        die2 = BitmapFactory.decodeResource(res, R.drawable.mouse_die_1);

        die1 = Bitmap.createScaledBitmap(die1, width, heght, false);
        die2 = Bitmap.createScaledBitmap(die2, width, heght, false);


        fly1 = BitmapFactory.decodeResource(res, R.drawable.mouse_fly1);
        fly2 = BitmapFactory.decodeResource(res, R.drawable.mouse_fly2);


        fly1 = Bitmap.createScaledBitmap(fly1, width, heght, false);
        fly2 = Bitmap.createScaledBitmap(fly2, width, heght, false);

        y = screenY * 3 / 4 - 50;
        x = (int) (100 * screenRatioX);

    }

    public Bitmap getRun() {
        if (isFly) {
            if (flyCounter == 0) {
                flyCounter++;
                return fly1;
            }
            flyCounter--;
            return fly2;
        }
        if (y == screenY * 3 / 4 - 50) {
            if (runCounter == 0) {
                runCounter++;
                return run1;
            }
            if (runCounter == 1) {
                runCounter++;
                return run2;
            }
            if (runCounter == 2) {
                runCounter++;
                return run3;
            }
            runCounter = 0;
            return run4;
        }
        return fly1;

    }

    public Rect getColider() {
        return new Rect(x, y, x + width, y + heght);
    }

    public Bitmap getDie() {
        if (dieCounter == 0) {
            dieCounter++;
            return die1;
        }
        dieCounter--;
        return die2;
    }
}
