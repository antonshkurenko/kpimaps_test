package me.cullycross.weather.utils;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import com.thbs.skycons.library.CloudFogView;
import com.thbs.skycons.library.CloudHvRainView;
import com.thbs.skycons.library.CloudMoonView;
import com.thbs.skycons.library.CloudRainView;
import com.thbs.skycons.library.CloudSnowView;
import com.thbs.skycons.library.CloudSunView;
import com.thbs.skycons.library.CloudThunderView;
import com.thbs.skycons.library.CloudView;
import com.thbs.skycons.library.MoonView;
import com.thbs.skycons.library.SkyconView;
import com.thbs.skycons.library.SunView;
import com.thbs.skycons.library.WindView;

/**
 * Created by: Anton Shkurenko (cullycross)
 * Project: KPImapsTest
 * Date: 11/3/15
 * Code style: SquareAndroid (https://github.com/square/java-code-styles)
 * Follow me: @tonyshkurenko
 */
public class WeatherViewUtils {

  private static final String CLOUD_FOG = "fog";
  private static final String CLOUD_HEAVY_RAIN = "cloud-heavy-rain";
  private static final String CLOUD_MOON = "partly-cloudy-night";
  private static final String CLOUD_RAIN = "rain";
  private static final String CLOUD_SNOW = "sleet";
  private static final String CLOUD_SUN = "partly-cloudy-day";
  private static final String CLOUD_THUNDER = "thunderstorm";
  private static final String CLOUD = "cloudy";
  private static final String MOON = "clear-night";
  private static final String SUN = "clear-day";
  private static final String WIND = "wind";

  public static SkyconView getWeatherView(Context ctx, String weather) {
    switch(weather) {
      case CLOUD:
        return new CloudView(ctx);
      case CLOUD_FOG:
        return new CloudFogView(ctx);
      case CLOUD_HEAVY_RAIN:
        return new CloudHvRainView(ctx);
      case CLOUD_MOON:
        return new CloudMoonView(ctx);
      case CLOUD_RAIN:
        return new CloudRainView(ctx);
      case CLOUD_SNOW:
        return new CloudSnowView(ctx);
      case CLOUD_SUN:
        return new CloudSunView(ctx);
      case CLOUD_THUNDER:
        return new CloudThunderView(ctx);
      case MOON:
        return new MoonView(ctx);
      case SUN:
        return new SunView(ctx);
      case WIND:
        return new WindView(ctx);
      default:
        /*throw new IllegalArgumentException("Wrong weather type " + weather);*/
        return new SkyconView(ctx); // nothing
    }
  }
}
