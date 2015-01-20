package com.example.bubblegame;

import android.graphics.Canvas;

public class FinishGameThread extends Thread {
	// 2
	static final long FPS = 10;
	// 2//
	private FinishGameBoard FinishGameBoard;
	private boolean running = false;

	public FinishGameThread(FinishGameBoard FinishGameBoard){
		this.FinishGameBoard = FinishGameBoard;
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
				c = FinishGameBoard.getHolder().lockCanvas();

				synchronized (FinishGameBoard.getHolder()) {
				//	FinishGameBoard.update(startTime);
					FinishGameBoard.draw(c);
				}
			} finally {
				if (c != null) {
					FinishGameBoard.getHolder().unlockCanvasAndPost(c);
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

