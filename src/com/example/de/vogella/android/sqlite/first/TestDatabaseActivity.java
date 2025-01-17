package com.example.de.vogella.android.sqlite.first;

import java.util.List;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class TestDatabaseActivity extends ListActivity {
  private CommentsDataSource datasource;
  TanakhImageView image;
  ArrayAdapter<Comment> adapter;
  
  @Override
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_database);
    datasource = new CommentsDataSource(this);
    datasource.open();
    List<Comment> values = datasource.getAllComments();

    // Use the SimpleCursorAdapter to show the
    // elements in a ListView
    adapter = new ArrayAdapter<Comment>(this, android.R.layout.simple_list_item_1, values);
    setListAdapter(adapter);
  
  }

  public void onClick(View view) 
  {
	@SuppressWarnings("unchecked")
    ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
     switch (view.getId()) 
    {   
    
    /*case R.id.add:
      String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
      int nextInt = new Random().nextInt(3);
      // Save the new comment to the database
      comment = datasource.createComment(comments[nextInt]);
      adapter.add(comment);
      image = (ImageView) findViewById(R.id.imageView1);
      image.setImageResource(R.drawable.joshua);
      break;
    case R.id.delete:
      if (getListAdapter().getCount() > 0) {
        comment = (Comment) getListAdapter().getItem(0);
        datasource.deleteComment(comment);
        adapter.remove(comment);
      }
      break;
    
    case R.id.list:
	    {
	       	setSelection (2); 
	    }   
    break;
    
    case R.id.imageView1:
    	{
    		setSelection (2); 
    	}     
    break;   
    */
    }
    adapter.notifyDataSetChanged();
  }

  @Override
  protected void onResume() {
    datasource.open();
    super.onResume();
  }

  @Override
  protected void onPause() {
    datasource.close();
    super.onPause();
  }
}
