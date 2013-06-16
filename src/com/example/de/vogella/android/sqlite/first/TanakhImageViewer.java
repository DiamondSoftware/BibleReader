
package com.example.de.vogella.android.sqlite.first;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
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

public class TanakhImageViewer extends ImageView {

    Matrix matrix;

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // Remember some things for zooming
    PointF last = new PointF();
    PointF start = new PointF();
    float minScale = 1f;
    float maxScale = 3f;
    float[] m;


    int viewWidth, viewHeight;
    static final int CLICK = 3;
    float saveScale = 1f;
	private float mScaleFactor = 1.f;

    protected float origWidth, origHeight;
    int oldMeasuredWidth, oldMeasuredHeight;


    ScaleGestureDetector mScaleDetector;

    Context context;

    public TanakhImageViewer(Context context) {
        super(context);
        sharedConstructing(context);
    }

    public TanakhImageViewer(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedConstructing(context);
    }
    
	// Finds verse associated with coordinates
	// Needs context - book & chapter
	private void findCoordinatesVerse(int x, int y)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		
		GridLayout grid = (GridLayout) inflater.inflate(R.layout.activity_test_database, null);				
		final ListView verseList = (ListView) grid.findViewById(android.R.id.list);		
		
		ListActivity myListActivity  = (ListActivity) verseList.getContext();

	    Log.d("scale factor", " = "  + mScaleFactor);
	    Log.d("save scale", " = "  + saveScale);

		
		
		// Column #1
		if (x  >= ((810 * 2)*mScaleFactor) && x <= ((1100*2)*mScaleFactor))
		{
			
			//Verse 1:
				if (y >= ((150 * 2) *mScaleFactor) && y <= ((270 * 2)* mScaleFactor))
					{
						myListActivity.setSelection(0);
						//draw a rectangle
						//Paint myPaint = new Paint();
					//myPaint.setColor(Color.rgb(0, 0, 0));
						//myPaint.setStrokeWidth(10);
						//localCanvas.drawRect(((810 * 2)*mScaleFactor), ((150 * 2) *mScaleFactor), ((1100 * 2)*mScaleFactor), ((270 * 2)* mScaleFactor), myPaint);
					
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
    
    private void sharedConstructing(Context context) {
        super.setClickable(true);
        this.context = context;
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        matrix = new Matrix();
        m = new float[9];
        setImageMatrix(matrix);
        setScaleType(ScaleType.MATRIX);

        setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mScaleDetector.onTouchEvent(event);
                PointF curr = new PointF(event.getX(), event.getY());

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    	last.set(curr);
                        start.set(last);
                        mode = DRAG;
                        break;
                        
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {
                            float deltaX = curr.x - last.x;
                            float deltaY = curr.y - last.y;
                            float fixTransX = getFixDragTrans(deltaX, viewWidth, origWidth * saveScale);
                            float fixTransY = getFixDragTrans(deltaY, viewHeight, origHeight * saveScale);
                            matrix.postTranslate(fixTransX, fixTransY);
                            fixTrans();
                            last.set(curr.x, curr.y);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        mode = NONE;
                        int xDiff = (int) Math.abs(curr.x - start.x);
                        int yDiff = (int) Math.abs(curr.y - start.y);
                        if (xDiff < CLICK && yDiff < CLICK)
                            performClick();
                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;
                        break;
                }
         
      		    findCoordinatesVerse((int) event.getX(), (int) event.getY());               
                setImageMatrix(matrix);
                invalidate();
                return true; // indicate event was handled
            }

        });
    }

    public void setMaxZoom(float x) {
        maxScale = x;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            mode = ZOOM;
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float mScaleFactor = detector.getScaleFactor();
            float origScale = saveScale;
            saveScale *= mScaleFactor;
            if (saveScale > maxScale) {
                saveScale = maxScale;
                mScaleFactor = maxScale / origScale;
            } else if (saveScale < minScale) {
                saveScale = minScale;
                mScaleFactor = minScale / origScale;
            }

            if (origWidth * saveScale <= viewWidth || origHeight * saveScale <= viewHeight)
                matrix.postScale(mScaleFactor, mScaleFactor, viewWidth / 2, viewHeight / 2);
            else
                matrix.postScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY());

            fixTrans();
            return true;
        }
    }

    void fixTrans() {
        matrix.getValues(m);
        float transX = m[Matrix.MTRANS_X];
        float transY = m[Matrix.MTRANS_Y];
        
        float fixTransX = getFixTrans(transX, viewWidth, origWidth * saveScale);
        float fixTransY = getFixTrans(transY, viewHeight, origHeight * saveScale);

        if (fixTransX != 0 || fixTransY != 0)
            matrix.postTranslate(fixTransX, fixTransY);
    }

    float getFixTrans(float trans, float viewSize, float contentSize) {
        float minTrans, maxTrans;

        if (contentSize <= viewSize) {
            minTrans = 0;
            maxTrans = viewSize - contentSize;
        } else {
            minTrans = viewSize - contentSize;
            maxTrans = 0;
        }

        if (trans < minTrans)
            return -trans + minTrans;
        if (trans > maxTrans)
            return -trans + maxTrans;
        return 0;
    }
    
    float getFixDragTrans(float delta, float viewSize, float contentSize) {
        if (contentSize <= viewSize) {
            return 0;
        }
        return delta;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        
        //
        // Rescales image on rotation
        //
        if (oldMeasuredHeight == viewWidth && oldMeasuredHeight == viewHeight
                || viewWidth == 0 || viewHeight == 0)
            return;
        oldMeasuredHeight = viewHeight;
        oldMeasuredWidth = viewWidth;

        if (saveScale == 1) {
            //Fit to screen.
            float scale;

            Drawable drawable = getDrawable();
            if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0)
                return;
            int bmWidth = drawable.getIntrinsicWidth();
            int bmHeight = drawable.getIntrinsicHeight();
            
            Log.d("bmSize", "bmWidth: " + bmWidth + " bmHeight : " + bmHeight);

            float scaleX = (float) viewWidth / (float) bmWidth;
            float scaleY = (float) viewHeight / (float) bmHeight;
            scale = Math.min(scaleX, scaleY);
            matrix.setScale(scale, scale);

            // Center the image
            float redundantYSpace = (float) viewHeight - (scale * (float) bmHeight);
            float redundantXSpace = (float) viewWidth - (scale * (float) bmWidth);
            redundantYSpace /= (float) 2;
            redundantXSpace /= (float) 2;

            matrix.postTranslate(redundantXSpace, redundantYSpace);

            origWidth = viewWidth - 2 * redundantXSpace;
            origHeight = viewHeight - 2 * redundantYSpace;
            setImageMatrix(matrix);
        }
        fixTrans();
    }
}