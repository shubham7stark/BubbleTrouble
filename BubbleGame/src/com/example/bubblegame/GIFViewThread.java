package com.example.bubblegame;

import android.graphics.Canvas;

public class GIFViewThread extends Thread {
	// 2
	static final long FPS = 10;
	// 2//
	private GIFView gifview;
	private boolean running = false;

	public GIFViewThread(GIFView gifview){
		this.gifview = gifview;
	}

	public void setRunning(boolean run){
		running = run;
	}

	@Override
	public void run(){

		long ticksPS = 1000 / FPS;
		long startTime;
		long sleepTime;
		while (running) {
			Canvas c = null;
			startTime = System.currentTimeMillis();
			try {
				c = gifview.getHolder().lockCanvas();

				synchronized (gifview.getHolder()) {
					gifview.update(startTime);
					gifview.draw(c);
				}
			} finally {
				if (c != null) {
					gifview.getHolder().unlockCanvasAndPost(c);
				}
			}
			sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
			try {
				if (sleepTime > 0)
					sleep(sleepTime);
				else
					sleep(10);
			} catch (Exception e){
			}
		}
	}
	
}

