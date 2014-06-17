package com.example.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchFilterActivity extends Activity {

	  EditText etSiteFilter;
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_search_filter);
	    SearchFilter searchFilter = (SearchFilter) getIntent().getParcelableExtra("filter");
	    setUpSpinner(R.id.spnImageSize, R.array.image_size_array, searchFilter.getImageSize());
	    setUpSpinner(R.id.spnColorFilter, R.array.color_filter_array, searchFilter.getColorFilter());
	    setUpSpinner(R.id.spnImageType, R.array.image_type_array, searchFilter.getImageType());
	    etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);

	    if (searchFilter.getSiteFilter() != null) {
	      etSiteFilter.setText(searchFilter.getSiteFilter());
	    }
	  }
	  
	  protected void setUpSpinner(int spnId, int resArrayId, String currentValue) {
	    Spinner spn = (Spinner) findViewById(spnId);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	       this, resArrayId, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spn.setAdapter(adapter);
	    int selectedIndex = 0;
	    for (int i = 0; i < adapter.getCount(); ++i) {
	      if (adapter.getItem(i).toString().equalsIgnoreCase(currentValue)) {
	        selectedIndex = i;
	        break;
	      }
	    }
	    spn.setSelection(selectedIndex);
	  }
	  
	  public String getSpinnerSelectedString(int spnId) {
	    Spinner spn = (Spinner) findViewById(spnId);
	    @SuppressWarnings("unchecked")
	    ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spn.getAdapter();
	    return adapter.getItem(spn.getSelectedItemPosition()).toString();
	  }
	  
	  public void onSaveChange(View v) {
	    Intent data = new Intent();
	      
	    SearchFilter filter = new SearchFilter(
	        getSpinnerSelectedString(R.id.spnImageSize), 
	        getSpinnerSelectedString(R.id.spnColorFilter),
	        getSpinnerSelectedString(R.id.spnImageType),
	        etSiteFilter.getText().toString());
	           
	    data.putExtra("saved_filter", filter);
	    setResult(RESULT_OK, data); 
	    finish();
	  }
	}