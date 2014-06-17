package com.example.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	
	SearchFilter searchFilter = new SearchFilter("", "", "", "");
	public static final int CHANGE_FILTER = 1;
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("url", imageResult.getFullUrl());
				startActivity(i);
			}
		});
		gvResults.setOnScrollListener(new EndlessScrollListener() {

		      @Override
		      public void onLoadMore(int page, int totalItemsCount) {
		        // TODO Auto-generated method stub
		        customLoadMoreDataFromApi(totalItemsCount, false); 
		      }
				  
				});
			}
		@Override 
		public boolean onCreateOptionsMenu(Menu menu) {
		  MenuInflater inflater = getMenuInflater();
		  inflater.inflate(R.menu.search_menu, menu);
		  return true;
		}
	    
		public void onChangeSearchSettings(MenuItem item) {
		    Intent i = new Intent(getApplicationContext(), SearchFilterActivity.class);
		    i.putExtra("filter", searchFilter);
		    startActivityForResult(i, CHANGE_FILTER);
			}
	
	

	private void setupViews() {
		// TODO Auto-generated method stub
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
	}
	
	public void onImageSearch(View v) {
		  customLoadMoreDataFromApi(0, true);
		}
		
		
		
	//for pulling more data into the adapter from api
	  public void customLoadMoreDataFromApi(int offset, boolean clear) {
	    String query = etQuery.getText().toString();
	    final boolean clearResult = clear;
	    Toast.makeText(this, "Searching for : " + query, Toast.LENGTH_SHORT).show();
	    Log.d("DEBUG", String.valueOf(offset));
	    AsyncHttpClient client = new AsyncHttpClient();
	client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&v=1.0&start="+offset+ searchFilter.toParamString() +"&q="+Uri.encode(query),
					new JsonHttpResponseHandler() {
	    @Override
	    public void onSuccess(JSONObject response) {
	      JSONArray imageJsonResult = null;
	      try {
	        imageJsonResult = response.getJSONObject("responseData").getJSONArray("results");
	        if (clearResult) {
	          imageResults.clear();
	        }
	        imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResult));
	 //       Log.d("DEBUG", imageResults.toString());
	      } catch (JSONException e) {
	        e.printStackTrace();
	      }
	    }
	  });   
	}
		 @Override
		   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		     if (resultCode == RESULT_OK && requestCode == CHANGE_FILTER) {
		       searchFilter = data.getParcelableExtra("saved_filter");
		     }
		   }
}
