package com.example.de.vogella.android.sqlite.first;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class TanakhImageView extends ImageView	{

	public TanakhImageView(Context context) {
		super(context);
		//setOnClickListener(velumListener);		
	}

	private OnClickListener velumListener = new OnClickListener()
	{
  		@Override
		public void onClick(View v) {  			
  			
  			int[] values = new int[2]; 
            v.getLocationOnScreen(values);
            Log.d("X & Y",values[0]+" "+values[1]);
  				}
	};

	private int coords[] = new int[2];

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	    super.onWindowFocusChanged(hasFocus);

	    // Use onWindowFocusChanged to get the placement of
	    // the image because we have to wait until the image
	    // has actually been placed on the screen  before we
	    // get the coordinates. That makes it impossible to
	    // do in onCreate, that would just give us (0, 0).
	    getLocationOnScreen(coords);
	    //Log.d("X & Y", "image location on screen: " +  coords[0] +  " " + coords[1]);
	}

	public boolean onTouchEvent(MotionEvent event) {
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        Log.d("X & Y", "touch event - down");

	        int eventX = (int) event.getX();
	        int eventY = (int) event.getY();
		    Log.d("X & Y", "event (x, y) = " +  eventX + " "+ eventY);
      
		    int xOnField = eventX - coords[0];
	        int yOnField = eventY - coords[1];
		    Log.d("X & Y", "on field (x, y) = "  +  xOnField +  " " + yOnField);
	    }
	    return super.onTouchEvent(event);
	}	
	
	
	
	
	public TanakhImageView(Context context, AttributeSet attrs) {
		  super(context, attrs);
		  //setOnClickListener(velumListener);		

	}
	
	
	
}
