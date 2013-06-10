package com.example.de.vogella.android.sqlite.first;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class TanakhImageView extends ImageView	{

	public TanakhImageView(Context context) {
		super(context);
		setOnClickListener(theCommonListener);		
	}

	private OnClickListener theCommonListener = new OnClickListener()
	{
  		@Override
		public void onClick(View v) {  			
  			setImageResource(R.drawable.ic_launcher);
		}
	};

	public TanakhImageView(Context context, AttributeSet attrs) {
		  super(context, attrs);
		  setOnClickListener(theCommonListener);		

	}
	
	
	
}
