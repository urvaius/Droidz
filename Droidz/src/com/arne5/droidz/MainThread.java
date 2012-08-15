/**
 * 
 */
package com.arne5.droidz;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;


/**
 * @author impaler
 *
 * The Main thread which contains the game loop. The thread must have access to 
 * the surface view and holder to trigger events every game tick.
 */
public class MainThread extends Thread {
	
	private static final String TAG = MainThread.class.getSimpleName();

	// Surface holder that can access the physical surface
	private SurfaceHolder surfaceHolder;
	// The actual view that handles inputs
	// and draws to the surface
	private MainGamePanel gamePanel;

	// flag to hold game state 
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	@Override
	public void run() {
		Canvas canvas;
		
		Log.d(TAG, "Starting game loop");
		while (running) {
			canvas = null;
			// try locking the canvas for exclusive pixel editin on the surface
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder){
					//update game state
					// draws the canvas panel
					this.gamePanel.onDraw(canvas);
				}
				}finally {
					// in case of an exception the surface is not left in an inconsitant state
					if (canvas != null){
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
			}//end finally
		}
		
	}
	
}
