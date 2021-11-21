package com.toncuongquang.mousejoyride.View;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.toncuongquang.mousejoyride.GameActivity;
import com.toncuongquang.mousejoyride.Model.Background;
import com.toncuongquang.mousejoyride.Model.Coin;
import com.toncuongquang.mousejoyride.Model.Laser;
import com.toncuongquang.mousejoyride.Model.Mouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver;
    private int sceenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private GameActivity gameActivity;
    private Background background;
    private Mouse mouse;
    private List<Coin> coinList;
    private List<Laser> laserList;
    Random random;
    Random randomLaser;

    public GameView(GameActivity gameActivity, int screenX, int screenY) {
        super(gameActivity);
        this.gameActivity = gameActivity;
        this.sceenX = screenX;
        this.screenY = screenY;
        coinList = new ArrayList<>();
        laserList = new ArrayList<>();
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background = new Background(screenX, screenY, getResources());

        mouse = new Mouse(this, screenY, getResources());

        paint = new Paint();
        for (int i = 0; i < 10; i++) {
            Coin coin = new Coin(getResources());
            coinList.add(coin);
        }

        for (int i = 0; i < 5; i++) {
            Laser laser = new Laser(getResources());
            laserList.add(laser);
        }
        random = new Random();
        randomLaser = new Random();
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        background.x -= 10 * screenRatioX;

        if (mouse.isFly) {
            mouse.y -= 10 * screenRatioY;
        } else {
            mouse.y += 15 * screenRatioY;
        }
        if (mouse.y > screenY * 3 / 4 - 50) {
            mouse.y = screenY * 3 / 4 - 50;
        }

        for (Coin coin : coinList) {
            coin.x -= coin.speed;
            if (coin.x + coin.width < 0) {


                if (coin.speed < 10 * screenRatioX) {
                    coin.speed = (int) (10 * screenRatioX);
                }
                coin.x = sceenX + random.nextInt(5000);
                coin.y = random.nextInt(screenY * 3 / 4 - 50);
            }
        }
        for (Laser laser : laserList) {
            laser.x -= laser.speed;
            if (laser.x + laser.width < 0) {

                if (laser.speed < 10 * screenRatioX) {
                    laser.speed = (int) (10 * screenRatioX);
                }
                laser.x = sceenX + randomLaser.nextInt(5000);
                laser.y = randomLaser.nextInt(screenY * 3 / 4 - 50);
            }
        }
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background.background, background.x, background.y, paint);
            canvas.drawBitmap(background.background, background.x + background.background.getWidth(), background.y, paint);
            canvas.drawBitmap(background.background, background.x + background.background.getWidth() * 2, background.y, paint);
            canvas.drawBitmap(background.background, background.x + background.background.getWidth() * 3, background.y, paint);
            canvas.drawBitmap(background.background, background.x + background.background.getWidth() * 4, background.y, paint);


            canvas.drawBitmap(mouse.getRun(), mouse.x, mouse.y, paint);

            for (Coin coin : coinList) {
                canvas.drawBitmap(coin.getCoin(), coin.x, coin.y, paint);
            }
            for (Laser laser : laserList) {
                canvas.drawBitmap(laser.getLaser(), laser.x, laser.y, paint);
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < sceenX / 2) {
                    mouse.isFly = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                mouse.isFly = false;
        }


        return true;
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
