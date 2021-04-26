package com.example.tankoyunu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.hardware.SensorEvent;
import android.opengl.Matrix;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class OyunEkrani extends AppCompatActivity {

    Button buttonGeri, buttonSag, buttonSol, buttonileri;
    ImageView tank;

    boolean direksiyonSol = false;
    boolean direksiyonSag = false;
    boolean hizlanma = false;
    boolean yavaslama = false;
    ImageView dusman1;
    ImageView dusman2;
    TextView skor;

    float hiz=0;
    float hiz1 = 0;

    private ConstraintLayout cl;
    private int ekranGenisligi;
    private int ekranYuksekligi;
    private int tankGenisligi;
    private int tankYuksekligi;
    private int tankY;
    private int tankX;
    private int dusman1Y;
    private int dusman2Y;
    private int dusman1X;
    private int dusman2X;
    private int puan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun_ekrani);



        buttonileri = (Button)findViewById(R.id.buttonileri);
        buttonGeri = (Button)findViewById(R.id.buttonGeri);
        buttonSag = (Button)findViewById(R.id.buttonSag);
        buttonSol = (Button)findViewById(R.id.buttonSol);
        tank = (ImageView) findViewById(R.id.tank);
        dusman1 = (ImageView) findViewById(R.id.dusman1);
        dusman2 = (ImageView)findViewById(R.id.dusman2);
        skor = findViewById(R.id.skor);


        cl = findViewById(R.id.cl);
        //Cisimleri ekranın dışına çıkarma
        dusman1.setY(+20);
        dusman2.setY(+20);
        dusman1.setX(+20);
        dusman2.setX(+20); // burayı düzenle

        final Timer timer = new Timer(10000000,1);
        final YonlendirmeTimer timer2 = new YonlendirmeTimer(1000000,1);


        tank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
                timer2.start();

            }
        });


        buttonileri.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    System.out.println("ekrana basıldı");
                    hizlanma = true;
                }
                else if (event.getAction()==MotionEvent.ACTION_UP){
                    System.out.println("ekran bırakıldı");
                    hizlanma = false;
                }

                return false;

            }
        });
        buttonGeri.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    System.out.println("erkana basıldı");
                    yavaslama = true;
                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    System.out.println("ekran bırakıldı");
                    yavaslama = false;
                }

                return false;
            }
        });
        buttonSol.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        direksiyonSol = true;
                        System.out.println("basıldı");
                        return true;
                    case MotionEvent.ACTION_UP:
                        direksiyonSol = false;
                        System.out.println("bırakıldı");
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
        buttonSag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    System.out.println("erkana basıldı");
                    direksiyonSag = true;
                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    System.out.println("ekran bırakıldı");
                    direksiyonSag = false;

                }
                return false;
            }
        });

    }
    class Timer extends CountDownTimer{
        public Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {


            if (hizlanma==true){

                hiz+=0.03;
                System.out.println("ne1");
                tank.animate().rotation(0);

            }if(hizlanma==false && hiz>0){

                hiz-=0.01;
                System.out.println("ne2");
                if(hiz==0){
                    hiz=0;
                }
            }
            if(yavaslama==true ) {

                hiz -= 0.03;
                tank.animate().rotation(180);

            }
            if(yavaslama==false && hiz<0){

                hiz+=0.01;
                System.out.println("ne4");
                if(hiz==0){
                    hiz=0;
                    System.out.println("ne3");
                }

            }

                tank.setY((tank.getY()-hiz));

            ekranDisiCikma();
            carpismaKontrol();
            dusmanHareket();
            dusman2Carpisma();

        }
        public void ekranDisiCikma(){

            tankY = (int) tank.getY();
            tankX = (int) tank.getX();
            tankGenisligi = tank.getWidth();
            tankYuksekligi = tank.getHeight();
            ekranYuksekligi = cl.getHeight();
            ekranGenisligi = cl.getWidth();
            if(tankY <=0){
                tankY=0;
            }if(tankY >= ekranYuksekligi- tankYuksekligi){
                tankY = ekranYuksekligi - tankYuksekligi;
            }

            if(tankX <=0){
                tankX=0;
            }if(tankX >= ekranGenisligi- tankGenisligi){
                tankX = ekranGenisligi - tankGenisligi;
            }


            tank.setY(tankY);
            tank.setX(tankX);

        }
        public void dusmanHareket(){
            ekranYuksekligi = cl.getHeight();
            ekranGenisligi = cl.getWidth();

            dusman1Y +=2;
            dusman2Y +=1;
            if(dusman1Y>ekranYuksekligi){
                dusman1Y = -50 ;
                dusman1X = (int) Math.floor(Math.random() * ekranGenisligi);
            }
            if(dusman2Y>ekranYuksekligi){
                dusman2Y = 0;
                dusman2X = (int) Math.floor(Math.random()*ekranGenisligi)  ;
            }
            dusman1.setX(dusman1X);
            dusman1.setY(dusman1Y);
            dusman2.setY(dusman2Y);
            dusman2.setX(dusman2X);
        }

        @Override
        public void onFinish() {
            System.out.println("timer durdu");


        }
    }

    class YonlendirmeTimer extends CountDownTimer{
        public YonlendirmeTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            if(direksiyonSag==true){
                hiz1+=0.03;
                tank.animate().rotation(+90);

            }else if(direksiyonSag==false && hiz1>0){
                hiz1-=0.01;
                System.out.println("ne2");
                if(hiz1==0){
                    hiz1=0;
                }
            }else if(direksiyonSol==true){
                hiz1 -= 0.03;
                tank.animate().rotation(-90);

            }else if (direksiyonSol==false){
                hiz1+=0.01;
                System.out.println("ne4");
                if(hiz1==0){
                    hiz1=0;
                    System.out.println("ne3");
                }
            }
            tank.setX((tank.getX()+hiz1));


        }

        @Override
        public void onFinish() {
            System.out.println("timer durdu");
        }
    }
    public void carpismaKontrol(){
        int dusman1MerkezX = dusman1X + dusman1.getWidth();
        int dusman1MerkezY = dusman1Y + dusman1.getHeight();

        if ( tankY <= dusman1MerkezY &&dusman1MerkezY <= tankY+tankYuksekligi &&tankX<=dusman1MerkezX
                && dusman1MerkezX<=tankX+tankGenisligi+tankYuksekligi){
            dusman1Y = -10;
            puan += 10;
            dusman1X = (int) Math.floor(Math.random() * ekranGenisligi);

        }
        skor.setText(String.valueOf(puan));
        dusman1.setX(dusman1X);
        dusman1.setY(dusman1Y);

    }public void dusman2Carpisma(){
        int dusman2MeX = dusman2X + dusman2.getWidth();
        int dusman2MeY = dusman2Y + dusman2.getHeight();

        if ( tankY <= dusman2MeY &&dusman2MeY <= tankY+tankYuksekligi &&tankX<=dusman2MeX
                && dusman2MeX<=tankX+tankGenisligi+tankYuksekligi){
            puan +=10;
            dusman2Y = -20;
            dusman2X = (int) Math.floor(Math.random()*ekranGenisligi) ;
        }
        skor.setText(String.valueOf(puan));
        dusman2.setY(dusman2Y);
        dusman2.setX(dusman2X);
    }

}
