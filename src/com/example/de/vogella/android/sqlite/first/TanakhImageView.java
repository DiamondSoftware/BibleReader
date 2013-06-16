package com.example.de.vogella.android.sqlite.first;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;

public class TanakhImageView extends ImageView	{

	private ScaleGestureDetector mScaleDetector;
	private float mScaleFactor = 1.f;
	private Drawable mIcon;
	private Context localContext;
	private Canvas	localCanvas;
	
	
	public TanakhImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        //mIcon = context.getResources().getDrawable(R.drawable.joshua);
		mIcon = getDrawable();
		mIcon.setBounds(0, 0, mIcon.getIntrinsicWidth(), mIcon.getIntrinsicHeight());
        
		Log.d("width, height",mIcon.getIntrinsicWidth() + " " +  mIcon.getIntrinsicHeight());
        
		setOnClickListener(velumListener);		
		localContext = context;
	}
	public TanakhImageView(Context context) {
		this(context, null, 0);
	}

	public TanakhImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
		
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
	    @Override
	    public boolean onScale(ScaleGestureDetector detector) {
	        mScaleFactor *= detector.getScaleFactor();

	        // Don't let the object get too small or too large.
	        mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

	        invalidate();
	        return true;
	    }
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
	    Log.d("X & Y", "image location on screen: " +  coords[0] +  " " + coords[1]);	    
	}

	public boolean onTouchEvent(MotionEvent event) {
	    // Warning - we need to distill this from any action that is not actually a touch 
		// select; if user wanted to move the velum or resize....
		// I think we need to grab the ACTION_UP as well and work out 
		// if position has changed or not..
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
		    Log.d("X & Y", "image location on screen: " +  coords[0] +  " " + coords[1]);

			Log.d("X & Y", "touch event - down");

	        int eventX = (int) event.getX();
	        int eventY = (int) event.getY();
		    Log.d("X & Y", "event (x, y) = " +  eventX + " "+ eventY);
      
		    int xOnField = eventX - coords[0];
	        int yOnField = eventY - coords[1];
		    Log.d("X & Y", "on field (x, y) = "  +  xOnField +  " " + yOnField);

		   // findCoordinatesVerse(xOnField, yOnField);
		    findCoordinatesVerse(eventX, eventY);
		    
		}
		mScaleDetector.onTouchEvent(event);				
		return true;
	}	
	
	@Override
	public void onDraw(Canvas canvas) {
	    //super.onDraw(canvas);
	    //canvas.save();
	    canvas.scale(mScaleFactor, mScaleFactor);
	    mIcon.draw(canvas);
	    canvas.restore();
	    localCanvas = canvas;
	}
	
	// Finds verse associated with coordinates
	// Needs context - book & chapter
	private void findCoordinatesVerse(int x, int y)
	{
		LayoutInflater inflater = (LayoutInflater) localContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		
		GridLayout grid = (GridLayout) inflater.inflate(R.layout.activity_test_database, null);				
		final ListView verseList = (ListView) grid.findViewById(android.R.id.list);		
		
		ListActivity myListActivity  = (ListActivity) verseList.getContext();

	    Log.d("scale factor", " = "  + mScaleFactor);

		
		
		// Column #1
		if (x  >= ((810 * 2)*mScaleFactor) && x <= ((1100*2)*mScaleFactor))
		{
			
			//Verse 1:
				if (y >= ((150 * 2) *mScaleFactor) && y <= ((270 * 2)* mScaleFactor))
					{
						myListActivity.setSelection(0);
						//draw a rectangle
						Paint myPaint = new Paint();
						myPaint.setColor(Color.rgb(0, 0, 0));
						myPaint.setStrokeWidth(10);
						localCanvas.drawRect(((810 * 2)*mScaleFactor), ((150 * 2) *mScaleFactor), ((1100 * 2)*mScaleFactor), ((270 * 2)* mScaleFactor), myPaint);
					
					}
				else
				if (y >= ((270* 2) * mScaleFactor) && y <= ((440 * 2)*mScaleFactor))
				myListActivity.setSelection(1);
				else
				if (y >= ((440* 2)*mScaleFactor) && y <= ((530 * 2)*mScaleFactor))
				myListActivity.setSelection(2);
				else
				if (y >= ((530* 2)*mScaleFactor) && y <= ((680 * 2)*mScaleFactor))
				myListActivity.setSelection(3);
				else
				if (y >= ((680* 2)*mScaleFactor) && y <= ((820 * 2)*mScaleFactor))
				myListActivity.setSelection(4);
				else
				if (y >= ((820* 2)*mScaleFactor) && y <= ((930 * 2)*mScaleFactor))
				myListActivity.setSelection(5);
				else
				if (y >= ((930* 2)*mScaleFactor) && y <= ((1140 * 2)*mScaleFactor))
				myListActivity.setSelection(6);
				else
				if (y >= ((1140* 2)*mScaleFactor) && y <= ((1320 * 2)*mScaleFactor))
				myListActivity.setSelection(7);

		}
		else myListActivity.setSelection(0);			

	}
}
