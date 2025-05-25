package com.sds.mdg.bubbletrouble;

import android.graphics.Canvas;


public class BubbleThread extends Thread {
	static final long FPS = 10;
	
	private SurfacePanel SurfacePanel;
	private boolean running = false;

	public BubbleThread(SurfacePanel SurfacePanel){
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

				synchronized (SurfacePanel.getHolder()){
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
