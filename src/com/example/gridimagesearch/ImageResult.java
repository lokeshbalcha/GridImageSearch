package com.example.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;

	
	public class ImageResult {
		private String fullUrl;
		private String thumbUrl;
		
		public ImageResult(JSONObject json) {
			try {
				this.fullUrl = json.getString("url");
				this.thumbUrl = json.getString("tbUrl");
			} catch (JSONException e) {
				this.fullUrl = null;
				this.thumbUrl = null;
			}
		}
		public String getFullUrl() {
			return this.fullUrl;
		}
		
		public String getThumbUrl() {
			return this.thumbUrl;
		}
		
		public String toString() {
			return this.thumbUrl;
		}
		public static ArrayList<? extends ImageResult> fromJSONArray(
				JSONArray array) {
			ArrayList<ImageResult> results = new ArrayList<ImageResult>();
			for (int x = 0; x < array.length(); x++) {
				try {
					results.add(new ImageResult(array.getJSONObject(x)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return results;
		}
		  public int describeContents() {
		    // TODO Auto-generated method stub
		    return 0;
		  }

		  public void writeToParcel(Parcel dest, int flags) {
		    dest.writeString(fullUrl);
		    dest.writeString(thumbUrl);
	}
	}