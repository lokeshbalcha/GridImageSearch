package com.example.gridimagesearch;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class SearchFilter implements Parcelable{
	  private String imageSize = null;
	  private String colorFilter = null;
	  private String imageType = null;
	  private String siteFilter = null;
	  
	  public SearchFilter(String size, String color, String type, String site) {
	    imageSize = size;
	    colorFilter = color;
	    imageType = type;
	    siteFilter = site;
	  }
	  
	  private SearchFilter(Parcel source) {
	    imageSize = source.readString();
	    colorFilter = source.readString();
	    imageType = source.readString();
	    siteFilter = source.readString();
	  }
	  
	  public static final Parcelable.Creator<SearchFilter> CREATOR
	      = new Parcelable.Creator<SearchFilter> () {

	      @Override
	      public SearchFilter createFromParcel(Parcel source) {
	        return new SearchFilter(source);
	      }

	      @Override
	      public SearchFilter[] newArray(int size) {
	        return new SearchFilter[size];
	      }
	  };

	 
	  @Override
	  public int describeContents() {
	    // TODO Auto-generated method stub
	    return 0;
	  }

	  @Override
	  public void writeToParcel(Parcel dest, int flags) {
	    // TODO Auto-generated method stub
	    dest.writeString(imageSize);
	    dest.writeString(colorFilter);
	    dest.writeString(imageType);
	    dest.writeString(siteFilter);
	  }
	  
	  public String getImageSize() {
	    return imageSize;
	  }

	  public void setImageSize(String imageSize) {
	    this.imageSize = imageSize;
	  }

	  public String getColorFilter() {
	    return colorFilter;
	  }

	  public void setColorFilter(String colorFilter) {
	    this.colorFilter = colorFilter;
	  }

	  public String getImageType() {
	    return imageType;
	  }

	  public void setImageType(String imageType) {
	    this.imageType = imageType;
	  }

	  public String getSiteFilter() {
	    return siteFilter;
	  }

	  public void setSiteFilter(String siteFilter) {
	    this.siteFilter = siteFilter;
	  }
	  
	  public String toParamString() {
	    StringBuilder builder = new StringBuilder();
	    if (!imageType.equals("all")) {
	      builder.append("&imgtype=");
	      builder.append(imageType);
	    }
	    
	    if (!imageSize.equals("all")) {
	      builder.append("&imgsz=");
	      builder.append(imageSize);
	    }
	    
	    if (!colorFilter.equals("all")) {
	      builder.append("&imgcolor=");
	      builder.append(colorFilter);
	    }
	    
	    if (!siteFilter.isEmpty()) {
	      builder.append("&as_sitesearch=");
	      builder.append(Uri.encode(siteFilter));
	    }
	    return builder.toString();
	  }
	}
