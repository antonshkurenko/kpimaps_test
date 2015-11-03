package me.cullycross.weather.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import me.cullycross.weather.pojos.Day;
import me.cullycross.weather.utils.DayDeserializer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by: Anton Shkurenko (cullycross)
 * Project: KPImapsTest
 * Date: 11/3/15
 * Code style: SquareAndroid (https://github.com/square/java-code-styles)
 * Follow me: @tonyshkurenko
 */
public class RestClient {

  private static final String BASE_URL = "https://api.forecast.io/";

  private final Retrofit mRetrofit;

  public RestClient() {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .registerTypeAdapter(new TypeToken<List<Day>>() {
        }.getType(), new DayDeserializer())
        .excludeFieldsWithoutExposeAnnotation()
        .create();

    mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
  }

  public WeatherApi createWeatherApi() {
    return mRetrofit.create(WeatherApi.class);
  }

  public interface WeatherApi {

    @GET("forecast/370e49f901a90f85c079e0511cf6c675/{lat},{lng}") Observable<List<Day>> getWeather(
        @Path("lat") double lat, @Path("lng") double lng);
  }
}
