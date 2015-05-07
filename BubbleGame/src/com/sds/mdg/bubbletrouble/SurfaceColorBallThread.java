package com.sds.mdg.bubbletrouble;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.Log;

public class SurfaceColorBallThread extends Thread {
	// 2
	static long FPS = 10;
	// 2//
	private SurfacePanel SurfacePanel;
	protected boolean running = false;
    
	public SurfaceColorBallThread(SurfacePanel SurfacePanel){
		this.SurfacePanel = SurfacePanel;
	}

	public void setRunning(boolean run){
		running = run;
	}

	@Override
	public void run(){

		long ticksPS = 500 / FPS;
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

