package com.example.bubblegame;

import android.graphics.Canvas;

public class SurfaceColorBallThread extends Thread {
	// 2
	static long FPS = 10;
	// 2//
	private SurfacePanel SurfacePanel;
	private boolean running = false;

	public SurfaceColorBallThread(SurfacePanel SurfacePanel){
		this.SurfacePanel = SurfacePanel;
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
				c = SurfacePanel.getHolder().lockCanvas();

				synchronized (SurfacePanel.getHolder()) {
					SurfacePanel.update(startTime);
					SurfacePanel.draw(c);
				}
			} finally {
				if (c != null) {
					SurfacePanel.getHolder().unlockCanvasAndPost(c);
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

