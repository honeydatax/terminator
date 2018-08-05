package android3d.app.android;



import android.app.*;
import android.os.*;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.*;

import android.graphics.Paint.Style;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.widget.ImageView;


import java.lang.Object;
import java.util.Random;

import java.lang.Object;
import android.view.View;


import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import android.view.View.OnTouchListener;

public class MainActivity extends Activity
{
	int max=16;
	Bitmap bm ;
	ImageView mI;
	int w;
	int h,a,h1,w1,ws,hs,mx,my;
	int[]x=new int[max];
	
	Bitmap newImage;
	int yt;
	
	int hh;
	int rrr;
	int[] y=new int[max];
	int[] xxx=new int[max];
	Rect r1=new Rect();
	Rect r2=new Rect();

	Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
			for (int i=0;i<max;i++){
				xxx[i]++;}
			ddraw();
			timerHandler.postDelayed(this, 500);


		}};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		w1 =size.x;
		h1=size.y;


		mI = (ImageView)findViewById(R.id.ch);

		bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		rrr=bm.getWidth();
		yt =h1/2;
		mx=w1/3*bm.getWidth()/100;
		my=h1/2*bm.getHeight()/100;

		newImage = Bitmap.createBitmap(w1,h1,Bitmap.Config.ARGB_8888);

        Random r = new Random();
        
			

		for(int i=0;i<max;i++){
			x[i]=r.nextInt(w1);
			xxx[i]=0;

			y[i]=h1/2;


		}



		r1.bottom=bm.getHeight();
		r1.left=0;
		r1.right=bm.getWidth();
		r1.top=0;

		mI.setOnTouchListener(new OnTouchListener(){

				@Override
				public boolean onTouch(View v, MotionEvent event) {
int i;
					int action = event.getAction();
					int xx = (int) event.getX();
					int yy = (int) event.getY();

					
					for (i=max-1;i>-1;i--){
						if( action==MotionEvent.ACTION_DOWN && xx>x[i] && xx<x[i]+(mx/16*(xxx[i]+1)) && yy>y[i] && yy < y[i]+(my /16*(xxx[i]+1)  )){
							organiza(i);
							ddraw();
i=-1;
							tones(1000);
						}}
					return true;

				}});

		timerHandler.postDelayed(timerRunnable, 0);
    }

	public void ddraw(){



		Canvas c = new Canvas(newImage);
		Paint myPaint = new Paint();
		myPaint.setColor(Color.rgb(128, 128, 255));

		c.drawRect(0, 0,w1, yt, myPaint);
		myPaint.setColor(Color.rgb(128, 0,128));

		c.drawRect(0, yt,w1, h1, myPaint);
		for (int i=0;i<max;i++){
			
			if (xxx[i]>15)organiza(i);
			r2.top =y[i];
			r2.left=x[i];
			r2.right=x[i]+(mx/16*(xxx[i]+1));
			r2.bottom=y[i]+(my/16*(xxx[i]+1));
			c.drawBitmap(bm, r1, r2, null);
		}
		mI.setImageBitmap(newImage);



	}




	public void tones (double fr){
		int sr = 44100;

		int buffsize = AudioTrack.getMinBufferSize(sr,AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        // create an audiotrack object
        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
											   sr, AudioFormat.CHANNEL_OUT_MONO,
											   AudioFormat.ENCODING_PCM_16BIT, buffsize,
											   AudioTrack.MODE_STREAM);

        short samples[] = new short[buffsize];
        int amp = 10000;
        double twopi = 8.*Math.atan(1.);

        double ph = 0.0;

        // start audio
		audioTrack.play();

		// synthesis loop


		for(int i=0; i < buffsize; i++){
			samples[i] = (short) (amp*Math.sin(ph));
			ph += twopi*fr/sr;
		}
		audioTrack.write(samples, 0, buffsize);

		audioTrack.stop();
		audioTrack.release();


	}

	
	
	public void organiza(int pp){
		Random r = new Random();
	
		xxx[pp]=r.nextInt(w1);
		int gx=x[pp];
		int gy=y[pp];
		if(pp!=0){
		for(int ii=pp;ii>0;ii--){
			
			x[ii]=x[ii-1];
			y[ii]=y[ii-1];
			xxx[ii]=xxx[ii-1];
		}
		x[0]=gx;
		y[0]=gy;
		xxx[0]=0;
	}}

}




