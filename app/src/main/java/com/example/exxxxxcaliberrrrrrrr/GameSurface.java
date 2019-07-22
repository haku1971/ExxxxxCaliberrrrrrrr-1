package com.example.exxxxxcaliberrrrrrrr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Chronometer;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;


    private final List<ChibiCharacter> chibiList = new ArrayList<ChibiCharacter>();
    private final List<Explosion> explosionList = new ArrayList<Explosion>();

    private static final int MAX_STREAMS = 100;
    private int soundIdExplosion;
    private int soundIdBackground;

    private boolean soundPoolLoaded;
    private SoundPool soundPool;

    private ChibiCharacter chibiMain;

    private long startTime;
    private long endTime;

    private Context context;
    private String score;
    public GameSurface(Context context) {
        super(context);

        // Đảm bảo Game Surface có thể focus để điều khiển các sự kiện.
        this.setFocusable(true);

        // Sét đặt các sự kiện liên quan tới Game.
        this.getHolder().addCallback(this);
        this.initSoundPool();
        this.context=context;
    }

    private void initSoundPool() {
        // Với phiên bản Android API >= 21
        if (Build.VERSION.SDK_INT >= 21) {

            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        // Với phiên bản Android API < 21
        else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }


        // Sự kiện SoundPool đã tải lên bộ nhớ thành công.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPoolLoaded = true;
                playSoundBackground();
            }
        });

        // Tải file nhạc tiếng nổ (background.mp3) vào SoundPool.
        this.soundIdBackground = this.soundPool.load(this.getContext(), R.raw.summoningcircle, 1);

        // Tải file nhạc tiếng nổ (explosion.wav) vào SoundPool.
        this.soundIdExplosion = this.soundPool.load(this.getContext(), R.raw.gun1, 1);


    }

    public void playSoundExplosion() {
        if (this.soundPoolLoaded) {
            float leftVolumn = 0.8f;
            float rightVolumn = 0.8f;

            this.soundPool.play(this.soundIdExplosion, leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    public void playSoundBackground() {
        if (this.soundPoolLoaded) {
            float leftVolumn = 0.8f;
            float rightVolumn = 0.8f;
            this.soundPool.play(this.soundIdBackground, leftVolumn, rightVolumn, 1, -1, 1f);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            int x = (int) event.getX();
            int y = (int) event.getY();

            Iterator<ChibiCharacter> iterator = this.chibiList.iterator();

            //Di chuyển của quái
//            for(ChibiCharacter chibi: chibiList) {
//                int movingVectorX = chibiMain.getX() ;
//                int movingVectorY = chibiMain.getY() ;
//                chibi.setMovingVector(movingVectorX, movingVectorY);
//            }

            //Di chuyển của nhân vật chính
            int movingVectorX = x - chibiMain.getX();
            int movingVectorY = y - chibiMain.getY();
            chibiMain.setMovingVector(movingVectorX, movingVectorY);

            return true;
        }
        return false;
}

    private int speed = 5;
    private int frame = speed;
    private boolean trigger = false;
    private boolean isExplosion = false;

    public void update() {

        if(trigger == false) {
            //update main
            chibiMain.update();
            if (frame == speed) {
                chibiMain.update_canvas();
            }
            //update quái
            for (ChibiCharacter chibi : chibiList) {
                chibi.update();

                if (frame == speed) {
                    chibi.update_canvas();

                }
            }
            if (frame == speed) {
                frame = 0;
            }
            frame++;

        }
        //update hiệu ứng nổ
        for (Explosion explosion : this.explosionList) {
            explosion.update();

        }


        Iterator<Explosion> iterator = this.explosionList.iterator();

        while (iterator.hasNext()) {
            Explosion explosion = iterator.next();
            if (explosion.isFinish()) {

                iterator.remove();

                //Dừng game
                this.gameThread.setRunning(false);

                endTime=System.nanoTime();
                score = endTime-startTime+"";
                //dua ve trang ket   qua
                MainActivity.callMe(score);
            }
        }

        //Game over
        int x = chibiMain.getX()+(chibiMain.getWidth()/2);
        int y = chibiMain.getY()+(chibiMain.getHeight()/2);

        Iterator<ChibiCharacter> iterator2 = this.chibiList.iterator();

        // Kiểm tra xem có click vào nhân vật nào không.
        while (iterator2.hasNext()) {
            ChibiCharacter chibi = iterator2.next();
            if (chibi.getX() < x && x < chibi.getX() + chibi.getWidth()
                    && chibi.getY() < y && y < chibi.getY() + chibi.getHeight()) {
                trigger = true;
                // Loại bỏ nhân vật Game hiện tại ra khỏi iterator và list.
                iterator2.remove();


                // Tạo mới một đối tượng Explosion.
                Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.explosion1a);
                Explosion explosion = new Explosion(this, bitmap, chibiMain.getX() - 250, chibiMain.getY() - 250);
                if(isExplosion==false) {
                    this.explosionList.add(explosion);
                    isExplosion=true;
                }

            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        chibiMain.draw(canvas);
        for (ChibiCharacter chibi : chibiList) {
            chibi.draw(canvas);
        }
        for (Explosion explosion : this.explosionList) {
            explosion.draw(canvas);

        }
    }


    // Thi hành phương thức của interface SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Bitmap MainChar = BitmapFactory.decodeResource(this.getResources(), R.drawable.saber);
        chibiMain = new ChibiCharacter(this, MainChar, 1000, 1000);

        Random randomChar = new Random();
        Random randomX = new Random();
        Random randomY = new Random();
        for (int j = 0; j < 10; j++) {
            int c = randomChar.nextInt(6);
            Bitmap chibiBitmap = null;
            switch (c) {
                case 0:
                    chibiBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.rider);
                    break;
                case 1:
                    chibiBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.bluehair);
                    break;
                case 2:
                    chibiBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.masteryi);
                    break;
                case 3:
                    chibiBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.rider2);
                    break;
                case 4:
                    chibiBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.witch);
                    break;
                case 5:
                    chibiBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.yeallowhair);
                    break;

            }
            int x = randomX.nextInt(1024);
            int y = randomX.nextInt(2048);
            ChibiCharacter chibi = new ChibiCharacter(this, chibiBitmap, x, y);
            this.chibiList.add(chibi);

        }
        for(ChibiCharacter charac: chibiList) {
            Random RandomVector = new Random();
            int movingVectorX = RandomVector.nextInt(1024) ;
            int movingVectorY = RandomVector.nextInt(2048) ;
            charac.setMovingVector(movingVectorX, movingVectorY);
        }
        this.gameThread = new GameThread(this, holder,context);
        this.gameThread.setRunning(true);
        this.gameThread.start();

        startTime=System.nanoTime();

    }

  // Thi hành phương thức của interface SurfaceHolder.Callback
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//            ((MainActivity) context).callMe(score);
        }


    // Thi hành phương thức của interface SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//        boolean retry = true;
//        while (retry) {
//            try {
//                this.gameThread.setRunning(false);
//
//                // Luồng cha, cần phải tạm dừng chờ GameThread kết thúc.
//                this.gameThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            retry = true;
//        }
    }
}
