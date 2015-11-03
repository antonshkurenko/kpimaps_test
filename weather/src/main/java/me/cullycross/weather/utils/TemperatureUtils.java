package me.cullycross.weather.utils;

/**
 * Created by: Anton Shkurenko (cullycross)
 * Project: KPImapsTest
 * Date: 11/3/15
 * Code style: SquareAndroid (https://github.com/square/java-code-styles)
 * Follow me: @tonyshkurenko
 */
public class TemperatureUtils {

  private TemperatureUtils() {

  }

  public static float ftoC(float f) {
    return (f-32)*(5f/9f);
  }
}
