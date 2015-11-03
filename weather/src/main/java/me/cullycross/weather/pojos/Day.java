package me.cullycross.weather.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by: Anton Shkurenko (cullycross)
 * Project: KPImapsTest
 * Date: 11/3/15
 * Code style: SquareAndroid (https://github.com/square/java-code-styles)
 * Follow me: @tonyshkurenko
 */
public class Day {
  @SerializedName("time") @Expose private long mDate;
  @SerializedName("temperatureMin") @Expose private float mMin;
  @SerializedName("temperatureMax") @Expose private float mMax;
  @SerializedName("icon") @Expose private String mIcon;

  public long getDate() {
    return mDate;
  }

  public float getMin() {
    return mMin;
  }

  public float getMax() {
    return mMax;
  }

  public String getIcon() {
    return mIcon;
  }
}
