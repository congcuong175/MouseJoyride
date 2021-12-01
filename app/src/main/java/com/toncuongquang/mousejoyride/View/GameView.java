package com.toncuongquang.mousejoyride.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.toncuongquang.mousejoyride.GameActivity;
import com.toncuongquang.mousejoyride.MainActivity;
import com.toncuongquang.mousejoyride.Model.Background;
import com.toncuongquang.mousejoyride.Model.Bullet;
import com.toncuongquang.mousejoyride.Model.BulletDown;
import com.toncuongquang.mousejoyride.Model.Coin;
import com.toncuongquang.mousejoyride.Model.Laser;
import com.toncuongquang.mousejoyride.Model.LaserLanscape;
import com.toncuongquang.mousejoyride.Model.MagicBox;
import com.toncuongquang.mousejoyride.Model.Mouse;
import com.toncuongquang.mousejoyride.Model.Rocket;
import com.toncuongquang.mousejoyride.R;

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
    private List<LaserLanscape> laserLanscapeList;
    private List<Rocket> rocketList;
    private List<Bullet> bulletList;
    private List<BulletDown> bulletListtwo;
    private List<MagicBox> magicBoxList;
    Random random;
    Random randomLaser;
    Random randomRocket;
    Random randomBox;

    public static int point = 0;

    public GameView(GameActivity gameActivity, int screenX, int screenY) {
        super(gameActivity);
        this.gameActivity = gameActivity;
        this.sceenX = screenX;
        this.screenY = screenY;
        coinList = new ArrayList<>();
        laserList = new ArrayList<>();
        laserLanscapeList = new ArrayList<>();
        rocketList = new ArrayList<>();
        bulletList = new ArrayList<>();
        bulletListtwo = new ArrayList<>();
        magicBoxList = new ArrayList<>();
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background = new Background(screenX, screenY, getResources());

        mouse = new Mouse(this, screenY, getResources());

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);
        for (int i = 0; i < 10; i++) {
            Coin coin = new Coin(getResources());
            coinList.add(coin);
        }

        for (int i = 0; i < 3; i++) {
            Laser laser = new Laser(getResources());
            laserList.add(laser);
        }
        for (int i = 0; i < 3; i++) {
            LaserLanscape laser = new LaserLanscape(getResources());
            laserLanscapeList.add(laser);
        }
        for (int i = 0; i < 3; i++) {
            Rocket rocket = new Rocket(getResources());
            rocketList.add(rocket);
        }
        for (int i = 0; i < 3; i++) {
            MagicBox magicBox = new MagicBox(getResources());
            magicBoxList.add(magicBox);
        }
        random = new Random();
        randomLaser = new Random();
        randomRocket = new Random();
        randomBox = new Random();
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    List<Bullet> trashBullet = new ArrayList<>();
    List<BulletDown> trashBulletTwo = new ArrayList<>();

    private void update() {


        newBullet();
        newBulletTwo();
        background.x -= 10 * screenRatioX;


        if (mouse.isFly) {
            mouse.y -= 10 * screenRatioY;
            for (BulletDown bullet : bulletListtwo) {
                bullet.x = mouse.x - 75;
                bullet.y = mouse.y + (mouse.heght / 2) + 25;
            }

        } else {
            bulletListtwo.clear();
            mouse.y += 15 * screenRatioY;
        }
        if (mouse.y > screenY * 3 / 4 - 50) {
            mouse.y = screenY * 3 / 4 - 50;
        }
        for (LaserLanscape laser : laserLanscapeList) {
            laser.x -= laser.speed;
            //va chạm vs chuột

            if (laser.isActive) {
                if (Rect.intersects(laser.getCollider(), mouse.getColider())) {
                    isGameOver = true;
                }
            }
            if (laser.x + laser.width < 0) {

                if (laser.speed < 10 * screenRatioX) {
                    laser.speed = (int) (10 * screenRatioX);
                }
                laser.x = sceenX + randomLaser.nextInt(5000);
                laser.y = randomLaser.nextInt(screenY * 3 / 4 - 50);
            }
        }


        for (Rocket rocket : rocketList) {
            rocket.x -= rocket.speed;

            if (Rect.intersects(mouse.getColider(), rocket.getCollider()) && !rocket.isShoot) {
                rocket.isBoom = true;
                isGameOver = true;
            }
            if (rocket.x + rocket.width < 0) {

                if (rocket.speed < 10 * screenRatioX) {
                    rocket.speed = (int) (10 * screenRatioX);
                }
                rocket.x = sceenX + randomRocket.nextInt(5000);
                rocket.y = randomLaser.nextInt(screenY * 3 / 4 - 50);
            }

            for (Bullet bullet : bulletList) {
                if (bullet.x > sceenX) {
                    trashBullet.add(bullet);
                }
                bullet.x += bullet.speed;
                //xử lý chạm đạn vs rocket
                if (Rect.intersects(bullet.getCollider(), rocket.getCollider())) {
                    rocket.isBoom = true;
                    rocket.isShoot = true;
                }
            }
            if (rocket.x < -100) {
                rocket.isShoot = false;
                rocket.isBoom = false;
            }

        }
        for (Bullet bullet : trashBullet) {
            bulletList.remove(bullet);
        }


        for (Coin coin : coinList) {
            coin.x -= coin.speed;
            if (Rect.intersects(coin.getCollider(), mouse.getColider())) {
                coin.x -= 1000;
                point++;
            }
            if (coin.x + coin.width < 0) {
                if (coin.speed < 10 * screenRatioX) {
                    coin.speed = (int) (10 * screenRatioX);
                }
                coin.x = sceenX + random.nextInt(5000);
                coin.y = random.nextInt(screenY * 3 / 4 - 50);

            }
        }


        for (MagicBox magicBox : magicBoxList) {
            magicBox.x -= magicBox.speed;
            //va chạm vs chuột
            if (Rect.intersects(magicBox.getCollider(), mouse.getColider())) {
                sum += 10;
                magicBox.x -= 1000;
                Log.d("bullet", sum + "");

            }

            if (magicBox.x + magicBox.width < 0) {

                if (magicBox.speed < 10 * screenRatioX) {
                    magicBox.speed = (int) (10 * screenRatioX);
                }
                magicBox.x = sceenX + randomBox.nextInt(5000);
                magicBox.y = randomBox.nextInt(screenY * 3 / 4 - 50);
            }
        }
        for (Laser laser : laserList) {
            laser.x -= laser.speed;
            //va chạm vs chuột

            if (laser.isActive) {
                if (Rect.intersects(laser.getCollider(), mouse.getColider())) {
                    isGameOver = true;
                }
            }
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

            canvas.drawBitmap(background.background, background.x + background.background.getWidth() * 0, background.y, paint);
            canvas.drawBitmap(background.background, background.x + background.background.getWidth() * 1, background.y, paint);
            canvas.drawBitmap(background.background, background.x + background.background.getWidth() * 2, background.y, paint);
            canvas.drawBitmap(background.background, background.x + background.background.getWidth() * 3, background.y, paint);
            canvas.drawBitmap(background.background, background.x + background.background.getWidth() * 4, background.y, paint);
            canvas.drawBitmap(background.background, background.x + background.background.getWidth()*5, background.y, paint);
            canvas.drawBitmap(background.background, background.x + background.background.getWidth()*6, background.y, paint);
            canvas.drawBitmap(background.background, background.x + background.background.getWidth()*7, background.y, paint);
            canvas.drawBitmap(background.background, background.x + background.background.getWidth()*8, background.y, paint);


            canvas.drawBitmap(mouse.getRun(), mouse.x, mouse.y, paint);

            for (Coin coin : coinList) {
                canvas.drawBitmap(coin.getCoin(), coin.x, coin.y, paint);
            }
            for (Laser laser : laserList) {
                canvas.drawBitmap(laser.getLaser(), laser.x, laser.y, paint);
            }
            for (LaserLanscape laser : laserLanscapeList) {
                canvas.drawBitmap(laser.getLaser(), laser.x, laser.y, paint);
            }
            for (Rocket rocket : rocketList) {
                canvas.drawBitmap(rocket.getRocket(), rocket.x, rocket.y, paint);
            }
            for (Bullet bullet : bulletList) {
                canvas.drawBitmap(bullet.getbullet(), bullet.x, bullet.y, paint);
            }
            for (BulletDown bullet : bulletListtwo) {
                canvas.drawBitmap(bullet.getbullet(), bullet.x, bullet.y, paint);
            }
            for (MagicBox box : magicBoxList) {
                canvas.drawBitmap(box.getbox(), box.x, box.y, paint);
            }


            if (isGameOver) {//1
                isPlaying = false;
                canvas.drawBitmap(mouse.getDie(), mouse.x, mouse.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                waitPlay();
            } else {
                canvas.drawText(point + "", sceenX / 2f, 164, paint);
                getHolder().unlockCanvasAndPost(canvas);
            }

        }
    }

    public void waitPlay() {
        try {
            thread.sleep(4000);
            point = 0;
            //gameover
            //gameActivity.finish();
            gameActivity.startActivity(new Intent(gameActivity, GameActivity.class));


        } catch (InterruptedException e) {
            e.printStackTrace();
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


    int count = 0;
    int count2 = 0;
    int sum = 10;

    public void newBullet() {
        if (sum > 0) {
            if (count == 10) {
                Bullet bullet = new Bullet(getResources());
                bullet.x = mouse.x + mouse.width - 20;
                bullet.y = mouse.y + (mouse.heght / 2);
                bulletList.add(bullet);
                sum--;
                count = 0;
            }
            count++;
        }

    }

    public void newBulletTwo() {
        if (mouse.isFly) {
            //bulletListtwo.clear();
            if (bulletListtwo.size() < 2) {
                BulletDown bullet = new BulletDown(getResources());

                bulletListtwo.add(bullet);
            }


        }

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
